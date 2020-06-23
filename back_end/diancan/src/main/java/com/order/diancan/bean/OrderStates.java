package com.order.diancan.bean;

import java.util.List;

public class OrderStates {
    private List<OrderId> data;
    private Integer state;

    public OrderStates(){}

    public OrderStates(List<OrderId> data, Integer state) {
        this.data = data;
        this.state = state;
    }

    public List<OrderId> getData() {
        return data;
    }

    public void setData(List<OrderId> data) {
        this.data = data;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}

