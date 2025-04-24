package com.example.ru_restaurant_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.manager.OrderManager;
import com.example.model.MenuItem;
import com.example.model.Order;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private ListView cartListView;
    private TextView subtotalTextView, taxTextView, totalTextView;
    private Button placeOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cartListView = findViewById(R.id.cartListView);
        subtotalTextView = findViewById(R.id.subtotalTextView);
        taxTextView = findViewById(R.id.taxTextView);
        totalTextView = findViewById(R.id.totalTextView);
        placeOrderButton = findViewById(R.id.placeOrderButton);

        //Get current cart from OrderManager
        Order cart = OrderManager.getInstance().getCurrentOrder();
        List<MenuItem> cartItems = cart.getItems();

        // Set up ListView with a basic adapter
        ArrayAdapter<MenuItem> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                cartItems
        );
        cartListView.setAdapter(adapter);

        // Display prices
        updateTotals(cart);


        onPlaceOrderButton(placeOrderButton, cartItems);
        onLongPress(cartListView, cartItems, adapter);
    }

    /**
     * This method handles the placement of orders
     * @param placeOrderButton
     * @param cartItems
     */
    private void onPlaceOrderButton(Button placeOrderButton, List<MenuItem> cartItems){
        // Handle place order
        placeOrderButton.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                Toast.makeText(this, "Cart is empty!", Toast.LENGTH_SHORT).show();
                return;
            }

            Order placedOrder = OrderManager.getInstance().placeOrder();
            Toast.makeText(this, "Order #" + placedOrder.getNumber() + " placed!", Toast.LENGTH_SHORT).show();

            finish(); // Close cart screen
        });
    }


    /**
     * This method handles the deletion of items from cart by user
     * @param cartListView
     * @param cartItems
     * @param adapter
     */

    private void onLongPress(ListView cartListView, List<MenuItem> cartItems, ArrayAdapter<MenuItem> adapter){
        // Enable long-press delete
        cartListView.setOnItemLongClickListener((parent, view, position, id) -> {
            MenuItem item = cartItems.get(position);
            // Show AlertDialog
            new AlertDialog.Builder(this)
                    .setTitle("Remove Item")
                    .setMessage("Are you sure you want to remove this item from the cart?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // User confirmed — remove the item
                        OrderManager.getInstance().getCurrentOrder().removeItem(item);
                        adapter.notifyDataSetChanged();
                        updateTotals(OrderManager.getInstance().getCurrentOrder());
                        Toast.makeText(this, "Item removed", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null) // Do nothing on cancel
                    .show();

            return true;
        });
    }

    private void updateTotals(Order cart) {
        subtotalTextView.setText(String.format("Subtotal: $%.2f", cart.getSubtotal()));
        taxTextView.setText(String.format("Tax: $%.2f", cart.getTax()));
        totalTextView.setText(String.format("Total: $%.2f", cart.getTotal()));
    }



}