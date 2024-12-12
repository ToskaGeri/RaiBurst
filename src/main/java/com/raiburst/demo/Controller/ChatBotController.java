package com.raiburst.demo.Controller;

import com.raiburst.demo.Dto.ChatRequest;
import com.raiburst.demo.Dto.ChatResponse;
import com.raiburst.demo.Dto.ChatState;
import com.raiburst.demo.Models.Transaction;
import com.raiburst.demo.Service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/chatbot")
public class ChatBotController {

    private final UserService userService;
    private final Map<Long, ChatState> userChatStates = new HashMap<>();

    public ChatBotController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/interact")
    public ChatResponse interact(@RequestBody ChatRequest request) {
        Long userId = request.getUserId();
        String userMessage = request.getUserMessage();

        // Retrieve or initialize the chat state for this user
        ChatState chatState = userChatStates.getOrDefault(userId, new ChatState());
        String chatbotResponse;

        switch (chatState.getState()) {
            case "GREETING":
                chatbotResponse = "Welcome! Please provide the recipient's phone number.";
                chatState.setState("AWAITING_RECIPIENT");
                break;

            case "AWAITING_RECIPIENT":
                chatState.setRecipientPhoneNumber(userMessage);
                chatbotResponse = "How much money would you like to send?";
                chatState.setState("AWAITING_AMOUNT");
                break;

            case "AWAITING_AMOUNT":
                try {
                    double amount = Double.parseDouble(userMessage);
                    if (amount <= 0) throw new NumberFormatException();
                    chatState.setAmount(amount);
                    chatbotResponse = "What is the currency for this payment? (e.g., USD)";
                    chatState.setState("AWAITING_CURRENCY");
                } catch (NumberFormatException e) {
                    chatbotResponse = "Invalid amount. Please enter a valid number.";
                }
                break;

            case "AWAITING_CURRENCY":
                chatState.setCurrency(userMessage.toUpperCase());
                chatbotResponse = "Please provide a description for this payment.";
                chatState.setState("AWAITING_DESCRIPTION");
                break;

            case "AWAITING_DESCRIPTION":
                chatState.setDescription(userMessage);
                try {
                    Transaction transaction = userService.makePayment(
                            userId,
                            chatState.getRecipientPhoneNumber(),
                            chatState.getAmount(),
                            chatState.getCurrency(),
                            chatState.getDescription()
                    );
                    chatbotResponse = "Payment successful! Transaction ID: " + transaction.getId();
                    chatState.reset();
                } catch (Exception e) {
                    chatbotResponse = "Failed to make the payment: " + e.getMessage();
                    chatState.reset();
                }
                break;

            default:
                chatbotResponse = "Let's start over. Please provide the recipient's phone number.";
                chatState.reset();
                break;
        }

        // Save the updated state
        userChatStates.put(userId, chatState);

        // Return the chatbot response
        return new ChatResponse(chatbotResponse);
    }
}


