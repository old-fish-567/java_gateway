package com.example.gateway.controller;


import com.example.gateway.entity.*;
import com.example.gateway.mapper.*;
import com.example.gateway.middleware.Translation;
import com.example.gateway.service.GrpcRuleService;
import com.example.gateway.service.LoadBalanceService;
import com.example.gateway.service.ServiceInfoService;
import com.example.gateway.service.TcpRuleService;
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
    private TcpRuleService tcpRuleService;
    @Autowired
    private TcpRuleMapper tcpRuleMapper;
    @Autowired
    private GrpcRuleService grpcRuleService;
    @Autowired
    private GrpcRuleMapper grpcRuleMapper;
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
    @ResponseBody
    public Result<ServiceInfo> ServiceDelete(@RequestParam(name = "id") int serviceID) {
        ServiceInfo serviceInfo = serviceInfoService.deleteServiceInfo(serviceID);
        if (serviceInfo != null) {
            return Result.success(serviceInfo, "删除成功");
        }
        return Result.error("222", "删除失败");
    }

    @GetMapping("/service_detail")
    @ResponseBody
    public Result<ServiceDetail> ServiceDetail(@RequestParam(name = "id" ) int serviceID) {
        ServiceInfo serviceInfo = serviceInfoService.find(serviceID);
        if (serviceInfo == null) {
            return Result.error("111", "未找到服务");
        }
        ServiceDetail serviceDetail = serviceInfoService.serviceDetail(serviceInfo);
        if (serviceDetail == null) {
            return Result.error("111", "未找到服务");
        }
        return Result.success(serviceDetail, "查找成功");
    }

    @GetMapping("/service_stat")
    @ResponseBody
    public Result<ServiceStatOutput> ServiceStat(@RequestParam(name = "id" ) int serviceID) {
//        ServiceInfo serviceInfo = serviceInfoService.find(serviceID);
//        if (serviceInfo == null) {
//            return Result.error("111", "未找到服务");
//        }
//        ServiceDetail serviceDetail = serviceInfoService.serviceDetail(serviceInfo);
//        if (serviceDetail == null) {
//            return Result.error("111", "未找到服务");
//        }

        int currentHour = new Date().getHours();
        List<Integer> todayList = new ArrayList<>();
        for (int i = 0; i <= currentHour; i++) {
            todayList.add(0);
        }
        List<Integer> yesterdayList = new ArrayList<>();
        for (int i = 0; i <= 23; i++) {
            yesterdayList.add(0);
        }
        ServiceStatOutput serviceStatOutput = new ServiceStatOutput(todayList, yesterdayList);

        return Result.success(serviceStatOutput, "成功");
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


    @PostMapping("/service_update_http")
    @ResponseBody
    public Result ServiceUpdateHTTP(@RequestBody ServiceUpdateHTTPInput serviceUpdateHTTPInput) {
        String msg = "";
        if (!translation.validServiceName(serviceUpdateHTTPInput.getServiceName())) {
            msg += "服务名输入非法 ";
        }
        if (serviceUpdateHTTPInput.getServiceDesc().isEmpty()) {
            msg += "服务描述必填 ";
        }
        if (serviceUpdateHTTPInput.getIpList().isEmpty()) {
            msg += "IP列表必填 ";
        }
        if (!translation.validIPList(serviceUpdateHTTPInput.getIpList())) {
            msg += "IP列表输入非法 ";
        }
        if (!translation.validWeightList(serviceUpdateHTTPInput.getWeightList())) {
            msg += "权重列表输入非法 ";
        }
        if (!translation.validRule(serviceUpdateHTTPInput.getRule())) {
            msg += "域名或前缀输入非法 ";
        }
        if (!translation.validHeaderTransform(serviceUpdateHTTPInput.getHeaderTransfor())) {
            msg += "header转化输入非法 ";
        }
        if (!translation.validURLRewrite(serviceUpdateHTTPInput.getUrlRewrite())) {
            msg += "URL重写输入非法 ";
        }

        if (!msg.isEmpty()) {
            return Result.error("2222", msg.trim());
        }

        if (serviceUpdateHTTPInput.getIpList().split(",").length != serviceUpdateHTTPInput.getWeightList().split(",").length) {
            return Result.error("1111", "IP列表与权重列表数量不一致");
        }


        ServiceInfo serviceInfo = serviceInfoService.find(serviceUpdateHTTPInput.getServiceName());
        if (serviceInfo == null) {
            return Result.error("11", "服务不存在");
        }
        ServiceDetail serviceDetail = serviceInfoService.serviceDetail(serviceInfo);
        if (serviceDetail == null) {
            return Result.error("11", "服务不存在");
        }
        //往数据库中更新数据
        return updateServiceInfo(serviceDetail, serviceUpdateHTTPInput);
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

    @Transactional(rollbackFor = Exception.class)
    public Result updateServiceInfo(ServiceDetail serviceDetail, ServiceUpdateHTTPInput serviceUpdateHTTPInput) {

        try {
            // 往数据库中保存数据的代码段
            ServiceInfo info = serviceDetail.getInfo();
            info.setServiceDesc(serviceUpdateHTTPInput.getServiceDesc());
            info.setUpdateAt(new Date());
            serviceInfoService.save(info);

            HttpRule httpRule1 = serviceDetail.getHttpRule();
            httpRule1.setNeedHttps(serviceUpdateHTTPInput.getNeedHttps());
            httpRule1.setNeedStripUri(serviceUpdateHTTPInput.getNeedStripUri());
            httpRule1.setNeedWebsocket(serviceUpdateHTTPInput.getNeedWebsocket());
            httpRule1.setUrlRewrite(serviceUpdateHTTPInput.getUrlRewrite());
            httpRule1.setHeaderTransfor(serviceUpdateHTTPInput.getHeaderTransfor());
            httpRuleMapper.save(httpRule1);

            AccessControl accessControl = serviceDetail.getAccessControl();
            accessControl.setOpenAuth(serviceUpdateHTTPInput.getOpenAuth());
            accessControl.setBlackList(serviceUpdateHTTPInput.getBlackList());
            accessControl.setWhiteList(serviceUpdateHTTPInput.getWhiteList());
            accessControl.setClientIPFlowLimit(serviceUpdateHTTPInput.getClientIpFlowLimit());
            accessControl.setServiceFlowLimit(serviceUpdateHTTPInput.getServiceFlowLimit());
            accessControlMapper.save(accessControl);

            LoadBalance loadBalance = serviceDetail.getLoadBalance();
            loadBalance.setRoundType(serviceUpdateHTTPInput.getRoundType());
            loadBalance.setIpList(serviceUpdateHTTPInput.getIpList());
            loadBalance.setWeightList(serviceUpdateHTTPInput.getWeightList());
            loadBalance.setUpstreamConnectTimeout(serviceUpdateHTTPInput.getUpstreamConnectTimeout());
            loadBalance.setUpstreamHeaderTimeout(serviceUpdateHTTPInput.getUpstreamHeaderTimeout());
            loadBalance.setUpstreamIdleTimeout(serviceUpdateHTTPInput.getUpstreamIdleTimeout());
            loadBalance.setUpstreamMaxIdle(serviceUpdateHTTPInput.getUpstreamMaxIdle());
            loadBalanceMapper.save(loadBalance);
            return Result.success("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("保存失败"); // 抛出运行时异常触发回滚
        }
    }

    @PostMapping("/service_add_grpc")
    @ResponseBody
    public Result ServiceAddGrpc(@RequestBody ServiceAddGrpcInput serviceAddGrpcInput) {
        String msg = "";
        if (!translation.validServiceName(serviceAddGrpcInput.getServiceName())) {
            msg += "服务名输入非法 ";
        }
        if (serviceAddGrpcInput.getServiceDesc().isEmpty()) {
            msg += "服务描述必填 ";
        }
        if (serviceAddGrpcInput.getIpList().isEmpty()) {
            msg += "IP列表必填 ";
        }
        if (!translation.validIPList(serviceAddGrpcInput.getIpList())) {
            msg += "IP列表输入非法 ";
        }
        if (!translation.validWeightList(serviceAddGrpcInput.getWeightList())) {
            msg += "权重列表输入非法 ";
        }
        if (!translation.validHeaderTransform(serviceAddGrpcInput.getHeaderTransfor())) {
            msg += "header转化输入非法 ";
        }
        if (!msg.isEmpty()) {
            return Result.error("2222", msg.trim());
        }


        ServiceInfo serviceInfo = serviceInfoService.find(serviceAddGrpcInput.getServiceName());
        if (serviceInfo != null) {
            return Result.error("11", "服务已存在");
        }
        TcpRule tcpRule = tcpRuleService.findByPort(serviceAddGrpcInput.getPort());
        if (tcpRule != null) {
            return Result.error("111", "端口已占用");
        }
        GrpcRule grpcRule = grpcRuleService.findByPort(serviceAddGrpcInput.getPort());
        if (grpcRule != null) {
            return Result.error("111", "端口已占用");
        }
        if (serviceAddGrpcInput.getIpList().split(",").length != serviceAddGrpcInput.getWeightList().split(",").length) {
            return Result.error("1111", "IP列表与权重列表数量不一致");
        }

        //往数据库中保存数据
        return saveServiceInfo(serviceAddGrpcInput);
    }
    @Transactional(rollbackFor = Exception.class)
    public Result saveServiceInfo(ServiceAddGrpcInput serviceAddGrpcInput) {
        try {
            // 往数据库中保存数据的代码段
            ServiceInfo serviceInfo1 = new ServiceInfo();
            serviceInfo1.setLoadType(serviceAddGrpcInput.getRoundType());
            serviceInfo1.setServiceName(serviceAddGrpcInput.getServiceName());
            serviceInfo1.setServiceDesc(serviceAddGrpcInput.getServiceDesc());
            serviceInfo1.setCreateAt(new Date());
            serviceInfo1.setUpdateAt(new Date());
            serviceInfoService.save(serviceInfo1);

            GrpcRule grpcRule = new GrpcRule();
            grpcRule.setServiceId(serviceInfo1.getId());
            grpcRule.setPort(serviceAddGrpcInput.getPort());
            grpcRule.setHeadrTransfor(serviceAddGrpcInput.getHeaderTransfor());
            grpcRuleMapper.save(grpcRule);


            AccessControl accessControl = new AccessControl();
            accessControl.setServiceId(serviceInfo1.getId());
            accessControl.setOpenAuth(serviceAddGrpcInput.getOpenAuth());
            accessControl.setBlackList(serviceAddGrpcInput.getBlackList());
            accessControl.setWhiteList(serviceAddGrpcInput.getWhiteList());
            accessControl.setClientIPFlowLimit(serviceAddGrpcInput.getClientIpFlowLimit());
            accessControl.setServiceFlowLimit(serviceAddGrpcInput.getServiceFlowLimit());
            accessControl.setWhiteHostName(serviceAddGrpcInput.getWhiteHostName());
            accessControlMapper.save(accessControl);

            LoadBalance loadBalance = new LoadBalance();
            loadBalance.setServiceId(serviceInfo1.getId());
            loadBalance.setRoundType(serviceAddGrpcInput.getRoundType());
            loadBalance.setIpList(serviceAddGrpcInput.getIpList());
            loadBalance.setWeightList(serviceAddGrpcInput.getWeightList());
            if (serviceAddGrpcInput.getForbidList() == null) {
                loadBalance.setForbidList("");
            } else {
                loadBalance.setForbidList(serviceAddGrpcInput.getForbidList());
            }
            loadBalanceMapper.save(loadBalance);

            return Result.success("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("保存失败"); // 抛出运行时异常触发回滚
        }
    }

    @PostMapping("/service_update_grpc")
    @ResponseBody
    public Result ServiceUpdateGrpc(@RequestBody ServiceUpdateGrpcInput serviceUpdateGrpcInput) {
        String msg = "";
        if (!translation.validServiceName(serviceUpdateGrpcInput.getServiceName())) {
            msg += "服务名输入非法 ";
        }
        if (serviceUpdateGrpcInput.getServiceDesc().isEmpty()) {
            msg += "服务描述必填 ";
        }
        if (serviceUpdateGrpcInput.getIpList().isEmpty()) {
            msg += "IP列表必填 ";
        }
        if (!translation.validIPList(serviceUpdateGrpcInput.getIpList())) {
            msg += "IP列表输入非法 ";
        }
        if (!translation.validWeightList(serviceUpdateGrpcInput.getWeightList())) {
            msg += "权重列表输入非法 ";
        }
        if (!translation.validHeaderTransform(serviceUpdateGrpcInput.getHeaderTransfor())) {
            msg += "header转化输入非法 ";
        }
        if (!msg.isEmpty()) {
            return Result.error("2222", msg.trim());
        }

        if (serviceUpdateGrpcInput.getIpList().split(",").length != serviceUpdateGrpcInput.getWeightList().split(",").length) {
            return Result.error("1111", "IP列表与权重列表数量不一致");
        }


        ServiceInfo serviceInfo = serviceInfoService.find(serviceUpdateGrpcInput.getServiceName());
        if (serviceInfo == null) {
            return Result.error("11", "服务不存在");
        }
        ServiceDetail serviceDetail = serviceInfoService.serviceDetail(serviceInfo);
        if (serviceDetail == null) {
            return Result.error("11", "服务不存在");
        }
        //往数据库中更新数据
        return updateServiceInfo(serviceDetail, serviceUpdateGrpcInput);
    }


    @Transactional(rollbackFor = Exception.class)
    public Result updateServiceInfo(ServiceDetail serviceDetail, ServiceUpdateGrpcInput serviceUpdateGrpcInput) {
        try {
            // 往数据库中保存数据的代码段
            ServiceInfo serviceInfo1 = serviceDetail.getInfo();
            serviceInfo1.setServiceDesc(serviceUpdateGrpcInput.getServiceDesc());
            serviceInfo1.setUpdateAt(new Date());
            serviceInfoService.save(serviceInfo1);

            GrpcRule grpcRule = serviceDetail.getGrpcRule();
            grpcRule.setServiceId(serviceInfo1.getId());
            grpcRule.setHeadrTransfor(serviceUpdateGrpcInput.getHeaderTransfor());
            grpcRuleMapper.save(grpcRule);


            AccessControl accessControl = serviceDetail.getAccessControl();
            accessControl.setServiceId(serviceInfo1.getId());
            accessControl.setOpenAuth(serviceUpdateGrpcInput.getOpenAuth());
            accessControl.setBlackList(serviceUpdateGrpcInput.getBlackList());
            accessControl.setWhiteList(serviceUpdateGrpcInput.getWhiteList());
            accessControl.setClientIPFlowLimit(serviceUpdateGrpcInput.getClientIpFlowLimit());
            accessControl.setServiceFlowLimit(serviceUpdateGrpcInput.getServiceFlowLimit());
            accessControl.setWhiteHostName(serviceUpdateGrpcInput.getWhiteHostName());
            accessControlMapper.save(accessControl);

            LoadBalance loadBalance = serviceDetail.getLoadBalance();
            loadBalance.setServiceId(serviceInfo1.getId());
            loadBalance.setRoundType(serviceUpdateGrpcInput.getRoundType());
            loadBalance.setIpList(serviceUpdateGrpcInput.getIpList());
            loadBalance.setWeightList(serviceUpdateGrpcInput.getWeightList());
            loadBalance.setForbidList(serviceUpdateGrpcInput.getForbidList());
            loadBalanceMapper.save(loadBalance);

            return Result.success("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("保存失败"); // 抛出运行时异常触发回滚
        }
    }

    @PostMapping("/service_add_tcp")
    @ResponseBody
    public Result ServiceAddTcp(@RequestBody ServiceAddTcpInput serviceAddTcpInput) {
        String msg = "";
        if (!translation.validServiceName(serviceAddTcpInput.getServiceName())) {
            msg += "服务名输入非法 ";
        }
        if (serviceAddTcpInput.getServiceDesc().isEmpty()) {
            msg += "服务描述必填 ";
        }
        if (serviceAddTcpInput.getIpList().isEmpty()) {
            msg += "IP列表必填 ";
        }
        if (!translation.validIPList(serviceAddTcpInput.getIpList())) {
            msg += "IP列表输入非法 ";
        }
        if (!translation.validWeightList(serviceAddTcpInput.getWeightList())) {
            msg += "权重列表输入非法 ";
        }
        if (!translation.validHeaderTransform(serviceAddTcpInput.getHeaderTransfor())) {
            msg += "header转化输入非法 ";
        }
        if (!msg.isEmpty()) {
            return Result.error("2222", msg.trim());
        }


        ServiceInfo serviceInfo = serviceInfoService.find(serviceAddTcpInput.getServiceName());
        if (serviceInfo != null) {
            return Result.error("11", "服务已存在");
        }
        TcpRule tcpRule = tcpRuleService.findByPort(serviceAddTcpInput.getPort());
        if (tcpRule != null) {
            return Result.error("111", "端口已占用");
        }
        GrpcRule grpcRule = grpcRuleService.findByPort(serviceAddTcpInput.getPort());
        if (grpcRule != null) {
            return Result.error("111", "端口已占用");
        }
        if (serviceAddTcpInput.getIpList().split(",").length != serviceAddTcpInput.getWeightList().split(",").length) {
            return Result.error("1111", "IP列表与权重列表数量不一致");
        }

        //往数据库中保存数据
        return saveServiceInfo(serviceAddTcpInput);
    }
    @Transactional(rollbackFor = Exception.class)
    public Result saveServiceInfo(ServiceAddTcpInput serviceAddTcpInput) {
        try {
            // 往数据库中保存数据的代码段
            ServiceInfo serviceInfo1 = new ServiceInfo();
            serviceInfo1.setLoadType(serviceAddTcpInput.getRoundType());
            serviceInfo1.setServiceName(serviceAddTcpInput.getServiceName());
            serviceInfo1.setServiceDesc(serviceAddTcpInput.getServiceDesc());
            serviceInfo1.setCreateAt(new Date());
            serviceInfo1.setUpdateAt(new Date());
            serviceInfoService.save(serviceInfo1);

            TcpRule tcpRule = new TcpRule();
            tcpRule.setServiceId(serviceInfo1.getId());
            tcpRule.setPort(serviceAddTcpInput.getPort());
            tcpRuleMapper.save(tcpRule);


            AccessControl accessControl = new AccessControl();
            accessControl.setServiceId(serviceInfo1.getId());
            accessControl.setOpenAuth(serviceAddTcpInput.getOpenAuth());
            accessControl.setBlackList(serviceAddTcpInput.getBlackList());
            accessControl.setWhiteList(serviceAddTcpInput.getWhiteList());
            accessControl.setClientIPFlowLimit(serviceAddTcpInput.getClientIpFlowLimit());
            accessControl.setServiceFlowLimit(serviceAddTcpInput.getServiceFlowLimit());
            accessControl.setWhiteHostName(serviceAddTcpInput.getWhiteHostName());
            accessControlMapper.save(accessControl);

            LoadBalance loadBalance = new LoadBalance();
            loadBalance.setServiceId(serviceInfo1.getId());
            loadBalance.setRoundType(serviceAddTcpInput.getRoundType());
            loadBalance.setIpList(serviceAddTcpInput.getIpList());
            loadBalance.setWeightList(serviceAddTcpInput.getWeightList());
            if (serviceAddTcpInput.getForbidList() == null) {
                loadBalance.setForbidList("");
            } else {
                loadBalance.setForbidList(serviceAddTcpInput.getForbidList());
            }            loadBalanceMapper.save(loadBalance);

            return Result.success("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("保存失败"); // 抛出运行时异常触发回滚
        }
    }

    @PostMapping("/service_update_tcp")
    @ResponseBody
    public Result ServiceUpdateTcp(@RequestBody ServiceUpdateTcpInput serviceUpdateTcpInput) {
        String msg = "";
        if (!translation.validServiceName(serviceUpdateTcpInput.getServiceName())) {
            msg += "服务名输入非法 ";
        }
        if (serviceUpdateTcpInput.getServiceDesc().isEmpty()) {
            msg += "服务描述必填 ";
        }
        if (serviceUpdateTcpInput.getIpList().isEmpty()) {
            msg += "IP列表必填 ";
        }
        if (!translation.validIPList(serviceUpdateTcpInput.getIpList())) {
            msg += "IP列表输入非法 ";
        }
        if (!translation.validWeightList(serviceUpdateTcpInput.getWeightList())) {
            msg += "权重列表输入非法 ";
        }
        if (!translation.validHeaderTransform(serviceUpdateTcpInput.getHeaderTransfor())) {
            msg += "header转化输入非法 ";
        }
        if (!msg.isEmpty()) {
            return Result.error("2222", msg.trim());
        }

        if (serviceUpdateTcpInput.getIpList().split(",").length != serviceUpdateTcpInput.getWeightList().split(",").length) {
            return Result.error("1111", "IP列表与权重列表数量不一致");
        }


        ServiceInfo serviceInfo = serviceInfoService.find(serviceUpdateTcpInput.getServiceName());
        if (serviceInfo == null) {
            return Result.error("11", "服务不存在");
        }
        ServiceDetail serviceDetail = serviceInfoService.serviceDetail(serviceInfo);
        if (serviceDetail == null) {
            return Result.error("11", "服务不存在");
        }
        //往数据库中更新数据
        return updateServiceInfo(serviceDetail, serviceUpdateTcpInput);
    }


    @Transactional(rollbackFor = Exception.class)
    public Result updateServiceInfo(ServiceDetail serviceDetail, ServiceUpdateTcpInput serviceUpdateTcpInput) {
        try {
            // 往数据库中保存数据的代码段
            ServiceInfo serviceInfo1 = serviceDetail.getInfo();
            serviceInfo1.setServiceDesc(serviceUpdateTcpInput.getServiceDesc());
            serviceInfo1.setUpdateAt(new Date());
            serviceInfoService.save(serviceInfo1);

            TcpRule tcpRule = serviceDetail.getTcpRule();
            tcpRule.setServiceId(serviceInfo1.getId());
            tcpRule.setPort(serviceUpdateTcpInput.getPort());
            tcpRuleMapper.save(tcpRule);


            AccessControl accessControl = serviceDetail.getAccessControl();
            accessControl.setServiceId(serviceInfo1.getId());
            accessControl.setOpenAuth(serviceUpdateTcpInput.getOpenAuth());
            accessControl.setBlackList(serviceUpdateTcpInput.getBlackList());
            accessControl.setWhiteList(serviceUpdateTcpInput.getWhiteList());
            accessControl.setClientIPFlowLimit(serviceUpdateTcpInput.getClientIpFlowLimit());
            accessControl.setServiceFlowLimit(serviceUpdateTcpInput.getServiceFlowLimit());
            accessControl.setWhiteHostName(serviceUpdateTcpInput.getWhiteHostName());
            accessControlMapper.save(accessControl);

            LoadBalance loadBalance = serviceDetail.getLoadBalance();
            loadBalance.setServiceId(serviceInfo1.getId());
            loadBalance.setRoundType(serviceUpdateTcpInput.getRoundType());
            loadBalance.setIpList(serviceUpdateTcpInput.getIpList());
            loadBalance.setWeightList(serviceUpdateTcpInput.getWeightList());
            loadBalance.setForbidList(serviceUpdateTcpInput.getForbidList());
            loadBalanceMapper.save(loadBalance);

            return Result.success("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("保存失败"); // 抛出运行时异常触发回滚
        }
    }



}
