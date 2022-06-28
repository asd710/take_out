package com.hm.takeout.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hm.takeout.boot.dto.SetmealDto;
import com.hm.takeout.boot.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    void saveWithDish(SetmealDto setmealDto);
    void updateStopById(Long id);
    void updateStartById(Long id);
    SetmealDto getByIdWithDish(Long id);
    void removeWithDish(List<Long> ids);
}
