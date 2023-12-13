package com.example.gateway.entity;

import jakarta.persistence.*;


@Table(name = "gateway_service_http_rule")

@Entity
public class HttpRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int serviceId;
    private int ruleType;
    private String rule;
    private int needHttps;
    private int needWebsocket;
    private int needStripUri;
    private String urlRewrite;
    private String headerTransfor;

    public HttpRule() {
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

    public int getRuleType() {
        return ruleType;
    }

    public void setRuleType(int ruleType) {
        this.ruleType = ruleType;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public int getNeedHttps() {
        return needHttps;
    }

    public void setNeedHttps(int needHttps) {
        this.needHttps = needHttps;
    }

    public int getNeedWebsocket() {
        return needWebsocket;
    }

    public void setNeedWebsocket(int needWebsocket) {
        this.needWebsocket = needWebsocket;
    }

    public int getNeedStripUri() {
        return needStripUri;
    }

    public void setNeedStripUri(int needStripUri) {
        this.needStripUri = needStripUri;
    }

    public String getUrlRewrite() {
        return urlRewrite;
    }

    public void setUrlRewrite(String urlRewrite) {
        this.urlRewrite = urlRewrite;
    }

    public String getHeaderTransfor() {
        return headerTransfor;
    }

    public void setHeaderTransfor(String headerTransfor) {
        this.headerTransfor = headerTransfor;
    }
}
