package br.com.gptale.service;

import br.com.gptale.dto.SendOptionDTO;
import br.com.gptale.dto.StoryChatDTO;
import br.com.gptale.dto.StoryDTO;
import br.com.gptale.dto.StorySaveOptionDTO;
import br.com.gptale.entity.Chat;
import br.com.gptale.entity.Story;
import br.com.gptale.exceptions.OptionNotFoundException;
import br.com.gptale.repository.StoryRepository;
import br.com.gptale.utils.ChatGPTPromptUtils;
import br.com.gptale.utils.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static br.com.gptale.entity.Role.USER;

@Service
public class StoryService {

    @Value("${openai.model}")
    private String model;

    @Autowired
    private StoryRepository repository;

    @Autowired
    private ChatService chatService;

    @Autowired
    private ModelMapper modelMapper;

    public StoryDTO startStory(Story story) {
        Chat chat = new Chat(model, ChatGPTPromptUtils.createInitialMessage(story));
        chatService.save(chat);

        StopWatch watch = new StopWatch();
        watch.start();
        String response = chatService.postToOpenAi(chat);
        watch.stop();
        System.out.printf("Post openai executado em %s segundos.%n", watch.getTotalTimeSeconds());
        System.out.println(response);

        String paragraph = StringUtils.extractParagraph(response);
        List<String> options = StringUtils.extractOptions(response);
        story.setParagraph(paragraph);
        story.setOptions(options);
        story.setFullStory(paragraph.trim());
        story.setChat(chat);

        return modelMapper.map(repository.save(story), StoryDTO.class);
    }

    public StoryDTO sendOption(SendOptionDTO dto) {
        Story story = repository.findById(dto.getId()).orElseThrow(EntityNotFoundException::new);

        if (story.getOptions().size() < dto.getOption()) {
            throw new OptionNotFoundException(dto.getOption());
        }

        Chat chat = story.getChat();
        chat.addMessage(USER.getValue(), createMessage(story, dto));

        String response = chatService.postToOpenAi(chat);
        if (response.isBlank()) {
            return null;
        }

        String paragraph = StringUtils.extractParagraph(response);
        story.setParagraph(paragraph);
        story.addToStory(paragraph);
        story.setOptions(StringUtils.extractOptions(response));
        story.setChat(chat);

        return modelMapper.map(repository.save(story), StoryDTO.class);
    }

    private String createMessage(Story story, SendOptionDTO dto) {
        story.incrementParagraphCount();
        return ChatGPTPromptUtils.createOptionMessage(story, dto);
    }

    public StorySaveOptionDTO saveStory(Long id) {
        Story story = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(story, StorySaveOptionDTO.class);
    }

    public StoryChatDTO findById(Long id) {
        return modelMapper.map(repository.findById(id), StoryChatDTO.class);
    }
}
