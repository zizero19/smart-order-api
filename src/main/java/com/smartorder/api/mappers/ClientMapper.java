package com.smartorder.api.mappers;

import org.mapstruct.Mapper;

import com.smartorder.api.dtos.client.ClientResponseDTO;
import com.smartorder.api.models.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientResponseDTO toResponse(Client client);
}
