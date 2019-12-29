package com.yidong.web.controller;

import com.github.pagehelper.PageInfo;
import com.yidong.entity.Role;
import com.yidong.entity.User;
import com.yidong.service.RoleService;
import com.yidong.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    @RequestMapping("/find")
    public String findAll(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("list",users);
        return "user-list";
    }

    @RequestMapping("/delete")
    public String delete(int id){
        userService.delete(id);
        return "redirect:/user/findByPage";
    }

    @RequestMapping("/insert")
    public String insert(User user){
        userService.insert(user);
        return "redirect:/user/findByPage";
    }

    @RequestMapping("/toupdate")
    public String toupdate(Model model, int id){
        model.addAttribute("user",userService.findById(id));
        return "user-update";
    }

    @RequestMapping("/update")
    public String update(User user){
        userService.update(user);
        return "redirect:/user/findByPage";
    }

    @RequestMapping("/findByPage")
    public String findByPage(
           @RequestParam(defaultValue = "1") Integer pageNum,
           @RequestParam(defaultValue = "5") Integer pageSize,
           HttpServletRequest request
    ){
        Subject subject = SecurityUtils.getSubject();
        subject.checkPermission("用户管理");
        // 调用service, 分页查询
        PageInfo<User> pageInfo = userService.findByPage(pageNum,pageSize);

        // 保存查询结果
        request.setAttribute("pageInfo",pageInfo);

        // 返回跳转的路径名称  (默认是转发)
        return "user-list";

    }

    /**
     * 跳转到用户角色添加页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("userRole")
    public String userRole(String id,Model model ){
        List<Role> roleList = roleService.findAll();
        model.addAttribute("roleList",roleList);
        // 查询用户已经具有的角色 (查询条件： 用户id;  求： 角色信息)
        //SELECT r.* FROM user_role ur INNER JOIN role r ON ur.roleId=r.roleId WHERE ur.userId='1'
        // 查询用户角色的主要目的： 页面默认选中.
        List<Role> userRoleList = roleService.findUserRoleByUserId(id);
        // 定义一个角色字符串，保存所有角色名称
        String roleStr = "";
        if (userRoleList != null && userRoleList.size()>0){
            for(Role role : userRoleList){
                roleStr += role.getRoleName() + ",";
            }
        }

        model.addAttribute("roleStr",roleStr);
        model.addAttribute("userId",id);
        return "user-role-add";
    }

    @RequestMapping("/saveUserRole")
    public String saveUserRole(String userId,String[] ids){
        userService.saveUserRole(userId,ids);
        return "redirect:/user/findByPage";
    }


}
