package com.raiburst.demo.Dto;

public class ChatResponse {
    private String chatbotMessage;

    public ChatResponse(String chatbotMessage) {
        this.chatbotMessage = chatbotMessage;
    }

    public String getChatbotMessage() {
        return chatbotMessage;
    }

    public void setChatbotMessage(String chatbotMessage) {
        this.chatbotMessage = chatbotMessage;
    }
}