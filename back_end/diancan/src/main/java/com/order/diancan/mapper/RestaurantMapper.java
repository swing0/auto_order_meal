package com.order.diancan.mapper;

import com.order.diancan.bean.Restaurant;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RestaurantMapper {
    //注册饭店
    @Insert("INSERT INTO restaurant(name,address,phone,classification,scoring_times,total_score) VALUE (#{name},#{address},#{phone},#{classification},#{scoring_times},#{total_score})")
    //获取刚插入数据的id
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void insert(@Param("name") String name,
                @Param("address") String address,
                @Param("phone") String phone,
                @Param("classification") String classification,
                @Param("scoring_times") long scoring_times,
                @Param("total_score") long total_score
                );

    //根据手机号查找刚插入的饭店的id
    @Select("SELECT * FROM restaurant WHERE phone=#{phone}")
    int selectIdFromPhone(@Param("phone") String phone);

    //查询所有的饭店信息
    @Select("SELECT * FROM restaurant")
    List<Restaurant> selectAllInfo();

    //查找所有的饭店id
    @Select("SELECT id FROM restaurant")
    int[] selectAllId();
}
