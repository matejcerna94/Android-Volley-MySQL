package com.matejcerna.tabapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import butterknife.ButterKnife;

import static com.matejcerna.tabapplication.RecyclerViewAdapterCategoryName.itemsList;
import static com.matejcerna.tabapplication.TableActivity.tablesList;

public class NarudzbaActivity extends AppCompatActivity {

    int table_id;
    String string_item_id;
    String item_name;
    String item_image;
    String item_category;
    int item_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_narudzba);
        ButterKnife.bind(this);

        string_item_id = getIntent().getExtras().getString("item_id");
        item_id = Integer.parseInt(string_item_id);
        item_name = getIntent().getExtras().getString("item_name");
        item_image = getIntent().getExtras().getString("item_image");
        item_category = getIntent().getExtras().getString("item_category");

        table_id = PocetnaActivity.table_id;
        Log.d("kod stol id narudzba", String.valueOf(table_id));
        Log.d("kod item id narudzba", String.valueOf(item_id));
        Log.d("kod item name narudzba", item_name);
        Log.d("kod item image narudzba", item_image);
        Log.d("kod item categ narudzba", item_category);
    }
}
