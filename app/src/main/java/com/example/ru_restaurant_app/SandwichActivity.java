package com.example.ru_restaurant_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.manager.OrderManager;
import com.example.model.AddOns;
import com.example.model.Bread;
import com.example.model.Protein;
import com.example.model.Sandwich;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Activity that allows users to customize a Sandwich order.
 * Users select protein, bread, toppings, and quantity, then add to order or proceed to combo.
 *
 * @author Elvin Xu
 */
public class SandwichActivity extends AppCompatActivity {
    private Spinner proteinSpinner, breadSpinner, quantitySpinner;
    private TextView priceText;
    private LinearLayout addOnsLayout;
    private List<CheckBox> addOnCheckboxes = new ArrayList<>();

    private Protein selectedProtein;
    private Bread selectedBread;
    private final ArrayList<AddOns> selectedAddOns = new ArrayList<>();
    private int quantity = 1;

    /**
     * Sets up the sandwich selection interface: dropdowns, checkboxes, and buttons.
     * Adds event listeners and initial price calculation.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sandwich_selection);

        proteinSpinner = findViewById(R.id.proteinSpinner);
        breadSpinner = findViewById(R.id.breadSpinner);
        quantitySpinner = findViewById(R.id.quantitySpinner);
        priceText = findViewById(R.id.priceText);
        addOnsLayout = findViewById(R.id.addOnsLayout);

        setupSpinner(proteinSpinner, Protein.values());
        setupSpinner(breadSpinner, Bread.values());
        setupSpinner(quantitySpinner, Arrays.asList(1, 2, 3, 4, 5));
        setupAddOnCheckboxes();

        proteinSpinner.setOnItemSelectedListener(listener);
        breadSpinner.setOnItemSelectedListener(listener);
        quantitySpinner.setOnItemSelectedListener(listener);

        findViewById(R.id.addToOrderButton).setOnClickListener(v -> addSandwichToOrder());
        findViewById(R.id.addComboButton).setOnClickListener(v -> goToComboScreen());
    }

    /**
     * Sets up checkboxes for all available add-ons with price update listeners.
     */
    private void setupAddOnCheckboxes() {
        for (AddOns addOn : AddOns.values()) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(addOn.toString());
            checkBox.setTag(addOn);
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> updatePrice());
            addOnCheckboxes.add(checkBox);
            addOnsLayout.addView(checkBox);
        }
    }

    /**
     * Adds the current sandwich configuration to the order and shows a confirmation toast.
     */
    private void addSandwichToOrder() {
        Sandwich sandwich = getCurrentSandwich();
        OrderManager.getInstance().getCurrentOrder().addItem(sandwich);
        Toast.makeText(this, "Added " + quantity + " sandwiches to order!", Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * Starts the ComboActivity and passes the current sandwich to it.
     */
    private void goToComboScreen() {
        Sandwich sandwich = getCurrentSandwich();
        Intent comboIntent = new Intent(SandwichActivity.this, ComboActivity.class);
        comboIntent.putExtra("sandwich", sandwich);
        startActivity(comboIntent);
    }

    /**
     * Returns the currently selected sandwich based on UI selections.
     *
     * @return A new Sandwich object with selected attributes
     */
    private Sandwich getCurrentSandwich() {
        selectedProtein = (Protein) proteinSpinner.getSelectedItem();
        selectedBread = (Bread) breadSpinner.getSelectedItem();
        quantity = (int) quantitySpinner.getSelectedItem();

        selectedAddOns.clear();
        for (CheckBox cb : addOnCheckboxes) {
            if (cb.isChecked()) {
                selectedAddOns.add((AddOns) cb.getTag());
            }
        }

        return new Sandwich(selectedBread, selectedProtein, selectedAddOns, quantity);
    }

    /**
     * Updates the price display based on selected protein, add-ons, and quantity.
     */
    private void updatePrice() {
        Protein protein = (Protein) proteinSpinner.getSelectedItem();
        int quantity = (int) quantitySpinner.getSelectedItem();
        double price = (protein != null) ? protein.getBasePrice() : 0;

        for (CheckBox cb : addOnCheckboxes) {
            if (cb.isChecked()) {
                AddOns addOn = (AddOns) cb.getTag();
                price += addOn.getPrice();
            }
        }

        price *= quantity;
        priceText.setText(String.format("Price $%.2f", price));
    }

    /**
     * Generic utility method to populate a Spinner with enum values.
     *
     * @param spinner Spinner view to populate
     * @param values  Enum values
     * @param <T>     Type of enum
     */
    private <T> void setupSpinner(Spinner spinner, T[] values) {
        ArrayAdapter<T> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    /**
     * Overloaded method to populate a Spinner with a list (e.g., for quantity).
     *
     * @param spinner Spinner view to populate
     * @param values  List of items
     */
    private void setupSpinner(Spinner spinner, List<Integer> values) {
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    /**
     * Listener for any spinner change. Triggers price updates.
     */
    private final AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            updatePrice();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    };
}