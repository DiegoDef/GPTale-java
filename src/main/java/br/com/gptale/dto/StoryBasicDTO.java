package br.com.gptale.dto;

import lombok.Data;

@Data
public class StoryBasicDTO {

    private Long id;

    private String title;

    private String gender;

    private int maxParagraph;
}
