package com.hm.takeout.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hm.takeout.boot.entity.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where phone = #{phone}")
    User selectByPhone(String phone);
}
