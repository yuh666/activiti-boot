package org.laotie777.activiti.service;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.laotie777.activiti.entity.LeaveBill;
import org.laotie777.activiti.entity.WorkflowBean;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author yuh
 * 2018/1/15.
 */
public interface IWorkflowService {

    /**
     * 保存新的流程定义
     * @param file
     * @param filename
     */
    void saveNewDeploye(File file, String filename);

    /**
     * 查询部署列表
     * @return
     */
    List<Deployment> findDeploymentList();

    /**
     * 定义列表
     * @return
     */
    List<ProcessDefinition> findProcessDefinitionList();

    /**
     * 根据部署ID查询流程图片
     * @param deploymentId
     * @param imageName
     * @return
     */
    InputStream findImageInputStream(String deploymentId, String imageName);

    /**
     * 根据定义ID删除流程
     * @param deploymentId
     */
    void deleteProcessDefinitionByDeploymentId(String deploymentId);

    /**
     * 启动流程实例
     * @param leaveBillId
     */
    void saveStartProcess(Long leaveBillId);

    /**
     * 根据名字查询任务
     * @param name
     * @return
     */
    List<Task> findTaskListByName(String name);

    /**
     * 根据任务Id查询FormKey
     * @param taskId
     * @return
     */
    String findTaskFormKeyByTaskId(String taskId);

    /**
     * 根据任务Id查询请假单
     * @param taskId
     * @return
     */
    LeaveBill findLeaveBillByTaskId(String taskId);

    /**
     * 根据任务ID查询结果列表
     * @param taskId
     * @return
     */
    List<String> findOutComeListByTaskId(String taskId);


    /**
     * 提交任务
     * @param workflowBean
     */
    void saveSubmitTask(WorkflowBean workflowBean);

    /**
     * 根据任务ID查询注释
     * @param taskId
     * @return
     */
    List<Comment> findCommentByTaskId(String taskId);

    /**
     * 根据请假单ID查询注释
     * @param id
     * @return
     */
    List<Comment> findCommentByLeaveBillId(Long id);

    /**
     *  根据任务Id查询流程实例
     * @param taskId
     * @return
     */
    ProcessDefinition findProcessDefinitionByTaskId(String taskId);

    /**
     *  查询联合任务
     * @param taskId
     * @return
     */
    Map<String, Object> findCoordingByTask(String taskId);

    /**
     * 流部署
     * @param png
     * @param bpmn
     */
    void saveNewDeploye(MultipartFile png, MultipartFile bpmn,String fileName);
}
