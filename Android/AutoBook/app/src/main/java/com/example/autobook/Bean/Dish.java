package com.example.autobook.Bean;

import android.graphics.Bitmap;

public class Dish {
    private int id;
    private String name;
    private int price;
    private int restaurant_id;
    private int sales_volume;
    private int scoring_times;
    private int total_score;
    private String classification;
    private String cuisine;
    private String image;

    public Dish() {
    }

    public Dish(int id, String name, int price, int restaurant_id, int sales_volume, int scoring_times, int total_score, String classification, String cuisine, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.restaurant_id = restaurant_id;
        this.sales_volume = sales_volume;
        this.scoring_times = scoring_times;
        this.total_score = total_score;
        this.classification = classification;
        this.cuisine = cuisine;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public int getSales_volume() {
        return sales_volume;
    }

    public void setSales_volume(int sales_volume) {
        this.sales_volume = sales_volume;
    }

    public int getScoring_times() {
        return scoring_times;
    }

    public void setScoring_times(int scoring_times) {
        this.scoring_times = scoring_times;
    }

    public int getTotal_score() {
        return total_score;
    }

    public void setTotal_score(int total_score) {
        this.total_score = total_score;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
