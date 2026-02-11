package com.smartorder.api.dtos.order;

import java.util.List;

public record OrderRequestDTO(
        Long clientId,
        List<Long> productIds
) {
}
