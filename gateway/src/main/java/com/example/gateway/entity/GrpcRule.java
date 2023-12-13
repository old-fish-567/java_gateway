package com.example.gateway.entity;

import jakarta.persistence.*;

@Table(name = "gateway_service_grpc_rule")
@Entity
public class GrpcRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int serviceId;
    private int port;
    private String headerTransfor;

    public GrpcRule() {
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

    public String getHeadrTransfor() {
        return headerTransfor;
    }

    public void setHeadrTransfor(String headrTransfor) {
        this.headerTransfor = headrTransfor;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
