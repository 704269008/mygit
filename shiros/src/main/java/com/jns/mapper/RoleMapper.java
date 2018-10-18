package com.jns.mapper;


import com.jns.entity.Role;
import com.jns.entity.RolePermission;

import java.util.List;

public interface RoleMapper {
    void addRole(Role role);
    void deleteRole(int roleId);
    void updateRole(Role role);

    Role findById(int roleId);
    List<Role> findRolesByUserName(String userName);
    List<Role> findAllRoles();


    void deleteUserRole(int roleId);

    //根据roleid删除角色权限
    void deleteRolePermission(int roleId);
    //添加某个role的权限
    void addRolePermission(RolePermission rolePermission);
}
