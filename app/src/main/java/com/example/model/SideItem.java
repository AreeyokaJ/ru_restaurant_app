package com.example.model;

/**
 * Represents a side item in the menu.
 * A side has a base price from the Side enum and an additional charge
 * based on size (Medium: +$0.50, Large: +$1.00).
 *
 * @author Elvin Xu
 */
public class SideItem extends MenuItem {
    private Side side;
    private Size size;

    /**
     * Constructs a SideItem with the given side type, size, and quantity.
     *
     * @param side     The type of side selected (e.g., Chips, Fries)
     * @param size     The size of the side (Small, Medium, Large)
     * @param quantity The number of sides ordered
     */
    public SideItem(Side side, Size size, int quantity) {
        this.side = side;
        this.size = size;
        this.quantity = quantity;
    }

    /**
     * Returns the total price of this side item based on size and quantity.
     *
     * @return the total price
     */
    @Override
    public double price() {
        double basePrice = side.getBasePrice();
        double sizeAdd = 0.0;

        switch (size) {
            case MEDIUM:
                sizeAdd = 0.50;
                break;
            case LARGE:
                sizeAdd = 1.00;
                break;
            default:
                sizeAdd = 0.0;
        }

        return (basePrice + sizeAdd) * quantity;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) x%d Price: %.2f", side.toString(), size.toString(), quantity, price());
    }
}