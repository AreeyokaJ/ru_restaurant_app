package com.example.model;
import java.util.ArrayList;

/**
 * Represents a Burger menu item.
 * A burger includes a bread type (limited to certain types), a single or double beef patty,
 * optional add-ons, and quantity.
 * The price is based on the patty size and the selected add-ons, multiplied by quantity.
 * Inherits from Sandwich.
 *
 * @author Elvin Xu
 */
public class Burger extends Sandwich {
    private boolean doublePatty;

    private static final double SINGLE_BASE_PRICE = 6.99;
    private static final double DOUBLE_PATTY_EXTRA = 2.50;

    /**
     * Constructs a Burger object.
     *
     * @param bread       The type of bread (must be Brioche, Wheat bread, or Pretzel)
     * @param addOns      A list of selected add-ons
     * @param doublePatty True for double patty, false for single
     * @param quantity    The number of burgers ordered
     */
    public Burger(Bread bread, ArrayList<AddOns> addOns, boolean doublePatty, int quantity) {
        super(bread, null, addOns, quantity); // Protein not needed for burgers
        this.doublePatty = doublePatty;
    }

    /**
     * Calculates the total price of the burger order.
     * Base price is $6.99 for single, and $9.49 for double.
     * Each add-on adds its own price.
     * Total is multiplied by quantity.
     *
     * @return The total price for the burger(s)
     */
    @Override
    public double price() {
        double basePrice = SINGLE_BASE_PRICE;
        if (doublePatty) {
            basePrice += DOUBLE_PATTY_EXTRA;
        }

        for (AddOns addOn : addOns) {
            basePrice += addOn.getPrice();
        }

        return basePrice * quantity;
    }

    /**
     * Returns a string representation of the burger, including bread, patty type,
     * add-ons, quantity, and total price.
     *
     * @return A string summary of the burger order
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Burger [Bread: ").append(bread);
        sb.append(", Patty: ").append(doublePatty ? "Double" : "Single");
        sb.append(", Add-Ons: ");

        if (addOns == null || addOns.isEmpty()) {
            sb.append("None");
        } else {
            for (AddOns addOn : addOns) {
                sb.append(addOn).append(" ");
            }
        }

        sb.append(", Quantity: ").append(quantity);
        sb.append(", Price: $").append(String.format("%.2f", price()));
        sb.append("]");
        return sb.toString();
    }

    /**
     * Returns whether the burger has a double patty.
     * @return true if double patty, false otherwise
     */
    public boolean isDoublePatty() {
        return doublePatty;
    }

    /**
     * Sets the value of  whether the burger has a double patty or not
     * @param newStatus new status of single/double patty
     */
    public void setDoublePatty(boolean newStatus) {
        this.doublePatty = newStatus;
    }

    /**
     * Mutator method for bread field
     * @param bread
     */
    public void setBread(Bread bread){
        this.bread = bread;
    }

    /**
     * Accessor method for addOns
     */
    public ArrayList<AddOns> getAddOns(){
        return this.addOns;
    }

    /**
     * Mutator method for quantity
     */
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    /**
     * Accessor method for quantity
     * @return
     */
    public int getQuantity(){
        return this.quantity;
    }


}
