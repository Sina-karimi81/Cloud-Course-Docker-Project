package com.example.dictionary.endpoints;

import com.example.dictionary.dto.DefinitionResponse;
import com.example.dictionary.dto.RandomWordResponse;
import com.example.dictionary.service.DictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DictionaryWS {

    private final DictionaryService dictionaryService;

    public DictionaryWS(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping("/word")
    public ResponseEntity<DefinitionResponse> getDefinition(@Param("word") String word) {
        DefinitionResponse definition = dictionaryService.getDefinition(word);
        log.info("the value returned from the definition api is {}", definition);
        if (definition != null) {
            return ResponseEntity.ok(definition);
        } else {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping("/random")
    public ResponseEntity<RandomWordResponse> getRandomWord() {
        RandomWordResponse randomWord = dictionaryService.getRandomWord();
        log.info("the value returned from the random word api is {}", randomWord);
        return ResponseEntity.ok(randomWord);
    }

}
