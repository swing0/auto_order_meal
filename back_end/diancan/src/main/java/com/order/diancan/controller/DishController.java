package com.order.diancan.controller;

import com.alibaba.fastjson.JSON;
import com.order.diancan.AutoRefer.BagFBack;
import com.order.diancan.bean.Dish;
import com.order.diancan.service.DishService;
import com.order.diancan.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;


    //根据饭店id查询其所有菜品
    @RequestMapping(value = "/restaurant_id/{restaurant_id}",method = RequestMethod.GET)
    public String allDish(@PathVariable("restaurant_id") int restaurant_id){
        return JSON.toJSONString(dishService.dishesFromRestaurant(restaurant_id));
    }

    //根据价格查找所有饭店的推荐
    @RequestMapping(value = "/{restaurant_id}/{price}",method = RequestMethod.GET)
    public Object selectBestDishes(@PathVariable int restaurant_id, @PathVariable("price") long price ){
        StringBuilder json = new StringBuilder();

        List<Integer> ids = selectFromRestaurant(price,restaurant_id);
        for (int i = 0; i < ids.size(); i++) {
            json.append(JSON.toJSONString(dishService.dishesFromId(ids.get(i))));
        }
        if (json.toString().equals("")){
            return ResultUtil.error(204,"未能找到推荐菜单");
        }else {
            return json.toString().replace("][",",");
        }
    }

    //查找特定饭店的特定价格推荐
    public List<Integer> selectFromRestaurant(long price, int restaurant_id){
        List<Dish> dishes = dishService.dishesFromRestaurant(restaurant_id);
        //菜品的id
        int[] id = new int[dishes.size()];
        //菜品的单价
        float[] all_price = new float[dishes.size()];
        //菜品的评分
        float[] score = new float[dishes.size()];
        //顾客可接受的最大花费
        float max_price =  price;


        for (int i =0; i < dishes.size(); i++){
            id[i] = (int) dishes.get(i).getId();
            all_price[i] = dishes.get(i).getPrice();
            try {
                score[i] = dishes.get(i).getTotal_score()/dishes.get(i).getScoring_times();
            } catch (Exception e) {
                //除以零错误
                score[i] = 0;
            }
        }
        BagFBack bagFBack = new BagFBack(id,all_price, score, max_price);
        // 从第0层开始回溯，找方案
        return bagFBack.solve(bagFBack);
    }

}
