package com.matejcerna.tabapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderTotal {
    @SerializedName("order_price")
    @Expose
    private String order_price;

    public String getOrder_price() {
        return order_price;
    }

    public void setOrder_price(String order_price) {
        this.order_price = order_price;
    }
}
