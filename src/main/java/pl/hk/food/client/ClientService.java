package pl.hk.food.client;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.hk.food.security.ClientRole;
import pl.hk.food.security.Role;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public List<Client> getProductCatalog() {
        return clientRepository.findAll();
    }

    public Client findClientById(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()) {
            return client.get();
        } else {
            throw new RuntimeException();
        }
    }

    public Client findClientByUsername(String username) {
        Optional<Client> client = clientRepository.findByUsername(username);
        if (client.isPresent()) {
            return client.get();
        } else {
            throw new RuntimeException();
        }
    }

    public void editClient(Client client) {
        Client client1 = findClientById(client.getId());
        client1.setFirstName(client.getFirstName());
        client1.setLastName(client.getLastName());
        client1.setAddress(client.getAddress());
        clientRepository.save(client1);
    }


    public void registerUser(String username, String rawPassword, String firstName, String lastName, String address) {
        Client clientToAdd = new Client();

        clientToAdd.setUsername(username);
        String encryptedPassword = passwordEncoder.encode(rawPassword);
        clientToAdd.setPassword(encryptedPassword);

        List<ClientRole> list = Collections.singletonList(new ClientRole(clientToAdd, Role.ROLE_USER));
        clientToAdd.setRoles(new HashSet<>(list));
        clientToAdd.setAddress(address);
        clientToAdd.setFirstName(firstName);
        clientToAdd.setLastName(lastName);

        clientRepository.save(clientToAdd);
    }
}
