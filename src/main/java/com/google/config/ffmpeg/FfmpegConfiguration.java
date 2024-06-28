package com.google.config.ffmpeg;

import com.google.properties.FfmpegProperties;
import com.google.service.ffmpeg.FfmpegService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FfmpegConfiguration {

    @Bean
    public FfmpegService ffmpegService(FfmpegProperties ffmpegProperties) {
        return new FfmpegService(ffmpegProperties);
    }
}

