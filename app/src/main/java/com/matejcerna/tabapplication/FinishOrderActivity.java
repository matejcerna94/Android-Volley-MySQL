package com.matejcerna.tabapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import static com.matejcerna.tabapplication.TableActivity.tablesList;

public class FinishOrderActivity extends AppCompatActivity {

    public int position;
    public int table_id;
    public String table_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_order);

        position = getIntent().getExtras().getInt("key_table_position_finish_order");
        table_id = Integer.parseInt(tablesList.get(position).getTable_id());
        table_name = tablesList.get(position).getTable_name();
        Log.d("KOD table id finish", String.valueOf(table_id));
        Log.d("KOD table name finish", table_name);
    }
}
