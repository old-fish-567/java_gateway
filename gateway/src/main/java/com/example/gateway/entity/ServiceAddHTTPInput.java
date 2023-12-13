package com.example.gateway.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Bean;

import java.io.Serializable;

public class ServiceAddHTTPInput implements Serializable {

    @JsonProperty("service_name")
    private String serviceName;
    @JsonProperty("service_desc")
    private String serviceDesc;
    @JsonProperty("rule_type")
    private int ruleType;
    @JsonProperty("rule")
    private String rule;
    @JsonProperty("need_https")
    private int needHttps;
    @JsonProperty("need_strip_uri")
    private int needStripUri;
    @JsonProperty("need_websocket")
    private int needWebsocket;
    @JsonProperty("rule_rewrite")
    private String UrlRewrite;
    @JsonProperty("header_transfor")
    private String headerTransfor;
    @JsonProperty("open_auth")
    private int openAuth;
    @JsonProperty("black_list")
    private String blackList;
    @JsonProperty("white_list")
    private String whiteList;
    @JsonProperty("clientip_flow_limit")
    private int clientIpFlowLimit;
    @JsonProperty("service_flow_limit")
    private int serviceFlowLimit;
    @JsonProperty("round_type")
    private int roundType;
    @JsonProperty("ip_list")
    private String ipList;
    @JsonProperty("weight_list")
    private String weightList;
    @JsonProperty("upstream_header_timeout")
    private int upstreamHeaderTimeout;
    @JsonProperty("upstream_connect_timeout")
    private int upstreamConnectTimeout;
    @JsonProperty("upstream_idle_timeout")
    private int upstreamIdleTimeout;
    @JsonProperty("upstream_max_idle")
    private int upstreamMaxIdle;

    public ServiceAddHTTPInput() {
    }

    public ServiceAddHTTPInput(String serviceName, String serviceDesc, int ruleType, String rule, int needHttps, int needStripUri, int needWebsocket, String urlRewrite, String headerTransfor, int openAuth, String blackList, String whiteList, int clientIpFlowLimit, int serviceFlowLimit, int roundType, String ipList, String weightList, int upstreamHeaderTimeout, int upstreamConnectTimeout, int upstreamIdleTimeout, int upstreamMaxIdle) {
        this.serviceName = serviceName;
        this.serviceDesc = serviceDesc;
        this.ruleType = ruleType;
        this.rule = rule;
        this.needHttps = needHttps;
        this.needStripUri = needStripUri;
        this.needWebsocket = needWebsocket;
        UrlRewrite = urlRewrite;
        this.headerTransfor = headerTransfor;
        this.openAuth = openAuth;
        this.blackList = blackList;
        this.whiteList = whiteList;
        this.clientIpFlowLimit = clientIpFlowLimit;
        this.serviceFlowLimit = serviceFlowLimit;
        this.roundType = roundType;
        this.ipList = ipList;
        this.weightList = weightList;
        this.upstreamHeaderTimeout = upstreamHeaderTimeout;
        this.upstreamConnectTimeout = upstreamConnectTimeout;
        this.upstreamIdleTimeout = upstreamIdleTimeout;
        this.upstreamMaxIdle = upstreamMaxIdle;
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

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
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

    public int getNeedStripUri() {
        return needStripUri;
    }

    public void setNeedStripUri(int needStripUri) {
        this.needStripUri = needStripUri;
    }

    public int getNeedWebsocket() {
        return needWebsocket;
    }

    public void setNeedWebsocket(int needWebsocket) {
        this.needWebsocket = needWebsocket;
    }

    public String getUrlRewrite() {
        return UrlRewrite;
    }

    public void setUrlRewrite(String urlRewrite) {
        UrlRewrite = urlRewrite;
    }

    public String getHeaderTransfor() {
        return headerTransfor;
    }

    public void setHeaderTransfor(String headerTransfor) {
        this.headerTransfor = headerTransfor;
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

    public int getClientIpFlowLimit() {
        return clientIpFlowLimit;
    }

    public void setClientIpFlowLimit(int clientIpFlowLimit) {
        this.clientIpFlowLimit = clientIpFlowLimit;
    }

    public int getServiceFlowLimit() {
        return serviceFlowLimit;
    }

    public void setServiceFlowLimit(int serviceFlowLimit) {
        this.serviceFlowLimit = serviceFlowLimit;
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

    public int getUpstreamConnectTimeout() {
        return upstreamConnectTimeout;
    }

    public int getUpstreamHeaderTimeout() {
        return upstreamHeaderTimeout;
    }

    public void setUpstreamHeaderTimeout(int upstreamHeaderTimeout) {
        this.upstreamHeaderTimeout = upstreamHeaderTimeout;
    }

    public void setUpstreamConnectTimeout(int upstreamConnectTimeout) {
        this.upstreamConnectTimeout = upstreamConnectTimeout;
    }

    public int getUpstreamIdleTimeout() {
        return upstreamIdleTimeout;
    }

    public void setUpstreamIdleTimeout(int upstreamIdleTimeout) {
        this.upstreamIdleTimeout = upstreamIdleTimeout;
    }

    public int getUpstreamMaxIdle() {
        return upstreamMaxIdle;
    }

    public void setUpstreamMaxIdle(int upstreamMaxIdle) {
        this.upstreamMaxIdle = upstreamMaxIdle;
    }
}
