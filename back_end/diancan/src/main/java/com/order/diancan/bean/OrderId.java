package com.order.diancan.bean;

import java.io.Serializable;
import java.util.List;

public class OrderId implements Serializable {
    private Long id;

    public OrderId(){}

    public OrderId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
