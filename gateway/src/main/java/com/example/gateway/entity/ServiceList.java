package com.example.gateway.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class ServiceList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private int id;
    @JsonProperty("service_name")
    private String serviceName;
    @JsonProperty("service_desc")
    private String getServiceDesc;
    @JsonProperty("load_type")
    private int loadType;
    @JsonProperty("service_addr")
    private String serviceAddr;
    @JsonProperty("qps")
    private int qps;
    @JsonProperty("qpd")
    private int qpd;
    @JsonProperty("total_node")
    private int totalNade;

    public ServiceList() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getGetServiceDesc() {
        return getServiceDesc;
    }

    public void setGetServiceDesc(String getServiceDesc) {
        this.getServiceDesc = getServiceDesc;
    }

    public int getLoadType() {
        return loadType;
    }

    public void setLoadType(int loadType) {
        this.loadType = loadType;
    }

    public String getServiceAddr() {
        return serviceAddr;
    }

    public void setServiceAddr(String serviceAddr) {
        this.serviceAddr = serviceAddr;
    }

    public int getQps() {
        return qps;
    }

    public void setQps(int qps) {
        this.qps = qps;
    }

    public int getQpd() {
        return qpd;
    }

    public void setQpd(int qpd) {
        this.qpd = qpd;
    }

    public int getTotalNade() {
        return totalNade;
    }

    public void setTotalNade(int totalNade) {
        this.totalNade = totalNade;
    }
}
