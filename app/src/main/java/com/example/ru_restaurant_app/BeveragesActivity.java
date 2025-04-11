package com.example.ru_restaurant_app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapter.BeverageAdapter;
import com.example.model.Beverage;
import com.example.model.BeverageItem;
import com.example.model.Flavor;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity that displays a list of beverage flavors using a RecyclerView.
 *
 * Author: Elvin Xu
 */
public class BeveragesActivity extends AppCompatActivity {
    private RecyclerView beverageRecyclerView;
    private BeverageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beverages);

        beverageRecyclerView = findViewById(R.id.beverageRecyclerView);
        beverageRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<BeverageItem> beverages = new ArrayList<>();
        for (Flavor flavor : Flavor.values()) {
            beverages.add(new BeverageItem(this, flavor));
        }

        adapter = new BeverageAdapter(beverages);
        beverageRecyclerView.setAdapter(adapter);
    }
}
