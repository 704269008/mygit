package com.jns.mapper;


import com.jns.entity.User;
import com.jns.entity.UserRole;

import java.util.List;

public interface UserMapper {
    void addUser(User user);
    void deleteUser(int userId);
    User findUserByUserName(String userName);
    List<User> findAllUsers();

    //删除用户角色
    void deleteUserRole(int userId);
    //添加用户角色
    void addUserRole(UserRole userRole);

    List<String> findRolesByUserName(String userName);
    List<String> findPermissionsByUserName(String userName);
}
