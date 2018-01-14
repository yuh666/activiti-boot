package org.laotie777.activiti.service;

import org.laotie777.activiti.entity.User;
import org.springframework.data.domain.Page;
import java.util.Collection;

/**
 * @author yuh
 * 2018/1/14.
 */
public interface IUserService {

    void save(User user);

    void saveAll(Collection<User> users);

    /**
     * 分页查询
     */
    public Page<User> findPage(int pageNO,int pageSize) ;

}
