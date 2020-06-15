package com.order.diancan.controller;

import com.alibaba.fastjson.JSON;
import com.order.diancan.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    //根据饭店id查询其所有菜品
    @RequestMapping(value = "/restaurant_id/{restaurant_id}",method = RequestMethod.GET)
    public String allDish(@PathVariable("restaurant_id") int restaurant_id){
        String json = JSON.toJSONString(dishService.dishesFromRestaurant(restaurant_id));
        return json;
    }

}
