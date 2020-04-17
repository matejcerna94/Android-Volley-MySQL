package com.matejcerna.tabapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Table {
    @SerializedName("table_id")
    @Expose
    private String table_id;

    @SerializedName("table_number")
    @Expose
    private String table_number;

    @SerializedName("table_capacity")
    @Expose
    private String table_capacity;

    @SerializedName("table_availability")
    @Expose
    private String table_availability;

    public String getTable_id() {
        return table_id;
    }

    public void setTable_id(String table_id) {
        this.table_id = table_id;
    }

    public String getTable_name() {
        return table_number;
    }

    public void setTable_name(String table_name) {
        this.table_number = table_name;
    }

    public String getTable_capacity() {
        return table_capacity;
    }

    public void setTable_capacity(String table_capacity) {
        this.table_capacity = table_capacity;
    }

    public String getTable_availability() {
        return table_availability;
    }

    public void setTable_availability(String table_availability) {
        this.table_availability = table_availability;
    }
}
