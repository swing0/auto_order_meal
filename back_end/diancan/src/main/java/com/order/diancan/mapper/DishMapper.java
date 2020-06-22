package com.order.diancan.mapper;

import com.order.diancan.bean.Dish;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {
    //根据饭店id查询其所有菜品
    @Select("SELECT * FROM dish WHERE restaurant_id=#{restaurant_id}")
    List<Dish> selectDishFromRestaurant(int restaurant_id);

    //根据菜品id查询其信息
    @Select("SELECT * FROM dish WHERE id=#{id}")
    List<Dish> selectDishFromId(int id);

    //根据菜品id返回菜品对象
    @Select("SELECT * FROM dish WHERE id = #{id}")
    Dish selectDishById(long id);

    //添加菜品
    @Insert("INSERT INTO dish(name,image,classification,cuisine,sales_volume,price,scoring_times,total_score,restaurant_id) " +
            "VALUE (#{name},#{image},#{classification},#{cuisine},#{sales_volume},#{price},#{scoring_times},#{total_score},#{restaurant_id})")
    void insert(@Param("name") String name,
                @Param("image") String image,
                @Param("classification") String classification,
                @Param("cuisine") String cuisine,
                @Param("sales_volume") String sales_volume,
                @Param("price") Long price,
                @Param("scoring_times") Long scoring_times,
                @Param("total_score") Long total_score,
                @Param("restaurant_id") Long restaurant_id);
}
