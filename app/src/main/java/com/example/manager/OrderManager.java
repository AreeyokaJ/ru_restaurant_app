package com.example.manager;

import com.example.model.Order;

public class OrderManager {
    private static OrderManager instance;
    private Order currentOrder;

    private OrderManager() {
        currentOrder = new Order(1);
    }

    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }
}
