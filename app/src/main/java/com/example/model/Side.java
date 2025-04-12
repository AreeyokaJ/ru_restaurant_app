package com.example.model;

/**
 * Represents a side item on the menu with base small size pricing.
 * Medium and large sizes are +$0.50 and +$1.00 respectively.
 * Used for both standalone and combo customization.
 *
 *  * Options include: Chips, Fries, Onion Rings, Apple Slices.
 *
 * Author: Elvin Xu
 */
public enum Side {
    CHIPS(1.99),
    FRIES(2.49),
    ONION_RINGS(3.29),
    APPLE_SLICES(1.29);

    private final double basePrice;

    /**
     * Creates a Side enum
     * @param basePrice of the item
     */
    Side(double basePrice) {
        this.basePrice = basePrice;
    }

    /**
     * Gets the base price of the side (small size).
     *
     * @return Small size price of the side
     */
    public double getBasePrice() {
        return basePrice;
    }

    /**
     * Returns the name of the side in a human-readable format.
     *
     * @return String name of the side
     */
    @Override
    public String toString() {
        switch (this) {
            case CHIPS: return "Chips";
            case FRIES: return "Fries";
            case ONION_RINGS: return "Onion Rings";
            case APPLE_SLICES: return "Apple Slices";
            default: return "";
        }
    }
}
