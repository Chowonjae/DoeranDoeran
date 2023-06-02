package com.purple.hello.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RoomCode {
    @Value("${firebase.api_key}")
    private String FIREBASE_API_KEY;

    public String makeUrl(long roomId) throws JsonProcessingException {
        String url = "https://doeran.kr/room/" + roomId;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String FIREBASE_DYNAMIC_LINKS_API = "https://firebasedynamiclinks.googleapis.com/v1/shortLinks?key=";

        String requestBody = "{ " +
                "\"dynamicLinkInfo\": { " +
                "\"domainUriPrefix\": \"https://doeran2.page.link\", " +
                "\"link\": \"" + url + "\", " +
                "\"androidInfo\": { " +
                "\"androidPackageName\": \"com.purple.hello\" " +
                "}, " +
                "\"iosInfo\": { " +
                "\"iosBundleId\": \"com.example.ios\" " +
                "} " +
                "} " +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(FIREBASE_DYNAMIC_LINKS_API + FIREBASE_API_KEY, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to create short link");
        }
    }
}
