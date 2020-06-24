package com.order.diancan.controller;

import com.alibaba.fastjson.JSON;
import com.order.diancan.AutoRefer.BagFBack;
import com.order.diancan.bean.Dish;
import com.order.diancan.service.DishService;
import com.order.diancan.utils.Msg;
import com.order.diancan.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
    public Msg allDish(@PathVariable("restaurant_id") int restaurant_id){
        try {
            List<Dish> dishList = dishService.dishesFromRestaurant(restaurant_id);
            return ResultUtil.success(dishList);
        } catch (Exception e) {
            return ResultUtil.error(400,"未知错误，查询失败:" + e);
        }
    }

    //根据菜品name查询菜品信息
    @RequestMapping(value = "infoByName",method = RequestMethod.POST)
    public Msg infoByName(@RequestBody Dish dish){
        try {
            Dish dishResult = dishService.dishByName(dish.getName());
            if (dishResult == null){
                return ResultUtil.error(202,"未找到菜品信息");
            }else {
                return ResultUtil.success(dishResult);
            }
        } catch (Exception e) {
            return ResultUtil.error(400,"未知错误，查询失败:" + e);
        }
    }

    //根据价格查找所有饭店的推荐
    @RequestMapping(value = "/{restaurant_id}/{price}",method = RequestMethod.GET)
    public Msg selectBestDishes(@PathVariable int restaurant_id, @PathVariable("price") long price ){
        try {
            List<Dish> dishList = new ArrayList<>();
            List<Integer> ids = selectFromRestaurant(price,restaurant_id);
            for (Integer id : ids) {
                dishList.add(dishService.dishById(id));
            }
            if (dishList.isEmpty()){
                return ResultUtil.error(201,"未能找到推荐菜单");
            }
            return ResultUtil.success(dishList);
        } catch (Exception e) {
            return ResultUtil.error(400,"未知错误,查询失败:" + e);
        }
    }

    //添加菜品
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public Msg addDishes(@RequestBody Dish dish){
        try {
            dishService.addDish(dish);
        } catch (DuplicateKeyException e){
            return ResultUtil.error(201,"菜名已存在");
        }catch (Exception e) {
            return ResultUtil.error(400,"未知错误:" + e);
        }
        return ResultUtil.success("添加成功");
    }

    //查询所有菜品
    @RequestMapping(value = "allDishes",method = RequestMethod.GET)
    public Msg allDishes(){
        try {
            return ResultUtil.success(dishService.selectAll());
        } catch (Exception e) {
            return ResultUtil.error(400,"查询失败:" + e);
        }
    }

    //修改菜品信息
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public Msg updateDishes(@RequestBody Dish dish){
        try {
            dishService.updateDish(dish);
        } catch (DuplicateKeyException e){
            return ResultUtil.error(201,"菜名已存在");
        }catch (Exception e) {
            return ResultUtil.error(400,"未知错误:" + e);
        }
        return ResultUtil.success("修改成功");
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
