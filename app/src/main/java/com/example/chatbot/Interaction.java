package com.example.chatbot;

public class Interaction {

    private String messageReceived;
    private String messageToSend;

    public Interaction(String messageReceived, String messageToSend){

        this.messageReceived = messageReceived;
        this.messageToSend = messageToSend;
    }

    public String getMessageReceived() {
        return messageReceived;
    }

    public String getMessageToSend() {
        return messageToSend;
    }
}
