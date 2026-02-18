package com.smartorder.api.services;

import java.util.List;

import com.smartorder.api.dtos.client.ClientRequestDTO;
import com.smartorder.api.dtos.client.ClientResponseDTO;

public interface ClientService {

    ClientResponseDTO create(ClientRequestDTO request);

    List<ClientResponseDTO> getAllClients();

    ClientResponseDTO findByClientId(Long id);

    void delete(Long id);
}
