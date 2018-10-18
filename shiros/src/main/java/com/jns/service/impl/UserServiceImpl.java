package com.jns.service.impl;


import com.jns.entity.User;
import com.jns.entity.UserRole;
import com.jns.mapper.UserMapper;
import com.jns.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public void addUser(User user) {

    }

    @Override
    public void deleteUser(int userId) {

    }

    @Override
    public User findUserByUserName(String userName) {
        User user=userMapper.findUserByUserName(userName);
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        return null;
    }

    @Override
    public String getPassword(String name) {
        User user=userMapper.findUserByUserName(name);
        return user.getUserPassword();
    }

    @Override
    public void deleteUserRole(int userId) {

    }

    @Override
    public void addUserRole(UserRole userRole) {

    }

    @Override
    public List<String> findRolesByUserName(String userName) {
        return null;
    }

    @Override
    public List<String> findPermissionsByUserName(String userName) {
        return null;
    }
}
