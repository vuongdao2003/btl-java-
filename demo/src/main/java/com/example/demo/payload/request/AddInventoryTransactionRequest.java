package com.example.demo.payload.request;


import java.time.LocalDateTime;

public class AddInventoryTransactionRequest {
    private int productId;
    private int quantitychange;
    private String type;
    private LocalDateTime date;
    private String note;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getQuantitychange() {
        return quantitychange;
    }

    public void setQuantitychange(int quantitychange) {
        this.quantitychange = quantitychange;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
