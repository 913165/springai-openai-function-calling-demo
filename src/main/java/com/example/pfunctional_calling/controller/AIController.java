package com.example.pfunctional_calling.controller;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class AIController {

    private Logger logger = Logger.getLogger(AIController.class.getName());

    private final OpenAiChatModel chatModel;

    @Autowired
    public AIController(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;

    }

    @GetMapping("/chat")
    public String chat(String message) {
        UserMessage userMessage = new UserMessage("What's the status of bookings H001, H002, and H003?");

        ChatResponse response = chatModel.call(new Prompt(List.of(userMessage),
                OpenAiChatOptions.builder().withFunction("bookingStatusFunction").build())); // (1) Enable the booking status function

        logger.info("Response: " + response);

        return response.getResult().getOutput().getContent();
    }

}
