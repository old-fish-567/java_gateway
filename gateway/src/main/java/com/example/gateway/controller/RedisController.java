package com.example.gateway.controller;

import com.example.gateway.entity.Admin;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * File: RedisController.java
 * Author: ydc
 * Date: 2023.12.03
 * Description: Redis的测试类.
 */
@RestController()
@RequestMapping("/redis")
@Slf4j
public class RedisController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @GetMapping("/testString")
    public String testString(HttpServletRequest request)  {
        log.info("redis 开始保存值  ");
        stringRedisTemplate.opsForValue().set("name", "早起的年轻人");
        log.info("redis 保存完成  ");
        String name = stringRedisTemplate.opsForValue().get("name");
        return "redis 中取出的值是  " + name ;
    }
}