package com.jns.entity;

public class User {
    private int userId;
    private String userCode;
    private String userName;
    private String userPassword;
    private String salt;
    private char locked;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public char getLocked() {
        return locked;
    }

    public void setLocked(char locked) {
        this.locked = locked;
    }

    @Override
    public String toString() {
        return "user["+userCode+"]";
    }
}
