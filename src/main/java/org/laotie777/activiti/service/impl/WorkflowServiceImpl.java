package org.laotie777.activiti.service.impl;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.laotie777.activiti.dao.ILeaveBillDao;
import org.laotie777.activiti.entity.Employee;
import org.laotie777.activiti.entity.LeaveBill;
import org.laotie777.activiti.entity.WorkflowBean;
import org.laotie777.activiti.service.IWorkflowService;
import org.laotie777.activiti.utils.SpringWebUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 工作流Service
 */
@Service
public class WorkflowServiceImpl implements IWorkflowService {


    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(WorkflowServiceImpl.class);

	/**请假申请Dao*/
	@Autowired
	private ILeaveBillDao leaveBillDao;

    /**
     * 仓库Service 查询定义和部署信息
     */
	@Autowired
	private RepositoryService repositoryService;

    /**
     * 运行时Service 查询流程实例
     */
	@Autowired
	private RuntimeService runtimeService;

    /**
     * 任务Service 查询用户任务
     */
	@Autowired
	private TaskService taskService;


	@Autowired
	private FormService formService;

    /**
     * 历史Service 查询流程历史信息
     */
	@Autowired
	private HistoryService historyService;



	@Override
    @Transactional(readOnly = true)
	public void saveNewDeploye(File file, String filename) {
            logger.info("开始部署 => " + filename);
        try {
            repositoryService.createDeployment().addZipInputStream(new ZipInputStream(new FileInputStream(file))).name(filename).deploy();
        } catch (FileNotFoundException e) {
            logger.error(filename + " => 部署文件不存在");
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询部署列表
     * @return
     */
	@Override
    @Transactional(readOnly = true)
	public List<Deployment> findDeploymentList() {
	    /*查的是ACT_RE_DEPLOYMENT*/
		return repositoryService.createDeploymentQuery().orderByDeploymenTime().desc().list();
	}

    /**
     * 查询流程定义列表
     * @return
     */
	@Override
    @Transactional(readOnly = true)
	public List<ProcessDefinition> findProcessDefinitionList() {
		/*ACT_RE_PROCDEF*/
	    return repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionVersion().desc().list();
	}

    /**
     * 根据部署ID查看图像
     * @param deploymentId
     * @param imageName
     * @return
     */
	@Override
    @Transactional(readOnly = true)
	public InputStream findImageInputStream(String deploymentId, String imageName) {
        return repositoryService.getResourceAsStream(deploymentId,imageName);
	}

    /**
     * 根据定义ID删除流程
     * @param deploymentId
     */
	@Override
    @Transactional(rollbackFor = Exception.class)
	public void deleteProcessDefinitionByDeploymentId(String deploymentId) {
	    //删除部署的流程 并且删除所有流程实例
        repositoryService.deleteDeployment(deploymentId,true);
	}

    /**
     * 启动流程实例
     * @param leaveBillId
     */
	@Override
    @Transactional(rollbackFor = Exception.class)
	public void saveStartProcess(Long leaveBillId) {
        //查出请假单 并利用快照保存
        LeaveBill leaveBill = leaveBillDao.findOne(leaveBillId);
        //设置成启动状态
        leaveBill.setState(1);
        //根据JAVABEAN的类名启动对应的业务流程实例
        String key = leaveBill.getClass().getSimpleName();
        //设置流程变量
        HashMap<String,Object> vars = new HashMap<>();
        //从session中取得当前登录人
        Employee employee = SpringWebUtil.get();
        //"inputUser" => 流程图中指定的
        vars.put("inputUser",employee.getName());
        //设置流程实例和具体业务实例关联
        String businessId = key+":"+leaveBillId;
        vars.put("businessId",businessId);
        //运行时service 启动实例
        runtimeService.startProcessInstanceByKey(key,businessId,vars);
    }

    /**
     * 查询个人任务
     * @param name
     * @return
     */
	@Override
	public List<Task> findTaskListByName(String name) {
	    //利用任务Service查询
		return taskService.createTaskQuery().taskAssignee(name).list();
	}

    /**
     * 获取流程图里面的FormKey
     * @param taskId
     * @return
     */
	@Override
	public String findTaskFormKeyByTaskId(String taskId) {
        TaskFormData taskFormData = formService.getTaskFormData(taskId);
        return taskFormData.getFormKey();
	}

    /**
     * 根据任务ID查询请假单
     * @param taskId
     * @return
     */
	@Override
	public LeaveBill findLeaveBillByTaskId(String taskId) {
        LeaveBill one = null;
	    try{
            //先根据taskId查询到Task
            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            //再查询到流程实例
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
            //根据businessKey查询到请假单
            String businessKey = processInstance.getBusinessKey();
            String leaveBillId = businessKey.split(":")[1];
            one = leaveBillDao.findOne(Long.parseLong(leaveBillId));
        }catch (Exception e){
	        logger.error("请假单查询失败");
        }

        return one;

    }

    /**
     * 查询活动后面的连线
     * @param taskId
     * @return
     */
	@Override
	public List<String> findOutComeListByTaskId(String taskId) {

	   /* List<String> names = new ArrayList<>();

	    //查询任务
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        //查询流程定义实体
        ProcessDefinitionEntity entity = (ProcessDefinitionEntity)repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
	    //查询运行中的流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        //查询执行到什么活动了
        String activityId = processInstance.getActivityId();
        //查询在实体中对应的位置
        ActivityImpl activity = entity.findActivity(activityId);
        //查询出来有几条线
        List<PvmTransition> outgoingTransitions = activity.getOutgoingTransitions();
        for (PvmTransition pvm:outgoingTransitions){
            Object name = pvm.getProperty("name");
            names.add((String)name);
        }

        if(names.size() == 0){
            names.add("默认提交");
        }

        return names;*/


        //返回存放连线的名称集合
        List<String> list = new ArrayList<String>();
        //1:使用任务ID，查询任务对象
        Task task = taskService.createTaskQuery()//
                .taskId(taskId)//使用任务ID查询
                .singleResult();
        //2：获取流程定义ID
        String processDefinitionId = task.getProcessDefinitionId();
        //3：查询ProcessDefinitionEntiy对象
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
        //使用任务对象Task获取流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        //使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
                .processInstanceId(processInstanceId)//使用流程实例ID查询
                .singleResult();
        //获取当前活动的id
        String activityId = pi.getActivityId();
        //4：获取当前的活动
        ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
        //5：获取当前活动完成之后连线的名称
        List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
        if(pvmList!=null && pvmList.size()>0){
            for(PvmTransition pvm:pvmList){
                String name = (String) pvm.getProperty("name");
                if(StringUtils.isNotBlank(name)){
                    list.add(name);
                }
                else{
                    list.add("默认提交");
                }
            }
        }
        return list;

	}


    /**
     * 完成任务
     * @param workflowBean
     */
	@Override
	public void saveSubmitTask(WorkflowBean workflowBean) {
	    //准备流程变量
        String outcome = workflowBean.getOutcome();
        HashMap<String,Object> map = new HashMap<>();
        if(outcome != null && !"默认提交".equals(outcome)){
            map.put("outcome",outcome);
        }
        //查询任务
        Task task = taskService.createTaskQuery().taskId(workflowBean.getTaskId()).singleResult();
        //添加批注
        Authentication.setAuthenticatedUserId(SpringWebUtil.get().getName());
        taskService.addComment(workflowBean.getTaskId(), task.getProcessInstanceId(), workflowBean.getComment());
        //完成任务
        taskService.complete(workflowBean.getTaskId(),map);
        //使用流程实例ID查询
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                .processInstanceId(task.getProcessInstanceId())
                .singleResult();
        //流程结束了
        if(pi==null){
            //更新请假单表的状态从1变成2（审核中-->审核完成）
            LeaveBill bill = leaveBillDao.findOne(workflowBean.getId());
            bill.setState(2);
        }
    }

    /**
     * 根据taskId查询所有批注
     * @param taskId
     * @return
     */
	@Override
	public List<Comment> findCommentByTaskId(String taskId) {

        //查询任务
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        List<Comment> taskComments = taskService.getProcessInstanceComments(task.getProcessInstanceId());
        return taskComments;
	}

    /**
     * 根据请假单ID查询批注
     * @param id
     * @return
     */
	@Override
	public List<Comment> findCommentByLeaveBillId(Long id) {
        LeaveBill one = leaveBillDao.findOne(id);
        String simpleName = one.getClass().getSimpleName();
        String key = simpleName + ":" + id;
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(key).singleResult();
        return taskService.getProcessInstanceComments(historicProcessInstance.getId());
	}

    /**
     * 根据taskId查询流程定义
     * @param taskId
     * @return
     */
	@Override
	public ProcessDefinition findProcessDefinitionByTaskId(String taskId) {
        //查询任务
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        //查询流程定义实体
        return repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
	}

	@Override
	public Map<String, Object> findCoordingByTask(String taskId) {
		return null;
	}

    /**
     * 流部署
     * @param png
     * @param bpmn
     */
    @Override
    public void saveNewDeploye(MultipartFile png, MultipartFile bpmn,String fileName) {

        try {
            repositoryService.createDeployment().addInputStream(png.getOriginalFilename(),png.getInputStream())
                    .addInputStream(bpmn.getOriginalFilename(),bpmn.getInputStream()).name(fileName).deploy();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
