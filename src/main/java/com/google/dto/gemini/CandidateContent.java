package com.aws.dto.gemini;

import lombok.Data;

import java.util.List;

@Data
public class CandidateContent {
    private List<ContentPart> parts;
    private String role;
}
