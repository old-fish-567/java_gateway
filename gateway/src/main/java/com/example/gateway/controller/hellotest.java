package com.example.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class hellotest {
    @GetMapping("/hallo")
    public String hallo() {
        return "hello Postman";
    }
}
