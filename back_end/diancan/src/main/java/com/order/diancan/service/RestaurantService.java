package com.order.diancan.service;

import com.order.diancan.bean.Restaurant;
import com.order.diancan.mapper.RestaurantMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
        return restaurant.getId();
    }

    //修改饭店信息
    public void updateRestaurant(Restaurant restaurant){
        restaurantMapper.update((long) restaurant.getId(),restaurant.getName(),restaurant.getAddress(),restaurant.getPhone(),restaurant.getClassification(),restaurant.getScoring_times(),restaurant.getTotal_score());
    }

    //根据名字查找饭店信息
    public Restaurant infoByName(Restaurant restaurant){
        return restaurantMapper.selectByName(restaurant.getName());
    }

    //返回所有的饭店信息
    public List<Restaurant> allRestaurant(){
        return restaurantMapper.selectAllInfo();
    }

    //根据饭店id返回饭店信息
    public Restaurant restaurantById(long restaurant){
        return restaurantMapper.selectById(restaurant);
    }

}
