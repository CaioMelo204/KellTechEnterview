package com.caiomelo204.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RevenueDto {
    @JsonProperty("razao_social")
    private String razaoSocial;

    @JsonProperty("capital_social")
    private String capitalSocial;

    @JsonProperty("situacao_cadastral")
    private String situacao;

    @JsonProperty("cnpj_raiz")
    private String cnpjRaiz;

    @JsonProperty("estabelecimento")
    private EstabelecimentoDTO estabelecimento;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EstabelecimentoDTO {

        private String tipo;
        private String logradouro;
        private String numero;
        private String complemento;
        private String bairro;

        @JsonProperty("atividade_principal")
        private CnaeDto principalCnae;

        @JsonProperty("atividades_secundarias")
        private List<CnaeDto> secondaryCnaes;

        @JsonProperty("cep")
        private String cep;

        @JsonProperty("municipio")
        private String municipio;

        @JsonProperty("uf")
        private String uf;
    }
}
