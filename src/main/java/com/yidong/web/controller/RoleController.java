package com.yidong.web.controller;

import com.github.pagehelper.PageInfo;
import com.yidong.entity.Permission;
import com.yidong.entity.Role;
import com.yidong.service.PermissionService;
import com.yidong.service.RoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;


    @RequestMapping("/find")
    public String findAll(Model model){
        List<Role> roles = roleService.findAll();
        model.addAttribute("list",roles);
        return "role-list";
    }

    @RequestMapping("/delete")
    public String delete(int id){
        roleService.delete(id);
        return "redirect:/role/findByPage";
    }

    @RequestMapping("/insert")
    public String insert(Role role){
        roleService.insert(role);
        return "redirect:/role/findByPage";
    }

    @RequestMapping("/toupdate")
    public String toupdate(Model model, int id){
        model.addAttribute("role",roleService.findById(id));
        return "role-update";
    }

    @RequestMapping("/update")
    public String update(Role role){
        roleService.update(role);
        return "redirect:/role/findByPage";
    }

    @RequestMapping("/findByPage")
    public String findByPage(
           @RequestParam(defaultValue = "1") Integer pageNum,
           @RequestParam(defaultValue = "5") Integer pageSize,
           HttpServletRequest request
    ){
        Subject subject = SecurityUtils.getSubject();
        subject.checkPermission("角色管理");
        // 调用service, 分页查询
        PageInfo<Role> pageInfo = roleService.findByPage(pageNum,pageSize);

        // 保存查询结果
        request.setAttribute("pageInfo",pageInfo);

        // 返回跳转的路径名称  (默认是转发)
        return "role-list";

    }


    /**
     * 角色分配权限页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/toRolePermission")
    public String toRolePermission(String id,Model model ){
        model.addAttribute("roleId",id);
        return "role-permission";
    }

    @RequestMapping("/getZtreeData")
    @ResponseBody
    public List<Map<String,Object>> getZtreeData(String roleId,Model model){
        List<Map<String,Object>> result = new ArrayList<>();
        List<Permission> permissionList = permissionService.findAll();
        List<Permission> rolePermissionList = permissionService.findRolePermissionByRoleId(roleId);
        for (Permission p : permissionList){
            Map<String,Object> map = new HashMap<>();
            map.put("id",p.getPermissionId());
            map.put("pId",p.getParentId());
            map.put("name",p.getPermissionName());
            map.put("open",true);
            if (rolePermissionList != null && rolePermissionList.size()>0){
                for (Permission rp : rolePermissionList){
                    if (p.getPermissionId() == rp.getPermissionId()){
                        map.put("checked",true);
                    }
                }
            }
            result.add(map);
        }
        return result;
    }

    @RequestMapping("saveRolePermission")
    public String saveRolePermission(Integer roleId,String permissionIds){
        roleService.saveRolePermission(roleId,permissionIds);
        return "redirect:/role/findByPage";
    }
}
