package com.order.diancan.controller;

import com.order.diancan.bean.Order;
import com.order.diancan.service.OrderService;
import com.order.diancan.utils.Msg;
import com.order.diancan.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Date;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    //添加订单
    @RequestMapping(value = "register",method = RequestMethod.POST)
    public Msg register(@RequestBody Order order){
        try {
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            order.setDate(timestamp);
            orderService.insertOrder(order);
        } catch (Exception e) {
            System.out.println(e);
            return ResultUtil.error(400,"未知错误，订单添加失败");
        }
        return ResultUtil.registerOrderSuccess();
    }
}
