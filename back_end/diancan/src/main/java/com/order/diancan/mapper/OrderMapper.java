package com.order.diancan.mapper;

import com.order.diancan.bean.Order;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface OrderMapper {
    //添加订单
    @Insert("INSERT INTO order_list(price,date,state,dish_id_list,restaurant_id,customer_id) VALUE (#{price},#{date},#{state},#{dish_id_list},#{restaurant_id},#{customer_id})")
    void insert(@Param("price") long price,
                @Param("date") String date,
                @Param("state") int state,
                @Param("dish_id_list") String dish_id_list,
                @Param("restaurant_id") long restaurant_id,
                @Param("customer_id") long customer_id
                );

    //改变订单状态
    @Update("UPDATE order_list SET state = #{state} WHERE id = #{id}")
    void updateState(@Param("id") long id,@Param("state") int state);

    //查找用户的特定状态订单,可能有多个
    @Select("SELECT * FROM order_list WHERE customer_id = #{customer_id} AND state = #{state}")
    List<Order> orderFromCustomerAndState(@Param("customer_id") long customer_id, @Param("state") int state);
}
