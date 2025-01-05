package com.google.dto.gemini;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Content {
    private List<ContentPart> parts;
    public Content() {
        this.parts = new ArrayList<>();
    }
}

