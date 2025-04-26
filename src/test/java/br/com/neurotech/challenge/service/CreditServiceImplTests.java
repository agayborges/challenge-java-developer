package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreditServiceImplTests {

    @Mock
    private ClientRepository repository;

    @InjectMocks
    private CreditServiceImpl service;

    private final UUID clientId = UUID.randomUUID();

    @Test
    void checkCredit_WithHatch_ShouldReturnEmpty() {
        // Arrange
        when(repository.findById(clientId)).thenReturn(Optional.empty());

        // Act
        Optional<Boolean> checked = service.checkCredit(clientId, VehicleModel.HATCH);

        // Assert
        assertTrue(checked.isEmpty());
        verify(repository, times(1)).findById(clientId);
    }

    @Test
    void checkCredit_WithHatchLowIncome_ShouldReturnFalse() {
        NeurotechClient lowIncomeClient = new NeurotechClient(clientId, "Mocked Client", (short) 35, BigDecimal.valueOf(4000.00));
        // Arrange
        when(repository.findById(clientId)).thenReturn(Optional.of(lowIncomeClient));

        // Act
        Optional<Boolean> checked = service.checkCredit(clientId, VehicleModel.HATCH);

        // Assert
        assertTrue(checked.isPresent());
        assertFalse(checked.get());
        verify(repository, times(1)).findById(clientId);
    }

    @Test
    void checkCredit_WithHatchHighIncome_ShouldReturnFalse() {
        NeurotechClient highIncomeClient = new NeurotechClient(clientId, "Mocked Client", (short) 35, BigDecimal.valueOf(16000.00));
        // Arrange
        when(repository.findById(clientId)).thenReturn(Optional.of(highIncomeClient));

        // Act
        Optional<Boolean> checked = service.checkCredit(clientId, VehicleModel.HATCH);

        // Assert
        assertTrue(checked.isPresent());
        assertFalse(checked.get());
        verify(repository, times(1)).findById(clientId);
    }

    @Test
    void checkCredit_WithHatch_ShouldReturnTrue() {
        NeurotechClient client = new NeurotechClient(clientId, "Mocked Client", (short) 35, BigDecimal.valueOf(7000.00));
        // Arrange
        when(repository.findById(clientId)).thenReturn(Optional.of(client));

        // Act
        Optional<Boolean> checked = service.checkCredit(clientId, VehicleModel.HATCH);

        // Assert
        assertTrue(checked.isPresent());
        assertTrue(checked.get());
        verify(repository, times(1)).findById(clientId);
    }

    @Test
    void checkCredit_WithSuv_ShouldReturnEmpty() {
        // Arrange
        when(repository.findById(clientId)).thenReturn(Optional.empty());

        // Act
        Optional<Boolean> checked = service.checkCredit(clientId, VehicleModel.SUV);

        // Assert
        assertTrue(checked.isEmpty());
        verify(repository, times(1)).findById(clientId);
    }

    @Test
    void checkCredit_WithSuvLowIncome_ShouldReturnFalse() {
        NeurotechClient lowIncomeClient = new NeurotechClient(clientId, "Mocked Client", (short) 35, BigDecimal.valueOf(4000.00));
        // Arrange
        when(repository.findById(clientId)).thenReturn(Optional.of(lowIncomeClient));

        // Act
        Optional<Boolean> checked = service.checkCredit(clientId, VehicleModel.SUV);

        // Assert
        assertTrue(checked.isPresent());
        assertFalse(checked.get());
        verify(repository, times(1)).findById(clientId);
    }

    @Test
    void checkCredit_WithSuvLowAge_ShouldReturnFalse() {
        NeurotechClient lowAgeClient = new NeurotechClient(clientId, "Mocked Client", (short) 15, BigDecimal.valueOf(16000.00));
        // Arrange
        when(repository.findById(clientId)).thenReturn(Optional.of(lowAgeClient));

        // Act
        Optional<Boolean> checked = service.checkCredit(clientId, VehicleModel.SUV);

        // Assert
        assertTrue(checked.isPresent());
        assertFalse(checked.get());
        verify(repository, times(1)).findById(clientId);
    }

    @Test
    void checkCredit_WithSuv_ShouldReturnTrue() {
        NeurotechClient client = new NeurotechClient(clientId, "Mocked Client", (short) 35, BigDecimal.valueOf(100000.00));
        // Arrange
        when(repository.findById(clientId)).thenReturn(Optional.of(client));

        // Act
        Optional<Boolean> checked = service.checkCredit(clientId, VehicleModel.SUV);

        // Assert
        assertTrue(checked.isPresent());
        assertTrue(checked.get());
        verify(repository, times(1)).findById(clientId);
    }
}
