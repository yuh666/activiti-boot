package org.laotie777.activiti.dao;

import org.laotie777.activiti.entity.LeaveBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface ILeaveBillDao extends JpaRepository<LeaveBill,Long>,JpaSpecificationExecutor<LeaveBill>{}