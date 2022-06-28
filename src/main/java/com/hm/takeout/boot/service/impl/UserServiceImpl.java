package com.hm.takeout.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hm.takeout.boot.entity.User;
import com.hm.takeout.boot.mapper.UserMapper;
import com.hm.takeout.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User getByPhone(String phone) {
        User user = userMapper.selectByPhone(phone);
        return user;
    }
}
