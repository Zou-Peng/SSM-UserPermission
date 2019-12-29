package com.yidong.mapper;

import com.yidong.entity.Permission;

import java.util.List;

public interface PermissionMapper {
    List<Permission> findAll();

    Permission findById(int permissionId);

    void delete(int permissionId);

    void insert(Permission permission);

    void update(Permission permission);

    List<Permission> findRolePermissionByRoleId(String roleId);

    List<Permission> findUserPermissionByUserId(int userId);
}
