package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "orderID")
    private Order order;

    private String orderID;
    private double amount;
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;

    public Payment() {}

    public Payment(String orderID, double amount, String status, Date paymentDate) {
        this.orderID = orderID;
        this.amount = amount;
        this.status = status;
        this.paymentDate = paymentDate;
    }

    // Getters & Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
}
