package com.example.chatbot.service;

import com.example.chatbot.model.ChatMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;

@Service
public class OpenAIService {
    private static final Logger logger = LoggerFactory.getLogger(OpenAIService.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Value("${openai.api.key}")
    private String apiKey;
    
    @Value("${openai.api.url}")
    private String apiUrl;
    
    private final RestTemplate restTemplate;
    
    public OpenAIService() {
        this.restTemplate = new RestTemplate();
    }
    
    public static class OpenAIResponse {
        public List<Choice> choices;
    }

    public static class Choice {
        public Message message;
    }

    public static class Message {
        public String role;
        public String content;
    }

    public String getResponse(List<ChatMessage> messages) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", messages);
        requestBody.put("temperature", 0.7);
        
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        
        try {
            logger.info("API Key (first 10 chars): {}", apiKey != null ? apiKey.substring(0, Math.min(10, apiKey.length())) + "..." : "null");
            logger.info("API URL: {}", apiUrl);
            logger.info("Request headers: {}", headers);
            logger.info("Request body: {}", objectMapper.writeValueAsString(requestBody));
            
            ResponseEntity<OpenAIResponse> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<OpenAIResponse>() {}
            );
            
            logger.info("Response status code: {}", responseEntity.getStatusCode());
            logger.info("Response headers: {}", responseEntity.getHeaders());
            
            OpenAIResponse response = responseEntity.getBody();
            
            if (response == null) {
                logger.error("Received null response from OpenAI API");
                return "I apologize, but I received an empty response. Please try again.";
            }
            
            if (response.choices == null) {
                logger.error("Response choices is null");
                return "I apologize, but I received an empty response. Please try again.";
            }
            
            if (response.choices.isEmpty()) {
                logger.error("Response choices is empty");
                return "I apologize, but I received an empty response. Please try again.";
            }
            
            String content = response.choices.get(0).message.content;
            if (content == null || content.trim().isEmpty()) {
                logger.error("Response content is null or empty");
                return "I apologize, but I received an empty response. Please try again.";
            }
            
            logger.info("Successfully received response from OpenAI API: {}", content);
            return content;
            
        } catch (Exception e) {
            logger.error("Error calling OpenAI API: {}", e.getMessage(), e);
            if (e.getMessage().contains("429")) {
                return "I apologize, but I'm currently experiencing high demand. Please try again in a few moments.";
            } else if (e.getMessage().contains("401")) {
                logger.error("Authentication failed. Please check your API key.");
                return "I apologize, but there seems to be an authentication issue. Please check the API configuration.";
            } else if (e.getMessage().contains("403")) {
                logger.error("Access forbidden. Please check your API permissions.");
                return "I apologize, but access to the API is forbidden. Please check your API permissions.";
            }
            logger.error("Unexpected error: {}", e.getMessage());
            return "I apologize, but I'm having trouble processing your request right now. Please try again later.";
        }
    }
} 