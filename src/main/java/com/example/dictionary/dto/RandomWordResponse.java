package com.example.dictionary.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RandomWordResponse {

    private String word;
    private String hostName;

}
