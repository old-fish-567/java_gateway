package com.example.gateway.controller;


import com.example.gateway.entity.*;
import com.example.gateway.mapper.AccessControlMapper;
import com.example.gateway.mapper.HttpRuleMapper;
import com.example.gateway.mapper.LoadBalanceMapper;
import com.example.gateway.mapper.ServiceInfoMapper;
import com.example.gateway.middleware.Translation;
import com.example.gateway.service.LoadBalanceService;
import com.example.gateway.service.ServiceInfoService;
import com.example.gateway.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.gateway.utils.GatewayConstant.*;

@Controller
@RequestMapping("service")
public class ServiceController {
    @Autowired
    private ServiceInfoService serviceInfoService;
    @Autowired
    private LoadBalanceMapper loadBalanceMapper;
    @Autowired
    private ServiceInfoMapper serviceInfoMapper;
    @Autowired
    private Translation translation;
    @Autowired
    private HttpRuleMapper httpRuleMapper;
    @Autowired
    private AccessControlMapper accessControlMapper;


    @GetMapping("/service_list")
    @ResponseBody
    public Result<ServiceListOutput> serviceList(@RequestParam(value = "info", defaultValue = "") String info, @RequestParam("page_size") int pageSize, @RequestParam("page_no") int pageNo) {
        Page<ServiceInfo> serviceInfoPage = serviceInfoService.pageList(info, pageSize, pageNo);

        List<ServiceInfo> serviceInfoList = serviceInfoPage.getContent();
        List<ServiceList> serviceLists = new ArrayList<>();
        int total = 0;

        //格式化输出信息
        for (ServiceInfo serviceInfo : serviceInfoList) {
            ServiceDetail serviceDetail = serviceInfoService.serviceDetail(serviceInfo);
            //1、http后缀接入 clusterIP+clusterPort+path
            //2、http域名接入 domain
            //3、tcp、grpc接入 clusterIP+servicePort
            String serviceAddr = "unknown";
            String clusterIP = cluster_ip;
            String clusterPort = cluster_port;
            String clusterSSLPort = cluster_ssl_port;
            if (serviceDetail != null) {
                if (serviceDetail.getInfo().getLoadType() == loadTypeHTTP && serviceDetail.getHttpRule().getRuleType() == HTTPRuleTypePrefixURL && serviceDetail.getHttpRule().getNeedHttps() == 1) {

                    serviceAddr = clusterIP + ":" + clusterSSLPort + serviceDetail.getHttpRule().getRule();
                }
                if (serviceDetail.getInfo().getLoadType() == loadTypeHTTP && serviceDetail.getHttpRule().getRuleType() == HTTPRuleTypePrefixURL && serviceDetail.getHttpRule().getNeedHttps() == 0) {
                    serviceAddr = clusterIP + ":" + clusterPort + serviceDetail.getHttpRule().getRule();
                }
                if (serviceDetail.getInfo().getLoadType() == loadTypeHTTP && serviceDetail.getHttpRule().getRuleType() == HTTPRuleTypeDomain) {
                    serviceAddr = serviceDetail.getHttpRule().getRule();
                }
                if (serviceDetail.getInfo().getLoadType() == LoadTypeTCP) {
                    serviceAddr = clusterIP + ":" + serviceDetail.getTcpRule().getPort();
                }
                if (serviceDetail.getInfo().getLoadType() == loadTypeGRPC) {
                    serviceAddr = clusterIP + ":" + serviceDetail.getGrpcRule().getPort();
                }
            }

            ServiceList serviceList = new ServiceList();
            serviceList.setId(serviceInfo.getId());
            serviceList.setServiceName(serviceInfo.getServiceName());
            serviceList.setLoadType(serviceInfo.getLoadType());
            serviceList.setGetServiceDesc(serviceInfo.getServiceDesc());
            serviceList.setServiceAddr(serviceAddr);
            serviceList.setQpd(0);
            serviceList.setQps(0);
            if (serviceDetail != null) {
                LoadBalance loadBalance = serviceDetail.getLoadBalance();
                String[] IpList = loadBalance.getIpList().split(",");
                serviceList.setTotalNade(IpList.length);
            } else {
                serviceList.setTotalNade(0);
            }
            serviceLists.add(serviceList);
        }
        ServiceListOutput serviceListOutput = new ServiceListOutput();
        serviceListOutput.setTotal(serviceLists.size());
        serviceListOutput.setServiceLists(serviceLists);

        return Result.success(serviceListOutput, "成功");
    }


    @GetMapping("/service_delete")
    public Result<ServiceInfo> ServiceDelete(@RequestParam(name = "id") int serviceID) {
        ServiceInfo serviceInfo = serviceInfoService.deleteServiceInfo(serviceID);
        if (serviceInfo != null) {
            return Result.success(serviceInfo, "删除成功");
        }
        return Result.error("222", "删除失败");
    }

    @PostMapping("/service_add_http")
    @ResponseBody
    public Result ServiceAddHTTP(@RequestBody ServiceAddHTTPInput serviceAddHTTPInput) {
        String msg = "";
        if (!translation.validServiceName(serviceAddHTTPInput.getServiceName())) {
            msg += "服务名输入非法 ";
        }
        if (serviceAddHTTPInput.getServiceDesc().isEmpty()) {
            msg += "服务描述必填 ";
        }
        if (serviceAddHTTPInput.getIpList().isEmpty()) {
            msg += "IP列表必填 ";
        }
        if (!translation.validIPList(serviceAddHTTPInput.getIpList())) {
            msg += "IP列表输入非法 ";
        }
        if (!translation.validWeightList(serviceAddHTTPInput.getWeightList())) {
            msg += "权重列表输入非法 ";
        }
        if (!translation.validRule(serviceAddHTTPInput.getRule())) {
            msg += "域名或前缀输入非法 ";
        }
        if (!translation.validHeaderTransform(serviceAddHTTPInput.getHeaderTransfor())) {
            msg += "header转化输入非法 ";
        }
        if (!translation.validURLRewrite(serviceAddHTTPInput.getUrlRewrite())) {
            msg += "URL重写输入非法 ";
        }

        if (!msg.isEmpty()) {
            return Result.error("2222", msg.trim());
        }

        if (serviceAddHTTPInput.getIpList().split(",").length != serviceAddHTTPInput.getWeightList().split(",").length) {
            return Result.error("1111", "IP列表与权重列表数量不一致");
        }
        ServiceInfo serviceInfo = serviceInfoService.find(serviceAddHTTPInput.getServiceName());
        if (serviceInfo != null) {
            return Result.error("11", "服务已存在");
        }
        HttpRule httpRule = httpRuleMapper.findHttpRuleByRuleTypeAndRule(serviceAddHTTPInput.getRuleType(), serviceAddHTTPInput.getRule());
        if (httpRule != null) {
            return Result.error("2233", "服务接入前缀或域名已存在");
        }

        //往数据库中保存数据
        return saveServiceInfo(serviceAddHTTPInput);
    }

    @Transactional(rollbackFor = Exception.class)
    public Result saveServiceInfo(ServiceAddHTTPInput serviceAddHTTPInput) {
        // 往数据库中保存数据的代码段
        try {
            // 往数据库中保存数据的代码段
            ServiceInfo serviceInfo1 = new ServiceInfo();
            serviceInfo1.setServiceName(serviceAddHTTPInput.getServiceName());
            serviceInfo1.setServiceDesc(serviceAddHTTPInput.getServiceDesc());
            serviceInfo1.setCreateAt(new Date());
            serviceInfo1.setUpdateAt(new Date());
            serviceInfoService.save(serviceInfo1);

            HttpRule httpRule1 = new HttpRule();
            httpRule1.setServiceId(serviceInfo1.getId());
            httpRule1.setRuleType(serviceAddHTTPInput.getRuleType());
            httpRule1.setRule(serviceAddHTTPInput.getRule());
            httpRule1.setNeedHttps(serviceAddHTTPInput.getNeedHttps());
            httpRule1.setNeedStripUri(serviceAddHTTPInput.getNeedStripUri());
            httpRule1.setNeedWebsocket(serviceAddHTTPInput.getNeedWebsocket());
            httpRule1.setUrlRewrite(serviceAddHTTPInput.getUrlRewrite());
            httpRule1.setHeaderTransfor(serviceAddHTTPInput.getHeaderTransfor());
            httpRuleMapper.save(httpRule1);

            AccessControl accessControl = new AccessControl();
            accessControl.setServiceId(serviceInfo1.getId());
            accessControl.setOpenAuth(serviceAddHTTPInput.getOpenAuth());
            accessControl.setBlackList(serviceAddHTTPInput.getBlackList());
            accessControl.setWhiteList(serviceAddHTTPInput.getWhiteList());
            accessControl.setClientIPFlowLimit(serviceAddHTTPInput.getClientIpFlowLimit());
            accessControl.setServiceFlowLimit(serviceAddHTTPInput.getServiceFlowLimit());
            accessControl.setWhiteHostName("");
            accessControlMapper.save(accessControl);

            LoadBalance loadBalance = new LoadBalance();
            loadBalance.setServiceId(serviceInfo1.getId());
            loadBalance.setRoundType(serviceAddHTTPInput.getRoundType());
            loadBalance.setIpList(serviceAddHTTPInput.getIpList());
            loadBalance.setWeightList(serviceAddHTTPInput.getWeightList());
            loadBalance.setUpstreamConnectTimeout(serviceAddHTTPInput.getUpstreamConnectTimeout());
            loadBalance.setUpstreamHeaderTimeout(serviceAddHTTPInput.getUpstreamHeaderTimeout());
            loadBalance.setUpstreamIdleTimeout(serviceAddHTTPInput.getUpstreamIdleTimeout());
            loadBalance.setUpstreamMaxIdle(serviceAddHTTPInput.getUpstreamMaxIdle());
            loadBalance.setForbidList("");
            loadBalanceMapper.save(loadBalance);

            return Result.success("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("保存失败"); // 抛出运行时异常触发回滚
        }
    }

}
