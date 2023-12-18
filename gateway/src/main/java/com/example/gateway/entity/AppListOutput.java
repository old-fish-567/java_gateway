package com.example.gateway.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class AppListOutput {

    @JsonProperty("id")
    private int id;
    @JsonProperty("app_id")
    private String appID;
    @JsonProperty("name")
    private String name;
    @JsonProperty("secret")
    private String secret;
    @JsonProperty("white_ips")
    private String whiteIPS;
    @JsonProperty("qpd")
    private int qpd;
    @JsonProperty("qps")
    private int qps;
    @JsonProperty("real_qpd")
    private int realQpd;
    @JsonProperty("real_qps")
    private int realQps;
    @JsonProperty("create_at")
    private Date createAt;
    @JsonProperty("update_at")
    private Date updateAt;
    @JsonProperty("is_delete")
    private int isDelete;


    public AppListOutput() {
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

    public int getRealQpd() {
        return realQpd;
    }

    public void setRealQpd(int realQpd) {
        this.realQpd = realQpd;
    }

    public int getRealQps() {
        return realQps;
    }

    public void setRealQps(int realQps) {
        this.realQps = realQps;
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
