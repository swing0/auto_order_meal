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

    //修改订单信息
    @Update("UPDATE order_list SET price = #{price},date = #{date},state = #{state},dish_id_list = #{dish_id_list},restaurant_id = #{restaurant_id},customer_id = #{customer_id} WHERE id = #{id}")
    void updateOrder(@Param("id") Long id,
                     @Param("price") long price,
                     @Param("date") String date,
                     @Param("state") int state,
                     @Param("dish_id_list") String dish_id_list,
                     @Param("restaurant_id") long restaurant_id,
                     @Param("customer_id") long customer_id
                    );

    //改变订单状态
    @Update("UPDATE order_list SET state = #{state}, date = #{date} WHERE id = #{id}")
    void updateState(@Param("id") long id,@Param("state") int state,@Param("date") String date);


    //查找用户的特定状态订单,可能有多个
    @Select("SELECT * FROM order_list WHERE customer_id = #{customer_id} AND state = #{state}")
    List<Order> orderFromCustomerAndState(@Param("customer_id") long customer_id, @Param("state") int state);

    //查询订单
    @Select("SELECT * FROM order_list")
    List<Order> allOrder();

    //根据订单id查出菜品id列表
    @Select("SELECT dish_id_list FROM order_list WHERE id = #{id}")
    String selectDishIdList(@Param("id") long id);
}
