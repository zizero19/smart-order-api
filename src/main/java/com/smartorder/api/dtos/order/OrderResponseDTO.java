package com.smartorder.api.dtos.order;

import com.smartorder.api.dtos.orderItem.OrderItemResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
        Long id,
        Long customerId,
        String status,
        BigDecimal totalPrice,
        LocalDateTime createdAt,
        List<OrderItemResponseDTO> items
) {
}
