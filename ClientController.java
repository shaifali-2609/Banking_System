package Bankingcontroller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Banking.entity.Client;
import Banking.service.ClientService;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/clients")

public class ClientController{

    @Autowired
    private ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client createdClient = clientService.createClient(client);
        return ResponseEntity.ok(createdClient);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Client>> searchClients(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) LocalDate dateOfBirth,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Client> clients = clientService.searchClients(name, phone, email, dateOfBirth, page, size);
        return ResponseEntity.ok(clients);
    }

    @PostMapping("/{clientId}/phone")
    public ResponseEntity<?> addPhoneNumber(@PathVariable Long clientId, @RequestBody String phoneNumber) {
        clientService.addPhoneNumber(clientId, phoneNumber);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{clientId}/email")
    public ResponseEntity<?> addEmail(@PathVariable Long clientId, @RequestBody String email) {
        clientService.addEmail(clientId, email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transferMoney(@RequestParam Long fromClientId, @RequestParam Long toClientId, @RequestParam BigDecimal amount) {
        clientService.transferMoney(fromClientId, toClientId, amount);
        return ResponseEntity.ok().build();
    }
}

