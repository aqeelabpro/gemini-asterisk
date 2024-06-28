package com.google.service.google.gemini;

import com.google.dto.gemini.Content;
import com.google.dto.gemini.ContentPart;
import com.google.dto.gemini.RequestPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeminiService {
    private final RestTemplate restTemplate;

    @Value("${google.gemini.geminiApiUrl}")
    private String apiUrl;

    @Value("${google.gemini.geminiApiKey}")
    private String apiKey;

    public String getGeminiResponse(String text) {
        // Create request payload
        RequestPayload requestPayload = new RequestPayload();
        ContentPart contentPart = new ContentPart();
        contentPart.setText(text);
        Content content = new Content();
        content.getParts().add(contentPart);
        requestPayload.getContents().add(content);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Serialize payload to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson;
        try {
            requestJson = objectMapper.writeValueAsString(requestPayload);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        // Create HTTP entity
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        // Make the API call
        ResponseEntity<String> response = restTemplate.exchange(apiUrl + apiKey, HttpMethod.POST, entity, String.class);

        String geminiResponse = response.getBody();
        log.info("geminiResponse: {}", geminiResponse);

        // Parse the response and return the extracted text
        try {
            return parseJson(geminiResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String parseJson(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);

        // Extract "candidates" array
        JSONArray candidatesArray = jsonObject.getJSONArray("candidates");

        // Get the first candidate
        JSONObject firstCandidate = candidatesArray.getJSONObject(0);

        // Extract "content" object from the first candidate
        JSONObject content = firstCandidate.getJSONObject("content");

        // Extract "parts" array from the content
        JSONArray partsArray = content.getJSONArray("parts");

        // Get the text from the first part
        String text = partsArray.getJSONObject(0).getString("text");

        log.info("Extracted Text: {}", text);

        return text;
    }
}
