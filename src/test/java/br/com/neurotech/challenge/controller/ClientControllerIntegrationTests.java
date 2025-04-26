package br.com.neurotech.challenge.controller;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.service.ClientService;
import br.com.neurotech.challenge.service.CreditService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
public class ClientControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientService clientservice;

    @MockBean
    private CreditService creditService;

    private final UUID clientId = UUID.randomUUID();
    private final NeurotechClient client = new NeurotechClient(clientId, "Mocked Name", (short) 25, 10000.00);

    @Test
    void create_ShouldReturnCreatedStatus() throws Exception {
        // Arrange
        when(clientservice.save(any(NeurotechClient.class))).thenReturn(clientId);

        // Act & Assert
        mockMvc.perform(post("/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location",
                        "http://localhost/api/client/" + clientId));
    }

    @Test
    void get_WithExistingId_ShouldReturnClient() throws Exception {
        // Arrange
        when(clientservice.get(clientId)).thenReturn(Optional.of(client));

        // Act & Assert
        mockMvc.perform(get("/api/client/{id}", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(clientId.toString())))
                .andExpect(jsonPath("$.name", is("Mocked Name")))
                .andExpect(jsonPath("$.age", is(25)))
                .andExpect(jsonPath("$.income", is(10000.00)));
    }

    @Test
    void get_WithNonExistingId_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(clientservice.get(clientId)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/client/{id}", clientId))
                .andExpect(status().isNotFound());
    }


    @Test
    void checkCredit_WithHatch_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(creditService.checkCredit(clientId, VehicleModel.HATCH)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/client/{id}/check-credit/{model}", clientId, VehicleModel.HATCH))
                .andExpect(status().isNotFound());
    }

    @Test
    void checkCredit_WithHatch_ShouldReturnFalse() throws Exception {
        // Arrange
        when(creditService.checkCredit(clientId, VehicleModel.HATCH)).thenReturn(Optional.of(false));

        // Act & Assert
        mockMvc.perform(get("/api/client/{id}/check-credit/{model}", clientId, VehicleModel.HATCH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isBoolean())
                .andExpect(jsonPath("$").value(false));
    }

    @Test
    void checkCredit_WithHatch_ShouldReturnTrue() throws Exception {
        // Arrange
        when(creditService.checkCredit(clientId, VehicleModel.HATCH)).thenReturn(Optional.of(true));

        // Act & Assert
        mockMvc.perform(get("/api/client/{id}/check-credit/{model}", clientId, VehicleModel.HATCH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isBoolean())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    void checkCredit_WithSuv_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(creditService.checkCredit(clientId, VehicleModel.SUV)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/client/{id}/check-credit/{model}", clientId, VehicleModel.SUV))
                .andExpect(status().isNotFound());
    }

    @Test
    void checkCredit_WithSuv_ShouldReturnFalse() throws Exception {
        // Arrange
        when(creditService.checkCredit(clientId, VehicleModel.SUV)).thenReturn(Optional.of(false));

        // Act & Assert
        mockMvc.perform(get("/api/client/{id}/check-credit/{model}", clientId, VehicleModel.SUV))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isBoolean())
                .andExpect(jsonPath("$").value(false));
    }

    @Test
    void checkCredit_WithSuv_ShouldReturnTrue() throws Exception {
        // Arrange
        when(creditService.checkCredit(clientId, VehicleModel.SUV)).thenReturn(Optional.of(true));

        // Act & Assert
        mockMvc.perform(get("/api/client/{id}/check-credit/{model}", clientId, VehicleModel.SUV))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isBoolean())
                .andExpect(jsonPath("$").value(true));
    }
}
