package com.example.gateway.service;

import com.example.gateway.entity.TcpRule;
import org.springframework.stereotype.Service;

@Service
public interface TcpRuleService {
    TcpRule findByPort(int port);

}
