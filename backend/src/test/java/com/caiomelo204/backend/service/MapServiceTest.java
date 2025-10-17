package com.caiomelo204.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;

class MapsServiceTest {

    private MapsService mapsService;

    @BeforeEach
    void setUp() throws Exception {
        mapsService = new MapsService();

        Field field = MapsService.class.getDeclaredField("embedTemplate");
        field.setAccessible(true);
        field.set(mapsService, "https://www.google.com/maps?q={cep}&output=embed");
    }

    @Test
    @DisplayName("Deve gerar a URL correta do mapa com o CEP formatado")
    void shouldGenerateMapUrlWithCep() {
        String cep = " 13065-900 ";

        String result = mapsService.generateMap(cep);

        assertThat(result)
                .isEqualTo("https://www.google.com/maps?q=13065-900&output=embed");
    }

    @Test
    @DisplayName("Deve retornar null quando o CEP for nulo")
    void shouldReturnNullWhenCepIsNull() {
        String result = mapsService.generateMap(null);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Deve retornar null quando o CEP for vazio")
    void shouldReturnNullWhenCepIsBlank() {
        String result = mapsService.generateMap("   ");
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Deve substituir corretamente o placeholder {cep} no template")
    void shouldReplacePlaceholderCorrectly() {
        String cep = "01001-000";

        String result = mapsService.generateMap(cep);

        assertThat(result)
                .startsWith("https://www.google.com/maps?q=")
                .contains(cep)
                .endsWith("&output=embed");
    }

    @Test
    @DisplayName("Deve remover espaços internos e externos do CEP antes de gerar o link")
    void shouldTrimAndRemoveSpacesInCep() {
        String cep = " 13065 900 ";

        String result = mapsService.generateMap(cep);

        assertThat(result)
                .isEqualTo("https://www.google.com/maps?q=13065900&output=embed");
    }
}
