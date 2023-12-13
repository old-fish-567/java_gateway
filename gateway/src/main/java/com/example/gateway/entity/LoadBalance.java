package com.example.gateway.entity;

import jakarta.persistence.*;


@Table(name = "gateway_service_load_balance")
@Entity
public class LoadBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int serviceId;
    private int checkMethod;
    private int checkTimeout;
    @Column(name = "check_interval")
    private int intInterval;
    private int roundType;
    private String ipList;
    private String weightList;
    private String forbidList;
    private int UpstreamConnectTimeout;
    private int UpstreamHeaderTimeout;
    private int UpstreamIdleTimeout;
    private int UpstreamMaxIdle;

    public LoadBalance() {
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

    public int getCheckMethod() {
        return checkMethod;
    }

    public void setCheckMethod(int checkMethod) {
        this.checkMethod = checkMethod;
    }

    public int getCheckTimeout() {
        return checkTimeout;
    }

    public void setCheckTimeout(int checkTimeout) {
        this.checkTimeout = checkTimeout;
    }

    public int getIntInterval() {
        return intInterval;
    }

    public void setIntInterval(int intInterval) {
        this.intInterval = intInterval;
    }

    public int getRoundType() {
        return roundType;
    }

    public void setRoundType(int roundType) {
        this.roundType = roundType;
    }

    public String getIpList() {
        return ipList;
    }

    public void setIpList(String ipList) {
        this.ipList = ipList;
    }

    public String getWeightList() {
        return weightList;
    }

    public void setWeightList(String weightList) {
        this.weightList = weightList;
    }

    public String getForbidList() {
        return forbidList;
    }

    public void setForbidList(String forbidList) {
        this.forbidList = forbidList;
    }

    public int getUpstreamConnectTimeout() {
        return UpstreamConnectTimeout;
    }

    public void setUpstreamConnectTimeout(int upstreamConnectTimeout) {
        UpstreamConnectTimeout = upstreamConnectTimeout;
    }

    public int getUpstreamHeaderTimeout() {
        return UpstreamHeaderTimeout;
    }

    public void setUpstreamHeaderTimeout(int upstreamHeaderTimeout) {
        UpstreamHeaderTimeout = upstreamHeaderTimeout;
    }

    public int getUpstreamIdleTimeout() {
        return UpstreamIdleTimeout;
    }

    public void setUpstreamIdleTimeout(int upstreamIdleTimeout) {
        UpstreamIdleTimeout = upstreamIdleTimeout;
    }

    public int getUpstreamMaxIdle() {
        return UpstreamMaxIdle;
    }

    public void setUpstreamMaxIdle(int upstreamMaxIdle) {
        UpstreamMaxIdle = upstreamMaxIdle;
    }
}
