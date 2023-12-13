package com.example.gateway.mapper;

import com.example.gateway.entity.TcpRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TcpRuleMapper extends JpaRepository<TcpRule, Integer> {
    TcpRule findTcpRuleByServiceId(Integer serviceId);
}
