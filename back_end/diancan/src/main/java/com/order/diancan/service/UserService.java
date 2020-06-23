package com.order.diancan.service;

import com.order.diancan.bean.User;
import com.order.diancan.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserMapper userMapper;

    //注册用户
    public void insertUser(User user){
        userMapper.insert(user.getNickname(),user.getAccount(),user.getPassword(),user.getPhone(),user.getAddress());
    }

    //修改用户信息
    public void updateUser(User user){
        userMapper.update(user.getId(),user.getNickname(),user.getAccount(),user.getPassword(),user.getPhone(),user.getAddress());
    }


    //用户根据account登录
    public User selectUserByAccount(User user){
        return userMapper.select(user.getAccount());
    }

    //查询所有用户
    public List<User> selectAllUser(){return userMapper.selectAll();}


}
