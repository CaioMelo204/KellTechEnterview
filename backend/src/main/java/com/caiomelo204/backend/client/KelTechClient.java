package com.caiomelo204.backend.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import com.caiomelo204.backend.exception.ExternalServiceException;
import com.caiomelo204.backend.exception.TimeoutException;
import com.caiomelo204.backend.dto.KelTechResponseDto;
import com.caiomelo204.backend.dto.PartnerBaseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Component
@RequiredArgsConstructor
public class KelTechClient {

    private final WebClient webClient;

    @Value("${kel-url}")
    private String baseUrl;

    public List<PartnerBaseDto> searchPartner() {
        try {
            KelTechResponseDto response = webClient.get()
                    .uri(baseUrl)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,
                            res -> Mono.error(new ExternalServiceException("Erro 4xx ao consultar KelTech API")))
                    .onStatus(HttpStatusCode::is5xxServerError,
                            res -> Mono.error(new ExternalServiceException("Erro 5xx ao consultar KelTech API")))
                    .bodyToMono(String.class)
                    .map(body -> {
                        try {
                            ObjectMapper mapper = new ObjectMapper();
                            return mapper.readValue(body, KelTechResponseDto.class);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            throw new ExternalServiceException("Erro ao converter resposta da KelTech", e);
                        }
                    })
                    .timeout(Duration.ofSeconds(30))
                    .doOnError(e -> System.out.println(e.getMessage()))
                    .block();

            if (response == null ||
                    response.getMix() == null ||
                    response.getMix().getQuadroSocietario() == null ||
                    response.getMix().getQuadroSocietario().getData() == null ||
                    response.getMix().getQuadroSocietario().getData().getQuadroSocietario() == null) {
                throw new ExternalServiceException("Resposta inválida da API KelTech");
            }

            return response.getMix()
                    .getQuadroSocietario()
                    .getData()
                    .getQuadroSocietario();

        } catch (TimeoutException e) {
            throw new TimeoutException("Timeout ao consultar a API da KelTech");
        } catch (Exception e) {
            throw new ExternalServiceException("Erro inesperado ao buscar dados dos sócios", e);
        }
    }
}