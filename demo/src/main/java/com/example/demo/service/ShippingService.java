package com.example.demo.service;

import com.example.demo.entity.Order;
import com.example.demo.entity.Shipping;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ShippingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ShippingService {

    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private OrderRepository orderRepository;

    // 1. Create shipping
    public Shipping createShipping(String orderID, String shippingAddress, Date deliveryDate, String status, String shippingInfo) {
        try {
            Order order = orderRepository.findByOrderID(orderID);
            if (order == null) {
                throw new RuntimeException("Không tìm thấy Order với ID: " + orderID);
            }

            Shipping shipping = new Shipping();
            shipping.setShippingAddress(shippingAddress);
            shipping.setDeliveryDate(deliveryDate);
            shipping.setStatus(status);
            shipping.setShippingInfo(shippingInfo);
            shipping.setOrder(order);

            // Cập nhật trạng thái đơn hàng
            order.setStatus(status);

            // Lưu Shipping và Order
            Shipping savedShipping = shippingRepository.save(shipping);
            orderRepository.save(order);

            return savedShipping;
        } catch (RuntimeException e) {
            // Lỗi cụ thể do developer tạo (ví dụ: không tìm thấy order)
            throw e;
        } catch (Exception e) {
            // Lỗi không xác định (ví dụ: lỗi database, null pointer...)
            throw new RuntimeException("Lỗi khi tạo shipping: " + e.getMessage(), e);
        }
    }


    // 2. Update status
    public Shipping updateStatus(Long shippingId, String newStatus) {
        Shipping shipping = shippingRepository.findById(shippingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy OrderID " + shippingId));

        shipping.setStatus(newStatus);
        shippingRepository.save(shipping);

        // Update order status
        Order order = shipping.getOrder();
        order.setStatus(newStatus);
        orderRepository.save(order);

        return shipping;
    }

    // 3. Track shipping
    public Shipping trackShipping(Long shippingId) {
        return shippingRepository.findById(shippingId)
                .orElseThrow(() -> new RuntimeException("Shipping not found with ID: " + shippingId));
    }

    // 4. Track shipping history
    public List<Shipping> trackShippingHistory() {
        return shippingRepository.findAll();
    }
}
