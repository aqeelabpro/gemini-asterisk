package com.aws.controller.tts;

import com.aws.dto.SpeechDto;
import com.aws.service.google.GoogleSpeechService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class GoogleTextToSpeechController {
    private final GoogleSpeechService googleSpeechService;

    @PostMapping("/google-text-to-speech")
    public String speechToText(@RequestBody SpeechDto speechDto) throws IOException {
        return googleSpeechService.convertTextInToSpeech(speechDto);
    }
}
