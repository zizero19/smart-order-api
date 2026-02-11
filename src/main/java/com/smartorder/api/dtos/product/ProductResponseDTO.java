package com.smartorder.api.dtos.product;

import java.math.BigDecimal;

public record ProductResponseDTO(
        Long id,
        String name,
        BigDecimal price
) {
}
