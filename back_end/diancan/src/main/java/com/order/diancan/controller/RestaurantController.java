package com.order.diancan.controller;

import com.alibaba.fastjson.JSON;
import com.order.diancan.bean.Restaurant;
import com.order.diancan.service.RestaurantService;
import com.order.diancan.utils.Msg;
import com.order.diancan.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    //餐厅注册
    @RequestMapping(value = "register",method = RequestMethod.POST)
    public Object register(@RequestBody Restaurant restaurant){
        try {
            int restaurant_id = restaurantService.insertRestaurant(restaurant);
            return ResultUtil.success(restaurant_id);
        } catch (DuplicateKeyException e){
            return ResultUtil.error(201,"电话号码已注册");
        } catch (Exception e) {
            return ResultUtil.error(400,"出现异常，餐厅注册失败:" + e);
        }
    }

    //修改餐厅信息
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public Object update(@RequestBody Restaurant restaurant){
        try {
            restaurantService.updateRestaurant(restaurant);
        } catch (DuplicateKeyException e){
            return ResultUtil.error(201,"电话号码已注册");
        } catch (Exception e) {
            return ResultUtil.error(400,"出现异常，餐厅信息修改失败:" + e);
        }
        return ResultUtil.success("成功修改餐厅信息");
    }

    //返回所有的餐厅信息
    @RequestMapping(value = "info",method = RequestMethod.GET)
    public Msg allInfo(){
        try {
            List<Restaurant> restaurantList = restaurantService.allRestaurant();
            return ResultUtil.success(restaurantList);
        } catch (Exception e) {
            return ResultUtil.error(400,"餐厅信息返回失败:" + e);
        }
    }

    //根据名字查找饭店信息
    @RequestMapping(value = "infoByName",method = RequestMethod.POST)
    public Msg infoByName(@RequestBody Restaurant restaurant){
        try {
            Restaurant restaurantResult = restaurantService.infoByName(restaurant);
            if (restaurantResult == null){
                return ResultUtil.error(202,"没有该用户信息");
            }else {
                return ResultUtil.success(restaurantResult);
            }
        } catch (Exception e) {
            return ResultUtil.error(400,"餐厅信息返回失败:" + e);
        }
    }

    //对饭店进行评分
    @RequestMapping(value = "score",method = RequestMethod.POST)
    public Msg scoreRestaurant(@RequestBody Restaurant restaurant){
        try {
            restaurantService.scoreRestaurant(restaurant);
        } catch (Exception e) {
            return ResultUtil.error(400,"未知错误：" + e);
        }
        return ResultUtil.success("饭店评分成功");
    }
}
