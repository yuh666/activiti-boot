package org.laotie777.activiti.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
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
import org.springframework.beans.factory.annotation.Autowired;


public class WorkflowServiceImpl implements IWorkflowService {
	/**请假申请Dao*/
	@Autowired
	private ILeaveBillDao leaveBillDao;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private FormService formService;

	@Autowired
	private HistoryService historyService;


	@Override
	public void saveNewDeploye(File file, String filename) {

	}

	@Override
	public List<Deployment> findDeploymentList() {
		return null;
	}

	@Override
	public List<ProcessDefinition> findProcessDefinitionList() {
		return null;
	}

	@Override
	public InputStream findImageInputStream(String deploymentId, String imageName) {
		return null;
	}

	@Override
	public void deleteProcessDefinitionByDeploymentId(String deploymentId) {

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
