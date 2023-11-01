package br.com.gptale.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static br.com.gptale.entity.Role.USER;

@Data
public class ChatGPTRequest {
    private String model;
    private List<Message> messages;

    public ChatGPTRequest(String model, String prompt) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new Message(USER.getValue(), prompt));
    }
}
