package com.example.gateway.service.serviceImpl;

import com.example.gateway.entity.User;
import com.example.gateway.mapper.UserMapper;
import com.example.gateway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User loginService(String uname, String password) {
        User user = userMapper.findByUnameAndPassword(uname, password);
        if (user != null) {
            user.setPassword("");
        }
        return user;
    }

    @Override
    public User registService(User user) {
        //当新用户的用户名已存在时
        if(userMapper.findByUname(user.getUname())!=null){
            // 无法注册
            return null;
        }else{
            //返回创建好的用户对象(带uid)
            User newUser = userMapper.save(user);
            if(newUser != null){
                newUser.setPassword("");
            }
            return newUser;
        }
    }
}
