package com.example.autobook.Bean;

import android.graphics.Bitmap;

public class Refer_Dish {
    private int id;
    private String name;
    private int price;
    private Bitmap image;
    private boolean check;

    public Refer_Dish(int id, String name, int price, Bitmap image, boolean check) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.check = check;
    }

    public Refer_Dish() {
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

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    @Override
    public String toString() {
        return "Refer_Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", image=" + image +
                ", check=" + check +
                '}';
    }
}
