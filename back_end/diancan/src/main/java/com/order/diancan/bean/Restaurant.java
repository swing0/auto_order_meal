package com.order.diancan.bean;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private int id;
    private String name;
    private String address;
    private String phone;
    private String classification;
    private long scoring_times;
    private long total_score;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
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
}
