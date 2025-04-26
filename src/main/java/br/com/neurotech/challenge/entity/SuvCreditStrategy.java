package br.com.neurotech.challenge.entity;

import java.math.BigDecimal;

public class SuvCreditStrategy implements CreditStrategy {

    private static final BigDecimal FLOOR_INCOME = new BigDecimal(8000);
    private static final int FLOOR_AGE = 20;

    @Override
    public boolean checkCredit(NeurotechClient client) {
        if (client == null || client.getIncome() == null) {
            return false;
        }

        return client.getAge() > FLOOR_AGE &&
                client.getIncome().compareTo(FLOOR_INCOME) > 0;
    }
}
