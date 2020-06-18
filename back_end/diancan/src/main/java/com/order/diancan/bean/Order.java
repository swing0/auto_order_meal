package com.order.diancan.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class Order implements Serializable {
    private Long id;
    private Long price;
    private Timestamp date;
    private Integer state;
    private Long restaurant_id;
    private String dish_id_list;
    private Long customer_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = (Timestamp) date;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Long restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getDish_id_list() {
        return dish_id_list;
    }

    public void setDish_id_list(String dish_id_list) {
        this.dish_id_list = dish_id_list;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }
}
