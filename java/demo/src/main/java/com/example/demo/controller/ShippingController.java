package com.example.demo.controller;

import com.example.demo.entity.Shipping;
import com.example.demo.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/shipping")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;

    // 1. Create shipping
    @PostMapping("/create")
    public Shipping createShipping(
            @RequestParam String orderID,
            @RequestParam String shippingAddress,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date deliveryDate,
            @RequestParam String status,
            @RequestParam String shippingInfo
    ) {
        return shippingService.createShipping(orderID, shippingAddress, deliveryDate, status, shippingInfo);
    }

    // 2. Update status
    @PutMapping("/update-status")
    public Shipping updateStatus(
            @RequestParam Long shippingId,
            @RequestParam String newStatus
    ) {
        return shippingService.updateStatus(shippingId, newStatus);
    }

    // 3. Track shipping
    @GetMapping("/track/{id}")
    public Shipping trackShipping(@PathVariable Long id) {
        return shippingService.trackShipping(id);
    }

    // 4. Track shipping history
    @GetMapping("/history")
    public List<Shipping> trackShippingHistory() {
        return shippingService.trackShippingHistory();
    }
}
