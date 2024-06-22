package com.aws.controller.stt;

import com.aws.service.stt.SpeechToTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/v1")
public class SpeechToTextController {

    private final SpeechToTextService speechToTextService;

    @Autowired
    public SpeechToTextController(SpeechToTextService speechToTextService) {
        this.speechToTextService = speechToTextService;
    }

    @PostMapping("/speech-to-text")
    public String speechToText(@RequestParam("file") MultipartFile file) {
        String transcription = speechToTextService.convertSpeechToText(file);
        return transcription != null ? transcription : "Transcription failed";
    }

//    @PostMapping("/speech-to-text-with-file-path")
//    public String speechToText(@RequestBody String filePath) {
//        String transcription = speechToTextService.convertSpeechToText(filePath);
//        return transcription != null ? transcription : "Transcription failed";
//    }
}
