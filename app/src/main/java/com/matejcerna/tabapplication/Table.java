package com.matejcerna.tabapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Table {
    @SerializedName("table_id")
    @Expose
    private String table_id;

    @SerializedName("table_name")
    @Expose
    private String table_name;

    @SerializedName("table_capacity")
    @Expose
    private String table_capacity;

    public String getTable_id() {
        return table_id;
    }

    public void setTable_id(String table_id) {
        this.table_id = table_id;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getTable_capacity() {
        return table_capacity;
    }

    public void setTable_capacity(String table_capacity) {
        this.table_capacity = table_capacity;
    }
}
