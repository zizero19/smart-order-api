package com.smartorder.api.services;

import java.util.List;

import com.smartorder.api.dtos.orderItem.OrderItemRequestDTO;
import com.smartorder.api.dtos.orderItem.OrderItemResponseDTO;

public interface OrderItemService {

    OrderItemResponseDTO addItem(Long orderId, OrderItemRequestDTO request);

    List<OrderItemResponseDTO> listByOrderId(Long orderId);

    OrderItemResponseDTO changeQuantity(Long orderId, Long orderItemId, Integer quantity);

    void removeItem(Long orderId, Long orderItemId);

}
