package br.com.neurotech.challenge.entity;

import java.math.BigDecimal;

public class HatchCreditStrategy implements CreditStrategy {

    private static final BigDecimal FLOOR_INCOME = new BigDecimal(5000);
    private static final BigDecimal ROOF_INCOME = new BigDecimal(15000);

    @Override
    public boolean checkCredit(NeurotechClient client) {
        if (client == null || client.getIncome() == null) {
            return false;
        }

        BigDecimal income = client.getIncome();
        return income.compareTo(FLOOR_INCOME) >= 0 &&
                income.compareTo(ROOF_INCOME) <= 0;
    }
}
