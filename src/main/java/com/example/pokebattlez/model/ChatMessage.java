package com.example.pokebattlez.model;

public class ChatMessage {
    private String name;
    private String body;

    public ChatMessage(String name, String body) {
        this.name = name;
        this.body = body;
    }

    public ChatMessage() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
