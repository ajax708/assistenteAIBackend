package com.soporte.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class RestClient {
/*
    private static final Logger LOGGER = Logger.getLogger(RestClientSai.class.getName());
    private final RestTemplate restTemplate;





    public Object consumeServiceWithAuth(String baseUrl, String user, String pass, Object requestBody, Long connectTimeout, Long readTimeout, Class clas) throws Exception {
        restTemplate.setRequestFactory(getRequestFactory(connectTimeout.intValue(), readTimeout.intValue()));
        HttpHeaders headers = new HttpHeaders();
        headers.set("user", user);
        headers.set("password", pass);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Object> response = restTemplate.exchange(baseUrl, HttpMethod.POST, requestEntity, clas);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Error al consumir el servicio: " + response.getStatusCode());
        }
    }


    public Object consumeServiceBearerToken(String session, String baseUrl, String jwtToken, Object requestBody, HttpMethod httpMethod, Long connectTimeout, Long readTimeout, Class clas) {
        LOGGER.log(Level.INFO, "request:[{0}]{1}", new Object[]{session, new Gson().toJson(requestBody)});
        restTemplate.setRequestFactory(this.getRequestFactory(connectTimeout.intValue(), readTimeout.intValue()));
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Object> response = restTemplate.exchange(baseUrl, httpMethod, requestEntity, clas);
        LOGGER.log(Level.INFO, "response:[{0}]{1}", new Object[]{session, response});
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Error al consumir el servicio: " + response.getStatusCode());
        }
    }

    private ClientHttpRequestFactory getRequestFactory(int connectTimeout, int readTimeout) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeout);
        factory.setReadTimeout(readTimeout);
        return factory;
    }


    public void consumirChatGPT(){
        try {
      String prompt = "A continuación se te presenta una prédica: "${audioText}". Por favor, genera 3 frases o mensajes cortos que resuman la esencia de esta prédica";

      String response = await axios.post('https://api.openai.com/v1/chat/completions', {
                    model: 'gpt-3.5-turbo',
                    messages: [
            { role: 'system', content: 'Eres un asistente experto en generar mensajes cortos y concisos a partir de textos más largos.' },
            { role: 'user', content: prompt },
        ],
            max_tokens: 300,
      }, {
                headers: {
                    'Authorization': Bearer ${process.env.APIKEY_OPENIA},
                    'Content-Type': 'application/json',
                },
            });

      const messages = response.data.choices[0].message.content.split('\n').filter(msg => msg.trim() !== '');
            return messages;
        } catch (error) {
            console.error("Error al consumir GPT-3.5", error);
            throw new BadRequestException('Failed to generate text with GPT-3.5');
        }
    }

    async generarTextoConGPT3(audioText: string): Promise<string[]> {
        try {
      const prompt = A continuación se te presenta una prédica: "${audioText}". Por favor, genera 3 frases o mensajes cortos que resuman la esencia de esta prédica.;

      const response = await axios.post('https://api.openai.com/v1/chat/completions', {
                    model: 'gpt-3.5-turbo',
                    messages: [
            { role: 'system', content: 'Eres un asistente experto en generar mensajes cortos y concisos a partir de textos más largos.' },
            { role: 'user', content: prompt },
        ],
            max_tokens: 300,
      }, {
                headers: {
                    'Authorization': Bearer ${process.env.APIKEY_OPENIA},
                    'Content-Type': 'application/json',
                },
            });

      const messages = response.data.choices[0].message.content.split('\n').filter(msg => msg.trim() !== '');
            return messages;
        } catch (error) {
            console.error("Error al consumir GPT-3.5", error);
            throw new BadRequestException('Failed to generate text with GPT-3.5');
        }
    }
*/
}
