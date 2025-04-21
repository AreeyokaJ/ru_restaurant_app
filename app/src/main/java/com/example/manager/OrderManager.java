package com.example.manager;

import com.example.model.MenuItem;
import com.example.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private static OrderManager instance;
    private Order currentOrder;
    private final List<Order> orderHistory;
    private int nextOrderNumber = 1;

    private OrderManager() {
        orderHistory = new ArrayList<>();
        currentOrder = new Order(nextOrderNumber);
    }

    public static synchronized OrderManager getInstance(){
        if (instance == null){
            instance = new OrderManager();
        }
        return instance;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void addToCart(MenuItem item){
        currentOrder.addItem(item);
    }

    public void clearCart(){
        currentOrder = new Order(nextOrderNumber);
    }

    public Order placeOrder(){
        Order placed = currentOrder;
        orderHistory.add(placed);
        nextOrderNumber++;
        currentOrder = new Order(nextOrderNumber);
        return placed;
    }

    public List<Order> getOrderHistory(){
        return orderHistory;
    }

}
