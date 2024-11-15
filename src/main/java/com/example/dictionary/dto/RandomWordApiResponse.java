package com.example.dictionary.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RandomWordApiResponse implements Serializable {
    private List<String> word;
}
