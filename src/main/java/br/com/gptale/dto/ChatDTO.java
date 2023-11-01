package br.com.gptale.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChatDTO {

    private String model;

    private List<Message> messages;

    private Double temperature = 0.8;
}
