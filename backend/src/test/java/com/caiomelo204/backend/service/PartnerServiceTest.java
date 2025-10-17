package com.caiomelo204.backend.service;

import com.caiomelo204.backend.client.KelTechClient;
import com.caiomelo204.backend.client.RecipeClient;
import com.caiomelo204.backend.dto.PartnerDetailedDto;
import com.caiomelo204.backend.dto.PartnerResumeDto;
import com.caiomelo204.backend.dto.RevenueDto;
import com.caiomelo204.backend.exception.BusinessException;
import com.caiomelo204.backend.exception.ResourceNotFoundException;
import com.caiomelo204.backend.mapper.PartnerMapper;
import com.caiomelo204.backend.dto.PartnerBaseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PartnerServiceTest {

    @Mock
    private KelTechClient kelTechClient;

    @Mock
    private RecipeClient recipeClient;

    @Mock
    private MapsService mapsService;

    @Mock
    private PartnerMapper partnerMapper;

    @InjectMocks
    private PartnerService partnerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar lista de sócios filtrada pela participação mínima")
    void shouldReturnFilteredPartnersByParticipation() {
        PartnerBaseDto socio1 = new PartnerBaseDto();
        socio1.setNome("João");
        socio1.setParticipacao(40.0);
        PartnerBaseDto socio2 = new PartnerBaseDto();
        socio2.setNome("Maria");
        socio2.setParticipacao(10.0);

        when(kelTechClient.searchPartner()).thenReturn(List.of(socio1, socio2));
        when(partnerMapper.toResume(any())).thenAnswer(inv -> {
            PartnerBaseDto s = inv.getArgument(0);
            PartnerResumeDto dto = new PartnerResumeDto();
            dto.setNome(s.getNome());
            dto.setParticipacao(s.getParticipacao());
            return dto;
        });

        List<PartnerResumeDto> result = partnerService.findAllByParticipation(20.0);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNome()).isEqualTo("João");
    }

    @Test
    @DisplayName("Deve lançar BusinessException quando participação mínima for inválida")
    void shouldThrowBusinessExceptionWhenParticipationInvalid() {
        assertThatThrownBy(() -> partnerService.findAllByParticipation(-1.0))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("participação mínima");
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando lista de sócios for vazia")
    void shouldThrowNotFoundWhenNoPartners() {
        when(kelTechClient.searchPartner()).thenReturn(List.of());
        assertThatThrownBy(() -> partnerService.findAllByParticipation(10.0))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Nenhum sócio encontrado");
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando nenhum sócio atender ao filtro")
    void shouldThrowNotFoundWhenNoPartnersMatchFilter() {
        PartnerBaseDto socio = new PartnerBaseDto();
        socio.setParticipacao(5.0);
        when(kelTechClient.searchPartner()).thenReturn(List.of(socio));
        assertThatThrownBy(() -> partnerService.findAllByParticipation(50.0))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Nenhum sócio com participação");
    }

    @Test
    @DisplayName("Deve retornar detalhes completos do sócio com CNPJ válido")
    void shouldReturnPartnerDetailsByCnpj() {
        PartnerBaseDto socio = new PartnerBaseDto();
        socio.setCnpjEmpresa("12.345.678/0001-99");
        socio.setCep("13065900");

        RevenueDto receita = new RevenueDto();
        RevenueDto.EstabelecimentoDTO est = new RevenueDto.EstabelecimentoDTO();
        est.setCep("01001000");
        receita.setEstabelecimento(est);

        PartnerDetailedDto detalhado = new PartnerDetailedDto();
        detalhado.setNome("João");

        when(kelTechClient.searchPartner()).thenReturn(List.of(socio));
        when(recipeClient.searchByCnpj("12345678000199")).thenReturn(receita);
        when(mapsService.generateMap("01001000")).thenReturn("https://maps.com?q=01001000");
        when(partnerMapper.toDetailed(any(), any(), any())).thenReturn(detalhado);

        PartnerDetailedDto result = partnerService.findByCnpJ("12345678000199");

        assertThat(result.getNome()).isEqualTo("João");
    }

    @Test
    @DisplayName("Deve lançar BusinessException quando CNPJ for inválido ou vazio")
    void shouldThrowBusinessExceptionWhenCnpjInvalid() {
        assertThatThrownBy(() -> partnerService.findByCnpJ(" "))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("CNPJ inválido");
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando sócio não for encontrado")
    void shouldThrowNotFoundWhenPartnerNotFound() {
        PartnerBaseDto socio = new PartnerBaseDto();
        socio.setCnpjEmpresa("12.345.678/0001-99");
        when(kelTechClient.searchPartner()).thenReturn(List.of(socio));
        assertThatThrownBy(() -> partnerService.findByCnpJ("99.999.999/0001-99"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Sócio não encontrado");
    }
}
