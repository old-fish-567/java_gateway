package com.example.gateway.service.serviceImpl;

import com.example.gateway.entity.*;
import com.example.gateway.mapper.*;
import com.example.gateway.service.ServiceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
public class ServiceInfoServiceImpl implements ServiceInfoService {
    @Autowired
    private ServiceInfoMapper serviceInfoMapper;
    @Autowired
    private HttpRuleMapper httpRuleMapper;
    @Autowired
    private TcpRuleMapper tcpRuleMapper;
    @Autowired
    private GrpcRuleMapper grpcRuleMapper;
    @Autowired
    private AccessControlMapper accessControlMapper;
    @Autowired
    private LoadBalanceMapper loadBalanceMapper;

    @Override
    public ServiceInfo find(String serviceName) {
        return serviceInfoMapper.findByServiceNameAndIsDelete(serviceName, 0);
    }

    @Override
    public ServiceInfo find(int serviceID) {
        return serviceInfoMapper.findById(serviceID);
    }

    @Override
    public ServiceInfo find(String serviceName, String serviceDesc) {
        return serviceInfoMapper.findByServiceNameOrServiceDesc(serviceName, serviceDesc);
    }

    @Override
    public ServiceInfo save(ServiceInfo serviceInfo) {
        return serviceInfoMapper.save(serviceInfo);
    }

    @Override
    public Page<ServiceInfo> pageList(String info, int pageSize, int pageNo) {
        // 构造分页请求
        PageRequest pageRequest = PageRequest.of(pageNo - 1, pageSize);
        // 调用 Spring Data JPA 的查询方法
//        Page<ServiceInfo> resultPage = serviceInfoMapper.findPagedAndFiltered(pageRequest);
        Page<ServiceInfo> resultPage = serviceInfoMapper.findPagedAndFiltered(info, pageRequest);

        return resultPage;
    }

    @Override
    public ServiceDetail serviceDetail(ServiceInfo serviceInfo) {
        HttpRule httpRule = httpRuleMapper.findHttpRuleByServiceId(serviceInfo.getId());
        TcpRule tcpRule = tcpRuleMapper.findTcpRuleByServiceId(serviceInfo.getId());
        GrpcRule grpcRule = grpcRuleMapper.findGrpcRuleByServiceId(serviceInfo.getId());
        AccessControl accessControl = accessControlMapper.findAccessControlByServiceId(serviceInfo.getId());
        LoadBalance loadBalance = loadBalanceMapper.findLoadBalanceByServiceId(serviceInfo.getId());
        ServiceDetail serviceDetail = new ServiceDetail(serviceInfo, httpRule, tcpRule, grpcRule, loadBalance, accessControl);
        return serviceDetail;
    }


    @Override
    public ServiceInfo deleteServiceInfo(int serviceId) {
        ServiceInfo serviceInfo = serviceInfoMapper.findById(serviceId);
        serviceInfo.setIsDelete(1);
        return serviceInfoMapper.save(serviceInfo);
    }
}
