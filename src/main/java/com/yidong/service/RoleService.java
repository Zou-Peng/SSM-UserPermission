package com.yidong.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yidong.entity.Role;
import com.yidong.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    public List<Role> findAll(){
        return roleMapper.findAll();
    }

    public Role findById(int id){
        return roleMapper.findById(id);
    }

    public void delete(int id){
        roleMapper.delete(id);
    }

    public void insert(Role role){
        roleMapper.insert(role);
    }

    public void update(Role role) {
        roleMapper.update(role);
    }

    public PageInfo<Role> findByPage(int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Role> list = roleMapper.findAll();
        PageInfo<Role> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public Role findByRolename(String rolename) {
        return roleMapper.findByRolename(rolename);
    }

    public List<Role> findUserRoleByUserId(String userId) {
        return roleMapper.findUserRoleByUserId(userId);
    }

    public void saveRolePermission(Integer roleId, String permissionIds) {
        roleMapper.deleteRolePermissionByRoleId(roleId);

        if (permissionIds !=null && permissionIds.length()>0){
            String[] array = permissionIds.split(",");
            if (array != null && array.length>0){
                for (String permissionId : array){
                    roleMapper.saveRolePermission(roleId,permissionId);
                }
            }
        }
    }
}
