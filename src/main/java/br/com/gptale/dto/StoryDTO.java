package br.com.gptale.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StoryDTO {

    private Long id;

    private String title;

    private String gender;

    private String paragraph;

    private List<String> options = new ArrayList<>();
}
