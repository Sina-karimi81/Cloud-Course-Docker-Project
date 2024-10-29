package com.example.dictionary.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Definition")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DefinitionEntity implements Serializable {

    @Id
    private String word;
    private String definition;
    private boolean valid;

}
