package com.example.demo.repository;

import com.example.demo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {

    @Query(value = "SELECT orderID FROM orders ORDER BY orderid DESC LIMIT 1", nativeQuery = true)
    String findLastOrderID();

    Order findByOrderID(String orderID);

}
