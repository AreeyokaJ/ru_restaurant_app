package com.example.model;

import java.io.Serializable;

/**
 * Abstract class that represents menu item
 * @author Elvin Xu
 */
public abstract class MenuItem implements Serializable  {
    protected int quantity;

    /**
     * The abstract method price calculates the price for the specific MenuItem object
     * @return double value of price
     */
    public abstract double price();
}
