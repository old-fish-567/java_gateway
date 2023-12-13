package com.example.gateway.service.serviceImpl;

import com.example.gateway.entity.HttpRule;
import com.example.gateway.mapper.HttpRuleMapper;
import com.example.gateway.service.HttpRuleService;
import org.springframework.beans.factory.annotation.Autowired;

public class HttpRuleServiceImpl implements HttpRuleService {
    @Autowired
    HttpRuleMapper httpRuleMapper;
    @Override
    public HttpRule findHttpRuleById(int id) {
        return httpRuleMapper.getReferenceById(id);
    }
}
