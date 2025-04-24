package com.example.ru_restaurant_app;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapter.BeverageAdapter;
import com.example.model.SideOrBeverageItem;
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

    /**
     * Called when the activity is starting.
     * Sets up the layout, initializes title text based on intent,
     * and prepares the beverage list using RecyclerView.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down, this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_list);

        setupTitle();
        initializeRecyclerView();
    }

    /**
     * Sets the menu title based on the intent extra "menuTitle".
     * If none is provided, defaults to "Item".
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
     * Initializes the RecyclerView with a list of beverage flavors.
     * Each beverage is wrapped in a SideOrBeverageItem object for display.
     */
    private void initializeRecyclerView() {
        beverageRecyclerView = findViewById(R.id.beverageRecyclerView);
        beverageRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<SideOrBeverageItem> beverages = new ArrayList<>();
        for (Flavor flavor : Flavor.values()) {
            beverages.add(new SideOrBeverageItem(this, flavor));
        }

        adapter = new BeverageAdapter(beverages);
        beverageRecyclerView.setAdapter(adapter);
    }
}
