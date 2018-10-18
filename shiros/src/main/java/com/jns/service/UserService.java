package com.jns.service;


import com.jns.entity.UserRole;
import com.jns.entity.User;
import java.util.List;

public interface UserService {
    void addUser(User user);
    void deleteUser(int userId);
    User findUserByUserName(String userName);
    List<User> findAllUsers();
    String getPassword(String name);
    //删除用户角色
    void deleteUserRole(int userId);
    //添加用户角色
    void addUserRole(UserRole userRole);

    List<String> findRolesByUserName(String userName);
    List<String> findPermissionsByUserName(String userName);
}
