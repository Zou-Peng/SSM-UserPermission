package com.yidong.web.controller;

import com.yidong.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    // 注入servlet中的会话对象
    @Autowired
    private HttpSession session;

   @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(String username,String password){

        System.out.println(username);
        // shiro实现登录认证
        Subject subject = SecurityUtils.getSubject();
        // 定义一个token，封装用户输入的用户密码
        AuthenticationToken token = new UsernamePasswordToken(username,password);
        // shiro登录认证: 自动去到realm中的认证方法
        subject.login(token);

        // 登录成功, 存储用户信息到session
        // 先获取身份
        User user = (User) subject.getPrincipal();
        // 再存储数据
        session.setAttribute("loginUser",user);

        return "main";
    }



}
