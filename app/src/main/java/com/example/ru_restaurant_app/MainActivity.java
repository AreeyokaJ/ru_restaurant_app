package com.example.ru_restaurant_app;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.ru_restaurant_app.databinding.ActivityMainBinding;

/**
 * Main menu screen that allows navigation to Beverages, Sides, Sandwiches, Burgers, Cart, and Orders.
 * Also contains a toggle for switching between light and dark mode.
 *
 * @author Elvin Xu
 */
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    /**
     * Initializes the main screen and sets up all button click listeners and dark mode toggle.
     *
     * @param savedInstanceState Bundle containing previous state if available
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        setupClickListeners();
        // Ensure light mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    /**
     * Initializes all button click listeners to navigate to appropriate screens.
     */
    private void setupClickListeners() {
        binding.beveragesButton.setOnClickListener(v -> openActivity(BeveragesActivity.class, "Beverage"));
        findViewById(R.id.sidesButton).setOnClickListener(v -> openActivity(SidesActivity.class, "Side"));
        findViewById(R.id.sandwichButton).setOnClickListener(v -> startActivity(new Intent(this, SandwichActivity.class)));
        onBurgerButtonClick(findViewById(R.id.burgerButton));
        onCartButtonClick(findViewById(R.id.currentOrder));
        onOrderButtonClick(findViewById(R.id.orders));
    }

    /**
     * Navigates to the given activity and optionally adds a "menuTitle" extra.
     *
     * @param cls        Destination activity class
     * @param menuTitle  Title to pass in the intent (nullable)
     */
    private void openActivity(Class<?> cls, String menuTitle) {
        Intent intent = new Intent(MainActivity.this, cls);
        intent.putExtra("menuTitle", menuTitle);
        startActivity(intent);
    }

    /**
     * Handles click event to open the orders screen.
     *
     * @param orderButton ImageButton for Orders
     */
    private void onOrderButtonClick(ImageButton orderButton) {
        orderButton.setOnClickListener(v -> startActivity(new Intent(this, OrderActivity.class)));
    }

    /**
     * Handles click event to open the cart screen.
     *
     * @param cartButton ImageButton for Cart
     */
    private void onCartButtonClick(ImageButton cartButton) {
        cartButton.setOnClickListener(v -> startActivity(new Intent(this, CartActivity.class)));
    }

    /**
     * Handles click event to open the burger selection screen.
     *
     * @param burgerButton ImageButton for Burgers
     */
    private void onBurgerButtonClick(ImageButton burgerButton) {
        burgerButton.setOnClickListener(v -> startActivity(new Intent(this, BurgerActivity.class)));
    }

    /**
     * Inflates the menu options in the toolbar.
     *
     * @param menu Menu object to inflate into
     * @return true if successfully created
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Handles menu option selection.
     *
     * @param item Selected menu item
     * @return true if handled
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Handles navigation when the up button in the toolbar is pressed.
     *
     * @return true if navigation was handled
     */
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
}
