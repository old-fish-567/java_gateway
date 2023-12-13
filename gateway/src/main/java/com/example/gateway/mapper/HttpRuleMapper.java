package com.example.gateway.mapper;

import com.example.gateway.entity.HttpRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HttpRuleMapper extends JpaRepository<HttpRule, Integer> {
    HttpRule findHttpRuleByServiceId(Integer id);
    HttpRule findHttpRuleByRuleTypeAndRule(int ruleType, String rule);
}
