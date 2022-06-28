package com.hm.takeout.boot.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hm.takeout.boot.entity.User;

public interface UserService extends IService<User> {
    User getByPhone(String phone);
}
