package com.matejcerna.tabapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Staff {
    @SerializedName("staff_id")
    @Expose
    private String staff_id;

    @SerializedName("staff_name")
    @Expose
    private String staff_name;

    @SerializedName("staff_password")
    @Expose
    private String staff_password;

    @SerializedName("role_name")
    @Expose
    private String role_name;

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public String getStaff_password() {
        return staff_password;
    }

    public void setStaff_password(String staff_password) {
        this.staff_password = staff_password;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }
}
