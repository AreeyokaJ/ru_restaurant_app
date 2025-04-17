package com.example.model;

/**
 * Represents a Combo meal which includes a sandwich or burger,
 * a small side (chips or apple slices), and a medium beverage (cola, tea, or juice).
 * Adds $2.00 to the base price of the sandwich or burger.
 *
 * The beverage and side sizes are fixed as:
 * - Beverage: Medium
 * - Side: Small
 *
 * Valid side options: CHIPS, APPLE_SLICES
 * Valid drink flavors: COLA, TEA, JUICE
 *
 * Author: Elvin Xu
 */
public class Combo extends MenuItem{
    private Sandwich sandwich;
    private Beverage drink;
    private Side side;

    private static final double COMBO_UPCHARGE = 2.00;

    /**
     * Constructs a Combo meal with a given sandwich, side, and drink.
     * Side is always small, drink is always medium. Quantity is inherited from sandwich.
     *
     * @param sandwich The sandwich or burger in the combo
     * @param side     The side item (must be CHIPS or APPLE_SLICES)
     * @param flavor   The drink flavor (must be COLA, TEA, or JUICE)
     */
    public Combo(Sandwich sandwich, Side side, Flavor flavor) {
        if (side != Side.CHIPS && side != Side.APPLE_SLICES) {
            throw new IllegalArgumentException("Combo only supports CHIPS or APPLE_SLICES as sides.");
        }

        if (flavor != Flavor.COLA && flavor != Flavor.TEA && flavor != Flavor.JUICE) {
            throw new IllegalArgumentException("Combo only supports COLA, TEA, or JUICE as drink flavors.");
        }

        this.sandwich = sandwich;
        this.side = side;
        this.drink = new Beverage(Size.MEDIUM, flavor, sandwich.quantity);
        this.quantity = sandwich.quantity;
    }

    /**
     * Calculates the total price of the combo:
     * (sandwich price + combo upcharge) * quantity
     *
     * @return total price of the combo meal
     */
    @Override
    public double price() {
        return (sandwich.price() / sandwich.quantity + COMBO_UPCHARGE) * quantity;
    }

    /**
     * Returns a string representation of the combo meal.
     *
     * @return combo description with sandwich, side, drink, and price
     */
    @Override
    public String toString() {
        return "Combo Meal [" +
                "Main: " + sandwich +
                ", Side: " + side.toString() + " (Small)" +
                ", Drink: " + drink.toString() +
                ", Total Combo Price: $" + String.format("%.2f", price()) +
                "]";
    }
}
