package org.laotie777.activiti.service.impl;

import org.laotie777.activiti.dao.IUserDao;
import org.laotie777.activiti.entity.User;
import org.laotie777.activiti.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * @author yuh
 * 2018/1/14.
 */
@Service
public class UserServiceImpl implements IUserService{
    @Autowired
    private IUserDao userDao;

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public void saveAll(Collection<User> users) {
        userDao.save(users);
    }


    /**
     * 分页查询
     */
    @Override
    @Transactional(readOnly = true)
    public Page<User> findPage(int pageNo,int pageSize) {
        PageRequest pageable = new PageRequest(pageNo - 1, pageSize);
        return userDao.findAll(pageable);
    }
}
