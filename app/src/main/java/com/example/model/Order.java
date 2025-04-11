package com.example.model;
import java.util.ArrayList;

/**
 * Class that represents order
 * @author Elvin Xu
 */
public class Order {
    private static final double NJ_TAX_RATE = 0.06625;

    private final int number; //a unique integer to identify the order
    private ArrayList<MenuItem> items;

    /**
     * Constructs a new Order with a unique order number.
     *
     * @param number Unique ID for the order
     */
    public Order(int number) {
        this.number = number;
        this.items = new ArrayList<>();
    }

    /**
     * Adds a menu item to the order.
     *
     * @param item The MenuItem to add
     */
    public void addItem(MenuItem item) {
        items.add(item);
    }

    /**
     * Removes a menu item from the order.
     *
     * @param item The MenuItem to remove
     * @return true if successfully removed, false otherwise
     */
    public boolean removeItem(MenuItem item) {
        return items.remove(item);
    }

    /**
     * Calculates the subtotal of the order.
     *
     * @return Total cost of all items before tax
     */
    public double getSubtotal() {
        double subtotal = 0;
        for (MenuItem item : items) {
            subtotal += item.price();
        }
        return subtotal;
    }

    /**
     * Calculates the sales tax based on NJ tax rate.
     *
     * @return Calculated tax amount
     */
    public double getTax() {
        return getSubtotal() * NJ_TAX_RATE;
    }

    /**
     * Calculates the final total including tax.
     *
     * @return Total amount due
     */
    public double getTotal() {
        return getSubtotal() + getTax();
    }

    /**
     * Gets the order number.
     *
     * @return The order ID
     */
    public int getNumber() {
        return number;
    }

    /**
     * Gets the list of items in the order.
     *
     * @return List of MenuItems
     */
    public ArrayList<MenuItem> getItems() {
        return items;
    }

    /**
     * Returns a detailed summary of the order.
     *
     * @return String with all order details
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order #").append(number).append("\n");

        for (MenuItem item : items) {
            sb.append("- ").append(item.toString()).append("\n");
        }

        sb.append(String.format("Subtotal: $%.2f\n", getSubtotal()));
        sb.append(String.format("Tax (6.625%%): $%.2f\n", getTax()));
        sb.append(String.format("Total: $%.2f\n", getTotal()));
        return sb.toString();
    }
}