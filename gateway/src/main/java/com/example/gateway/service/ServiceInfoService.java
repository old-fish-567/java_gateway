package com.example.gateway.service;

import com.example.gateway.entity.ServiceDetail;
import com.example.gateway.entity.ServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface ServiceInfoService {
    ServiceInfo find(String serviceName);
    ServiceInfo find(int serviceID);

    ServiceInfo find(String serviceName, String serviceDesc);

    ServiceInfo save(ServiceInfo serviceInfo);

    Page<ServiceInfo> pageList(String info, int pageNo, int pageSize);

    ServiceDetail serviceDetail(ServiceInfo serviceInfo);

    ServiceInfo deleteServiceInfo(int serviceId);




}
