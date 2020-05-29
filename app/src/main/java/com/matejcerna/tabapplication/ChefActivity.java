package com.matejcerna.tabapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ChefActivity extends AppCompatActivity {

    String staff_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef);

        staff_name = getIntent().getStringExtra("chef_name");
        getSupportActionBar().setTitle("Signed in as: " + staff_name);
    }
}
