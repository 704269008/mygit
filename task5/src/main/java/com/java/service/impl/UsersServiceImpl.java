package com.java.service.impl;

import com.java.mapper.UsersMapper;
import com.java.pojo.Users;
import com.java.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UsersServiceImpl implements UsersService{
    @Autowired
    private UsersMapper usersMapper;
    @Override
    public void add(Users users) {
        usersMapper.add(users);
    }

    @Override
    public void update(Users users) {
        usersMapper.update(users);
    }

    @Override
    public List<Users> list() {
        return usersMapper.list();
    }

    @Override
    public Users findByName(String phone) {
        return usersMapper.findByName(phone);
    }

    @Override
    public Users checkLogin(String phone, String password) {
        Users users=usersMapper.findByName(phone);
        if(users != null && users.getPassword().equals(password)){
            return users;
        }
        return null;
    }
}
