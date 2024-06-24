package com.soporte.service;

import com.soporte.dto.MensajeOpenAI;
import com.soporte.dto.OpenAIRequest;
import com.soporte.dto.OpenAIResponse;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service

public class OpenAIService {
    private final WebClient webClient;

    @Value("${openai.api.key}")
    private String apiKey;

    public OpenAIService(WebClient webClient) {
        this.webClient = webClient;
    }

    @Async
    public CompletableFuture<List<String>> generarTextoConGPT3(String audioText) {
        try {
            String prompt = "A continuación se te presenta una prédica: \"" + audioText + "\". Por favor, genera 3 frases o mensajes cortos que resuman la esencia de esta prédica.";

            Mono<OpenAIResponse> responseMono = webClient.post()
                    .uri("https://api.openai.com/v1/chat/completions")
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(new OpenAIRequest(
                            List.of(
                                    new MensajeOpenAI("system", "Eres un asistente experto en generar mensajes cortos y concisos a partir de textos más largos."),
                                    new MensajeOpenAI("user", prompt)
                            ),
                            "gpt-3.5-turbo",
                            300
                    ))
                    .retrieve()
                    .bodyToMono(OpenAIResponse.class);
            OpenAIResponse response = responseMono.block();
            String[] messages = response.getChoices().get(0).getMessage().getContent().split("\n");
            List<String> result = Arrays.stream(messages)
                    .filter(msg -> !msg.trim().isEmpty())
                    .collect(Collectors.toList());

            return CompletableFuture.completedFuture(result);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to generate text with GPT-3.5", e);
        }
    }
}
