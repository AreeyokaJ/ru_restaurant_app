package com.example.model;
import java.util.ArrayList;

/**
 * Represents a customizable Sandwich menu item.
 * A sandwich includes a bread type, a protein choice, optional add-ons, and a quantity.
 * The price is calculated based on the protein base price and the selected add-ons,
 * multiplied by the quantity.
 *
 * @author Elvin Xu
 */
public class Sandwich extends MenuItem {
    protected Bread bread;
    protected Protein protein;
    protected ArrayList<AddOns> addOns;

    /**
     * Constructs a Sandwich object with the given components.
     *
     * @param bread    The type of bread
     * @param protein  The protein option
     * @param addOns   A list of selected add-ons (can be empty or null)
     * @param quantity The number of sandwiches ordered
     */
    public Sandwich(Bread bread, Protein protein, ArrayList<AddOns> addOns, int quantity) {
        this.bread = bread;
        this.protein = protein;
        this.addOns = (addOns != null) ? addOns : new ArrayList<>();
        this.quantity = quantity;
    }

    /**
     * Calculates the total price of the sandwich order.
     * Base price is based on the selected protein.
     * Each add-on adds its own price.
     * Total is multiplied by the quantity.
     *
     * @return The total price of the sandwich order
     */
    @Override
    public double price() {
        double total = protein.getBasePrice();
        for (AddOns addOn : addOns) {
            total += addOn.getPrice();
        }

        return total * quantity;
    }

    /**
     * Returns a string representation of the sandwich,
     * including bread type, protein, add-ons, quantity, and total price.
     *
     * @return A string summary of the sandwich order
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sandwich [Bread: ").append(bread);
        sb.append(", Protein: ").append(protein);
        sb.append(", Add-Ons: ");

        if (addOns.isEmpty()) {
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
}
