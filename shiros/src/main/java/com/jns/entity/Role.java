package com.jns.entity;

public class Role {
    private int roleId;
    private String roleName;
    private char available;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public char getAvailable() {
        return available;
    }

    public void setAvailable(char available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "role["+roleName+"]";
    }
}
