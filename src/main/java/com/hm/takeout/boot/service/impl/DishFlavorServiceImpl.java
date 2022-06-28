package com.hm.takeout.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hm.takeout.boot.dto.DishDto;
import com.hm.takeout.boot.entity.DishFlavor;
import com.hm.takeout.boot.mapper.DishFlavorMapper;
import com.hm.takeout.boot.service.DishFlavorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
