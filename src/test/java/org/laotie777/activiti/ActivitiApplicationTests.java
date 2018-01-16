package org.laotie777.activiti;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.laotie777.activiti.entity.User;
import org.laotie777.activiti.service.IUserService;
import org.laotie777.activiti.service.IWorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipInputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiApplicationTests {


    @Autowired
    private IUserService userService;
    @Autowired
    private IWorkflowService workflowService;


    Random random = new Random(100000L);

    @Test
    public void contextLoads() {
        //System.out.println(controller);


    }


    @Test
    public void saveUser() {
        ArrayList<User> users = new ArrayList<User>();

        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setId(System.currentTimeMillis() + i);
            user.setName("laotie" + i);
            users.add(user);
        }

        userService.saveAll(users);
    }


    @Test
    public void deploye(){

        workflowService.saveNewDeploye(new File("/Users/yuh/Desktop/归档.zip"),"流程1");

    }


}
