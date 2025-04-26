package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.dto.ClientDto;
import org.springframework.stereotype.Service;

import br.com.neurotech.challenge.entity.NeurotechClient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface ClientService {
	
	/**
	 * Salva um novo cliente
	 * 
	 * @return ID do cliente recém-salvo
	 */
	UUID save(NeurotechClient client);
	
	/**
	 * Recupera um cliente baseado no seu ID
	 */
	Optional<NeurotechClient> get(UUID id);
}
