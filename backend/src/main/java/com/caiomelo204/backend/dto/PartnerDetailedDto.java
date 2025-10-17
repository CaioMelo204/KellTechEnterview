package com.caiomelo204.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartnerDetailedDto {
    private String nome;
    private String cnpj;
    private Double participacao;
    private String cep;
    private RevenueDto receita;
    private String mapUrlEmbed;
    private List<CnaeDto> cnaes;
}
