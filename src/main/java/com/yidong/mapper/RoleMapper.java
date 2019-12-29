package com.yidong.mapper;

import com.yidong.entity.Role;

import java.util.List;

public interface RoleMapper {
    List<Role> findAll();

    Role findById(int id);

    void delete(int id);

    void insert(Role role);

    void update(Role role);

    Role findByRolename(String rolename);

    List<Role> findUserRoleByUserId(String userId);

    void saveRolePermission(Integer roleId, String permissionId);

    void deleteRolePermissionByRoleId(Integer roleId);
}
