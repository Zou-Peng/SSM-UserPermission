package com.yidong.test;

import com.yidong.entity.User;
import com.yidong.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;

public class company {
    @org.junit.Test
    public void find() throws IOException {
        ApplicationContext ac =
                new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        UserService userService = ac.getBean(UserService.class);
        List<User> list = userService.findAll();
        System.out.println("list = " + list);

    }
}
