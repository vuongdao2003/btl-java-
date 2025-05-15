package com.example.demo.service;

import com.example.demo.entity.Payment;
import com.example.demo.entity.Revenue;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.RevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RevenueService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RevenueRepository revenueRepository;

    // 1. Tính doanh thu trong một ngày cụ thể
    public Revenue createRevenue(Date date) {
        Date startOfDay = getStartOfDay(date);
        Date endOfDay = getEndOfDay(date);

        List<Payment> payments = paymentRepository.findByPaymentDateBetween(startOfDay, endOfDay);
        double total = payments.stream().mapToDouble(Payment::getAmount).sum();

        Revenue revenue = new Revenue();
        revenue.setTotalSales(total);
        revenue.setReportDate(date);

        return revenueRepository.save(revenue);
    }


    // 2. Tính tổng doanh thu từ ngày bắt đầu đến ngày kết thúc
    public double calculateTotalRevenue(Date startDate, Date endDate) {
        List<Payment> payments = paymentRepository.findByPaymentDateBetween(startDate, endDate);
        return payments.stream().mapToDouble(Payment::getAmount).sum();
    }

    // 3. Lấy thông tin doanh thu theo Ngày
    public Revenue getRevenueByDate(Date date) {
        return revenueRepository.findByReportDate(date)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy doanh thu cho ngày: " + date));
    }

    // === Hàm hỗ trợ xử lý thời gian ===

    private Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }
}
