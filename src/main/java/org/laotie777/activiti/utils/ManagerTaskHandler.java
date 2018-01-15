package org.laotie777.activiti.utils;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.laotie777.activiti.entity.Employee;
import org.laotie777.activiti.service.IEmployeeService;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

/**
 * 员工经理任务分配
 *
 */
@SuppressWarnings("serial")
public class ManagerTaskHandler implements TaskListener,ServletContextAware {


	private ServletContext servletContext = null;

	@Override
	public void notify(DelegateTask delegateTask) {

		Employee employee = SpringWebUtil.get();
		//当前用户
		String name = employee.getName();
		//使用当前用户名查询用户的详细信息
		//从web中获取spring容器
		WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		IEmployeeService employeeService = (IEmployeeService) ac.getBean("employeeService");
		Employee emp = employeeService.findEmployeeByName(name);
		//设置个人任务的办理人
		delegateTask.setAssignee(emp.getManager().getName());

	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}
