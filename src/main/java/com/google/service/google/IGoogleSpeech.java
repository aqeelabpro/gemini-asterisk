package com.aws.service.google;

import com.aws.dto.SpeechDto;

import java.io.IOException;

public interface IGoogleSpeech {
    String convertTextInToSpeech(SpeechDto speech) throws IOException;

    String transcribeSpeechToText(SpeechDto speech) throws IOException;
}
