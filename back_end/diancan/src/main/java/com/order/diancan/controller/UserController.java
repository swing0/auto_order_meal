package com.order.diancan.controller;

import com.order.diancan.bean.OrderId;
import com.order.diancan.bean.OrderState;
import com.order.diancan.bean.OrderStates;
import com.order.diancan.bean.User;
import com.order.diancan.mapper.UserMapper;
import com.order.diancan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;
import com.order.diancan.utils.Msg;
import com.order.diancan.utils.ResultUtil;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //注册用户
    @RequestMapping(value = "register",method = RequestMethod.POST)
    public Msg register(@RequestBody User user){
        try {
            userService.insertUser(user);
        } catch (DuplicateKeyException e){
            return ResultUtil.error(201,"用户名已存在或电话号码已被注册");
        } catch (Exception e) {
            return ResultUtil.error(400,"出现异常，用户注册失败:" + e);
        }
        return ResultUtil.success("注册成功");
    }

    //修改用户信息
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public Msg update(@RequestBody User user){
        try {
            userService.updateUser(user);
        } catch (DuplicateKeyException e){
            return ResultUtil.error(201,"用户名已存在或电话号码已被注册");
        } catch (Exception e) {
            return ResultUtil.error(400,"出现异常，用户信息修改失败:" + e);
        }
        return ResultUtil.success("用户信息修改成功");
    }


    //用户根据account登录
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Msg login(@RequestBody User user){
        try {
            User userResult = userService.selectUserByAccount(user);
            if (userResult == null){
                return ResultUtil.error(202,"该用户没有注册");
            }else if (userResult.getPassword().equals(user.getPassword())){
                userResult.setPassword("");
                return ResultUtil.success(userResult);
            }else {
                return ResultUtil.error(203,"密码错误");
            }
        } catch (Exception e) {
            return ResultUtil.error(400,"出现异常，用户登录失败:" + e);
        }
    }

    //根据账号查找用户信息
    @RequestMapping(value = "infoByAccount")
    public Msg infoByAccount(@RequestBody User user){
        try {
            User userResult = userService.selectUserByAccount(user);
            if (userResult == null){
                return ResultUtil.error(202,"没有该用户信息");
            }else {
                return ResultUtil.success(userResult);
            }
        } catch (Exception e) {
            return ResultUtil.error(400,"出现异常，用户登录失败:" + e);
        }
    }

    //查询所有用户
    @RequestMapping(value = "allUser",method = RequestMethod.GET)
    public Msg allUser(){
        try {
            List<User> userList = userService.selectAllUser();
            return ResultUtil.success(userList);
        } catch (Exception e) {
            return ResultUtil.error(400,"未知错误,用户查询失败:" + e);
        }
    }

    //测试
    @RequestMapping("/test")
    public Msg hello(){
        List<OrderId> ids = new ArrayList<>();
        ids.add(0, new OrderId((long) 1));
        ids.add(1, new OrderId((long) 2));
        OrderStates orderStates = new OrderStates(ids,1);
        return ResultUtil.success(orderStates);
    }
}
