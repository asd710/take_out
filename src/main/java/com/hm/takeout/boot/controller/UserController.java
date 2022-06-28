package com.hm.takeout.boot.controller;

import com.hm.takeout.boot.common.R;
import com.hm.takeout.boot.entity.User;
import com.hm.takeout.boot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/login")
    public R<User> login(HttpServletRequest request,@RequestBody User user){
        String phone = user.getPhone();
        User userPhone = userService.getByPhone(phone);
        //判断是否为空
        if (StringUtils.isNotEmpty(phone)){
            //判断是在用户表中
            if (userPhone != null){
                //保存到session中
                request.getSession().setAttribute("user",userPhone.getId());
            }else {
                userService.save(user);
                request.getSession().setAttribute("user",userPhone.getId());
            }
            return R.success(user);
        }
        return R.error("登陆失败");
    }
}
