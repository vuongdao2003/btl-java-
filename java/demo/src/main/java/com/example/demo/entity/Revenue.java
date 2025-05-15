package com.example.demo.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Revenue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // Thuộc tính ID, có thể là khóa chính

    private double totalSales; // Tổng doanh thu

    private Date reportDate; // Ngày báo cáo

    // Constructor
    public Revenue() {}

    public Revenue(Long id, double totalSales, Date reportDate) {
        this.id = id;
        this.totalSales = totalSales;
        this.reportDate = reportDate;
    }

    // Getter và Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }
}
