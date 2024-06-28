package com.google.dto.gemini;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RequestPayload {
    private List<Content> contents = new ArrayList<>();

}
