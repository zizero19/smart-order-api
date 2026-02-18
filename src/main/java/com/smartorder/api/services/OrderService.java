package com.smartorder.api.services;

import com.smartorder.api.dtos.order.OrderRequestDTO;
import com.smartorder.api.dtos.order.OrderResponseDTO;

import java.util.List;

public interface OrderService {

    OrderResponseDTO create(OrderRequestDTO request);

    List<OrderResponseDTO> getAllOrders();

    OrderResponseDTO findByOrderId(Long id);

    void delete(Long id);
}
