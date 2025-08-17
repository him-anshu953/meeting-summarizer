package com.mangodesk.sumarizer.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class SummarizeController {

    @Value("${groq.api.key}")
    private String groqApiKey;

    @Value("${groq.model}")
    private String model;

    @PostMapping("/summarize")
    public ResponseEntity<Map<String, String>> summarize(@RequestBody Map<String, String> body) {
        try {
            String transcriptText = body.get("transcriptText");
            String prompt = body.get("prompt");

            if (transcriptText == null || prompt == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Missing transcript or prompt"));
            }

            HttpClient client = HttpClient.newHttpClient();

            // build request JSON
            String reqJson = "{ \"model\": \"" + model + "\", " +
                    "\"messages\": [" +
                    "{\"role\":\"system\",\"content\":\"You are an expert meeting notes summarizer.\"}," +
                    "{\"role\":\"user\",\"content\":\"PROMPT: " + prompt + "\\nTRANSCRIPT:\\n" + transcriptText + "\"}" +
                    "] }";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.groq.com/openai/v1/chat/completions"))
                    .header("Authorization", "Bearer " + groqApiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(reqJson))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // log raw Groq response
            String responseBody = response.body();
            System.out.println("Groq Response: " + responseBody);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(responseBody);

            JsonNode choices = json.path("choices");
            if (choices.isMissingNode() || !choices.isArray() || choices.size() == 0) {
                return ResponseEntity.status(500).body(Map.of(
                        "error", "Groq response invalid",
                        "raw", responseBody));
            }

            // extract assistant message
            JsonNode message = choices.get(0).path("message");
            String summary = message.path("content").asText();

            if (summary == null || summary.isBlank()) {
                return ResponseEntity.status(500).body(Map.of(
                        "error", "Empty summary",
                        "raw", responseBody));
            }

            return ResponseEntity.ok(Map.of("summary", summary));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}
