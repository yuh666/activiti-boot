package org.laotie777.activiti.service.impl;


import org.laotie777.activiti.dao.IEmployeeDao;
import org.laotie777.activiti.entity.Employee;
import org.laotie777.activiti.service.IEmployeeService;

public class EmployeeServiceImpl implements IEmployeeService {

    private IEmployeeDao employeeDao;

    public void setEmployeeDao(IEmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    /**
     * 使用用户名作为查询条件，查询用户对象
     */
    @Override
    public Employee findEmployeeByName(String name) {
        Employee employee = employeeDao.findByName(name);
        return employee;
    }

}
