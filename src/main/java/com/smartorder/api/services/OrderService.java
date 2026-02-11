package com.smartorder.api.services;

import com.smartorder.api.dtos.order.OrderRequestDTO;
import com.smartorder.api.dtos.order.OrderResponseDTO;

import java.util.List;

public interface OrderService {

    OrderResponseDTO create(OrderRequestDTO request);

    OrderResponseDTO findByOrderId(Long id);

    List<OrderResponseDTO> findAllOrders();

    void delete(Long id);
}
