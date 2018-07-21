package com.java.mapper;

import com.java.pojo.Users;

import java.util.List;

public interface UsersMapper {
int add(Users users);
int update(Users users);
Users findByName(String phone);
List<Users> list();
}
