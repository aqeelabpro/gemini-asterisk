package com.aws.controller.google;

import com.aws.service.google.gemini.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class GeminiController {

    private final GeminiService geminiService;

    @PostMapping("/gemini-response")
    public String getGeminiResponse(@RequestBody String text) {
        return geminiService.getGeminiResponse(text);
    }
}