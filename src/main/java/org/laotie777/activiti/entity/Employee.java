package org.laotie777.activiti.entity;

import javax.persistence.*;

/**
 * 用户表
 */
@Entity
@Table(name = "a_employee")
public class Employee {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    /**
     * 用户名
     */
    @Column(name = "name",unique = true)
	private String name;
    /**
     * 密码
     */
    @Column(name = "password")
	private String password;
    /**
     * 电子邮箱
     */
    @Column(name = "email",unique = true)
	private String email;
    /**
     * 角色
     */
    @Column(name = "role")
	private String role;
    @ManyToOne(targetEntity = Employee.class)
    @JoinColumn(name = "manager_id")
	private Employee manager;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Employee getManager() {
		return manager;
	}
	public void setManager(Employee manager) {
		this.manager = manager;
	}

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", manager=" + manager +
                '}';
    }
}

