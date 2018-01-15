package org.laotie777.activiti.service.impl;

import java.util.List;

import org.laotie777.activiti.dao.ILeaveBillDao;
import org.laotie777.activiti.entity.LeaveBill;
import org.laotie777.activiti.service.ILeaveBillService;
import org.laotie777.activiti.utils.SpringWebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LeaveBillServiceImpl implements ILeaveBillService {

    @Autowired
    private ILeaveBillDao leaveBillDao;

    public void setLeaveBillDao(ILeaveBillDao leaveBillDao) {
        this.leaveBillDao = leaveBillDao;
    }

    /**
     * 查询自己的请假单的信息
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<LeaveBill> findLeaveBillList() {
        List<LeaveBill> list = leaveBillDao.findAll();
        return list;
    }

    /**
     * 保存请假单
     *
     * @param leaveBill
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveLeaveBill(LeaveBill leaveBill) {
        //获取请假单ID
        Long id = leaveBill.getId();
        /**新增保存*/
        if (id == null) {
            //1：从Session中获取当前用户对象，将LeaveBill对象中user与Session中获取的用户对象进行关联
            //建立管理关系
            leaveBill.setUser(SpringWebUtil.get());
            //2：保存请假单表，添加一条数据
            //leaveBillDao.save(leaveBill);
        }

        //1：执行update的操作，完成更新
        leaveBillDao.save(leaveBill);


    }

    /**
     * 使用请假单ID，查询请假单的对象
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public LeaveBill findLeaveBillById(Long id) {
        LeaveBill bill = leaveBillDao.findOne(id);
        return bill;
    }

    /**
     * 使用请假单ID，删除请假单
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteLeaveBillById(Long id) {
        leaveBillDao.delete(id);
    }

}
