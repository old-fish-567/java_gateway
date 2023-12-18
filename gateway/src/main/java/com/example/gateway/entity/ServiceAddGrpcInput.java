package com.example.gateway.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ServiceAddGrpcInput implements Serializable {

    @JsonProperty("service_name")
    private String serviceName;
    @JsonProperty("service_desc")
    private String serviceDesc;
    @JsonProperty("port")
    private int port;
    @JsonProperty("header_transfor")
    private String headerTransfor;
    @JsonProperty("open_auth")
    private int openAuth;
    @JsonProperty("black_list")
    private String blackList;
    @JsonProperty("white_list")
    private String whiteList;
    @JsonProperty("white_host_name")
    private String whiteHostName;
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
    @JsonProperty("forbid_list")
    private String forbidList;


    public ServiceAddGrpcInput() {
    }

    public ServiceAddGrpcInput(String serviceName, String serviceDesc, int port, String headerTransfor, int openAuth, String blackList, String whiteList, String whiteHostName, int ruleType, String rule, int needHttps, int needStripUri, int needWebsocket, String urlRewrite, int clientIpFlowLimit, int serviceFlowLimit, int roundType, String ipList, String weightList, String forbidList) {
        this.serviceName = serviceName;
        this.serviceDesc = serviceDesc;
        this.port = port;
        this.headerTransfor = headerTransfor;
        this.openAuth = openAuth;
        this.blackList = blackList;
        this.whiteList = whiteList;
        this.whiteHostName = whiteHostName;
        this.clientIpFlowLimit = clientIpFlowLimit;
        this.serviceFlowLimit = serviceFlowLimit;
        this.roundType = roundType;
        this.ipList = ipList;
        this.weightList = weightList;
        forbidList = forbidList;
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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getWhiteHostName() {
        return whiteHostName;
    }

    public void setWhiteHostName(String whiteHostName) {
        this.whiteHostName = whiteHostName;
    }

    public String getForbidList() {
        return forbidList;
    }

    public void setForbidList(String forbidList) {
        forbidList = forbidList;
    }
}
