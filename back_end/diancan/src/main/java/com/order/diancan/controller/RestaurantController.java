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
            return ResultUtil.registerRestaurantSuccess(restaurant_id);
        } catch (DuplicateKeyException e){
            return ResultUtil.error(201,"电话号码已注册");
        } catch (Exception e) {
            return ResultUtil.error(400,"出现异常，餐厅注册失败");
        }
    }

    //返回所有的餐厅信息
    @RequestMapping(value = "info",method = RequestMethod.GET)
    public String allInfo(){
        String json = JSON.toJSONString(restaurantService.allRestaurant());
        return json;
    }
}
