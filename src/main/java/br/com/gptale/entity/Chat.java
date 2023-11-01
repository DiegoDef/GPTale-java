package br.com.gptale.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "m_chat")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_chat")
    private Long id;

    @NotBlank
    @Column(name = "model")
    private String model;


    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<MessageChat> messages = new ArrayList<>();

    public Chat(String model, String initialMessage) {
        this.model = model;
        this.messages.add(new MessageChat(Role.USER.getValue(), initialMessage, this));
    }

    public void addMessage(String role, String message) {
        this.messages.add(new MessageChat(role, message, this));
    }
}
