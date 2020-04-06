package com.matejcerna.tabapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CategoriesPagerAdapter extends FragmentStatePagerAdapter {
    //int mNumOfTabs;
    private List<Category> categoryList = new ArrayList<>();
    // Title List
    private List<String> categories = new ArrayList<>();
    int product_result;

    public CategoriesPagerAdapter(FragmentManager fm, List<Category> categoryList, List<String> categories) {
        super(fm);
        this.categoryList = categoryList;
        this.categories = categories;
    }

    @Override
    public Fragment getItem(int position) {
        /*Category currentCat = categoryList.get(position);
        return DynamicFragment.newInstance(currentCat.getCategory_name());*/
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new DrinksFragment();
                break;
            case 1:
                fragment = new DesertFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }
}
