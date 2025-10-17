package com.caiomelo204.backend.controller;

import com.caiomelo204.backend.dto.PartnerDetailedDto;
import com.caiomelo204.backend.dto.PartnerResumeDto;
import com.caiomelo204.backend.service.PartnerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(PartnerController.class)
class PartnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PartnerService partnerService;

    @Test
    @DisplayName("Deve retornar lista de sócios ao buscar por participação mínima")
    void shouldReturnListOfPartnersByParticipation() throws Exception {
        PartnerResumeDto dto1 = new PartnerResumeDto();
        dto1.setNome("João da Silva");
        dto1.setCnpj("12345678000199");
        dto1.setParticipacao(30.0);
        dto1.setCep("13065900");

        PartnerResumeDto dto2 = new PartnerResumeDto();
        dto2.setNome("Maria Souza");
        dto2.setCnpj("98765432000100");
        dto2.setParticipacao(50.0);
        dto2.setCep("01001000");

        when(partnerService.findAllByParticipation(20.0)).thenReturn(List.of(dto1, dto2));

        mockMvc.perform(get("/socios")
                        .param("participacaoMin", "20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome").value("João da Silva"))
                .andExpect(jsonPath("$[1].participacao").value(50.0));
    }

    @Test
    @DisplayName("Deve retornar 400 quando parâmetro de participação mínima não for informado")
    void shouldReturnBadRequestWhenParamMissing() throws Exception {
        mockMvc.perform(get("/socios"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("Deve retornar detalhes do sócio ao buscar por CNPJ")
    void shouldReturnPartnerDetailsByCnpj() throws Exception {
        PartnerDetailedDto detailed = new PartnerDetailedDto();
        detailed.setNome("João da Silva");
        detailed.setCnpj("12345678000199");
        detailed.setParticipacao(40.0);
        detailed.setCep("13065900");
        detailed.setMapUrlEmbed("https://maps.google.com?q=13065900");

        when(partnerService.findByCnpJ("12345678000199")).thenReturn(detailed);

        mockMvc.perform(get("/socios/12345678000199")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João da Silva"))
                .andExpect(jsonPath("$.cnpj").value("12345678000199"))
                .andExpect(jsonPath("$.mapUrlEmbed").value("https://maps.google.com?q=13065900"));
    }
}
