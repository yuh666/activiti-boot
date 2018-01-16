package org.laotie777.activiti.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.laotie777.activiti.dao.ILeaveBillDao;
import org.laotie777.activiti.entity.LeaveBill;
import org.laotie777.activiti.entity.WorkflowBean;
import org.laotie777.activiti.service.IWorkflowService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List<Deployment> findDeploymentList() {
	    /*查的是ACT_RE_DEPLOYMENT*/
		return repositoryService.createDeploymentQuery().orderByDeploymenTime().desc().list();
	}

    /**
     * 查询流程定义列表
     * @return
     */
	@Override
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
	public InputStream findImageInputStream(String deploymentId, String imageName) {
        return repositoryService.getResourceAsStream(deploymentId,imageName);
	}

    /**
     * 根据定义ID删除流程
     * @param deploymentId
     */
	@Override
	public void deleteProcessDefinitionByDeploymentId(String deploymentId) {
	    //删除部署的流程 并且删除所有流程实例
        repositoryService.deleteDeployment(deploymentId,true);
	}

	@Override
	public void saveStartProcess(WorkflowBean workflowBean) {

	}

	@Override
	public List<Task> findTaskListByName(String name) {
		return null;
	}

	@Override
	public String findTaskFormKeyByTaskId(String taskId) {
		return null;
	}

	@Override
	public LeaveBill findLeaveBillByTaskId(String taskId) {
		return null;
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
