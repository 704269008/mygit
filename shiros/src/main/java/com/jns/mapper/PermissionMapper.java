package com.jns.mapper;


import com.jns.entity.Permission;

import java.util.List;

public interface PermissionMapper {
    void addPermission(Permission permisssion);
    void deletePermission(long resourceId);
    void updatePermission(Permission permission);
    Permission findById(long resourceId);
    List<Permission> findPermissionsByUserName(String name);
    List<Permission> findPermissionsByRoleId(int roleId);
    List<Permission> findAllPermissions();
    void deleteRolePermission(long permissionId);

}
