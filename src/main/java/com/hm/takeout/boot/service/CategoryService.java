package com.hm.takeout.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hm.takeout.boot.entity.Category;

public interface CategoryService extends IService<Category> {
    public void delete(Long ids);
}
