package com.aws.controller.stt;

import com.aws.dto.SpeechDto;
import com.aws.service.google.GoogleSpeechService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class GoogleSpeechToTextController {
    private final GoogleSpeechService googleSpeechService;

    @PostMapping("/google-speech-to-text")
    public String speechToText(@RequestBody SpeechDto speechDto) throws IOException {
        return googleSpeechService.transcribeSpeechToText(speechDto);
    }
}
