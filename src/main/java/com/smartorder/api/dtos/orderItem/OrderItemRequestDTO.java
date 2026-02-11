package com.smartorder.api.dtos.orderItem;

public record OrderItemRequestDTO(
        Long productId,
        Integer quantity
) {
}
