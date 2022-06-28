package com.hm.takeout.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hm.takeout.boot.entity.ShoppingCart;
import com.hm.takeout.boot.mapper.ShoppingCartMapper;
import com.hm.takeout.boot.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
