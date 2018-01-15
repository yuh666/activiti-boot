package org.laotie777.activiti.controller;

import org.laotie777.activiti.entity.Employee;
import org.laotie777.activiti.service.IEmployeeService;
import org.laotie777.activiti.utils.SpringWebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * @author yuh
 * 2018/1/15.
 */
@RestController
public class LoginController {


    @Autowired
    private IEmployeeService employeeService;

    /**
     * 登录页面
     *
     * @return
     */
    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    /**
     * 登录
     *
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(HttpSession session, String name) {
        Employee employeeByName = employeeService.findEmployeeByName(name);
        session.setAttribute(SpringWebUtil.GLOBLE_USER_SESSION, employeeByName);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main");
        return modelAndView;
    }


    /**
     * 左frame
     * @return
     */
    @RequestMapping("/left")
    public ModelAndView left() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("left");
        return modelAndView;
    }

    /**
     * 右frame
     * @return
     */
    @RequestMapping("/top")
    public ModelAndView top() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("top");
        return modelAndView;
    }

    /**
     * 欢迎 frame
     * @return
     */
    @RequestMapping("/welcome")
    public ModelAndView welcome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("welcome");
        return modelAndView;
    }

    /**
     *登出
     * @return
     */
    @RequestMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        session.removeAttribute(SpringWebUtil.GLOBLE_USER_SESSION);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

}
