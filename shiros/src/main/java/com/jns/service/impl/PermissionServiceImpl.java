package com.jns.service.impl;

import com.jns.entity.Permission;
import com.jns.mapper.PermissionMapper;
import com.jns.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Service
public class PermissionServiceImpl implements PermissionService{
    @Autowired
    PermissionMapper permissionMapper;
    @Override
    public Set<String> listPermissions(String userName) {
        List<Permission> permissions=permissionMapper.findPermissionsByUserName(userName);
        Set<String> result=new HashSet<>();
        for(Permission permission:permissions){
            result.add(permission.getResourceName());
        }
        return result;
    }
}
