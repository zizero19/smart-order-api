package com.smartorder.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.smartorder.api.dtos.client.ClientRequestDTO;
import com.smartorder.api.dtos.client.ClientResponseDTO;
import com.smartorder.api.models.Client;
import com.smartorder.api.repositories.ClientRepository;
import com.smartorder.api.services.ClientService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientResponseDTO create(ClientRequestDTO request) {

        Optional<Client> client = clientRepository.findByCpf(request.cpf());

        if (client.isPresent()) {
            throw new RuntimeException("CPF ja cadastrado");
        }

        Client newClient = new Client(request.name(), request.email(), request.cpf());

        clientRepository.save(newClient);

        return mapToResponse(newClient);
    }

    @Override
    @Transactional
    public ClientResponseDTO findByClientId(Long id) {
        Client client;

        client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente nao encontrado"));

        return mapToResponse(client);
    }

    @Override
    @Transactional
    public List<ClientResponseDTO> getAllClients() {

        return clientRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente nao encontrado"));

        clientRepository.delete(client);
    }

    private ClientResponseDTO mapToResponse(Client client) {
        return new ClientResponseDTO(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getCpf(),
                client.getStatus(),
                client.getScore());
    }
}
