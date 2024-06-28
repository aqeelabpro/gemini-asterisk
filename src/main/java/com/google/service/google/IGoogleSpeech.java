package com.google.service.google;

import com.google.dto.SpeechDto;

import java.io.IOException;

public interface IGoogleSpeech {
    String convertTextInToSpeech(SpeechDto speech) throws IOException;

    String transcribeSpeechToText(SpeechDto speech) throws IOException;
}
