package br.com.neurotech.challenge.service;

import org.springframework.stereotype.Service;

import br.com.neurotech.challenge.entity.VehicleModel;

import java.util.Optional;
import java.util.UUID;

@Service
public interface CreditService {
	
	/**
	 * Efetua a checagem se o cliente está apto a receber crédito
	 * para um determinado modelo de veículo
	 */
	Optional<Boolean> checkCredit(UUID clientId, VehicleModel model);
	
}
