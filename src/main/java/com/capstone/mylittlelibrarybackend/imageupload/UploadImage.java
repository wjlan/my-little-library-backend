package com.capstone.mylittlelibrarybackend.imageupload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class UploadImage {

    @Value("${imgur.client.id}")
    private String imgurClientId;

    private final RestTemplate restTemplate = new RestTemplate();

    public String uploadImage(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            throw new IllegalArgumentException("No file uploaded");
        }

        // Convert image to Base64
        String imageBase64 = Base64.getEncoder().encodeToString(multipartFile.getBytes());

        // Imgur API URL for image upload
        String url = "https://api.imgur.com/3/image";

        // Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Client-ID " + imgurClientId);
        headers.set("Content-Type", "application/json");

        // Set up request body
        Map<String, String> body = new HashMap<>();
        body.put("image", imageBase64);
        body.put("type", "base64");

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(body, headers);

        // Make the request
        ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);

        // Handle response
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = responseEntity.getBody();
            if (responseBody != null && responseBody.containsKey("data")) {
                Map<String, String> data = (Map<String, String>) responseBody.get("data");
                return data.get("link"); // URL of the uploaded image
            }
        }

        throw new IOException("Failed to upload image to Imgur");
    }
}
