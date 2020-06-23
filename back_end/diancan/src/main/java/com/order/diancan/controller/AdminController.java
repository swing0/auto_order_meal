package com.order.diancan.controller;

import com.order.diancan.bean.Admin;
import com.order.diancan.service.AdminService;
import com.order.diancan.utils.Msg;
import com.order.diancan.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    //管理员注册
    @RequestMapping(value = "register",method = RequestMethod.POST)
    public Msg register(@RequestBody Admin admin){
        try {
            adminService.insertAdmin(admin);
        } catch (DuplicateKeyException e){
            return ResultUtil.error(201,"管理员名已存在");
        } catch (Exception e) {
            return ResultUtil.error(400,"出现异常，管理员注册失败:" + e);
        }
        return ResultUtil.success("管理员注册成功");
    }

    //管理员根据account登录
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Object login(@RequestBody Admin admin){
        try {
            Admin adminResult = adminService.selectAdminByAccount(admin);
            if (adminResult == null){
                return ResultUtil.error(202,"该管理员没有注册");
            }else if (adminResult.getPassword().equals(admin.getPassword())){
                adminResult.setPassword("");
                return ResultUtil.success(adminResult);
            }else {
                return ResultUtil.error(203,"密码错误");
            }
        } catch (Exception e) {
            return ResultUtil.error(400,"出现异常，管理员登录失败" + e);
        }
    }
}
