package com.example.model;

/**
 * Abstract class that represents menu item
 * @author Elvin Xu
 */
public abstract class MenuItem {
    protected int quantity;

    /**
     * The abstract method price calculates the price for the specific MenuItem object
     * @return double value of price
     */
    public abstract double price();
}
