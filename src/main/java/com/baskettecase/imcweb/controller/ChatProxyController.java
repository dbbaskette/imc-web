package com.baskettecase.imcweb.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/chat-proxy")
public class ChatProxyController {
    
    @Value("${imc.chatbot.url}")
    private String chatbotUrl;
    
    private final RestTemplate restTemplate = new RestTemplate();
    
    @GetMapping("/config")
    public ResponseEntity<Map<String, String>> getChatConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("chatbotUrl", chatbotUrl);
        return ResponseEntity.ok(config);
    }
    
    @PostMapping("/message")
    public ResponseEntity<Map<String, Object>> sendMessage(@RequestBody Map<String, String> request) {
        try {
            String message = request.get("message");
            String sessionId = request.getOrDefault("sessionId", UUID.randomUUID().toString());
            String customerId = request.get("customerId");
            
            if (message == null || message.trim().isEmpty()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Message cannot be empty");
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
            // Log customer context for debugging
            if (customerId != null) {
                System.out.println("Chat request from customer ID: " + customerId);
            }
            
            // Create request payload for imc-chatbot
            Map<String, String> chatbotRequest = new HashMap<>();
            chatbotRequest.put("message", message);
            chatbotRequest.put("sessionId", sessionId);
            
            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            // Create HTTP entity
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(chatbotRequest, headers);
            
            // Make request to imc-chatbot
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                chatbotUrl,
                HttpMethod.POST,
                entity,
                (Class<Map<String, Object>>) (Class<?>) Map.class
            );
            
            // Return the response from imc-chatbot
            Map<String, Object> result = new HashMap<>();
            if (response.getBody() != null) {
                result.putAll(response.getBody());
            }
            result.put("success", true);
            
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to connect to chatbot: " + e.getMessage());
            errorResponse.put("success", false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
