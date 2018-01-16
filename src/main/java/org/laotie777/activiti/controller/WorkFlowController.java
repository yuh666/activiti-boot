package org.laotie777.activiti.controller;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.laotie777.activiti.entity.WorkflowBean;
import org.laotie777.activiti.service.IWorkflowService;
import org.laotie777.activiti.service.impl.WorkflowServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
     *
     * @return
     */
    @RequestMapping("deployHome")
    public ModelAndView deployHome() {

        List<Deployment> deploymentList = workflowService.findDeploymentList();
        List<ProcessDefinition> processDefinitionList = workflowService.findProcessDefinitionList();

        ModelAndView mav = new ModelAndView();
        mav.addObject("depList", deploymentList);
        mav.addObject("pdList", processDefinitionList);
        mav.setViewName("/workflow/workflow");
        return mav;
    }


    /**
     * 流程部署
     *
     * @param bean
     * @return
     */
    @RequestMapping("newdeploy")
    public ModelAndView newdeploy(String fileName, @RequestParam("file") MultipartFile file) {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/workflow/workflow");

        try {
            File jdkFile = new File("./1.zip");
            org.apache.commons.io.FileUtils.copyInputStreamToFile(file.getInputStream(), jdkFile);
            workflowService.saveNewDeploye(jdkFile, fileName);
        } catch (Exception e) {
            logger.error(fileName + "=> 文件上传失败");
        }
        return mav;

    }


    /**
     * 流程部署
     *
     * @param bean
     */
    @RequestMapping("viewImage/{id}/{diagramResourceName}")
    public void newdeploy(@PathVariable(name = "id") String id,
                          @PathVariable(name = "diagramResourceName") String diagramResourceName,
                          HttpServletResponse response) {

        ServletOutputStream outputStream = null;
        try {
            InputStream imageInputStream = workflowService.findImageInputStream(id, diagramResourceName);
            byte[] arr = new byte[1024];
            int f = -1;
            outputStream = response.getOutputStream();
            while ((f = imageInputStream.read(arr)) != -1) {
                outputStream.write(arr, 0, f);
            }
            outputStream.flush();
        } catch (IOException e) {
            logger.error("读取流程图失败");
            throw new RuntimeException(e);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {

            }
        }
    }


}

