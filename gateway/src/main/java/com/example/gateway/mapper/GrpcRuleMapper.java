package com.example.gateway.mapper;

import com.example.gateway.entity.GrpcRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrpcRuleMapper extends JpaRepository<GrpcRule, Integer> {
    GrpcRule findGrpcRuleByServiceId(Integer serviceId);
    GrpcRule findGrpcByPort(int port);
}
