package com.example.gateway.service;


import com.example.gateway.entity.GrpcRule;
import org.springframework.stereotype.Service;

@Service
public interface GrpcRuleService {
    GrpcRule findByPort(int port);
}
