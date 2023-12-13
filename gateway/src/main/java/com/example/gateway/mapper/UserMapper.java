package com.example.gateway.mapper;

import com.example.gateway.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends JpaRepository<User, Long> {
    User findByUname(String uname);

    User findByUnameAndPassword(String uname, String password);
}
