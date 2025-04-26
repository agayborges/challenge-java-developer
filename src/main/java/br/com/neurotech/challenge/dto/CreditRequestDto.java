package br.com.neurotech.challenge.dto;

import br.com.neurotech.challenge.entity.VehicleModel;

import java.util.UUID;

public record CreditRequestDto(UUID clientId, VehicleModel vehicleModel) {
}
