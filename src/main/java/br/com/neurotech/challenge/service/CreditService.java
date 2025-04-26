package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.dto.ClientDto;
import br.com.neurotech.challenge.dto.CreditResponseDto;
import br.com.neurotech.challenge.entity.Credit;
import org.springframework.stereotype.Service;

import br.com.neurotech.challenge.entity.VehicleModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface CreditService {
	
	/**
	 * Efetua a checagem se o cliente está apto a receber crédito
	 * para um determinado modelo de veículo
	 */
	Optional<Boolean> checkCredit(UUID clientId, VehicleModel model);

	CreditResponseDto requestCredit(UUID clientId, VehicleModel model);

	Optional<Credit> get(UUID id);

	List<ClientDto> findSpecialClientsForHatchCredit();
}
