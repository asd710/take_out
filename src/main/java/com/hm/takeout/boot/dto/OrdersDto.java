package com.hm.takeout.boot.dto;


import com.hm.takeout.boot.entity.OrderDetail;
import com.hm.takeout.boot.entity.Orders;
import lombok.Data;
import java.util.List;

@Data
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;

}
