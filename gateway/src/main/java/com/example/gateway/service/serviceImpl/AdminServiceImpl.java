package com.example.gateway.service.serviceImpl;

import com.example.gateway.entity.Admin;
import com.example.gateway.mapper.AdminMapper;
import com.example.gateway.service.AdminService;
import com.example.gateway.utils.GatewayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Override
    public Admin loginService(String userName, String password) {
        Admin admin = adminMapper.findByUserName(userName);
        if (admin == null) {
            return null;
        }

        String password1 = GatewayUtil.md5(password + admin.getSalt());

        Admin admin1 = adminMapper.findByUserNameAndPassword(userName, password1);
        if (admin1 != null) {
            admin1.setPassword("");
        }
        return admin1;
    }

    @Override
    public Admin registerService(Admin admin) {
        if (adminMapper.findByUserName(admin.getUserName()) != null) {
            return null;
        } else {
            admin.setSalt(GatewayUtil.generateUUID().substring(0, 5));
            admin.setPassword(GatewayUtil.md5(admin.getPassword() + admin.getSalt()));
            admin.setIsDelete(0);
            admin.setCreateAt(new Date());
            admin.setUpdateAt(new Date());
            Admin newAdmin = adminMapper.save(admin);
            if (newAdmin != null) {
                newAdmin.setPassword("");
            }
            return newAdmin;
        }
    }

    @Override
    public Admin findAdminById(int id) {
        return adminMapper.findById(id);
    }

    public Admin update(Admin admin) {
        return adminMapper.save(admin);
    }
}
