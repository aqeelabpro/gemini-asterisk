package com.aws.dto.gemini.response.dto;

import com.aws.dto.gemini.Candidate;
import com.aws.dto.gemini.UsageMetadata;
import lombok.Data;

import java.util.List;

@Data
public class Response {
    private List<Candidate> candidates;
    private UsageMetadata usageMetadata;
}
