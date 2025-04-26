package br.com.neurotech.challenge.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SuvCreditStrategyTests {

    @Test
    void checkCredit_WithLowIncome_ShouldReturnFalse() {
        NeurotechClient client = new NeurotechClient(UUID.randomUUID(), "Mocked Name", (short) 21, BigDecimal.valueOf(7999.99));
        assertFalse(VehicleModel.SUV.checkCredit(client));
    }

    @Test
    void checkCredit_WithLowAge_ShouldReturnFalse() {
        NeurotechClient client = new NeurotechClient(UUID.randomUUID(), "Mocked Name", (short) 19, BigDecimal.valueOf(8000.00));
        assertFalse(VehicleModel.SUV.checkCredit(client));
    }

    @Test
    void checkCredit_ShouldReturnTrue() {
        Random random = new Random();
        short randomAge = (short) random.nextInt(20, Short.MAX_VALUE);
        double randomIncome = random.nextDouble(8000.00, Double.MAX_VALUE);

        NeurotechClient client = new NeurotechClient(UUID.randomUUID(), "Mocked Name", randomAge, BigDecimal.valueOf(randomIncome));
        assertTrue(VehicleModel.SUV.checkCredit(client));
    }
}
