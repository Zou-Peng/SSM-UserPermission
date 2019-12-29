package com.yidong.mapper;

import com.yidong.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    List<User> findAll();

    User findById(int id);

    void delete(int id);

    void insert(User user);

    void update(User user);

    User findByUsername(String username);

    void saveUserRole(@Param("userId") String userId, @Param("roleId") String roleId);

    void deleteUserRoleByUserId(String userId);
}
