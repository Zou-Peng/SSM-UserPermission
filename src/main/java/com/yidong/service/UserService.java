package com.yidong.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yidong.entity.User;
import com.yidong.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> findAll(){
        return userMapper.findAll();
    }

    public User findById(int id){
        return userMapper.findById(id);
    }

    public void delete(int id){
        userMapper.delete(id);
    }

    public void insert(User user){
        userMapper.insert(user);
    }

    public void update(User user) {
        userMapper.update(user);
    }

    public PageInfo<User> findByPage(int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<User> list = userMapper.findAll();
        PageInfo<User> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    public void saveUserRole(String userId, String[] roleIds) {
        //用户分配角色
        //解除用户角色的关系
        //DELETE FROM user_role WHERE userId=''
        userMapper.deleteUserRoleByUserId(userId);

        //-- 2. 用户分配角色
        //INSERT INTO user_role(userId,roleId)VALUES('','')
        if (roleIds != null && roleIds.length>0){
            for (String roleId : roleIds) {
                userMapper.saveUserRole(userId,roleId);
            }
        }
    }
}
