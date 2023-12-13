package com.example.gateway.mapper;


import com.example.gateway.entity.Admin;
import com.example.gateway.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMapper extends JpaRepository<Admin, Integer> {
    Admin findByUserName(String userName);
    Admin findByUserNameAndPassword(String userName, String password);
    Admin findById(int id);

}
