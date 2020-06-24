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


    //根据菜品id返回菜品对象
    public Dish dishById(long id){return dishMapper.selectDishById(id);}

    //根据菜品name返回菜品对象
    public Dish dishByName(String name){return dishMapper.selectDishByName(name);}

    //添加菜品
    public void addDish(Dish dish){ dishMapper.insert(
            dish.getName(),
            dish.getImage(),
            dish.getClassification(),
            dish.getCuisine(),
            dish.getSales_volume(),
            dish.getPrice(),
            dish.getScoring_times(),
            dish.getTotal_score(),
            dish.getRestaurant_id()
    );}

    //查询所有菜品
    public List<Dish> selectAll(){return dishMapper.selectAll();}

    //修改菜品信息
    public void updateDish(Dish dish){ dishMapper.update(
            dish.getId(),
            dish.getName(),
            dish.getImage(),
            dish.getClassification(),
            dish.getCuisine(),
            dish.getSales_volume(),
            dish.getPrice(),
            dish.getScoring_times(),
            dish.getTotal_score(),
            dish.getRestaurant_id()
    );}

    //根据菜品id使菜品的销量加一
    public void saleVolumeAdd(long id){dishMapper.saleVolumeAdd(id);}

}
