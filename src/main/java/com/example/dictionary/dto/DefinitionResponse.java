package com.example.dictionary.dto;

import com.example.dictionary.common.SourceType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DefinitionResponse {

    private SourceType sourceType;
    private String definition;
    private String word;
    private String hostName;

}
