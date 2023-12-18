package com.example.gateway.service.serviceImpl;


import com.example.gateway.entity.TcpRule;
import com.example.gateway.mapper.TcpRuleMapper;
import com.example.gateway.service.TcpRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TcpRuleServiceImpl implements TcpRuleService {
    @Autowired
    private TcpRuleMapper tcpRuleMapper;


    @Override
    public TcpRule findByPort(int port) {
        return tcpRuleMapper.findTcpByPort(port);
    }
}
