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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartnerService {
    private final KelTechClient kelTechClient;
    private final RecipeClient recipeClient;
    private final MapsService mapsService;
    private final PartnerMapper partnerMapper;

    public List<PartnerResumeDto> findAllByParticipation(Double participacaoMin) {
        if (participacaoMin == null || participacaoMin < 0) {
            throw new BusinessException("O valor de participação mínima deve ser maior ou igual a zero.");
        }

        List<PartnerBaseDto> socios = kelTechClient.searchPartner();

        if (socios == null || socios.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum sócio encontrado na base da KelTech.");
        }

        List<PartnerBaseDto> filtrados = socios.stream()
                .filter(s -> s.getParticipacao() != null && s.getParticipacao() >= participacaoMin)
                .collect(Collectors.toList());

        if (filtrados.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum sócio com participação acima de " + participacaoMin + "% encontrado.");
        }

        return filtrados.stream()
                .map(partnerMapper::toResume)
                .collect(Collectors.toList());
    }

    public PartnerDetailedDto findByCnpJ(String cnpj) {
        if (cnpj == null || cnpj.isBlank()) {
            throw new BusinessException("CNPJ inválido ou vazio.");
        }

        List<PartnerBaseDto> socios = kelTechClient.searchPartner();

        PartnerBaseDto socio = socios.stream()
                .filter(s -> s.getCnpjEmpresa() != null && s.getCnpjEmpresa().replaceAll("\\D", "")
                        .equals(cnpj.replaceAll("\\D", "")))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Sócio não encontrado para o CNPJ: " + cnpj));

        RevenueDto receita = recipeClient.searchByCnpj(cnpj);

        String cep = receita.getEstabelecimento().getCep() != null && !receita.getEstabelecimento().getCep().isBlank()
                ? receita.getEstabelecimento().getCep()
                : socio.getCep();

        String mapUrl = mapsService.generateMap(cep);

        return partnerMapper.toDetailed(socio, receita, mapUrl);
    }
}
