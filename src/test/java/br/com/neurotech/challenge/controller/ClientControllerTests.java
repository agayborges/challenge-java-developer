package br.com.neurotech.challenge.controller;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.service.ClientServiceImpl;
import br.com.neurotech.challenge.service.CreditServiceImpl;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTests {

    @Mock
    private ClientServiceImpl clientService;

    @Mock
    private CreditServiceImpl creditService;

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
        when(clientService.save(newClient)).thenReturn(clientId);

        // Act
        ResponseEntity response = controller.createClient(newClient);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        URI expectedLocation = URI.create("http://localhost/" + clientId.toString());
        assertNotNull(response.getHeaders().getLocation());
        assertEquals(expectedLocation, response.getHeaders().getLocation());
        verify(clientService, times(1)).save(newClient);
    }

    @Test
    void get_WithExistingId_ShouldReturnClient() {
        // Arrange
        when(clientService.get(clientId)).thenReturn(Optional.of(client));

        // Act
        ResponseEntity<NeurotechClient> response = controller.findById(clientId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(client, response.getBody());
        verify(clientService, times(1)).get(clientId);
    }

    @Test
    void get_WithNonExistingId_ShouldReturnNotFound() {
        // Arrange
        when(clientService.get(clientId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<NeurotechClient> response = controller.findById(clientId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(clientService, times(1)).get(clientId);
    }

    @Test
    void checkCredit_WithHatch_ShouldReturnNotFound() {
        // Arrange
        when(creditService.checkCredit(clientId, VehicleModel.HATCH)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Boolean> response = controller.checkCredit(clientId, VehicleModel.HATCH);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(creditService, times(1)).checkCredit(clientId, VehicleModel.HATCH);
    }

    @Test
    void checkCredit_WithHatch_ShouldReturnFalse() {
        // Arrange
        when(creditService.checkCredit(clientId, VehicleModel.HATCH)).thenReturn(Optional.of(false));

        // Act
        ResponseEntity<Boolean> response = controller.checkCredit(clientId, VehicleModel.HATCH);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody());
        verify(creditService, times(1)).checkCredit(clientId, VehicleModel.HATCH);
    }

    @Test
    void checkCredit_WithHatch_ShouldReturnTrue() {
        // Arrange
        when(creditService.checkCredit(clientId, VehicleModel.HATCH)).thenReturn(Optional.of(true));

        // Act
        ResponseEntity<Boolean> response = controller.checkCredit(clientId, VehicleModel.HATCH);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
        verify(creditService, times(1)).checkCredit(clientId, VehicleModel.HATCH);
    }

    @Test
    void checkCredit_WithSuv_ShouldReturnNotFound() {
        // Arrange
        when(creditService.checkCredit(clientId, VehicleModel.SUV)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Boolean> response = controller.checkCredit(clientId, VehicleModel.SUV);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(creditService, times(1)).checkCredit(clientId, VehicleModel.SUV);
    }

    @Test
    void checkCredit_WithSuv_ShouldReturnFalse() {
        // Arrange
        when(creditService.checkCredit(clientId, VehicleModel.SUV)).thenReturn(Optional.of(false));

        // Act
        ResponseEntity<Boolean> response = controller.checkCredit(clientId, VehicleModel.SUV);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody());
        verify(creditService, times(1)).checkCredit(clientId, VehicleModel.SUV);
    }

    @Test
    void checkCredit_WithSuv_ShouldReturnTrue() {
        // Arrange
        when(creditService.checkCredit(clientId, VehicleModel.SUV)).thenReturn(Optional.of(true));

        // Act
        ResponseEntity<Boolean> response = controller.checkCredit(clientId, VehicleModel.SUV);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
        verify(creditService, times(1)).checkCredit(clientId, VehicleModel.SUV);
    }

}
