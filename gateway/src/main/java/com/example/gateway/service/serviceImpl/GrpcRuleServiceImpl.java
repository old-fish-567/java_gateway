package com.example.gateway.service.serviceImpl;

import com.example.gateway.entity.GrpcRule;
import com.example.gateway.mapper.GrpcRuleMapper;
import com.example.gateway.service.GrpcRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GrpcRuleServiceImpl implements GrpcRuleService {
    @Autowired
    private GrpcRuleMapper grpcRuleMapper;
    @Override
    public GrpcRule findByPort(int port) {
        return grpcRuleMapper.findGrpcByPort(port);
    }
}
