package com.example.gateway.entity;

import jakarta.persistence.*;

@Table(name = "gateway_service_access_control")
@Entity
public class AccessControl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int serviceId;
    private int openAuth;
    private String blackList;
    private String whiteList;
    @Column(name = "white_host_name")
    private String whiteHostName;
    @Column(name = "clientip_flow_limit")
    private int clientIPFlowLimit;
    private int serviceFlowLimit;

    public AccessControl() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getOpenAuth() {
        return openAuth;
    }

    public void setOpenAuth(int openAuth) {
        this.openAuth = openAuth;
    }

    public String getBlackList() {
        return blackList;
    }

    public void setBlackList(String blackList) {
        this.blackList = blackList;
    }

    public String getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(String whiteList) {
        this.whiteList = whiteList;
    }

    public String getWhiteHostName() {
        return whiteHostName;
    }

    public void setWhiteHostName(String whiteHostName) {
        this.whiteHostName = whiteHostName;
    }

    public int getClientIPFlowLimit() {
        return clientIPFlowLimit;
    }

    public void setClientIPFlowLimit(int clientIPFlowLimit) {
        this.clientIPFlowLimit = clientIPFlowLimit;
    }

    public int getServiceFlowLimit() {
        return serviceFlowLimit;
    }

    public void setServiceFlowLimit(int serviceFlowLimit) {
        this.serviceFlowLimit = serviceFlowLimit;
    }
}
