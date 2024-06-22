package com.aws.dto.gemini;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Candidate {
    private CandidateContent content;
    private String finishReason;
    private int index;
    private List<SafetyRating> safetyRatings;

}
