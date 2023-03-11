package com.example.firebase_prorject;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class UserModel {

    public  String userName,password;

    public UserModel(@NotNull String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
/*
    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {

        this.password = password;
    }
    */
}
