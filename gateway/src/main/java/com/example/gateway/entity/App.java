package com.example.gateway.entity;

import jakarta.persistence.*;

import java.util.Date;

@Table(name = "gateway_app")
@Entity
public class App {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    @Column(name = "app_id")
    private String appID;
    @Column(name = "name")
    private String name;
    @Column(name = "secret")
    private String secret;
    @Column(name = "white_ips")
    private String whiteIPS;
    @Column(name = "qpd")
    private int qpd;
    @Column(name = "qps")
    private int qps;
    @Column(name = "create_at")
    private Date createAt;
    @Column(name = "update_at")
    private Date updateAt;
    @Column(name = "is_delete")
    private int isDelete;


    public App() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getWhiteIPS() {
        return whiteIPS;
    }

    public void setWhiteIPS(String whiteIPS) {
        this.whiteIPS = whiteIPS;
    }

    public int getQpd() {
        return qpd;
    }

    public void setQpd(int qpd) {
        this.qpd = qpd;
    }

    public int getQps() {
        return qps;
    }

    public void setQps(int qps) {
        this.qps = qps;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }
}



