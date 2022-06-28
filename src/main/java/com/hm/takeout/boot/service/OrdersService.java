package com.hm.takeout.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hm.takeout.boot.entity.Orders;

public interface OrdersService extends IService<Orders> {

    void submit(Orders orders);
}
