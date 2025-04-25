package br.com.neurotech.challenge.controller;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.service.ClientService;
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
    private ClientService service;

    private final UUID clientId = UUID.randomUUID();
    private final NeurotechClient client = new NeurotechClient(clientId, "Mocked Name", (short) 25, 10000.00);

    @Test
    void create_ShouldReturnCreatedStatus() throws Exception {
        // Arrange
        when(service.save(any(NeurotechClient.class))).thenReturn(clientId);

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
        when(service.get(clientId)).thenReturn(Optional.of(client));

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
        when(service.get(clientId)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/client/{id}", clientId))
                .andExpect(status().isNotFound());
    }
}
