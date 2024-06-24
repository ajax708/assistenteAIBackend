package com.soporte.dto;

import lombok.Data;

import java.util.List;

@Data

public class OpenAIResponse {
    private List<Choice> choices;

    // getters and setters

    @Data
    public static class Choice {
        private MensajeOpenAI message;

        // getters and setters
    }
}
