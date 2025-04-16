package com.example.model;

/**
 * Class that represents protein
 * @author Elvin Xu
 */
public enum Protein {
    ROAST_BEEF(10.99),
    CHICKEN(8.99),
    SALMON(9.99);

    private final double basePrice;

    Protein(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getBasePrice() {
        return basePrice;
    }

    @Override
    public String toString() {
        switch (this) {
            case ROAST_BEEF: return "Roast Beef";
            case CHICKEN: return "Chicken";
            case SALMON: return "Salmon";
            default: return "";
        }
    }
}
