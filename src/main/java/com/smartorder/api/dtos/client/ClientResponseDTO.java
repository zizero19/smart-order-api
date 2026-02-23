package com.smartorder.api.dtos.client;

import com.smartorder.api.enums.ClientStatus;

public record ClientResponseDTO(
        Long id,
        String name,
        String email,
        String cpf,
        ClientStatus status,
        Integer score) {
}
