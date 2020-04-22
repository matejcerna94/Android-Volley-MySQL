package com.matejcerna.tabapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("order_id")
    @Expose
    private String order_id;

    @SerializedName("item_name")
    @Expose
    private String item_name;

    @SerializedName("item_image")
    @Expose
    private String item_image;

    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("single_item_price")
    @Expose
    private String single_item_price;

    @SerializedName("order_price")
    @Expose
    private String order_price;

    @SerializedName("note")
    @Expose
    private String note;

    @SerializedName("order_date_and_time")
    @Expose
    private String order_date_and_time;

    @SerializedName("item_id")
    @Expose
    private String item_id;

    @SerializedName("table_id")
    @Expose
    private String table_id;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_image() {
        return item_image;
    }

    public void setItem_image(String item_image) {
        this.item_image = item_image;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSingle_item_price() {
        return single_item_price;
    }

    public void setSingle_item_price(String single_item_price) {
        this.single_item_price = single_item_price;
    }

    public String getOrder_price() {
        return order_price;
    }

    public void setOrder_price(String order_price) {
        this.order_price = order_price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOrder_date_and_time() {
        return order_date_and_time;
    }

    public void setOrder_date_and_time(String order_date_and_time) {
        this.order_date_and_time = order_date_and_time;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getTable_id() {
        return table_id;
    }

    public void setTable_id(String table_id) {
        this.table_id = table_id;
    }
}
