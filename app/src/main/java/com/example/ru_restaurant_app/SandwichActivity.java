package com.example.ru_restaurant_app;

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
 * Users can select protein, bread, optional toppings, quantity, and see a dynamic price update.
 * Also includes buttons for adding to order or preparing to add a combo.
 *
 * Layout uses:
 * - Spinners for protein, bread, and quantity
 * - Checkboxes for add-ons
 * - A TextView to display dynamic price updates
 * - Two buttons: "Add to Order" and "Add Combo"
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
     * Initializes the layout, sets up all spinners and checkboxes, and attaches event listeners.
     *
     * @param savedInstanceState the saved instance state, if any
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

        //Dropdowns
        setupSpinner(proteinSpinner, Protein.values());
        setupSpinner(breadSpinner, Bread.values());
        setupSpinner(quantitySpinner, Arrays.asList(1, 2, 3, 4, 5));

        // Create checkboxes for each add-on
        for (AddOns addOn : AddOns.values()) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(addOn.toString());
            checkBox.setTag(addOn);
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> updatePrice());
            addOnCheckboxes.add(checkBox);
            addOnsLayout.addView(checkBox);
        }

        // Attach listeners for price updates
        proteinSpinner.setOnItemSelectedListener(listener);
        breadSpinner.setOnItemSelectedListener(listener);
        quantitySpinner.setOnItemSelectedListener(listener);

        // Handle Add to order click
        findViewById(R.id.addToOrderButton).setOnClickListener(v -> {
            selectedProtein = (Protein) proteinSpinner.getSelectedItem();
            selectedBread = (Bread) breadSpinner.getSelectedItem();
            quantity = (int) quantitySpinner.getSelectedItem();
            selectedAddOns.clear();
            for (CheckBox cb : addOnCheckboxes) {
                if (cb.isChecked()) selectedAddOns.add((AddOns) cb.getTag());

                Sandwich sandwich = new Sandwich(selectedBread, selectedProtein, selectedAddOns, quantity);
                OrderManager.getInstance().getCurrentOrder().addItem(sandwich);

                Toast.makeText(this, "Added to order!", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle Add Combo (Add later)

    }

    /**
     * Generic method to populate a Spinner with enum values.
     *
     * @param spinner Spinner to populate
     * @param values  Array of enum values
     * @param <T>     Type of enum
     */
    private <T> void setupSpinner(Spinner spinner, T[] values) {
        ArrayAdapter<T> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    /**
     * Overloaded method to populate a Spinner with a list of integers (mostly for quantity).
     *
     * @param spinner Spinner to populate
     * @param values  List of integers
     */
    private void setupSpinner(Spinner spinner, List<Integer> values) {
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    /**
     * Listener that updates the price whenever an item is selected in any dropdown.
     */
    private final AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            updatePrice();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    };

    /**
     * Recalculates the sandwich price based on selected protein, add-ons, and quantity.
     * Updates the price TextView.
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
}
