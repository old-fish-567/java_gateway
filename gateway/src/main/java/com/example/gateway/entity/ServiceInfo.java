package com.example.gateway.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Date;

@Table(name = "gateway_service_info")
@Entity
public class ServiceInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String serviceName;
    private String serviceDesc;
    private int loadType;
    private Date updateAt;
    private Date createAt;
    private int isDelete;

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

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String getServiceDesc) {
        this.serviceDesc = getServiceDesc;
    }

    public int getLoadType() {
        return loadType;
    }

    public void setLoadType(int loadType) {
        this.loadType = loadType;
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
}
