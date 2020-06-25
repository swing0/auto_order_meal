package com.order.diancan.service;

import com.order.diancan.bean.Admin;
import com.order.diancan.mapper.AdminMapper;
import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    //注册管理员
    public void insertAdmin(Admin admin){
        adminMapper.insert(admin.getAccount(),admin.getPassword(),admin.getRestaurant_id());
    }
    //TODO:注册管理员前应注册饭店

    //管理员根据account登录
    public Admin selectAdminByAccount(Admin admin){
        return adminMapper.select(admin.getAccount());
    }
}
