package com.order.diancan.controller;

import com.order.diancan.bean.User;
import com.order.diancan.mapper.UserMapper;
import com.order.diancan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.order.diancan.utils.Msg;
import com.order.diancan.utils.ResultUtil;

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
            return ResultUtil.error(201,"用户名已存在");
        } catch (Exception e) {
            return ResultUtil.error(400,"出现异常，用户注册失败");
        }
        return ResultUtil.registerSuccess();
    }

    //用户根据account登录
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Object login(@RequestBody User user){
        try {
            User userResult = userService.selectUserByAccount(user);
            if (userResult == null){
                return ResultUtil.error(202,"该用户没有注册");
            }else if (userResult.getPassword().equals(user.getPassword())){
                return ResultUtil.userLoginSuccess(userResult);
            }else {
                return ResultUtil.error(203,"密码错误");
            }
        } catch (Exception e) {
            return ResultUtil.error(400,"出现异常，用户登录失败");
        }
    }

    //测试
    @RequestMapping("/test")
    public String hello(){
        return "hello";
    }
}
