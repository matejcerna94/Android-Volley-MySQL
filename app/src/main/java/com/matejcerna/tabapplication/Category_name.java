package com.matejcerna.tabapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category_name {
    @SerializedName("item_id")
    @Expose
    private String item_id;

    @SerializedName("item_name")
    @Expose
    private String item_name;

    @SerializedName("item_image")
    @Expose
    private String item_image;

    @SerializedName("category_name")
    @Expose
    private String category_name;

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
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

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
