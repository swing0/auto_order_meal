package com.order.diancan.mapper;

import com.order.diancan.bean.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {
    //根据饭店id查询其所有菜品
    @Select("SELECT * FROM dish WHERE restaurant_id=#{restaurant_id}")
    List<Dish> selectDishFromRestaurant(int restaurant_id);

    //根据菜品id查询其信息
    @Select("SELECT * FROM dish WHERE id=#{id}")
    List<Dish> selectDishFromId(int id);
}
