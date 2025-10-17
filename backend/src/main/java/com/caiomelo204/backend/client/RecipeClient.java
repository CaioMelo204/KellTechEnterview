package com.caiomelo204.backend.client;

import org.springframework.beans.factory.annotation.Value;
import com.caiomelo204.backend.dto.RevenueDto;
import com.caiomelo204.backend.exception.ExternalServiceException;
import com.caiomelo204.backend.exception.TimeoutException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RecipeClient {
    private final WebClient webClient;

    @Value("${receita-url}")
    private String baseUrl;

    public RevenueDto searchByCnpj(String cnpj) {
        try {
            // remove pontuação
            String cleanCnpj = cnpj.replaceAll("\\D", "");

            RevenueDto revenue = webClient.get()
                    .uri(baseUrl + "/" + cleanCnpj)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,
                            res -> Mono.error(new ExternalServiceException("CNPJ inválido ou não encontrado.")))
                    .onStatus(HttpStatusCode::is5xxServerError,
                            res -> Mono.error(new ExternalServiceException("Erro na API pública da Receita.")))
                    .bodyToMono(RevenueDto.class)
                    .timeout(Duration.ofSeconds(30))
                    .doOnError(e -> System.err.println("❌ Falha WebClient: " + e.getMessage()))
                    .block();

            if (revenue == null) {
                throw new ExternalServiceException("Resposta vazia da Receita.");
            }

            return revenue;
        } catch (TimeoutException e) {
            throw new TimeoutException("Timeout ao consultar a API da Receita.");
        } catch (Exception e) {
            throw new ExternalServiceException("Erro inesperado ao consultar a Receita.", e);
        }
    }
}
