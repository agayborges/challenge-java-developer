package br.com.neurotech.challenge.controller;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.service.ClientService;
import br.com.neurotech.challenge.service.CreditService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.hamcrest.Matchers.anyOf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ClientController.class)
public class ClientControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientService clientService;

    @MockBean
    private CreditService creditService;

    private final UUID clientId = UUID.randomUUID();

    @Test
    void whenPostValidClient_thenReturnsCreated() throws Exception {
        NeurotechClient validClient = new NeurotechClient(null, "Mocked Client", (short) 25, BigDecimal.valueOf(1000.00));

        when(clientService.save(any(NeurotechClient.class))).thenReturn(clientId);

        mockMvc.perform(post("/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validClient)))
                .andExpect(status().isCreated());
    }

    @Test
    void whenPostClientWithNegativeAge_thenReturnsBadRequest() throws Exception {
        NeurotechClient invalidClient = new NeurotechClient(null, "Mocked Client", (short) -25, BigDecimal.valueOf(1000.00));

        mockMvc.perform(post("/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidClient)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.age").value("A idade deve ser maior ou igual a zero"));
    }

    @Test
    void whenPostClientWithBlankName_thenReturnsBadRequest() throws Exception {
        NeurotechClient invalidClient = new NeurotechClient(null, "", (short) 25, BigDecimal.valueOf(1000.00));

        mockMvc.perform(post("/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidClient)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value(
                        anyOf(
                                equalTo("O nome do cliente é obrigatório"),
                                equalTo("O nome deve ter entre 3 e 100 caracteres")
                        )));
    }

    @Test
    void whenPostClientWithShortName_thenReturnsBadRequest() throws Exception {
        NeurotechClient invalidClient = new NeurotechClient(null, "AB", (short) 25, BigDecimal.valueOf(1000.00));

        mockMvc.perform(post("/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidClient)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("O nome deve ter entre 3 e 100 caracteres"));
    }

    @Test
    void whenPostClientWithNullAge_thenReturnsBadRequest() throws Exception {
        String json = "{\"name\":\"Client without age\",\"age\":null, \"income\":10.01}";

        mockMvc.perform(post("/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.age").value("A idade é obrigatória"));
    }

    @Test
    void whenPostClientWithMoreThanTwoDecimalsIncome_thenReturnsBadRequest() throws Exception {
        NeurotechClient invalidClient = new NeurotechClient(null, "Client", (short) 25, BigDecimal.valueOf(1000.001));

        mockMvc.perform(post("/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidClient)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.income").value("A renda deve ter no máximo 10 dígitos inteiros e 2 decimais"));
    }

}
