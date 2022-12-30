package com.LearningEnglish.dto;

import com.LearningEnglish.model.Word;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResponseDto {

    private Word word;
    private List<Word> words;
    private String message;
    private boolean successful;
    private String newWord;
    private String text;
}
