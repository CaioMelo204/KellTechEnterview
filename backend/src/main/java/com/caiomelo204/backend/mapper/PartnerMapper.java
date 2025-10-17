package com.caiomelo204.backend.mapper;

import com.caiomelo204.backend.dto.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PartnerMapper {
    public PartnerResumeDto toResume(PartnerBaseDto partnerBaseDto) {
        if (partnerBaseDto == null) {
            return null;
        }
        PartnerResumeDto dto = new PartnerResumeDto();
        dto.setNome(partnerBaseDto.getNome());
        dto.setCep(partnerBaseDto.getCep());
        dto.setParticipacao(partnerBaseDto.getParticipacao());
        dto.setCnpj(partnerBaseDto.getCnpjEmpresa());
        return dto;
    }

    public PartnerDetailedDto toDetailed(PartnerBaseDto partnerDetailedDto, RevenueDto revenueDto, String map) {
        if (partnerDetailedDto == null) {
            return null;
        }
        PartnerDetailedDto dto = new PartnerDetailedDto();
        var allCnae = new ArrayList<CnaeDto>();
        var principal = revenueDto.getEstabelecimento().getPrincipalCnae();
        if (principal != null) {
            allCnae.add(principal);
        }
        var secundarios = revenueDto.getEstabelecimento().getSecondaryCnaes();
        if (secundarios != null && !secundarios.isEmpty()) {
            allCnae.addAll(secundarios);
        }
        dto.setNome(partnerDetailedDto.getNome());
        dto.setCnpj(partnerDetailedDto.getCnpjEmpresa());
        dto.setParticipacao(partnerDetailedDto.getParticipacao());
        dto.setCep(partnerDetailedDto.getCep());
        dto.setReceita(revenueDto);
        dto.setCnaes(allCnae);
        dto.setMapUrlEmbed(map);
        return dto;
    }
}
