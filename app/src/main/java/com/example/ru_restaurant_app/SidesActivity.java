package com.example.ru_restaurant_app;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapter.SideAdapter;
import com.example.model.SideOrBeverageItem;
import com.example.model.Side;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity that displays a list of available side items using a RecyclerView.
 * Shares layout and logic style with BeveragesActivity, but uses SideAdapter.
 *
 * Author: Elvin Xu
 */
public class SidesActivity extends AppCompatActivity {

    private RecyclerView sidesRecyclerView;
    private SideAdapter adapter;

    /**
     * Initializes the activity, sets title based on intent, and loads all sides into the RecyclerView.
     *
     * @param savedInstanceState State of the activity if it's being recreated
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_list); // Reusing shared layout

        setupTitle();
        initializeRecyclerView();
    }

    /**
     * Dynamically sets the menu title based on the intent's "menuTitle" extra.
     * Defaults to "Item" if none is provided.
     */
    private void setupTitle() {
        TextView titleView = findViewById(R.id.menuTitle);
        String itemType = getIntent().getStringExtra("menuTitle");

        if (itemType != null) {
            String localizedTitle = getString(R.string.select_your_generic, itemType);
            titleView.setText(localizedTitle);
        } else {
            titleView.setText(getString(R.string.select_your_generic, "Item"));
        }
    }

    /**
     * Initializes the RecyclerView with a list of side items.
     * Uses a SideAdapter to display all defined Side enum values.
     */
    private void initializeRecyclerView() {
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
