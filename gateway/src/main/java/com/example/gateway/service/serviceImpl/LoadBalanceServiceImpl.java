package com.example.gateway.service.serviceImpl;

import com.example.gateway.entity.ServiceInfo;
import com.example.gateway.mapper.LoadBalanceMapper;
import com.example.gateway.mapper.ServiceInfoMapper;
import com.example.gateway.service.LoadBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoadBalanceServiceImpl implements LoadBalanceService {
    @Autowired
    private LoadBalanceMapper loadBalanceMapper;

}
