package com.matejcerna.tabapplication;

import android.app.Application;

import java.util.ArrayList;

public class MyApplication extends Application {

    private ArrayList<Category> categoryList = new ArrayList<>();

    public ArrayList<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ArrayList<Category> categoryList) {
        this.categoryList = categoryList;
    }
}
