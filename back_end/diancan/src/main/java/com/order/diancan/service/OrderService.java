package com.order.diancan.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.order.diancan.bean.*;
import com.order.diancan.mapper.DishMapper;
import com.order.diancan.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private DishService dishService;

    //添加订单
    public void insertOrder(Order order){
        orderMapper.insert(order.getPrice(),order.getDate(),order.getState(),order.getDish_id_list(),order.getRestaurant_id(),order.getCustomer_id());
    }

    //修改订单信息
    public void updateOrder(Order order){
        orderMapper.updateOrder(order.getId(),order.getPrice(),order.getDate(),order.getState(),order.getDish_id_list(),order.getRestaurant_id(),order.getCustomer_id());
        if (order.getState().equals(2)){
            addDishSalesVolume(order.getId());
        }
    }

    //更改订单状态
    public void updateOrderState(long id,int state){
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        String date1 = timestamp.toString();
        orderMapper.updateState(id,state,date1);
        //当订单的状态变为2(付款)时，调用此函数，增加订单中菜品相应的销量
        if (state == 2){
            addDishSalesVolume(id);
        }
    }

    //批量修改订单状态
    public void updateManyOrderState(OrderStates orderStates){
        List<OrderId> ids = orderStates.getData();
        for (OrderId id : ids){
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            String date1 = timestamp.toString();
            orderMapper.updateState(id.getId(),orderStates.getState(),date1);
            //当订单的状态变为2(付款)时，调用此函数，增加订单中菜品相应的销量
            if (orderStates.getState().equals(2)){
                addDishSalesVolume(id.getId());
            }
        }

    }

    //根据订单id查询订单信息
    public Order infoById(Long id){
        return orderMapper.infoById(id);
    }

    //根据用户id和订单状态返回订单对象
    public List<Order> selectOrderByState(long customer_id, int state){
        return orderMapper.orderFromCustomerAndState(customer_id,state);
    }

    //当订单的状态变为2(付款)时，调用此函数，增加订单中菜品相应的销量
    public void addDishSalesVolume(long orderId){
        //根据订单id查出菜品id列表
        String idList = orderMapper.selectDishIdList(orderId);
        //根据菜品id列表解析出菜品的int型id数组
        String[] idsString = idList.split(",");//菜品id是按照逗号分割的
        for (String s : idsString) {
            //根据菜品的id查询使菜品的销量加一
            dishMapper.saleVolumeAdd(Long.parseLong(s));
        }
    }


    //根据顾客id与订单状态返回餐厅与菜品信息的json,可能有多个菜品
    public List<OrderDetails> restaurantAndDishes(long restaurant_id,int state){

        List<Order> orders = selectOrderByState(restaurant_id, state);
        List<OrderDetails> orderDetailsList = new ArrayList<>(orders.size());

        for (Order order : orders) {
            //找到餐厅信息
            Restaurant restaurant = restaurantService.restaurantById(order.getRestaurant_id());
            //将对应订单和餐厅的信息传入orderDetails
            //找到菜品信息，可能包含多个
            String idList = order.getDish_id_list();
            String[] idsString = idList.split(",");//菜品id是按照逗号分割的
            int[] idsInt = new int[idsString.length];
            //将菜品信息传入orderDetails
            List<SimpleDish> simpleDishList = new ArrayList<>(idsString.length);
            for (int j = 0; j < idsString.length; j++) {
                idsInt[j] = Integer.parseInt(idsString[j]);//存id的string数组转为int数组
                Dish dish = dishService.dishById(idsInt[j]);
                SimpleDish simpleDish = new SimpleDish(dish.getId(), dish.getName(), dish.getImage(), dish.getPrice());
                simpleDishList.add(simpleDish);
            }
            OrderDetails orderDetails = new OrderDetails(restaurant.getId(), restaurant.getName(), restaurant.getAddress(), restaurant.getPhone(), order.getId(), order.getPrice(), order.getDate(), order.getState(),simpleDishList);
            orderDetailsList.add(orderDetails);
        }
        return orderDetailsList;
    }

    //查询所有订单
    public List<Order> allOrder(){
        return orderMapper.allOrder();
    }

}
