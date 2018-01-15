package org.laotie777.activiti.dao;


import org.laotie777.activiti.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface IEmployeeDao extends JpaRepository<Employee,Long>,JpaSpecificationExecutor<Employee>{

	/**
	 * 根据名字查找
	 * @param name
	 * @return
	 */
	Employee findByName(String name);

}
