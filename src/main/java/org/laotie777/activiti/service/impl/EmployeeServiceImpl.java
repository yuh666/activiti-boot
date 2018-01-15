package org.laotie777.activiti.service.impl;


import org.laotie777.activiti.dao.IEmployeeDao;
import org.laotie777.activiti.entity.Employee;
import org.laotie777.activiti.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private IEmployeeDao employeeDao;

    public void setEmployeeDao(IEmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    /**
     * 使用用户名作为查询条件，查询用户对象
     */
    @Override
    @Transactional(readOnly = true)
    public Employee findEmployeeByName(String name) {
        Employee employee = employeeDao.findByName(name);
        return employee;
    }

}
