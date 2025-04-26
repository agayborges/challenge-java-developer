package br.com.neurotech.challenge.controller;

import br.com.neurotech.challenge.dto.ClientDto;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.service.ClientService;
import br.com.neurotech.challenge.service.CreditService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;

    private final CreditService creditService;

    public ClientController(ClientService clientService, CreditService creditService) {
        this.clientService = clientService;
        this.creditService = creditService;
    }

    @PostMapping
    public ResponseEntity createClient(@RequestBody @Valid NeurotechClient requestClient) {
        UUID clientId = clientService.save(requestClient);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clientId.toString())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NeurotechClient> findById(@PathVariable UUID id) {
        Optional<NeurotechClient> client = clientService.get(id);
        return client.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/check-credit/{vehicleModel}")
    public ResponseEntity<Boolean> checkCredit(@PathVariable UUID id, @PathVariable VehicleModel vehicleModel) {
        Optional<Boolean> checkCredit = creditService.checkCredit(id, vehicleModel);
        return checkCredit.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/find-special-clients-for-hatch-credit")
    public ResponseEntity<List<ClientDto>> findSpecialClientsForHatchCredit() {
        List<ClientDto> clients = creditService.findSpecialClientsForHatchCredit();
        return ResponseEntity.ok(clients);
    }
}
