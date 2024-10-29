package com.example.dictionary.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DictionaryApiResponse implements Serializable {

    private boolean valid;
    private String word;
    private String definition;

}
