package com.aws.config.ffmpeg;

import com.aws.properties.FfmpegProperties;
import com.aws.service.ffmpeg.FfmpegService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FfmpegConfiguration {

    @Bean
    public FfmpegService ffmpegService(FfmpegProperties ffmpegProperties) {
        return new FfmpegService(ffmpegProperties);
    }
}

