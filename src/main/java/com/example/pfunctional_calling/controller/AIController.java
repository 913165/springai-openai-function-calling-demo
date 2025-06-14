package com.example.springaiopenaifunctioncallingexample;

import com.example.springaiopenaifunctioncallingexample.service.MockBookingStatusService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class AIController {

    private Logger logger = Logger.getLogger(AIController.class.getName());

    private final OpenAiChatModel openAiChatModel;

    @Autowired
    public AIController(OpenAiChatModel openAiChatModel) {
        this.openAiChatModel = openAiChatModel;
    }

    @GetMapping("/chat")
    public String chat() {
        System.out.println("Chat endpoint called");
        String userMessage = "What is the status of Bookings for H001, H002 and H003 and H004?";
        //ChatResponse chatResponse = openAiChatModel.call(new Prompt(List.of(userMessage), OpenAiChatOptions.builder().withFunction("bookingStatus").build()));
        ToolCallback toolCallback = FunctionToolCallback
                .builder("bookingStatus", new MockBookingStatusService())
                .description("Get the status of bookings for a list of hotel IDs")
                .inputType(MockBookingStatusService.BookingRequest.class)
                .build();

        String response = ChatClient.create(openAiChatModel)
                .prompt(userMessage)
                .toolCallbacks(toolCallback)
                .call()
                .content();
        logger.info(userMessage);
        return response;
    }
}
