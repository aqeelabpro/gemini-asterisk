package com.aws.dto;

import lombok.Data;

@Data
public class SpeechDto {
    private String text;
    private String fileName;
    private String languageCode;
    private String languageName;
}
