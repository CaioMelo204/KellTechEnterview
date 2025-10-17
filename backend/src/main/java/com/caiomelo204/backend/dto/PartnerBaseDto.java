package com.caiomelo204.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PartnerBaseDto {
    private String nome;

    @JsonProperty("cnpjEmpresa")
    private String cnpjEmpresa;

    private Double participacao;

    private String cep;
}
