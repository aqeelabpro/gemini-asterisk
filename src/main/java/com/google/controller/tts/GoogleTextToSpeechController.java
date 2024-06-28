package com.google.controller.tts;

import com.google.dto.SpeechDto;
import com.google.service.google.GoogleSpeechService;
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
