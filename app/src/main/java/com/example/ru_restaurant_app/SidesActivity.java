package com.example.ru_restaurant_app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapter.SideAdapter;
import com.example.model.SideOrBeverageItem;
import com.example.model.Side;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity that displays a list of sides using a RecyclerView.
 * Mirrors the functionality of BeveragesActivity but uses SideAdapter.
 *
 * Author: Elvin Xu
 */
public class SidesActivity extends AppCompatActivity {
    private RecyclerView sidesRecyclerView;
    private SideAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_list); // Reusing same layout

        sidesRecyclerView = findViewById(R.id.beverageRecyclerView); // Reusing same ID
        sidesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<SideOrBeverageItem> sides = new ArrayList<>();
        for (Side side : Side.values()) {
            sides.add(new SideOrBeverageItem(this, side));
        }
        adapter = new SideAdapter(sides);
        sidesRecyclerView.setAdapter(adapter);
    }
}