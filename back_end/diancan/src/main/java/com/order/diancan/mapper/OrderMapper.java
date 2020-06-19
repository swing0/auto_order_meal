package com.order.diancan.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.sql.Timestamp;

@Mapper
public interface OrderMapper {
    //添加订单
    @Insert("INSERT INTO order_list(price,date,state,dish_id_list,restaurant_id,customer_id) VALUE (#{price},#{date},#{state},#{dish_id_list},#{restaurant_id},#{customer_id})")
    void insert(@Param("price") long price,
                @Param("date") Timestamp date,
                @Param("state") int state,
                @Param("dish_id_list") String dish_id_list,
                @Param("restaurant_id") long restaurant_id,
                @Param("customer_id") long customer_id
                );

    //改变订单状态
    @Update("UPDATE order_list SET state = #{state} WHERE id = #{id}")
    void updateState(@Param("id") long id,@Param("state") int state);
}
