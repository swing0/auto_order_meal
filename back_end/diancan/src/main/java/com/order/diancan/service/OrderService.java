package com.order.diancan.service;

import com.order.diancan.bean.Order;
import com.order.diancan.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    //添加订单
    public void insertOrder(Order order){
        orderMapper.insert(order.getPrice(),order.getDate(),order.getState(),order.getDish_id_list(),order.getRestaurant_id(),order.getCustomer_id());
    }

    //更改订单状态
    public void updateOrderState(long id,int state){
        orderMapper.updateState(id,state);
    }
}
