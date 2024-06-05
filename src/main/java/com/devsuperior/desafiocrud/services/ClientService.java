package com.devsuperior.desafiocrud.services;

import com.devsuperior.desafiocrud.DTO.ClientDTO;
import com.devsuperior.desafiocrud.entities.Client;
import com.devsuperior.desafiocrud.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.NoSuchElementException;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Transactional
    public Page<ClientDTO> findAll(Pageable pageable) {
        Page<Client> client = repository.findAll(pageable);
        return client.map(x -> new ClientDTO(x));
    }

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {
        Client client = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Client not found with id " + id));
        return new ClientDTO(client);
    }

    @Transactional
    public ClientDTO save(Client client) {
        Client cli = repository.save(client);
        return new ClientDTO(cli);
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO client) {
        Client cli = repository.getReferenceById(id);
        cli.setName(client.getName());
        cli.setCpf(client.getCpf());
        cli.setBirthDate(client.getBirthDate());
        cli.setIncome(client.getIncome());
        cli.setChildren(client.getChildren());
        cli = repository.save(cli);
        return new ClientDTO(cli);
    }

    @DeleteMapping
    public void delete(Long id){
        repository.deleteById(id);
    }
}

