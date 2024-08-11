package Banking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Banking.entity.Client;
import Banking.repository.ClientRepos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepos clientRepository;

    @Transactional
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Page<Client> searchClients(String name, String phone, String email, LocalDate dateOfBirth, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return clientRepository.searchClients(name, phone, email, dateOfBirth, pageable);
    }
    
    public Optional<Client> findByUsername(String username) {
        return clientRepository.findByUsername(username);
    }

    @Transactional
    public void addPhoneNumber(Long clientId, String phoneNumber) {
        Client client = clientRepository.findById(clientId).orElseThrow();
        client.getPhoneNumbers().add(phoneNumber);
        clientRepository.save(client);
    }

    @Transactional
    public void addEmail(Long clientId, String email) {
        Client client = clientRepository.findById(clientId).orElseThrow();
        client.getEmails().add(email);
        clientRepository.save(client);
    }

    @Transactional
    public void transferMoney(Long fromClientId, Long toClientId, BigDecimal amount) {
        Client fromClient = clientRepository.findById(fromClientId).orElseThrow();
        Client toClient = clientRepository.findById(toClientId).orElseThrow();

        if (fromClient.getAccount().getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        fromClient.getAccount().setBalance(fromClient.getAccount().getBalance().subtract(amount));
        toClient.getAccount().setBalance(toClient.getAccount().getBalance().add(amount));

        clientRepository.save(fromClient);
        clientRepository.save(toClient);
    }

    @Scheduled(fixedRate = 60000)
    public void applyInterestToAccounts() {
        List<Client> clients = clientRepository.findAll();
        for (Client client : clients) {
            BigDecimal balance = client.getAccount().getBalance();
            BigDecimal initialBalance = client.getAccount().getInitialBalance();

            BigDecimal newBalance = balance.add(balance.multiply(BigDecimal.valueOf(0.05)));
            if (newBalance.compareTo(initialBalance.multiply(BigDecimal.valueOf(2.07))) > 0) {
                newBalance = initialBalance.multiply(BigDecimal.valueOf(2.07));
            }

            client.getAccount().setBalance(newBalance);
            clientRepository.save(client);
        }
    }
}
