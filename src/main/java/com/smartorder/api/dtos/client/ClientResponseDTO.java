package com.smartorder.api.dtos.client;

public record ClientResponseDTO(
                Long id,
                String name,
                String email,
                String cpf) {
}
