package br.com.gptale.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SendOptionDTO {

    @NotNull
    private Long id;

    @NotNull
    private int option;
}
