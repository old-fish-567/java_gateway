package com.example.gateway.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;

import java.util.List;

public class ServiceListOutput {

    @JsonProperty("total")
    private int total;
    @JsonProperty("list")
    private List<ServiceList> serviceLists;

    public ServiceListOutput() {
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ServiceList> getServiceLists() {
        return serviceLists;
    }

    public void setServiceLists(List<ServiceList> serviceLists) {
        this.serviceLists = serviceLists;
    }
}
