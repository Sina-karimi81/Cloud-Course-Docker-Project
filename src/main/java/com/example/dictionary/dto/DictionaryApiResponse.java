package com.example.dictionary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DictionaryApiResponse implements Serializable {

    private boolean valid;
    private String word;
    private String definition;

}
