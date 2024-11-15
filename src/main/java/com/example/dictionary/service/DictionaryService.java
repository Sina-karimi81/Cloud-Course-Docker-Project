package com.example.dictionary.service;

import com.example.dictionary.common.SourceType;
import com.example.dictionary.dto.DefinitionResponse;
import com.example.dictionary.dto.DictionaryApiResponse;
import com.example.dictionary.dto.RandomWordApiResponse;
import com.example.dictionary.dto.RandomWordResponse;
import com.example.dictionary.entity.DefinitionEntity;
import com.example.dictionary.repository.RedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
public class DictionaryService {

    @Value("${api.ninja.api-key}")
    private String apiKey;
    @Value("${dictionary.api}")
    private String dictionaryApiUrl;
    @Value("${random-word.api}")
    private String randomWordApiUrl;

    private final RedisRepository repository;
    private final RestTemplate restTemplate;
    private final Environment environment;

    public DictionaryService(RedisRepository repository, Environment environment) {
        this.repository = repository;
        this.environment = environment;
        this.restTemplate = new RestTemplate();
    }

    public DefinitionResponse getDefinition(String word) {
        log.info("started to get the definition for {}", word);
        try {
            if (repository.existsById(word)) {
                log.info("the word definition already exists in the cache");
                Optional<DefinitionEntity> wordDefinition = repository.findById(word);
                return wordDefinition.map(
                        definitionEntity -> new DefinitionResponse(SourceType.REDIS,
                                definitionEntity.getDefinition(),
                                definitionEntity.getWord(),
                                environment.getProperty("HOSTNAME")))
                        .orElse(null);
            } else {
                log.info("the word definition does not exist in the cache, fetching from the API");
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.set("X-Api-Key", apiKey);

                HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

                String formattedUrl = String.format(dictionaryApiUrl + "?word=%s", word);

                ResponseEntity<DictionaryApiResponse> response = restTemplate.exchange(formattedUrl, HttpMethod.GET, requestEntity, DictionaryApiResponse.class);
                log.info("got a response of {} from the API for word definition", response.getStatusCode());
                DictionaryApiResponse body = response.getBody();
                if (body != null) {
                    saveToRedisCache(body);
                    return new DefinitionResponse(SourceType.API, body.getDefinition(), body.getWord(), environment.getProperty("HOSTNAME"));
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            log.error("error occurred while trying to fetch the given word's definition", e);
            throw new RuntimeException(e);
        }
    }

    private void saveToRedisCache(DictionaryApiResponse apiResponse) {
        log.info("saving the {} word to redis cache", apiResponse.getWord());
        DefinitionEntity definitionEntity = new DefinitionEntity(apiResponse.getWord(), apiResponse.getDefinition(), apiResponse.isValid());
        repository.save(definitionEntity);
        log.info("finished saving to cache");
    }

    public RandomWordResponse getRandomWord() {
        try {
            log.info("getting a random word from the API");
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Api-Key", apiKey);

            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<RandomWordApiResponse> response = restTemplate.exchange(randomWordApiUrl, HttpMethod.GET, requestEntity, RandomWordApiResponse.class);
            log.info("got a response of {} from the API for random word", response.getStatusCode());
            RandomWordApiResponse body = response.getBody();

            if (body != null && body.getWord() != null) {
                return new RandomWordResponse(body.getWord().get(0), environment.getProperty("HOSTNAME"));
            } else {
                return new RandomWordResponse("the API returned nothing!!!", environment.getProperty("HOSTNAME"));
            }
        } catch (Exception e) {
            log.error("error occurred while trying to fetch the given word's definition", e);
            throw new RuntimeException(e);
        }
    }
}
