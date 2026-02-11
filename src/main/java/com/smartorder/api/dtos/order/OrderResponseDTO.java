package com.smartorder.api.dtos.order;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
        Long id,
        Long customerId,
        List<Long> productIds,
        String status,
        LocalDateTime createdAt
) {
}
