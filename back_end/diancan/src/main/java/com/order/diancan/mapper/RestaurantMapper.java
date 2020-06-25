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

    //修改饭店信息
    @Update("UPDATE restaurant SET name = #{name},address = #{address},phone = #{phone},classification = #{classification},scoring_times = #{scoring_times},total_score = #{total_score} WHERE id = #{id}")
    void update(@Param("id") Long id,
                @Param("name") String name,
                @Param("address") String address,
                @Param("phone") String phone,
                @Param("classification") String classification,
                @Param("scoring_times") long scoring_times,
                @Param("total_score") long total_score
    );

    //根据手机号查找刚插入的饭店的id
    @Select("SELECT * FROM restaurant WHERE phone=#{phone}")
    int selectIdFromPhone(@Param("phone") String phone);

    //根据名字查找饭店信息
    @Select("SELECT * FROM restaurant WHERE name = #{name}")
    Restaurant selectByName(@Param("name") String name);

    //查询所有的饭店信息
    @Select("SELECT * FROM restaurant")
    List<Restaurant> selectAllInfo();

    //查找所有的饭店id
    @Select("SELECT id FROM restaurant")
    int[] selectAllId();

    //根据饭店id返回饭店对象
    @Select("SELECT * FROM restaurant WHERE id = #{id}")
    Restaurant selectById(long id);

    //顾客评价饭店时，饭店的评价次数加一
    @Update("UPDATE restaurant SET scoring_times = scoring_times + 1 WHERE id = #{id}")
    void addScoring_times(long id);

    //顾客评价饭店时，饭店的增加相应的评分
    @Update("UPDATE restaurant SET total_score = total_score + #{score} WHERE id = #{id}")
    void addTotal_score(long id,long score);
}
