package org.laotie777.activiti.controller;

import org.laotie777.activiti.entity.LeaveBill;
import org.laotie777.activiti.service.ILeaveBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author yuh
 * 2018/1/15.
 */
@RestController
@RequestMapping("/leaveBill")
public class LeaveBillController {

    @Autowired
    private ILeaveBillService leaveBillService;

    @RequestMapping("/home")
    public ModelAndView home(){
        List<LeaveBill> leaveBillList = leaveBillService.findLeaveBillList();
        ModelAndView view = new ModelAndView();
        view.addObject("list",leaveBillList);
        view.setViewName("/leaveBill/list");
        return view;
    }
}


