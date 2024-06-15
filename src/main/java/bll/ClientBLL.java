package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.validators.AgeValidator;
import bll.validators.EmailValidator;
import bll.validators.Validator;
import dao.ClientDAO;
import model.Client;

/**
 * @Author: Cretu Horatiu
 * @Since: May 2024
 */
public class ClientBLL {

    private List<Validator<Client>> validators;
    private ClientDAO clientDAO;

    public ClientBLL() {
        validators = new ArrayList<>();
        validators.add(new EmailValidator());
        validators.add(new AgeValidator());

        clientDAO = new ClientDAO();
    }

    /**
     * this method inserts a client in the DAO
     * @param client
     * @return client
     */
    public Client insertClient(Client client) {
        try {
            for (Validator<Client> currentValidator : validators) {
                currentValidator.validate(client);
            }
            clientDAO.insert(client);
        } catch (Exception e) {
            System.out.println("Could not insert client!\n");
            e.printStackTrace();
        }
        return client;
    }

    /**
     * this method updates the value of a client
     * @param client
     * @param desiredClientID
     * @return updatedClient
     */
    public Client updateClient(Client client, int desiredClientID) {
        try {
            for (Validator<Client> currentValidator : validators) {
                currentValidator.validate(client);
            }
            clientDAO.update(client, desiredClientID);
        } catch (Exception e) {
            System.out.println("Could not update client!\n");
            e.printStackTrace();
        }
        return client;
    }

    public ClientDAO getClientDAO() {
        return clientDAO;
    }
}
