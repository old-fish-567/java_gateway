package com.example.gateway.mapper;

import com.example.gateway.entity.GrpcRule;
import com.example.gateway.entity.LoadBalance;
import com.example.gateway.entity.ServiceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadBalanceMapper extends JpaRepository<LoadBalance, Integer> {
    LoadBalance findLoadBalanceByServiceId(Integer serviceId);

}
