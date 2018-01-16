package org.laotie777.activiti.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
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

	@Override
	public List<String> findOutComeListByTaskId(String taskId) {
		return null;
	}

	@Override
	public void saveSubmitTask(WorkflowBean workflowBean) {

	}

	@Override
	public List<Comment> findCommentByTaskId(String taskId) {
		return null;
	}

	@Override
	public List<Comment> findCommentByLeaveBillId(Long id) {
		return null;
	}

	@Override
	public ProcessDefinition findProcessDefinitionByTaskId(String taskId) {
		return null;
	}

	@Override
	public Map<String, Object> findCoordingByTask(String taskId) {
		return null;
	}
}
