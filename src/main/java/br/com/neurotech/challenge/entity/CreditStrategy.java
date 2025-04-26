package br.com.neurotech.challenge.entity;

public interface CreditStrategy {
    boolean checkCredit(NeurotechClient client);
}
