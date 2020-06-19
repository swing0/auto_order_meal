package com.order.diancan.service;

import com.order.diancan.bean.Dish;
import com.order.diancan.mapper.DishMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DishService {
    @Autowired
    private DishMapper dishMapper;
    //根据饭店id查询其所有菜品
    public List<Dish> dishesFromRestaurant(int restaurant_id){
        return dishMapper.selectDishFromRestaurant(restaurant_id);
    }

    //根据菜品id查询其信息
    public List<Dish> dishesFromId(int id){
        return dishMapper.selectDishFromId(id);
    }

    //添加菜品


}
