package com.example.ru_restaurant_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.manager.OrderManager;
import com.example.model.AddOns;
import com.example.model.Bread;
import com.example.model.Burger;
import com.example.model.Protein;
import com.example.model.Sandwich;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

/**
 * Activity for managing user activity on burger ordering screen
 *
 */
public class BurgerActivity extends AppCompatActivity {

    /**
     * Initializes burger ordering screen and all elements
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_burger);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Connect all UI elements
        Spinner pattySpinner = findViewById(R.id.patty_spinner);
        TextView priceTextView = findViewById(R.id.priceText);
        Spinner breadSpinner = findViewById(R.id.bread_spinner);
        CheckBox cheeseBox = findViewById(R.id.cheeseBox);
        CheckBox lettuceBox = findViewById(R.id.lettuceBox);
        CheckBox tomatoesBox = findViewById(R.id.tomatoBox);
        CheckBox avocadoBox = findViewById(R.id.avocadoBox);
        CheckBox onionBox = findViewById(R.id.onionBox);
        Spinner quantitySpinner = findViewById(R.id.quantity_spinner);
        Button orderButton = findViewById(R.id.add_to_order);
        Button comboButton = findViewById(R.id.make_combo);

        ArrayList<CheckBox> checkBoxes = new ArrayList<>();
        Collections.addAll(checkBoxes, cheeseBox, lettuceBox, tomatoesBox, avocadoBox, onionBox);

        //Create burger with default values
        Burger burger = new Burger(Bread.BRIOCHE, new ArrayList<>(), false, 1);

        updatePriceOnPattySelection(pattySpinner, burger, priceTextView);
        updatePriceOnToppingSelection(burger, checkBoxes, priceTextView);
        updatePriceOnQuantitySelection(burger, quantitySpinner, priceTextView);
        onAddToOrder(orderButton, burger);
        onAddToCombo(comboButton, burger);


    }

    /**
     * Takes user to combo ordering screen after ordering a burger
     * @param comboButton
     * @param burger
     */
    public void onAddToCombo(Button comboButton, Burger burger){
        comboButton.setOnClickListener(v -> {
            Intent comboIntent = new Intent(BurgerActivity.this, ComboActivity.class);
            comboIntent.putExtra("sandwich", burger);
            startActivity(comboIntent);
        });
    }


    /**
     * onAddToOrder adds burger selection to order on click
     * @param orderButton
     * @param burger
     */
    public void onAddToOrder(Button orderButton, Burger burger){
        // Handle Add to order click
        orderButton.setOnClickListener(v -> {

            OrderManager.getInstance().getCurrentOrder().addItem(burger);

            Toast.makeText(this, "Added burger(s) to order!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    /**
     * Update price upon user selection of quantity
     * @param burger
     * @param quantitySpinner
     * @param priceTextView
     */
    public void updatePriceOnQuantitySelection(Burger burger, Spinner quantitySpinner,
                                               TextView priceTextView){
        //Listen for changes
        quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selection = Integer.parseInt((String)(parent.getItemAtPosition(position)));
                burger.setQuantity(selection);

                // Update price display
                updatePriceDisplay(burger, priceTextView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * Update price display upon user selection of topping
     * @param burger
     * @param checkBoxes
     * @param priceTextView
     */
    public void updatePriceOnToppingSelection(Burger burger, ArrayList<CheckBox> checkBoxes,
                                              TextView priceTextView) {
        for (CheckBox checkBox: checkBoxes) {
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    burger.getAddOns().add(AddOns.CHEESE);
                } else {
                    burger.getAddOns().remove(AddOns.CHEESE);
                }

                updatePriceDisplay(burger, priceTextView);
            });
        }
    }


    /**
     * Updates price as user selects single or double patty
     * @param pattySpinner
     * @param burger
     * @param priceTextView
     */
    private void updatePriceOnPattySelection(Spinner pattySpinner, Burger burger, TextView priceTextView){
        //Listen for changes
        pattySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                burger.setDoublePatty(selection.equalsIgnoreCase("Double"));

                // Update price display
                updatePriceDisplay(burger, priceTextView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * Updates text field after user selection
     * @param burger
     * @param priceTextView
     */
    private void updatePriceDisplay(Burger burger, TextView priceTextView){
        DecimalFormat df = new DecimalFormat("#.##");
        double updatedPrice = burger.price();
        priceTextView.setText("Price $" + df.format(updatedPrice));
    }

}