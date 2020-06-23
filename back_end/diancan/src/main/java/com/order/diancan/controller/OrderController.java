package com.order.diancan.controller;

import com.order.diancan.bean.Order;
import com.order.diancan.bean.OrderDetails;
import com.order.diancan.bean.OrderState;
import com.order.diancan.service.OrderService;
import com.order.diancan.utils.Msg;
import com.order.diancan.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
            order.setDate(timestamp.toString());
            orderService.insertOrder(order);
        } catch (Exception e) {
            System.out.println(e);
            return ResultUtil.error(400,"未知错误，订单添加失败");
        }
        return ResultUtil.registerOrderSuccess();
    }

    //更改订单状态
    @RequestMapping(value = "updateState",method = RequestMethod.POST)
    public Msg updateState(@RequestBody OrderState orderState){
        try {
            orderService.updateOrderState(orderState.getId(),orderState.getState());
        } catch (Exception e) {
            return ResultUtil.error(400,"未知错误,订单修改失败");
        }
        return ResultUtil.success("订单状态修改成功");
    }

    //根据顾客id与订单状态返回餐厅与菜品信息的json
    @RequestMapping(value = "state",method = RequestMethod.POST)
    public Msg listState(@RequestBody OrderState orderState){
        try {
            List<OrderDetails> orderDetailsList = orderService.restaurantAndDishes(orderState.getId(),orderState.getState());
            if (orderDetailsList.isEmpty()){
                return ResultUtil.error(204,"数据不存在");
            }else {
                return ResultUtil.success(orderDetailsList);
            }
        } catch (Exception e) {
            return ResultUtil.error(400,"未知错误，返回信息失败");
        }
    }

    //查询所有订单
    @RequestMapping(value = "allOrder",method = RequestMethod.GET)
    public Msg allOrder(){
        try {
            List<Order> orderList = orderService.allOrder();
            return ResultUtil.success(orderList);
        } catch (Exception e) {
            return ResultUtil.error(400,"未知错误,订单查询失败");
        }

    }
}
