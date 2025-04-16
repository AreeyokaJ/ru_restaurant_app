package com.example.model;

/**
 * Class that represents add ons
 * @author Elvin Xu
 */
public enum AddOns {
    LETTUCE(0.30),
    TOMATOES(0.30),
    ONIONS(0.30),
    AVOCADO(0.50),
    CHEESE(1.00);

    private final double price;

    AddOns(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        switch (this) {
            case LETTUCE: return "Lettuce";
            case TOMATOES: return "Tomatoes";
            case ONIONS: return "Onions";
            case AVOCADO: return "Avocado";
            case CHEESE: return "Cheese";
            default: return "";
        }
    }
}
