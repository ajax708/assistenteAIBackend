package com.soporte.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.soporte.dto.MensajeOpenAI;
import com.soporte.dto.OpenAIRequest;
import com.soporte.dto.OpenAIResponse;
import okhttp3.*;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service

public class OpenAIService {
    private final WebClient webClient;


    @Value("${openai.api.key}")
    private static String apiKey ="";
    private static final String API_KEY = "";
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    public OpenAIService(WebClient webClient) {
        this.webClient = webClient;
    }

    @Async
    public CompletableFuture<List<String>> generarTextoConGPT3(String contenido) {
        try {
            String content = contenido;
            Mono<OpenAIResponse> responseMono = webClient.post()
                    .uri("https://api.openai.com/v1/chat/completions")
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(new OpenAIRequest(
                            List.of(
                                    new MensajeOpenAI("system", "Actúa como un experto en soporte técnico especializado en sistemas informáticos. Tu tarea es analizar el contenido del correo recibido, identificar y diagnosticar el problema descrito, y proporcionar una solución clara y detallada. Estructura tu respuesta en formato de correo electrónico profesional, incluyendo:\n" +
                                            "1. **Saludo Inicial**: Inicia el correo con un saludo apropiado.\n" +
                                            "2. **Cuerpo del Mensaje**:\n" +
                                            "    - **Diagnóstico del Problema**: Identifica y explica el problema de manera concisa.\n" +
                                            "    - **Solución Propuesta**: Proporciona pasos específicos y detallados para resolver el problema.\n" +
                                            "    - **Consejos Adicionales**: Si es relevante, ofrece consejos para evitar problemas similares en el futuro.\n" +
                                            "3. **Despedida**: Termina con una despedida formal.\n" +
                                            "4. **Claridad y Profesionalismo**: Comunica de manera clara, profesional y sin jerga innecesaria.\n" +
                                            "5. **Empatía y Cortesía**: Muestra empatía hacia el usuario y asegúrate de que la respuesta sea cortés y útil.\n" +
                                            "\n" +
                                            "Ejemplo de estructura:\n" +
                                            "- **Saludo Inicial**: Estimado/a [Nombre],\n" +
                                            "- **Diagnóstico del Problema**: Parece que el sistema no se está conectando a la red debido a configuraciones incorrectas del servidor DNS.\n" +
                                            "- **Solución Propuesta**: Verifique las configuraciones de DNS en la configuración de red de su sistema y asegúrese de que apuntan a los servidores DNS correctos.\n" +
                                            "- **Consejos Adicionales**: Para evitar este problema en el futuro, configure el DNS de forma automática o utilice servidores DNS públicos confiables como 8.8.8.8.\n" +
                                            "- **Empatía y Cortesía**: Lamento las molestias que esto pueda haber causado.\n" +
                                            "- **Despedida**: Atentamente, [Tu Nombre] / Equipo de Soporte Técnico\n" +
                                            "\n" +
                                            "Responde siempre basándote en el contenido del correo proporcionado y mantén un tono amigable y profesional en todo momento."),
                                    new MensajeOpenAI("user", contenido)
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

    public String chatGptIa (String content) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String generatedText ="";
        String prompt =  "Actúa como un experto en soporte técnico especializado en sistemas informáticos. Tu tarea es analizar el contenido del correo recibido, identificar y diagnosticar el problema descrito, y proporcionar una solución clara y detallada. Estructura tu respuesta en formato de correo electrónico profesional, incluyendo:\n" +
                "1. **Saludo Inicial**: Inicia el correo con un saludo apropiado.\n" +
                "2. **Cuerpo del Mensaje**:\n" +
                "    - **Diagnóstico del Problema**: Identifica y explica el problema de manera concisa.\n" +
                "    - **Solución Propuesta**: Proporciona pasos específicos y detallados para resolver el problema.\n" +
                "    - **Consejos Adicionales**: Si es relevante, ofrece consejos para evitar problemas similares en el futuro.\n" +
                "3. **Despedida**: Termina con una despedida formal.\n" +
                "4. **Claridad y Profesionalismo**: Comunica de manera clara, profesional y sin jerga innecesaria.\n" +
                "5. **Empatía y Cortesía**: Muestra empatía hacia el usuario y asegúrate de que la respuesta sea cortés y útil.\n" +
                "\n" +
                "Ejemplo de estructura:\n" +
                "- **Saludo Inicial**: Estimado/a [Nombre],\n" +
                "- **Diagnóstico del Problema**: Parece que el sistema no se está conectando a la red debido a configuraciones incorrectas del servidor DNS.\n" +
                "- **Solución Propuesta**: Verifique las configuraciones de DNS en la configuración de red de su sistema y asegúrese de que apuntan a los servidores DNS correctos.\n" +
                "- **Consejos Adicionales**: Para evitar este problema en el futuro, configure el DNS de forma automática o utilice servidores DNS públicos confiables como 8.8.8.8.\n" +
                "- **Empatía y Cortesía**: Lamento las molestias que esto pueda haber causado.\n" +
                "- **Despedida**: Atentamente, [Tu Nombre] / Equipo de Soporte Técnico\n" +
                "\n" +
                "Responde siempre basándote en el contenido del correo proporcionado y mantén un tono amigable y profesional en todo momento.";

        JsonObject jsonBody = new JsonObject();
        jsonBody.addProperty("model", "gpt-3.5-turbo");
        jsonBody.addProperty("max_tokens", 300);

        JsonArray messagesArray = new JsonArray();

        JsonObject systemMessage = new JsonObject();
        systemMessage.addProperty("role", "system");
        systemMessage.addProperty("content", prompt);
        messagesArray.add(systemMessage);

        JsonObject userMessage = new JsonObject();
        userMessage.addProperty("role", "user");
        userMessage.addProperty("content", content);
        messagesArray.add(userMessage);

        jsonBody.add("messages", messagesArray);

        RequestBody body = RequestBody.create(jsonBody.toString(), MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            String responseBody = response.body().string();
            System.out.println(responseBody);

            // Parsear la respuesta JSON
            JsonObject jsonResponse = new Gson().fromJson(responseBody, JsonObject.class);
             generatedText = jsonResponse.getAsJsonArray("choices")
                    .get(0).getAsJsonObject()
                    .getAsJsonObject("message")
                    .get("content").getAsString();

            System.out.println("Respuesta generada: " + generatedText);
        }
        return generatedText;
    }
}
