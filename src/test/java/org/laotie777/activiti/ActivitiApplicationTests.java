package org.laotie777.activiti;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.laotie777.activiti.entity.User;
import org.laotie777.activiti.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiApplicationTests {


	@Autowired
	private IUserService userService;

	Random random = new Random(100000L);

	@Test
	public void contextLoads() {
		//System.out.println(controller);



	}


	@Test
    public void saveUser(){
        ArrayList<User> users = new ArrayList<User>();

        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setId(System.currentTimeMillis()+i);
            user.setName("laotie"+i);
            users.add(user);
        }

        userService.saveAll(users);
    }

}
