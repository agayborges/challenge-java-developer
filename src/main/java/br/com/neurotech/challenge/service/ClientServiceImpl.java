package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.dto.ClientDto;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ClientServiceImpl implements  ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    @Override
    public UUID save(NeurotechClient client) {
        NeurotechClient saved = clientRepository.save(client);
        return saved.getId();
    }

    @Override
    public Optional<NeurotechClient> get(UUID id) {
        return clientRepository.findById(id);
    }
}
