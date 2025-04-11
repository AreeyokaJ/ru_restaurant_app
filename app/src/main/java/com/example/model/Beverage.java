package com.example.model;

/**
 * Represents a Beverage item with a selected size and flavor.
 * Pricing is based solely on size:
 * - Small: $1.99
 * - Medium: $2.49
 * - Large: $2.99
 *
 * Flavor does not affect price.
 *
 * Author: Elvin Xu
 */
public class Beverage extends MenuItem {
    private Size size;
    private Flavor flavor;

    private static final double SMALL_PRICE = 1.99;
    private static final double MEDIUM_PRICE = 2.49;
    private static final double LARGE_PRICE = 2.99;

    /**
     * Constructs a Beverage with specified size and flavor.
     *
     * @param size   The selected size
     * @param flavor The selected flavor
     * @param quantity The quantity ordered
     */
    public Beverage(Size size, Flavor flavor, int quantity) {
        this.size = size;
        this.flavor = flavor;
        this.quantity = quantity;
    }

    /**
     * Calculates the total price of the beverage based on size and quantity.
     *
     * @return Total price
     */
    @Override
    public double price() {
        double unitPrice;

        switch (size) {
            case SMALL:
                unitPrice = SMALL_PRICE;
                break;
            case MEDIUM:
                unitPrice = MEDIUM_PRICE;
                break;
            case LARGE:
                unitPrice = LARGE_PRICE;
                break;
            default:
                unitPrice = 0.0;
        }

        return unitPrice * quantity;
    }

    /**
     * Returns a string representation of the beverage item.
     *
     * @return Beverage description
     */
    @Override
    public String toString() {
        return "Beverage [Flavor: " + flavor +
                ", Size: " + size +
                ", Quantity: " + quantity +
                ", Price: $" + String.format("%.2f", price()) + "]";
    }
}