package com.yidong.test.page;

import com.github.pagehelper.PageInfo;
import com.yidong.entity.User;
import com.yidong.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class page {

    @Test
    public static void main(String[] args) {
        ApplicationContext ac =
                new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        UserService userService = ac.getBean(UserService.class);
        PageInfo<User> pageInfo = userService.findByPage(2,5);
    }
}
