package com.google.service.tts;

import com.amazonaws.services.polly.AmazonPolly;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;
import com.amazonaws.util.IOUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class TextToSpeechService {

    private final AmazonPolly polly;


    public TextToSpeechService(AmazonPolly amazonPolly) {
        this.polly = amazonPolly;
    }

    public String synthesize(String text) throws IOException {
        SynthesizeSpeechRequest synthesizeSpeechRequest = new SynthesizeSpeechRequest()
                .withText(text)
                .withVoiceId("Joanna") // You can choose any available voice
                .withOutputFormat(OutputFormat.Mp3);

        SynthesizeSpeechResult synthesizeSpeechResult = polly.synthesizeSpeech(synthesizeSpeechRequest);
        InputStream audioStream = synthesizeSpeechResult.getAudioStream();

        // Read the audio stream into a byte array
        byte[] audioBytes = IOUtils.toByteArray(audioStream);

        // Save the MP3 bytes to a file
        Path path = Paths.get("output.mp3");
        Files.write(path, audioBytes);

        return path.toAbsolutePath().toString();
    }
}
