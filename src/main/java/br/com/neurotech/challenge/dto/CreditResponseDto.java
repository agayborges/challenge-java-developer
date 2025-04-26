package br.com.neurotech.challenge.dto;

import java.util.UUID;

public record CreditResponseDto (UUID creditId, CreditStatus creditStatus, String message) {
}
