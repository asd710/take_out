package com.hm.takeout.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hm.takeout.boot.entity.Dish;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface DishMapper extends BaseMapper<Dish> {
    @Update("update dish set status = 0 where id = #{dis} ")
    void updateStopStatusById(Long ids);
    @Update("update dish set status = 1 where id = #{dis} ")
    void updateStartStatusById(Long ids);
    @Select("select * from dish where category_id = #{categoryId} and status = 1 order by sort DESC ,update_time DESC ")
    List<Dish> getcategoryById(Long categoryId);
}
