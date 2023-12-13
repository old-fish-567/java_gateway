package com.example.gateway.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Date;

@Entity
public class AdminLogin implements Serializable {
    @Id
    private int id;
    private String userName;

    private Date loginTime;
    public AdminLogin() {
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}
