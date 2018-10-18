package com.jns.service.impl;

import com.jns.entity.Role;
import com.jns.mapper.RoleMapper;
import com.jns.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    RoleMapper roleMapper;
    @Override
    public Set<String> listRoles(String name) {
        List<Role> roles=roleMapper.findRolesByUserName(name);
        Set<String> result=new HashSet<>();
        for(Role role:roles){
            result.add(role.getRoleName());
        }
        return result;
    }
}
