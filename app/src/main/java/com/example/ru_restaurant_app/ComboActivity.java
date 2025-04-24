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
 * ComboActivity allows a user to finalize a combo meal based on a given sandwich.
 * The user selects a side and a drink, sees the updated price, and adds the combo to the order.
 *
 * Author: Elvin Xu
 */
public class ComboActivity extends AppCompatActivity {
    private Sandwich baseSandwich;
    private Spinner sideSpinner, drinkSpinner;
    private TextView priceText;

    /**
     * Called when the activity starts. Reads the sandwich from intent and initializes the UI.
     *
     * @param savedInstanceState Bundle containing state data if activity was previously closed
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.combo_selection);

        baseSandwich = getIntent().getSerializableExtra("sandwich", Sandwich.class);
        if (baseSandwich == null) {
            Toast.makeText(this, "No sandwich passed to combo!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        sideSpinner = findViewById(R.id.sideSpinner);
        drinkSpinner = findViewById(R.id.drinkSpinner);
        priceText = findViewById(R.id.comboPrice);

        setupSpinner(sideSpinner, Arrays.asList(Side.CHIPS, Side.APPLE_SLICES));
        setupSpinner(drinkSpinner, Arrays.asList(Flavor.COLA, Flavor.TEA, Flavor.JUICE));

        sideSpinner.setOnItemSelectedListener(updateListener);
        drinkSpinner.setOnItemSelectedListener(updateListener);

        findViewById(R.id.addComboToOrder).setOnClickListener(v -> handleAddCombo());

        updatePrice();
    }

    /**
     * Handles the logic of creating a Combo object, adding it to the order,
     * and navigating back to the main screen.
     */
    private void handleAddCombo() {
        Side side = (Side) sideSpinner.getSelectedItem();
        Flavor drink = (Flavor) drinkSpinner.getSelectedItem();

        Combo combo = new Combo(baseSandwich, side, drink);
        OrderManager.getInstance().getCurrentOrder().addItem(combo);
        Toast.makeText(this, "Combo added to order!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(ComboActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    /**
     * Updates the price text field based on the selected side and drink.
     * Displays $0.00 if selection is invalid or not ready.
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

    /**
     * Listener that triggers price updates whenever a spinner selection changes.
     */
    private final android.widget.AdapterView.OnItemSelectedListener updateListener =
            new android.widget.AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                    updatePrice();
                }

                @Override
                public void onNothingSelected(android.widget.AdapterView<?> parent) {}
            };

    /**
     * Generic utility function to populate a Spinner dropdown with provided list items.
     *
     * @param spinner Spinner view to populate
     * @param items   List of items to populate the spinner with
     * @param <T>     Type of items in the spinner
     */
    private <T> void setupSpinner(Spinner spinner, List<T> items) {
        ArrayAdapter<T> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}