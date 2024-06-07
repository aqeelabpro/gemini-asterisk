package com.aws.service.ffmpeg;

import com.aws.properties.FfmpegProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class FfmpegService {

    private final FfmpegProperties ffmpegProperties;

    @Autowired
    public FfmpegService(FfmpegProperties ffmpegProperties) {
        this.ffmpegProperties = ffmpegProperties;
    }

    public String convertMp3ToWav(String filePath, String sampleRate) throws IOException, InterruptedException {
        File mp3File = new File(filePath);
        if (!mp3File.exists()) {
            throw new IOException("MP3 file not found: " + filePath);
        }

        String wavFilePath = filePath.replace(".mp3", ".wav");


        String[] command = {
                ffmpegProperties.getPath(),
                "-i", filePath,
                "-ar", sampleRate,
                wavFilePath
        };

        // Execute the command
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true); // Merge standard error with standard output
        Process process = processBuilder.start();

        // Wait for the process to complete
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("FFmpeg conversion failed with exit code " + exitCode);
        }

        return wavFilePath;
    }
}
