package com.devsuperior.desafiocrud.services;

import com.devsuperior.desafiocrud.DTO.ClientDTO;
import com.devsuperior.desafiocrud.entities.Client;
import com.devsuperior.desafiocrud.repositories.ClientRepository;
import com.devsuperior.desafiocrud.services.exceptions.DatabaseException;
import com.devsuperior.desafiocrud.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id " + id));
        return new ClientDTO(client);
    }

    @Transactional
    public ClientDTO insert(Client client) {
        Client cli = repository.save(client);
        return new ClientDTO(cli);
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO client) {
        try {
            Client cli = repository.getReferenceById(id);
            copyDtoToClient(client, cli);
            cli = repository.save(cli);
            return new ClientDTO(cli);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    private static void copyDtoToClient(ClientDTO client, Client cli) {
        cli.setName(client.getName());
        cli.setCpf(client.getCpf());
        cli.setBirthDate(client.getBirthDate());
        cli.setIncome(client.getIncome());
        cli.setChildren(client.getChildren());
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try {
            repository.deleteById(id);
        }
        // Apesar de ter apenas uma entidade e não existir relacionamento entre tabelas, está implementado esta condição
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

}

