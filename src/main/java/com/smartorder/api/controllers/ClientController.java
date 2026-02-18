package com.smartorder.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartorder.api.dtos.client.ClientRequestDTO;
import com.smartorder.api.dtos.client.ClientResponseDTO;
import com.smartorder.api.services.impl.ClientServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/cliente")
public class ClientController {

    @Autowired
    private ClientServiceImpl clientService;

    @PostMapping()
    public ResponseEntity<ClientResponseDTO> create(@RequestBody ClientRequestDTO clientRequest) {

        ClientResponseDTO clientResponse = clientService.create(clientRequest);

        return ResponseEntity.ok().body(clientResponse);

    }

    @GetMapping()
    public ResponseEntity<List<ClientResponseDTO>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

}
