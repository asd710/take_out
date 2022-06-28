package com.hm.takeout.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hm.takeout.boot.dto.DishDto;
import com.hm.takeout.boot.entity.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {
    void saveWithFlavor(DishDto dishDto);
    DishDto getByIdWithFlavor(Long id);
    void updateWithFlavor(DishDto dishDto);
    void updateStopStatusById(Long ids);
    void updateStartStatusById(Long ids);
    void deleteById(Long id);
    List<Dish> getcategoryById(Long id);
}
