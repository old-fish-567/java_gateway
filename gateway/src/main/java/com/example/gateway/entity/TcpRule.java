package com.example.gateway.entity;


import jakarta.persistence.*;


@Table(name = "gateway_service_tcp_rule")
@Entity
public class TcpRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int serviceId;
    private int port;

    public TcpRule() {
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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
