package com.aws.dto.gemini;

import lombok.Data;

@Data
public class UsageMetadata {
    private int promptTokenCount;
    private int candidatesTokenCount;
    private int totalTokenCount;
}
