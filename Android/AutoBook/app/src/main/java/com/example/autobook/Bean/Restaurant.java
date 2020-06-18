package com.example.autobook.Bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Restaurant implements Comparable, Serializable {
    private int id;
    private String name;
    private String classification;
    private String phone;
    private Float total_score;
    private Float scoring_times;
    private String address;

    private Float ava;

    public Restaurant(int id, String name, String classification, String phone, Float total_score, Float scoring_times, String address) {
        this.id = id;
        this.name = name;
        this.classification = classification;
        this.phone = phone;
        this.total_score = total_score;
        this.scoring_times = scoring_times;
        this.address = address;
        this.ava=total_score/scoring_times;
    }

    public Float getAva() {
        return ava;
    }

    public void setAva(Float ava) {
        this.ava = ava;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getClassification() {
        return classification;
    }

    public String getPhone() {
        return phone;
    }

    public Float getTotal_score() {
        return total_score;
    }

    public Float getScoring_times() {
        return scoring_times;
    }

    public String getAddress() {
        return address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setTotal_score(Float total_score) {
        this.total_score = total_score;
    }

    public void setScoring_times(Float scoring_times) {
        this.scoring_times = scoring_times;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int compareTo(Object o) {
        return this.ava.compareTo(((Restaurant)o).getAva());
    }

    public Restaurant() {
    }
}
