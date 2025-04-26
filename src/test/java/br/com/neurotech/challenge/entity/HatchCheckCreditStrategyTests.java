package br.com.neurotech.challenge.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HatchCheckCreditStrategyTests {

    @Test
    void checkCredit_WithLowIncome_ShouldReturnFalse() {
        NeurotechClient client = new NeurotechClient(UUID.randomUUID(), "Mocked Name", (short) 25, BigDecimal.valueOf(4999.99));
        assertFalse(VehicleModel.HATCH.checkCredit(client));
    }

    @Test
    void checkCredit_WithHighIncome_ShouldReturnFalse() {
        NeurotechClient client = new NeurotechClient(UUID.randomUUID(), "Mocked Name", (short) 25, BigDecimal.valueOf(15000.01));
        assertFalse(VehicleModel.HATCH.checkCredit(client));
    }

    @Test
    void checkCredit_ShouldReturnTrue() {
        double min = 5000.00;
        double max = 15000.00;
        Random random = new Random();
        double randomValue = random.nextDouble(min, max);

        NeurotechClient client = new NeurotechClient(UUID.randomUUID(), "Mocked Name", (short) 25, BigDecimal.valueOf(randomValue));
        assertTrue(VehicleModel.HATCH.checkCredit(client));
    }

}
