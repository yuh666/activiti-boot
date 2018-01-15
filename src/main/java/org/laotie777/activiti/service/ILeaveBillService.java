package org.laotie777.activiti.service;

import org.laotie777.activiti.entity.LeaveBill;

import java.util.List;

/**
 * @author yuh
 * 2018/1/15.
 */
public interface ILeaveBillService {

    /**
     * 查询所有请假单
     * @return
     */
    List<LeaveBill> findLeaveBillList();

    /**
     * 保存请假单
     * @param leaveBill
     */
    void saveLeaveBill(LeaveBill leaveBill);

    /**
     * 根据ID查找请假单
     * @param id
     * @return
     */
    LeaveBill findLeaveBillById(Long id);

    /**
     * 删除请假单
     * @param id
     */
    void deleteLeaveBillById(Long id);

}
