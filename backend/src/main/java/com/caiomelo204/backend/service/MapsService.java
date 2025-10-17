package com.caiomelo204.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MapsService {
    @Value("${maps-template}")
    private String embedTemplate;

    public String generateMap(String cep) {
        if (cep == null || cep.isBlank()) {
            return null;
        }

        String cepFormatado = cep.trim().replace(" ", "");

        return embedTemplate.replace("{cep}", cepFormatado);
    }
}
