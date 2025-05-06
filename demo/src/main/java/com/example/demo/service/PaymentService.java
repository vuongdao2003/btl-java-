package com.example.demo.service;

import com.example.demo.entity.Order;
import com.example.demo.entity.Payment;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaymentService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public String processPayment(String orderID) {
        Order order = orderRepository.findById(orderID)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với ID: " + orderID));

        // Cập nhật trạng thái đơn hàng
        order.setStatus("Đã thanh toán");
        orderRepository.save(order);

        // Sinh paymentID mới
        String lastPaymentID = paymentRepository.findLastPaymentID();
        String newPaymentID = generateNextPaymentID(lastPaymentID);

        // Tạo bản ghi thanh toán
        Payment payment = new Payment();
        payment.setId(newPaymentID);
        payment.setOrder(order); // Đối tượng liên kết
        payment.setPaymentDate(new Date());
        payment.setStatus("Đã thanh toán");

        paymentRepository.save(payment);

        return "Thanh toán thành công cho đơn hàng: " + orderID;
    }

    public String generateNextPaymentID(String lastID) {
        if (lastID == null || lastID.isEmpty()) return "PM001";
        int number = Integer.parseInt(lastID.substring(2)); // bỏ 'PM'
        return String.format("PM%03d", number + 1);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public List<Payment> getPaymentsByUserID(String userID) {
        return paymentRepository.findByUserID(userID);
    }

}
