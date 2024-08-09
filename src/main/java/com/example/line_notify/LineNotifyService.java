package com.example.line_notify;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class LineNotifyService {

    @Value("${line.notify.token}")
    private String lineNotifyToken;

    private static final String LINE_NOTIFY_API = "https://notify-api.line.me/api/notify";

    public void sendNotification(String message) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + lineNotifyToken);
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);
        String body = "message=" + encodedMessage;
        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                LINE_NOTIFY_API,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        System.out.println("Response: " + responseEntity.getBody());
    }

    @Scheduled(fixedRate = 60000)  // Sends notification every 1 minute
    public void sendScheduledNotification() {
        sendNotification("晚上好！陳睿泰");
    }
}
