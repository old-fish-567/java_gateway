package com.example.gateway.controller;

import com.example.gateway.entity.Admin;
import com.example.gateway.entity.AdminLogin;
import com.example.gateway.entity.User;
import com.example.gateway.service.AdminService;
import com.example.gateway.service.UserService;
import com.example.gateway.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/*
 * File: LoginController.java
 * Author: ydc
 * Date: 2023.12.03
 * Description: 用户登陆，注册，登出功能.
 */
@Controller
@RequestMapping("/admin_login")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    AdminService adminService;

    /**
     * Handles the login request, authenticates the admin, and creates a session upon successful login.
     *
     * @param request  The HttpServletRequest object to retrieve the session.
     * @param userName The username provided during login.
     * @param password The password provided during login.
     * @return A Result containing the AdminLogin details upon successful login.
     *         Returns an error Result if the login fails due to incorrect credentials.
     */
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result<AdminLogin> login(HttpServletRequest request, String userName, String password) {
        Admin admin = adminService.loginService(userName, password);
        if (admin != null) {
            AdminLogin adminLogin = new AdminLogin();
            adminLogin.setId(admin.getId());
            adminLogin.setUserName(admin.getUserName());
            adminLogin.setLoginTime(new Date());
            HttpSession session = request.getSession(true);
            session.setAttribute("admin", adminLogin);
            return Result.success(adminLogin, "成功","");
        } else {
            return Result.error("123","账号或密码错误！");
        }
    }

    /**
     * Handles the registration request, registers a new admin, and returns the result.
     *
     * @param newAdmin The Admin object containing details for registration.
     * @return A Result containing the registered Admin upon successful registration.
     *         Returns an error Result if the registration fails, e.g., due to an existing username.
     */
    @PostMapping("/register")
    @ResponseBody
    public Result<Admin> registController(@RequestBody Admin newAdmin){
        Admin admin = adminService.registerService(newAdmin);
        if(admin!=null){
            return Result.success(admin,"注册成功！");
        }else{
            return Result.error("456","用户名已存在！");
        }
    }

    /**
     * Handles the logout request, invalidates the session, and returns the result.
     *
     * @param request The HttpServletRequest object to retrieve the session.
     * @return A Result indicating the success of the logout operation.
     */
    @GetMapping("/logout")
    @ResponseBody
    public Result<AdminLogin> logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("admin");
        return Result.success();

    }



}
