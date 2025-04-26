package br.com.neurotech.challenge.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class NeurotechClientValidationTest {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenNameIsBlank_thenShouldGiveConstraintViolations() {
        NeurotechClient client = new NeurotechClient(UUID.randomUUID(), "", (short) 22, BigDecimal.valueOf(10.50));
        Set<ConstraintViolation<NeurotechClient>> violations = validator.validate(client);

        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size());
        String firstMessage = violations.iterator().next().getMessage();
        assertTrue("O nome deve ter entre 3 e 100 caracteres".equals(firstMessage) ||
                "O nome do cliente é obrigatório".equals(firstMessage));
        String secondMessage = violations.iterator().next().getMessage();
        assertTrue("O nome deve ter entre 3 e 100 caracteres".equals(secondMessage) ||
                "O nome do cliente é obrigatório".equals(secondMessage));
    }

    @Test
    void whenNameIsTooShort_thenShouldGiveConstraintViolations() {
        NeurotechClient client = new NeurotechClient(UUID.randomUUID(), "AB", (short) 22, BigDecimal.valueOf(10.50));
        Set<ConstraintViolation<NeurotechClient>> violations = validator.validate(client);

        assertFalse(violations.isEmpty());
        assertEquals("O nome deve ter entre 3 e 100 caracteres", violations.iterator().next().getMessage());
    }

    @Test
    void whenNameIsTooLong_thenShouldGiveConstraintViolations() {
        final String tooLongName = "TooLongName_TooLongName_TooLongName_TooLongName_TooLongName_TooLongName_TooLongName_TooLongName_TooLongName_TooLongName_TooLongName_TooLongName";
        NeurotechClient client = new NeurotechClient(UUID.randomUUID(), tooLongName, (short) 22, BigDecimal.valueOf(10.50));
        Set<ConstraintViolation<NeurotechClient>> violations = validator.validate(client);

        assertFalse(violations.isEmpty());
        assertEquals("O nome deve ter entre 3 e 100 caracteres", violations.iterator().next().getMessage());
    }

    @Test
    void whenAgeIsNegative_thenShouldGiveConstraintViolations() {
        NeurotechClient client = new NeurotechClient(UUID.randomUUID(), "Cliente", (short) -22, BigDecimal.valueOf(10.50));
        Set<ConstraintViolation<NeurotechClient>> violations = validator.validate(client);

        assertFalse(violations.isEmpty());
        assertEquals("A idade deve ser maior ou igual a zero", violations.iterator().next().getMessage());
    }

    @Test
    void whenClientIsValid_thenShouldNotGiveConstraintViolations() {
        NeurotechClient client = new NeurotechClient(UUID.randomUUID(), "Cliente", (short) 22, BigDecimal.valueOf(10.50));
        Set<ConstraintViolation<NeurotechClient>> violations = validator.validate(client);

        assertTrue(violations.isEmpty());
    }

    @Test
    void whenIncomeHasMoreThanTwoDecimals_thenShouldGiveConstraintViolations() {
        NeurotechClient client = new NeurotechClient(UUID.randomUUID(), "Cliente", (short) 22, BigDecimal.valueOf(10.509));
        Set<ConstraintViolation<NeurotechClient>> violations = validator.validate(client);

        assertFalse(violations.isEmpty());
        assertEquals("A renda deve ter no máximo 10 dígitos inteiros e 2 decimais", violations.iterator().next().getMessage());
    }

    @Test
    void whenIncomeHasTooManyWholeDigits_thenShouldGiveConstraintViolations() {
        NeurotechClient client = new NeurotechClient(UUID.randomUUID(), "Cliente", (short) 22, BigDecimal.valueOf(12345657890123456.50));
        Set<ConstraintViolation<NeurotechClient>> violations = validator.validate(client);

        assertFalse(violations.isEmpty());
        assertEquals("A renda deve ter no máximo 10 dígitos inteiros e 2 decimais", violations.iterator().next().getMessage());
    }
}
