package com.matejcerna.tabapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import butterknife.ButterKnife;

import static com.matejcerna.tabapplication.TableActivity.tablesList;

public class NarudzbaActivity extends AppCompatActivity {

    String string_table_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_narudzba);
        ButterKnife.bind(this);

    }
}
