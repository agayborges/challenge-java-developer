package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CreditServiceImpl implements CreditService {

    private final ClientRepository clientRepository;

    public CreditServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Optional<Boolean> checkCredit(UUID clientId, VehicleModel model) {
        Optional<NeurotechClient> client = clientRepository.findById(clientId);

        if (client.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(model.checkCredit(client.get()));
    }
}
