package br.com.neurotech.challenge.entity;

public class SuvCreditStrategy implements CreditStrategy {

    private static final int FLOOR_INCOME = 8000;
    private static final int FLOOR_AGE = 20;

    @Override
    public boolean checkCredit(NeurotechClient client) {
        if (client.getAge() > FLOOR_AGE && client.getIncome() > FLOOR_INCOME)
            return true;
        else
            return false;
    }
}
