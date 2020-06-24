package com.order.diancan.mapper;

import com.order.diancan.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    //insert注册用
    @Insert("INSERT INTO customer(nickname,account,password,phone,address) VALUE (#{nickname},#{account},#{password},#{phone},#{address})")
    void insert(@Param("nickname") String nickname,
               @Param("account") String account,
               @Param("password") String password,
               @Param("phone") String phone,
               @Param("address") String address
               );

    //根据账号查询用户信息
    @Select("SELECT * FROM customer WHERE account=#{account}")
    User select(@Param("account") String account);

    //修改用户信息
    @Update("UPDATE customer SET nickname = #{nickname},account = #{account},password = #{password},phone = #{phone},address = #{address} WHERE id = #{id}")
    void update(@Param("id") Long id,
                @Param("nickname") String nickname,
                @Param("account") String account,
                @Param("password") String password,
                @Param("phone") String phone,
                @Param("address") String address
                );

    //查询所有用户
    @Select("SELECT * FROM customer")
    List<User> selectAll();
}
