package com.example.gateway.service;

import com.example.gateway.entity.Admin;

public interface AdminService {
    /**
     * 登录业务逻辑
     * @param userName 账户名
     * @param password 密码
     * @return
     */
    Admin loginService(String userName, String password);


    /**
     * 注册业务逻辑
     * @param admin 要注册的User对象，属性中主键uid要为空，若uid不为空可能会覆盖已存在的user
     * @return
     */
    Admin registerService(Admin admin);

    Admin findAdminById(int id);

    Admin update(Admin admin);

}
