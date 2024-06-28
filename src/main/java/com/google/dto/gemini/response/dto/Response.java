package com.google.dto.gemini.response.dto;

import com.google.dto.gemini.Candidate;
import com.google.dto.gemini.UsageMetadata;
import lombok.Data;

import java.util.List;

@Data
public class Response {
    private List<Candidate> candidates;
    private UsageMetadata usageMetadata;
}
