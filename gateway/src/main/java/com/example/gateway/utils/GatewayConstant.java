package com.example.gateway.utils;

public interface GatewayConstant {
    /*
    cluster
     */
    String cluster_ip = "127.0.0.1";
    String cluster_port = "8880";
    String cluster_ssl_port = "4880";

    int loadTypeHTTP = 0;
    int LoadTypeTCP = 1;
    int loadTypeGRPC = 2;

    int HTTPRuleTypePrefixURL = 0;
    int HTTPRuleTypeDomain = 1;
}
