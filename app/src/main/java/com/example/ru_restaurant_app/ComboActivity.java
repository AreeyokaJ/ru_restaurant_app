package com.example.ru_restaurant_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.manager.OrderManager;
import com.example.model.Combo;
import com.example.model.Flavor;
import com.example.model.Sandwich;
import com.example.model.Side;

import java.util.Arrays;
import java.util.List;

/**
 * ComboActivity allows a user to finalize a combo meal based on a given sandwich or burger.
 * The user selects a valid side (chips or apples), a medium drink (cola, tea, juice).
 * The price is updated live and the final Combo object is added to the global order.
 *
 * Author: Elvin Xu
 */
public class ComboActivity extends AppCompatActivity {
    private Sandwich baseSandwich;
    private Spinner sideSpinner, drinkSpinner;
    private TextView priceText;

    /**
     * Initializes layout and populates dropdowns. Reads the passed sandwich from intent.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.combo_selection);

        //Get Sandwich object from intent
        baseSandwich = getIntent().getSerializableExtra("sandwich", Sandwich.class);

        if (baseSandwich == null) {
            Toast.makeText(this, "No sandwich passed to combo!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        sideSpinner = findViewById(R.id.sideSpinner);
        drinkSpinner = findViewById(R.id.drinkSpinner);
        priceText = findViewById(R.id.comboPrice);

        // Populate side and drinks
        setupSpinner(sideSpinner, Arrays.asList(Side.CHIPS, Side.APPLE_SLICES));
        setupSpinner(drinkSpinner, Arrays.asList(Flavor.COLA, Flavor.TEA, Flavor.JUICE));

        // Update price when changed
        sideSpinner.setOnItemSelectedListener(updateListener);
        drinkSpinner.setOnItemSelectedListener(updateListener);

        findViewById(R.id.addComboToOrder).setOnClickListener(v -> {
            Side side = (Side) sideSpinner.getSelectedItem();
            Flavor drink = (Flavor) drinkSpinner.getSelectedItem();

            Combo combo = new Combo(baseSandwich, side, drink);
            OrderManager.getInstance().getCurrentOrder().addItem(combo);
            Toast.makeText(this, "Combo added to order!", Toast.LENGTH_SHORT).show();

            // Go back to MainActivity
            Intent intent = new Intent(ComboActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        updatePrice();
    }

    /**
     * Updates the price field by recalculating the Combo price.
     */
    private void updatePrice() {
        try {
            Side side = (Side) sideSpinner.getSelectedItem();
            Flavor drink = (Flavor) drinkSpinner.getSelectedItem();
            Combo combo = new Combo(baseSandwich, side, drink);
            priceText.setText(String.format("Price: $%.2f", combo.price()));
        } catch (Exception e) {
            priceText.setText("Price: $0.00");
        }
    }

    private final android.widget.AdapterView.OnItemSelectedListener updateListener = new android.widget.AdapterView.OnItemSelectedListener() {
        @Override public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
            updatePrice();
        }
        @Override public void onNothingSelected(android.widget.AdapterView<?> parent) {}
    };

    /**
     * Generic method to populate a Spinner with any list of items.
     */
    private <T> void setupSpinner(Spinner spinner, List<T> items) {
        ArrayAdapter<T> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
