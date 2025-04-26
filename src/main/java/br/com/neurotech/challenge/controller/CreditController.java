package br.com.neurotech.challenge.controller;

import br.com.neurotech.challenge.dto.CreditRequestDto;
import br.com.neurotech.challenge.dto.CreditResponseDto;
import br.com.neurotech.challenge.entity.Credit;
import br.com.neurotech.challenge.entity.NeurotechClient;
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
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/credit")
public class CreditController {

    private final CreditService creditService;

    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }

    @PostMapping
    public ResponseEntity requestCredit(@RequestBody @Valid CreditRequestDto dto) {
        CreditResponseDto creditResponseDto = creditService.requestCredit(dto.clientId(), dto.vehicleModel());

        return switch (creditResponseDto.creditStatus()) {
            case APPROVED -> buildCreatedResponse(creditResponseDto);
            case REJECTED -> ResponseEntity.unprocessableEntity().body(creditResponseDto);
            case CLIENT_NOT_FOUND -> ResponseEntity.notFound().build();
            default -> ResponseEntity.badRequest().build();
        };
    }

    @GetMapping("/{id}")
    public ResponseEntity<Credit> findById(@PathVariable UUID id) {
        Optional<Credit> credit = creditService.get(id);
        return credit.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    private static ResponseEntity buildCreatedResponse(CreditResponseDto creditResponseDto) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creditResponseDto.creditId().toString())
                .toUri();
        return ResponseEntity.created(location).body(creditResponseDto);
    }
}
