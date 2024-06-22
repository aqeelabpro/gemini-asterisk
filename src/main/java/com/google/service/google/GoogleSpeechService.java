package com.aws.service.google;

import com.aws.dto.SpeechDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.speech.v1.*;
import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service
public class GoogleSpeechService implements IGoogleSpeech {
    @Value("${audio.path}")
    private String AUDIO_FILE_PATH;

    @Value("${google.credentials.file.path}")
    private String GOOGLE_CREDENTIALS_FILE_PATH;

    private static final int SAMPLE_RATE_HZ = 8000;
    private static final String VOICE_FILE_EXTENSION = ".wav";

    @Override
    public String convertTextInToSpeech(SpeechDto googleSpeech) throws IOException {
        log.info("convertTextInToSpeech: {}", new ObjectMapper().writeValueAsString(googleSpeech));
        String fileName = String.valueOf(System.currentTimeMillis());
        String audioFileName = AUDIO_FILE_PATH + "/" + fileName + VOICE_FILE_EXTENSION;
        log.info("generating file: {}", audioFileName);

        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(GOOGLE_CREDENTIALS_FILE_PATH));
        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create(
                TextToSpeechSettings.newBuilder().setCredentialsProvider(() -> credentials).build())) {
            SynthesisInput input = SynthesisInput.newBuilder().setText(googleSpeech.getText().replaceAll("[^a-zA-Z0-9._-]","")).build();
            VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
                    .setSsmlGender(SsmlVoiceGender.FEMALE)
                    .setName(googleSpeech.getLanguageName())
                    .setLanguageCode(googleSpeech.getLanguageCode())
                    .build();
            AudioConfig audioConfig = AudioConfig.newBuilder()
                    .setSampleRateHertz(SAMPLE_RATE_HZ)
                    .setAudioEncoding(AudioEncoding.LINEAR16)
                    .build();
            SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);
            com.google.protobuf.ByteString audioContents = response.getAudioContent();

            try (OutputStream out = new FileOutputStream(audioFileName)) {
                out.write(audioContents.toByteArray());
                log.info("GoogleSpeechService: convertTextInToSpeech(): fileName: {} generated.", audioFileName);
                return fileName;
            } catch (IOException ex) {
                log.error("GoogleSpeechService: convertTextInToSpeech(): IOException: {}", ex.getMessage());
            }
        } catch (IOException ex) {
            log.error("GoogleSpeechService: convertTextInToSpeech(): IOException: {}", ex.getMessage());
        }
        return null;
    }

    @Override
    public String transcribeSpeechToText(SpeechDto googleSpeech) throws IOException {
        log.info("transcribeSpeechToText: {}", new ObjectMapper().writeValueAsString(googleSpeech));

        String audioFileName = AUDIO_FILE_PATH + "/" + googleSpeech.getFileName() + VOICE_FILE_EXTENSION;

        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(GOOGLE_CREDENTIALS_FILE_PATH));
        try (SpeechClient speech = SpeechClient.create(
                SpeechSettings.newBuilder().setCredentialsProvider(() -> credentials).build())) {
            Path path = Paths.get(audioFileName);
            byte[] data = Files.readAllBytes(path);
            ByteString audioBytes = ByteString.copyFrom(data);

            RecognitionConfig config = RecognitionConfig.newBuilder()
                    .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                    .setLanguageCode("en-US")
                    .setSampleRateHertz(SAMPLE_RATE_HZ)
                    .build();
            RecognitionAudio audio = RecognitionAudio.newBuilder().setContent(audioBytes).build();

            RecognizeResponse response = speech.recognize(config, audio);
            List<SpeechRecognitionResult> results = response.getResultsList();

            for (SpeechRecognitionResult result : results) {
                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                log.info("GoogleSpeechService: convertTextInToSpeech(): audioFile transcribed into the text: {}", alternative.getTranscript());
                return alternative.getTranscript();
            }
        } catch (IOException ex) {
            log.error("GoogleSpeechService: convertTextInToSpeech(): IOException: {}", ex);
        }
        return null;
    }

    private String generateAudioFile(String text) throws IOException {
        String fileName = null;
        if (StringUtils.hasText(text)) {
            SpeechDto textToSpeech = new SpeechDto();
            textToSpeech.setText(text);
            fileName = convertTextInToSpeech(textToSpeech);
        }
        return fileName;
    }

}

