package com.order.diancan.service;

import com.order.diancan.bean.Restaurant;
import com.order.diancan.mapper.RestaurantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RestaurantService {
    @Autowired
    private RestaurantMapper restaurantMapper;

    //注册饭店
    public int insertRestaurant(Restaurant restaurant){
        //注册饭店
        restaurantMapper.insert(restaurant.getName(),restaurant.getAddress(),restaurant.getPhone(),restaurant.getClassification(),restaurant.getScoring_times(),restaurant.getTotal_score());
        //根据手机号查找刚插入的饭店的id
        restaurant.setId(restaurantMapper.selectIdFromPhone(restaurant.getPhone()));
        System.out.println(restaurant.getId());
        return restaurant.getId();
    }

}
