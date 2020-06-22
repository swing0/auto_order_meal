package com.order.diancan.bean;

import java.io.Serializable;

public class Dish implements Serializable {
    private long id;
    private String name;
    private String image;
    private String classification;
    private String cuisine;
    private String sales_volume;
    private long price;
    private long scoring_times;
    private long total_score;
    private long restaurant_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getSales_volume() {
        return sales_volume;
    }

    public void setSales_volume(String sales_volume) {
        this.sales_volume = sales_volume;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getScoring_times() {
        return scoring_times;
    }

    public void setScoring_times(long scoring_times) {
        this.scoring_times = scoring_times;
    }

    public long getTotal_score() {
        return total_score;
    }

    public void setTotal_score(long total_score) {
        this.total_score = total_score;
    }

    public long getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(long restaurant_id) {
        this.restaurant_id = restaurant_id;
    }
}
