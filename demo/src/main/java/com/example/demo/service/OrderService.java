package com.example.demo.service;

import com.example.demo.dto.OrderDTO;
import com.example.demo.entity.Order;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductsReponsitory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductsReponsitory productsRepository;

    @Transactional
    public List<Order> createOrders(OrderDTO orderDTO) {
        List<Order> createdOrders = new ArrayList<>();

        List<String> productList = orderDTO.getProductList();
        if (productList == null || productList.isEmpty()) {
            throw new RuntimeException("Danh sách sản phẩm rỗng!");
        }

        Date orderDate;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            orderDate = dateFormat.parse(orderDTO.getOrderDate());
        } catch (Exception e) {
            throw new RuntimeException("Định dạng ngày tháng không hợp lệ. Định dạng đúng: yyyy-MM-dd");
        }

        for (String productID : productList) {
            Order order = new Order();

            String lastOrderID = orderRepository.findLastOrderID();
            String newOrderID = generateNextOrderID(lastOrderID);
            order.setOrderID(newOrderID);

            order.setUserID(orderDTO.getUserID());
            order.setProductID(productID);
            order.setOrderDate(orderDate);
            order.setStatus(orderDTO.getStatus());

            orderRepository.save(order);
            createdOrders.add(order);
        }

        return createdOrders;
    }

    // Sinh ID tu dong
    private String generateNextOrderID(String lastOrderID) {
        if (lastOrderID == null || lastOrderID.isEmpty()) {
            return "O001";
        }
        int number = Integer.parseInt(lastOrderID.substring(1));
        number++;
        return String.format("O%03d", number);
    }

    // All Order
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Order by ID
    public Order getOrderById(String orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public Order updateOrderStatus(String orderId, String newStatus) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.updateStatus(newStatus);
            return orderRepository.save(order);
        }
        return null;
    }

    public boolean deleteOrder(String orderId) {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            return true;
        }
        return false;
    }

}
