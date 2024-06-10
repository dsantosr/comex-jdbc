package com.comex.service;

import com.comex.dao.ClientDAO;
import com.comex.exception.EntityNotFoundException;
import com.comex.model.Client;

import java.util.List;

public class ClientService {

    private ClientDAO clientDAO;

    public ClientService() {
        this.clientDAO = new ClientDAO();
    }

    public void createClient(Client newClient) {
        System.out.println("Validando dados de cliente...");

        this.clientDAO.create(newClient);
        System.out.println("Enviando email para o cliente...");
    }

    public List<Client> listClients() {
        return this.clientDAO.listAll();
    }

    public void updateClient(Client client) {
        System.out.println("Tem que validar o objeto na atualização também...");

        this.clientDAO.create(client);
        System.out.println("Email: se não foi você que solicitou a alteração, sua conta pode ter sido rackeada...");
    }

    public void deleteClientByID(long id) throws EntityNotFoundException {
        Client clientToDelete = clientDAO.searchByID(id);
        if (clientToDelete == null) {
            throw new EntityNotFoundException("Cliente não está cadastrado: " + id);
        }

        this.clientDAO.delete(clientToDelete);
    }
}
