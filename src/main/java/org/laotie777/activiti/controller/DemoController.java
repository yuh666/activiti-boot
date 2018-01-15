package org.laotie777.activiti.controller;

import org.laotie777.activiti.entity.User;
import org.laotie777.activiti.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author yuh
 * 2018/1/14.
 */
@RestController
public class DemoController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/login")
    public ModelAndView demo(@RequestParam(value="pageNo", required=false, defaultValue="1")int pageNo,
                             @RequestParam(value="pageSize", required=false, defaultValue="5")int pageSize){

        ModelAndView mav = new ModelAndView();
        mav.setViewName("left");
        return mav;
    }
}
