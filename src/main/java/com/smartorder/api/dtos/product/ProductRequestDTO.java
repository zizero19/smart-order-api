package com.smartorder.api.dtos.product;

import java.math.BigDecimal;

public record ProductRequestDTO(
        String name,
        BigDecimal price
) {
}
