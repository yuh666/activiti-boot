package org.laotie777.activiti.service;

import org.laotie777.activiti.entity.Employee;

/**
 * @author yuh
 * 2018/1/15.
 */
public interface IEmployeeService {
    /**
     * 根据名字获取员工
     * @param name
     * @return
     */
    Employee findEmployeeByName(String name);

}
