package org.laotie777.activiti.controller;

import org.laotie777.activiti.entity.Employee;
import org.laotie777.activiti.entity.LeaveBill;
import org.laotie777.activiti.service.ILeaveBillService;
import org.laotie777.activiti.utils.SpringWebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author yuh
 * 2018/1/15.
 */
@RestController
@RequestMapping("/leaveBill")
public class LeaveBillController {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(LeaveBillController.class);

    @Autowired
    private ILeaveBillService leaveBillService;

    /**
     * 请假单列表页
     *
     * @return
     */
    @RequestMapping("/home")
    public ModelAndView home() {
        List<LeaveBill> leaveBillList = leaveBillService.findLeaveBillList();
        ModelAndView view = new ModelAndView();
        view.addObject("list", leaveBillList);
        view.setViewName("/leaveBill/list");
        return view;
    }

    /**
     * 新增以及修改页面
     *
     * @return
     */
    @RequestMapping("/input/{id}")
    public ModelAndView input(@PathVariable(name = "id") String id) {
        ModelAndView view = new ModelAndView();
        view.setViewName("/leaveBill/input");
        if (!StringUtils.isEmpty(id)) {
            LeaveBill leaveBillById = leaveBillService.findLeaveBillById(Long.parseLong(id));
            view.addObject("leaveBill", leaveBillById);
        }
        return view;
    }

    /**
     * 新增以及修改页面
     *
     * @return
     */
    @RequestMapping("/input")
    public ModelAndView inputAdd() {
        ModelAndView view = new ModelAndView();
       return view;
    }


    /**
     * 新增以及修改页面
     *
     * @return
     */
    @RequestMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable(name = "id") String id) {
        ModelAndView view = new ModelAndView();
        view.setViewName("forward:/leaveBill/home");
        if (!StringUtils.isEmpty(id)) {
           leaveBillService.deleteLeaveBillById(Long.parseLong(id));
        }
        return view;
    }



    /**
     * 增加请假单
     *
     * @param leaveBill
     * @return
     */
    @RequestMapping("/save")
    public ModelAndView save(LeaveBill leaveBill, HttpSession session) {
        ModelAndView view = new ModelAndView();
        view.setViewName("forward:/leaveBill/home");
        try {
            if (leaveBill.getUser() == null) {
                Employee emp = (Employee) session.getAttribute(SpringWebUtil.GLOBLE_USER_SESSION);
                leaveBill.setUser(emp);
            }
            leaveBillService.saveLeaveBill(leaveBill);
        } catch (Exception e) {
            logger.error(leaveBill.getId() + "=> 保存失败");
        }

        return view;
    }




}


