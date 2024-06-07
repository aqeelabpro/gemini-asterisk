package com.aws.asterisk.agi;

import com.aws.dto.SpeechDto;
import com.aws.service.google.GoogleSpeechService;
import com.aws.service.google.gemini.GeminiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log4j2
@Component
@RequiredArgsConstructor
public class AgiLogic {
    private final GeminiService geminiService;
    private final GoogleSpeechService speechService;

    public void transcribeSpeechToText(AgiRequest request, AgiChannel channel) {
        try {

            String fileName = channel.getVariable("RECORDING_FILE_NAME");

            SpeechDto speechToText = new SpeechDto();

            speechToText.setFileName(fileName);

            String googleSpeechToTextResponse = speechService.transcribeSpeechToText(speechToText);

            String geminiResponse = geminiService.getGeminiResponse(googleSpeechToTextResponse);

            channel.setVariable("GEMINI_RESPONSE", geminiResponse);

            log.info("speechToText AGI completed");
        } catch (AgiException | IOException ex) {
            log.error("AgiLogic: speechToText(): Exception: {}", ex.getMessage());
        }
    }


    public void convertTextInToSpeech(AgiRequest request, AgiChannel channel) {
        try {
            SpeechDto textToSpeech = new SpeechDto();
            textToSpeech.setText(channel.getVariable("GEMINI_RESPONSE"));
            textToSpeech.setLanguageCode(channel.getVariable("LANG_CODE"));
            textToSpeech.setLanguageName(channel.getVariable("LANG_NAME"));

            String fileName = speechService.convertTextInToSpeech(textToSpeech);
            channel.setVariable("ANSWER_FILE", fileName);
            log.info("textToSpeech AGI completed");
        } catch (AgiException | IOException ex) {
            log.error("AgiLogic: textToSpeech(): Exception: {}", ex.getMessage());
        }
    }
}
