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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_list);

        TextView titleView = findViewById(R.id.menuTitle);
        String itemType = getIntent().getStringExtra("menuTitle");

        if (itemType != null) {
            String localizedTitle = getString(R.string.select_your_generic, itemType);
            titleView.setText(localizedTitle);
        } else {
            titleView.setText(getString(R.string.select_your_generic, "Item"));
        }

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
