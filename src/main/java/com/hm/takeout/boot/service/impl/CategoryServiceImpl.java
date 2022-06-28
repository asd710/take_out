package com.hm.takeout.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hm.takeout.boot.common.CustomException;
import com.hm.takeout.boot.entity.Category;
import com.hm.takeout.boot.entity.Dish;
import com.hm.takeout.boot.entity.Setmeal;
import com.hm.takeout.boot.mapper.CategoryMapper;
import com.hm.takeout.boot.service.CategoryService;
import com.hm.takeout.boot.service.DishService;
import com.hm.takeout.boot.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    DishService dishService;
    @Autowired
    SetmealService setmealService;
    public void delete(Long ids){
        //增加查询条件，判断是否有外键关联
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,ids);
        int count1 = dishService.count(dishLambdaQueryWrapper);
        //如果查询出有关联菜品  抛异常
        if (count1 > 0 ){
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }
        //增加查询条件，判断是否有外键关联
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,ids);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        //如果查询出有关联菜品  抛异常
        if (count2 > 0 ){
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }

        categoryMapper.deleteById(ids);
    }
}
