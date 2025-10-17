package com.caiomelo204.backend.mapper;

import com.caiomelo204.backend.dto.PartnerDetailedDto;
import com.caiomelo204.backend.dto.PartnerResumeDto;
import com.caiomelo204.backend.dto.RevenueDto;
import com.caiomelo204.backend.dto.PartnerBaseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PartnerMapperTest {

    private PartnerMapper partnerMapper;

    @BeforeEach
    void setUp() {
        partnerMapper = new PartnerMapper();
    }

    @Test
    @DisplayName("Deve mapear PartnerBaseDto para PartnerResumeDto corretamente")
    void shouldMapToResumeCorrectly() {
        PartnerBaseDto base = new PartnerBaseDto();
        base.setNome("João da Silva");
        base.setCep("13065900");
        base.setParticipacao(35.0);
        base.setCnpjEmpresa("12345678000199");

        PartnerResumeDto dto = partnerMapper.toResume(base);

        assertThat(dto).isNotNull();
        assertThat(dto.getNome()).isEqualTo("João da Silva");
        assertThat(dto.getCep()).isEqualTo("13065900");
        assertThat(dto.getParticipacao()).isEqualTo(35.0);
        assertThat(dto.getCnpj()).isEqualTo("12345678000199");
    }

    @Test
    @DisplayName("Deve retornar null quando PartnerBaseDto for null em toResume")
    void shouldReturnNullWhenBaseIsNullToResume() {
        PartnerResumeDto dto = partnerMapper.toResume(null);
        assertThat(dto).isNull();
    }

    @Test
    @DisplayName("Deve mapear PartnerBaseDto e RevenueDto para PartnerDetailedDto corretamente")
    void shouldMapToDetailedCorrectly() {
        PartnerBaseDto base = new PartnerBaseDto();
        base.setNome("Maria Souza");
        base.setCep("01001000");
        base.setParticipacao(50.0);
        base.setCnpjEmpresa("98765432000100");

        RevenueDto revenue = new RevenueDto();
        RevenueDto.EstabelecimentoDTO est = new RevenueDto.EstabelecimentoDTO();
        est.setCep("01001000");
        revenue.setEstabelecimento(est);

        String mapUrl = "https://maps.google.com?q=01001000";

        PartnerDetailedDto dto = partnerMapper.toDetailed(base, revenue, mapUrl);

        assertThat(dto).isNotNull();
        assertThat(dto.getNome()).isEqualTo("Maria Souza");
        assertThat(dto.getCnpj()).isEqualTo("98765432000100");
        assertThat(dto.getParticipacao()).isEqualTo(50.0);
        assertThat(dto.getCep()).isEqualTo("01001000");
        assertThat(dto.getReceita()).isEqualTo(revenue);
        assertThat(dto.getMapUrlEmbed()).isEqualTo(mapUrl);
    }

    @Test
    @DisplayName("Deve retornar null quando PartnerBaseDto for null em toDetailed")
    void shouldReturnNullWhenBaseIsNullToDetailed() {
        RevenueDto revenue = new RevenueDto();
        PartnerDetailedDto dto = partnerMapper.toDetailed(null, revenue, "map");
        assertThat(dto).isNull();
    }
}
