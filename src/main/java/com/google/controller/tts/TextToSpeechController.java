package com.aws.controller.tts;

import com.aws.service.ffmpeg.FfmpegService;
import com.aws.service.tts.TextToSpeechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
public class TextToSpeechController {

    private final TextToSpeechService textToSpeechService;
    private final FfmpegService ffmpegService;

    @Autowired
    public TextToSpeechController(TextToSpeechService textToSpeechService, FfmpegService ffmpegService) {
        this.textToSpeechService = textToSpeechService;
        this.ffmpegService = ffmpegService;
    }

    @PostMapping("/text-to-speech")
    public ResponseEntity<String> textToSpeech(@RequestParam("text") String text, @RequestParam("sampleRate") String sampleRate) {
        try {
            String filePath = textToSpeechService.synthesize(text);
            String wavFilePath = ffmpegService.convertMp3ToWav(filePath, sampleRate);
            return ResponseEntity.ok(wavFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }
}
