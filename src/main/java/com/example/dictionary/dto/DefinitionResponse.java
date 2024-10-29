package com.example.dictionary.dto;

import com.example.dictionary.common.SourceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DefinitionResponse {

    private SourceType sourceType;
    private String definition;
    private String word;

}
