package com.order.diancan.service;

import com.alibaba.fastjson.JSON;
import com.order.diancan.bean.Dish;
import com.order.diancan.bean.Order;
import com.order.diancan.bean.Restaurant;
import com.order.diancan.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private DishService dishService;

    //添加订单
    public void insertOrder(Order order){
        orderMapper.insert(order.getPrice(),order.getDate(),order.getState(),order.getDish_id_list(),order.getRestaurant_id(),order.getCustomer_id());
    }

    //更改订单状态
    public void updateOrderState(long id,int state){
        orderMapper.updateState(id,state);
    }

    //根据用户id和订单状态返回订单对象
    public List<Order> selectOrderByState(long customer_id, int state){
        return orderMapper.orderFromCustomerAndState(customer_id,state);
    }

    //根据顾客id与订单状态返回餐厅与菜品信息的json,可能有多个菜品
    public String restaurantAndDishes(long restaurant_id,int state){
        StringBuilder json = new StringBuilder();
        List<Order> orders = selectOrderByState(restaurant_id, state);
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            //找到餐厅信息
            Restaurant restaurant = restaurantService.restaurantById(order.getRestaurant_id());
            json.append(JSON.toJSONString(restaurant));
            json.append(JSON.toJSONString(order));
            //找到菜品信息，可能包含多个
            String idList = order.getDish_id_list();
            String[] idsString = idList.split(",");//菜品id是按照逗号分割的
            int[] idsInt = new int[idsString.length];
            for (int j = 0; j < idsString.length; j++) {
                idsInt[j] = Integer.parseInt(idsString[j]);//存id的string数组转为int数组
                Dish dish =  dishService.dishById(idsInt[j]);
                json.append(JSON.toJSONString(dish));
            }
        }
        return json.toString().replace("}{","},{");
    }

}
