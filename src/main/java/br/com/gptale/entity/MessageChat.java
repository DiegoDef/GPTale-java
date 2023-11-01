package br.com.gptale.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "d_message_conversation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageChat {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Column(name = "role")
    private String role;

    @Lob
    @NotBlank
    @Column(name = "content")
    private String content;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ID_CHAT")
    private Chat conversation;

    @Column(name = "created_date")
    private Instant createdDate = Instant.now();

    public MessageChat(String role, String content, Chat conversation) {
        this.role = role;
        this.content = content;
        this.conversation = conversation;
    }
}
