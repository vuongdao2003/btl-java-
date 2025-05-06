package com.example.demo.dto;

import java.util.List;

public class OrderDTO {
    private String orderID;
    private String userID;
    private List<String> productList;
    private double totalAmount;
    private String orderDate;
    private String status;

    // Constructors
    public OrderDTO() {}

    public OrderDTO(String orderID, String userID, List<String> productList, double totalAmount, String orderDate, String status) {
        this.orderID = orderID;
        this.userID = userID;
        this.productList = productList;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.status = status;
    }

    // Getters and Setters
    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public List<String> getProductList() {
        return productList;
    }

    public void setProductList(List<String> productList) {
        this.productList = productList;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
