package com.example.gateway.entity;


import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Table(name = "gateway_admin")
@Entity
public class Admin implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String userName;

    private String salt;

    private String password;

    private Date updateAt;

    private Date createAt;

    private int isDelete;

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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public int getIsDelete() {
        return isDelete;
    }
    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", salt='" + salt + '\'' +
                ", password='" + password + '\'' +
                ", updateAt=" + updateAt +
                ", createAt=" + createAt +
                ", isDelete=" + isDelete +
                '}';
    }

}
