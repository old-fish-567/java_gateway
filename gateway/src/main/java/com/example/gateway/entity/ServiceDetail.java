package com.example.gateway.entity;

import jakarta.persistence.Entity;

public class ServiceDetail {
    private ServiceInfo info;
    private HttpRule httpRule;
    private TcpRule tcpRule;
    private GrpcRule grpcRule;
    private LoadBalance loadBalance;
    private AccessControl accessControl;


    public ServiceDetail() {
    }

    public ServiceDetail(ServiceInfo info, HttpRule httpRule, TcpRule tcpRule, GrpcRule grpcRule, LoadBalance loadBalance, AccessControl accessControl) {
        this.info = info;
        this.httpRule = httpRule;
        this.tcpRule = tcpRule;
        this.grpcRule = grpcRule;
        this.loadBalance = loadBalance;
        this.accessControl = accessControl;
    }

    public ServiceInfo getInfo() {
        return info;
    }

    public void setInfo(ServiceInfo info) {
        this.info = info;
    }

    public HttpRule getHttpRule() {
        return httpRule;
    }

    public void setHttpRule(HttpRule httpRule) {
        this.httpRule = httpRule;
    }

    public TcpRule getTcpRule() {
        return tcpRule;
    }

    public void setTcpRule(TcpRule tcpRule) {
        this.tcpRule = tcpRule;
    }

    public GrpcRule getGrpcRule() {
        return grpcRule;
    }

    public void setGrpcRule(GrpcRule grpcRule) {
        this.grpcRule = grpcRule;
    }

    public LoadBalance getLoadBalance() {
        return loadBalance;
    }

    public void setLoadBalance(LoadBalance loadBalance) {
        this.loadBalance = loadBalance;
    }

    public AccessControl getAccessControl() {
        return accessControl;
    }

    public void setAccessControl(AccessControl accessControl) {
        this.accessControl = accessControl;
    }
}
