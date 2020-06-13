package com.order.diancan.mapper;

import com.order.diancan.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    //insert注册用
    @Insert("INSERT INTO customer(nickname,account,password,phone,address) VALUE (#{nickname},#{account},#{password},#{phone},#{address})")
    void insert(@Param("nickname") String nickname,
               @Param("account") String account,
               @Param("password") String password,
               @Param("phone") long phone,
               @Param("address") String address
               );

    //select登录用
    @Select("SELECT * FROM customer WHERE account=#{account}")
    User select(@Param("account") String account);
}
