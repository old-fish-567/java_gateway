package com.example.gateway.mapper;

import com.example.gateway.entity.AccessControl;
import com.example.gateway.entity.GrpcRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessControlMapper extends JpaRepository<AccessControl, Integer> {
    AccessControl findAccessControlByServiceId(Integer serviceId);
}
