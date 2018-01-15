package org.laotie777.activiti.controller;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.laotie777.activiti.entity.WorkflowBean;
import org.laotie777.activiti.service.IWorkflowService;
import org.laotie777.activiti.service.impl.WorkflowServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.List;

/**
 * @author yuh
 * 2018/1/15.
 */
@RestController
@RequestMapping("/workflow")
public class WorkFlowController {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(WorkFlowController.class);

    @Autowired
    private IWorkflowService workflowService;
    /**
     * 查询流程定义列表 和 流程部署列表
     * @return
     */
    @RequestMapping("deployHome")
    public ModelAndView deployHome(){

        List<Deployment> deploymentList = workflowService.findDeploymentList();
        List<ProcessDefinition> processDefinitionList = workflowService.findProcessDefinitionList();

        ModelAndView mav = new ModelAndView();
        mav.addObject("depList", deploymentList);
        mav.addObject("pdList",processDefinitionList);
        mav.setViewName("/workflow/workflow");
        return mav;
    }


    /**
     * 流程部署
     * @param bean
     * @return
     */
    @RequestMapping("newdeploy")
    public ModelAndView newdeploy(WorkflowBean bean, @RequestParam("file") MultipartFile file){

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/workflow/workflow");

        try{
            File jdkFile = new File("./1.zip");
            org.apache.commons.io.FileUtils.copyInputStreamToFile(file.getInputStream(),jdkFile);
            workflowService.saveNewDeploye(jdkFile,bean.getFilename());
        }catch (Exception e){
            logger.error(bean.getFilename() + "=> 文件上传失败");
        }
        return mav;

    }





}

