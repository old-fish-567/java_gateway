package com.example.gateway.controller;


import com.example.gateway.entity.Admin;
import com.example.gateway.entity.AdminInfo;
import com.example.gateway.entity.AdminLogin;
import com.example.gateway.service.AdminService;
import com.example.gateway.utils.GatewayUtil;
import com.example.gateway.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
/*
 * File: AdminController.java
 * Author: ydc
 * Date: 2023.12.03
 * Description: 用户信息，修改密码等用户相关的功能.
 */
@RestController()
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    @Autowired
    private AdminService adminService;

    /**
     * Retrieves the information of the currently logged-in admin.
     *
     * @param request The HttpServletRequest object to retrieve the HttpSession.
     * @return A Result containing the AdminInfo with details like username, ID, login time, avatar, introduction, and roles.
     *         Returns an error Result if the session does not exist.
     */
    @GetMapping("/admin_info")
    public Result<AdminInfo> adminInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session == null) {
            return Result.error("666", "不存在session");
        }
        AdminInfo adminInfo = new AdminInfo();
        AdminLogin adminLogin = (AdminLogin) session.getAttribute("admin");
        adminInfo.setUserName(adminLogin.getUserName());
        adminInfo.setId(adminLogin.getId());
        adminInfo.setLoginTime(adminLogin.getLoginTime());
        adminInfo.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        adminInfo.setIntroduction("I am a super administrator");
        String[] roles = new String[] {"admin"};
        adminInfo.setRoles(roles);
        return Result.success(adminInfo, "成功", "");
    }


    /**
     * Changes the password of the currently logged-in admin.
     *
     * @param request  The HttpServletRequest object to retrieve the HttpSession.
     * @param password The new password to be set for the admin.
     * @return A Result indicating the success of the password change operation.
     *         Returns an error Result if the session is empty or if there are issues updating the password in the database.
     */
    @PostMapping("/change_pwd")
    @ResponseBody
    public Result<AdminInfo> changePwd(HttpServletRequest request, String password) {
        //1. session读取用户信息
        //2. adminLogin读取数据库admin信息
        //3. password+admin.salt md5存储密码
        //4. 执行数据库操作
        HttpSession session = request.getSession();
        if (session == null) {
            return Result.error("222","session空");
        }
        AdminLogin adminLogin = (AdminLogin) session.getAttribute("admin");
        Admin admin = adminService.findAdminById(adminLogin.getId());
        String newPassword = GatewayUtil.md5(password + admin.getSalt());
        admin.setPassword(newPassword);
        admin.setUpdateAt(new Date());
        adminService.update(admin);
        return Result.success();
    }
}
