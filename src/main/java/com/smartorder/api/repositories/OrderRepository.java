package com.smartorder.api.repositories;

import com.smartorder.api.enums.OrderStatus;
import com.smartorder.api.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByClientId(Long clientId);
}
