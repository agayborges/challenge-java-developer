package br.com.neurotech.challenge.entity;

public class HatchCreditStrategy implements CreditStrategy {

    private static final int FLOOR_INCOME = 5000;
    private static final int ROOF_INCOME = 15000;

    @Override
    public boolean checkCredit(NeurotechClient client) {
        if (client.getIncome() >= FLOOR_INCOME && client.getIncome() <= ROOF_INCOME)
            return true;
        else
            return false;
    }
}
