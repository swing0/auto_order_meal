package com.order.diancan.mapper;

import com.order.diancan.bean.Dish;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DishMapper {
    //根据饭店id查询其所有菜品
    @Select("SELECT * FROM dish WHERE restaurant_id=#{restaurant_id}")
    List<Dish> selectDishFromRestaurant(int restaurant_id);


    //根据菜品id返回菜品对象
    @Select("SELECT * FROM dish WHERE id = #{id}")
    Dish selectDishById(long id);

    //根据name返回菜品信息
    @Select("SELECT * FROM dish WHERE name = #{name}")
    List<Dish> selectDishByName(String name);

    //修改菜品信息
    @Update("UPDATE dish SET name = #{name},image = #{image},classification = #{classification},cuisine = #{cuisine},sales_volume = #{sales_volume},price = #{price},scoring_times = #{scoring_times},total_score = #{total_score},restaurant_id = #{restaurant_id} WHERE id = #{id}")
    void update(@Param("id") Long id,
                @Param("name") String name,
                @Param("image") String image,
                @Param("classification") String classification,
                @Param("cuisine") String cuisine,
                @Param("sales_volume") String sales_volume,
                @Param("price") Long price,
                @Param("scoring_times") Long scoring_times,
                @Param("total_score") Long total_score,
                @Param("restaurant_id") Long restaurant_id
                );
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

    //查询所有菜品
    @Select("SELECT * FROM dish")
    List<Dish> selectAll();

    //根据id使菜品销量加一
    @Update("UPDATE dish SET sales_volume = sales_volume + 1 WHERE id = #{id}")
    void saleVolumeAdd(long id);

    //顾客评价菜品时，菜品的评价次数加一
    @Update("UPDATE dish SET scoring_times = scoring_times + 1 WHERE id = #{id}")
    void addScoring_times(long id);

    //顾客评价菜品时，菜品的评价增加相应分数
    @Update("UPDATE dish SET total_score = total_score + #{score} WHERE id = #{id}")
    void addTotal_score(long id,long score);
}
