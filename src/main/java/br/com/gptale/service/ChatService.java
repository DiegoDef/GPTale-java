package br.com.gptale.service;

import br.com.gptale.dto.ChatDTO;
import br.com.gptale.dto.ChatGptResponse;
import br.com.gptale.dto.Message;
import br.com.gptale.entity.Chat;
import br.com.gptale.entity.MessageChat;
import br.com.gptale.repository.ChatRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatService {

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    @Autowired
    private RestTemplate template;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ChatRepository repository;

    public String postToOpenAi(Chat chat) {
        ChatGptResponse chatGptResponse = template.postForObject(apiURL, modelMapper.map(chat, ChatDTO.class), ChatGptResponse.class);
        if (chatGptResponse == null) {
            return "";
        }
        return addMessageToChat(chat, chatGptResponse);
    }

    private String addMessageToChat(Chat chat, ChatGptResponse chatGptResponse) {
        Message message = chatGptResponse.getChoices().get(0).getMessage();
        MessageChat messageChat = new MessageChat(message.getRole(), message.getContent(), chat);
        chat.getMessages().add(messageChat);
        repository.save(chat);
        return messageChat.getContent();
    }

    public void save(Chat chat) {
        repository.save(chat);
    }
}
