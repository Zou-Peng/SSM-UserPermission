package com.yidong.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yidong.entity.Permission;
import com.yidong.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    public List<Permission> findAll(){
        return permissionMapper.findAll();
    }

    public Permission findById(int id){
        return permissionMapper.findById(id);
    }

    public void delete(int id){
        permissionMapper.delete(id);
    }

    public void insert(Permission permission){
        permissionMapper.insert(permission);
    }

    public void update(Permission permission) {
        permissionMapper.update(permission);
    }

    public PageInfo<Permission> findByPage(int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Permission> list = permissionMapper.findAll();
        PageInfo<Permission> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }


    public List<Permission> findRolePermissionByRoleId(String roleId) {
        return permissionMapper.findRolePermissionByRoleId(roleId);
    }

    public List<Permission> findUserPermissionByUserId(int userId) {
        return permissionMapper.findUserPermissionByUserId(userId);
    }
}
