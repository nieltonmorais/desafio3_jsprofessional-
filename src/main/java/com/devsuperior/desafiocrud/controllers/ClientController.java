package com.devsuperior.desafiocrud.controllers;

import com.devsuperior.desafiocrud.DTO.ClientDTO;
import com.devsuperior.desafiocrud.repositories.ClientRepository;
import com.devsuperior.desafiocrud.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService service;

    @Autowired
    private ClientRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Long id) {
        try {
            ClientDTO clientDTO = service.findById(id);
            return ResponseEntity.ok(clientDTO);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

}

