package com.yidong.web.controller;

import com.github.pagehelper.PageInfo;
import com.yidong.entity.Permission;
import com.yidong.service.PermissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;


    @RequestMapping("/find")
    public String findAll(Model model){
        List<Permission> permissions = permissionService.findAll();
        model.addAttribute("list",permissions);
        return "permission-list";
    }

    @RequestMapping("/delete")
    public String delete(int id){
        permissionService.delete(id);
        return "redirect:/permission/findByPage";
    }

    /**
     * 进入添加页面: 从permission-list.jsp----->permission-add.jsp
     */
    @RequestMapping("/toAdd")
    public String toAdd(HttpServletRequest request) throws IOException {
        // 查询所有的权限，作为父菜单下拉列表显示
        List<Permission> permissionList = permissionService.findAll();
        request.setAttribute("permissionList",permissionList);

        return "permission-add";
    }


    @RequestMapping("/insert")
    public String insert(Permission permission){
        permissionService.insert(permission);
        return "redirect:/permission/findByPage";
    }

    @RequestMapping("/toupdate")
    public String toupdate(Model model, int id){
        model.addAttribute("permission",permissionService.findById(id));
        // 查询所有权限作为父菜单下拉列表显示
        List<Permission> permissionList = permissionService.findAll();
        model.addAttribute("permissionList",permissionList);

        return "permission-update";
    }

    @RequestMapping("/update")
    public String update(Permission permission){
        permissionService.update(permission);
        return "redirect:/permission/findByPage";
    }

    @RequestMapping("/findByPage")
    public String findByPage(
           @RequestParam(defaultValue = "1") Integer pageNum,
           @RequestParam(defaultValue = "5") Integer pageSize,
           HttpServletRequest request
    ){
        Subject subject = SecurityUtils.getSubject();
        subject.checkPermission("权限管理");
        // 调用service, 分页查询
        PageInfo<Permission> pageInfo = permissionService.findByPage(pageNum,pageSize);

        // 保存查询结果
        request.setAttribute("pageInfo",pageInfo);

        // 返回跳转的路径名称  (默认是转发)
        return "permission-list";

    }


}
