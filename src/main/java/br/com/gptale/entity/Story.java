package br.com.gptale.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "m_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Lob
    @NotBlank
    @Column(name = "title")
    private String title;

    @NotBlank
    @Column(name = "gender")
    private String gender;

    @Lob
    @Column(name = "paragraph")
    private String paragraph;

    @Lob
    @Column(name = "full_history")
    private String fullStory;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_chat")
    private Chat chat;

    @ElementCollection
    private List<String> options = new ArrayList<>();

    @Column(name = "max_paragraph")
    private int maxParagraph;

    @Column(name = "paragraph_count")
    private int paragraphCount = 1;

    public void addToStory(String paragraph) {
        this.fullStory = fullStory + "\n\n" + paragraph.trim();
    }

    public void incrementParagraphCount() {
        paragraphCount++;
    }
}
