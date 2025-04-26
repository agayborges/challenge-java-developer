package br.com.neurotech.challenge.entity;

import java.math.BigDecimal;

public interface CheckCreditStrategy {
    boolean checkCredit(NeurotechClient client);
}
