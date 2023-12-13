package com.example.gateway.mapper;

import com.example.gateway.entity.ServiceInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Repository
public interface ServiceInfoMapper extends JpaRepository<ServiceInfo, Integer> {
    ServiceInfo findByServiceNameAndIsDelete(String serviceName, int isDelete);

    ServiceInfo findById(int id);

    ServiceInfo findByServiceNameOrServiceDesc(String serviceName, String serviceDesc);

    @Query(value = "SELECT * FROM gateway_service_info " +
            "WHERE is_delete = 0 " +
            "AND (service_name LIKE %?1% OR service_desc LIKE %?1%)" +
            "ORDER BY id DESC",
            countQuery = "SELECT COUNT(*)\n" +
                    "FROM gateway_service_info\n" +
                    "WHERE is_delete = 0\n" +
                    "  AND (service_name LIKE '%' OR service_desc LIKE '%' OR '%' LIKE '%');",
            nativeQuery = true)
//    Page<ServiceInfo> findPagedAndFiltered(Pageable pageable);
    Page<ServiceInfo> findPagedAndFiltered(String info, Pageable pageable);



}
