package com.example.demo.service;

import java.util.*;
import com.example.demo.entity.Order;
import com.example.demo.entity.Products;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderManagementService {

    private List<Order> orderList = new ArrayList<>();

    /*public Order createOrder(String userID, List<Products> productList) {
        double totalAmount = calculateTotalAmount(productList);
        Order newOrder = new Order(userID, productList, totalAmount, new Date(), "Pending");
        orderList.add(newOrder);
        return newOrder;
    }*/

    public Order checkOrderStatus(Long orderID) {
        return orderList.stream()
                .filter(order -> order.getOrderID().equals(orderID))
                .findFirst()
                .orElse(null);
    }

    public Order updateOrderStatus(Long orderID, String status) {
        Order order = checkOrderStatus(orderID);
        if (order != null) {
            order.updateStatus(status);
            return order;
        }
        return null;
    }

    public void processReturns(Long orderID) {
        Order order = checkOrderStatus(orderID);
        if (order != null) {
            order.updateStatus("Returned");
            System.out.println("Order " + orderID + " has been returned.");
        }
    }

    private double calculateTotalAmount(List<Products> productList) {
        double total = 0.0;
        for (Products product : productList) {
            total += product.getPrice();
        }
        return total;
    }
}