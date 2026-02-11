package com.smartorder.api.dtos.orderItem;

import java.math.BigDecimal;

public record OrderItemResponseDTO(
        String productName,
        Integer quantity,
        BigDecimal unitPrice
) {
}
