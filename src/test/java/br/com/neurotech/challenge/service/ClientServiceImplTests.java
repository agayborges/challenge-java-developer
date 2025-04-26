package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTests {

    @Mock
    private ClientRepository repository;

    @InjectMocks
    private ClientServiceImpl service;

    private NeurotechClient neurotechClient = new NeurotechClient(UUID.randomUUID(), "Mocked Client", (short) 35, 10000.00);
    private final UUID clientId = UUID.randomUUID();


    @Test
    void get_WithExistingId_ShouldReturnClient() {
        // Arrange
        when(repository.findById(clientId)).thenReturn(Optional.of(neurotechClient));

        // Act
        Optional<NeurotechClient> foundClient = service.get(clientId);

        // Assert
        assertTrue(foundClient.isPresent());
        assertEquals(neurotechClient, foundClient.get());
        verify(repository, times(1)).findById(clientId);
    }

    @Test
    void get_WithNonExistingId_ShouldReturnEmpty() {
        // Arrange
        UUID nonExistingId = UUID.randomUUID();
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act
        Optional<NeurotechClient> foundClient = service.get(nonExistingId);

        // Assert
        assertFalse(foundClient.isPresent());
        verify(repository, times(1)).findById(nonExistingId);
    }

    @Test
    void save_ShouldReturnSavedClient() {
        // Arrange
        NeurotechClient newClient = new NeurotechClient();
        newClient.setAge((short) 25);
        newClient.setName("Test Name");
        newClient.setIncome(2500.00);

        when(repository.save(newClient)).thenAnswer(invocation -> {
            NeurotechClient argument = invocation.getArgument(0);
            argument.setId(clientId);
            return argument;
        });

        // Act
        UUID newClientId = service.save(newClient);

        // Assert
        assertNotNull(newClientId);
        verify(repository, times(1)).save(newClient);
    }

}
