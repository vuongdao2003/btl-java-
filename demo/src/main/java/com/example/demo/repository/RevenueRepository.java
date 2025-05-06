package com.example.demo.repository;

import com.example.demo.entity.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Long> {
    Optional<Revenue> findByReportDate(Date reportDate);
}
