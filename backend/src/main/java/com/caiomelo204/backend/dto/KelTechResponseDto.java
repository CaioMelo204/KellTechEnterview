package com.caiomelo204.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KelTechResponseDto {
    private Mix mix;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Mix {
        private QuadroSocietario quadroSocietario;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class QuadroSocietario {
        private DataContainer data;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataContainer {
        @JsonProperty("quadroSocietario")
        private List<PartnerBaseDto> quadroSocietario;
    }
}
