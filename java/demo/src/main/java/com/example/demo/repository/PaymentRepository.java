package com.example.demo.repository;

import com.example.demo.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    @Query("SELECT p FROM Payment p WHERE p.order.userID = :userID")
    List<Payment> findByUserID(String userID);

    @Query(value = "SELECT id FROM payments ORDER BY id DESC LIMIT 1", nativeQuery = true)
    String findLastPaymentID();

    List<Payment> findByPaymentDateBetween(Date startDate, Date endDate);
}
