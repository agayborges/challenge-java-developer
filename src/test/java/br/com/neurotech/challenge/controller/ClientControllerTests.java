package br.com.neurotech.challenge.controller;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.service.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTests {

    @Mock
    private ClientServiceImpl service;

    @InjectMocks
    private ClientController controller;

    private NeurotechClient client = new NeurotechClient(UUID.randomUUID(), "Mocked Client", (short) 25, 10000.00);
    private final UUID clientId = UUID.randomUUID();

    @Test
    void create_ShouldIncludeLocationHeader() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);

        // Arrange
        NeurotechClient newClient = new NeurotechClient();
        newClient.setName("Test Name");
        newClient.setAge((short) 2);
        newClient.setIncome(0.0);
        when(service.save(newClient)).thenReturn(clientId);

        // Act
        ResponseEntity response = controller.createClient(newClient);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        URI expectedLocation = URI.create("http://localhost/" + clientId.toString());
        assertNotNull(response.getHeaders().getLocation());
        assertEquals(expectedLocation, response.getHeaders().getLocation());
        verify(service, times(1)).save(newClient);
    }

    @Test
    void get_WithExistingId_ShouldReturnClient() {
        // Arrange
        when(service.get(clientId)).thenReturn(Optional.of(client));

        // Act
        ResponseEntity<NeurotechClient> response = controller.findById(clientId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(client, response.getBody());
        verify(service, times(1)).get(clientId);
    }

    @Test
    void get_WithNonExistingId_ShouldReturnNotFound() {
        // Arrange
        when(service.get(clientId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<NeurotechClient> response = controller.findById(clientId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(service, times(1)).get(clientId);
    }

}
