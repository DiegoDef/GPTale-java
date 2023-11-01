package br.com.gptale.resource;

import br.com.gptale.dto.*;
import br.com.gptale.entity.Story;
import br.com.gptale.service.StoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/")
public class StoryResource {

    @Autowired
    private StoryService storyService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/startStory")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public StoryDTO startStory(@RequestBody StoryBasicDTO dto) {
        return storyService.startStory(modelMapper.map(dto, Story.class));
    }

    @PostMapping("/sendOption")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public StoryDTO sendOption(@RequestBody SendOptionDTO dto) {
        return storyService.sendOption(dto);
    }

    @GetMapping("/saveStory/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public StorySaveOptionDTO saveStory(@PathVariable Long id) {
        return storyService.saveStory(id);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public StoryChatDTO find(@PathVariable Long id) {
        return storyService.findById(id);
    }

    @GetMapping("/teste")
    @Transactional
    public String teste() {
        return "Teste";
    }
}
