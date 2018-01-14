package org.laotie777.activiti.dao;

import org.laotie777.activiti.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author yuh
 * 2018/1/14.
 */
public interface IUserDao extends JpaRepository<User,Long> ,JpaSpecificationExecutor<User>{
}
