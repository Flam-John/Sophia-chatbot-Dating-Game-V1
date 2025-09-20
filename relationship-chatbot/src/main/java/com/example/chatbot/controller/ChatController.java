package com.example.chatbot.controller;

import com.example.chatbot.service.ChatService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    private final ChatService chatService;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }
    
    @PostMapping("/send")
    public Map<String, String> sendMessage(@RequestBody Map<String, String> request) {
        logger.info("Received message request: {}", request);
        try {
            String message = request.get("message");
            logger.info("Processing message: {}", message);
            
            String response = chatService.processMessage(message);
            logger.info("Got response from service: {}", response);
            
            Map<String, String> result = Map.of(
                "message", response,
                "sender", "Sophia",
                "timestamp", LocalTime.now().format(TIME_FORMATTER)
            );
            logger.info("Sending response back to client: {}", result);
            return result;
        } catch (Exception e) {
            logger.error("Error processing message: ", e);
            throw e;
        }
    }
    
    @PostMapping("/clear")
    public void clearHistory() {
        chatService.clearHistory();
    }
} 