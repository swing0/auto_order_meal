package com.example.autobook.Bean;

import java.io.Serializable;
import java.util.List;

public class OrderDetails implements Serializable {
    private int restaurant_id;
    private String restaurant_name;
    private String restaurant_address;
    private String restaurant_phone;
    private Long order_id;
    private Long price;
    private String date;
    private Integer state;
    private boolean paid;
    private List<SimpleDish> simpleDishes;

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public OrderDetails() {
    }

    public OrderDetails(int restaurant_id, String restaurant_name, String restaurant_address, String restaurant_phone, Long order_id, Long price, String date, Integer state, List<SimpleDish> simpleDishes) {
        this.restaurant_id = restaurant_id;
        this.restaurant_name = restaurant_name;
        this.restaurant_address = restaurant_address;
        this.restaurant_phone = restaurant_phone;
        this.order_id = order_id;
        this.price = price;
        this.date = date;
        this.state = state;
        this.simpleDishes = simpleDishes;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getRestaurant_address() {
        return restaurant_address;
    }

    public void setRestaurant_address(String restaurant_address) {
        this.restaurant_address = restaurant_address;
    }

    public String getRestaurant_phone() {
        return restaurant_phone;
    }

    public void setRestaurant_phone(String restaurant_phone) {
        this.restaurant_phone = restaurant_phone;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public List<SimpleDish> getSimpleDishes() {
        return simpleDishes;
    }

    public void setSimpleDishes(List<SimpleDish> simpleDishes) {
        this.simpleDishes = simpleDishes;
    }
}
