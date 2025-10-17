package com.caiomelo204.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartnerResumeDto {
    private String nome;
    private String cnpj;
    private Double participacao;
    private String cep;
}
