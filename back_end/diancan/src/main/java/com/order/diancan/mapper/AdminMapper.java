package com.order.diancan.mapper;

import com.order.diancan.bean.Admin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper {
    //insert注册用
    @Insert("INSERT INTO admin(account,password,restaurant_id) VALUE (#{account},#{password},#{restaurant_id})")
    int insert(@Param("account") String account,
               @Param("password") String password,
               @Param("restaurant_id") long restaurant_id
    );

    //select登录用
    @Select("SELECT * FROM admin WHERE account=#{account}")
    Admin select(@Param("account") String account);
}
