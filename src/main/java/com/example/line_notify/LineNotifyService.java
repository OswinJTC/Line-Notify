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
import java.util.Random;

@Service
public class LineNotifyService {

    @Value("${line.notify.token}")
    private String lineNotifyToken;
    private static final String LINE_NOTIFY_API = "https://notify-api.line.me/api/notify";

    int count = 1;
    int answer = 672;
    String temp_sentence = "";


    @Scheduled(fixedRate = 10000)  // Sends notification every 10 seconds
    public void sendStockPriceNotification() {
        Random random_generator  = new Random();
        int number = random_generator.nextInt(1000)+1;

        if(number == answer){
            temp_sentence = "陳睿泰大帥哥，這是目前第" + count + "次嘗試。你抽到的的號碼是 " + number + " 啦！！！恭喜中獎，你太帥了嗚嗚嗚～\uD83D\uDC4D\uD83D\uDC4D\uD83D\uDC4D";
        }else{
            temp_sentence = "陳睿泰帥哥，這是目前第" + count + "次嘗試。你抽到的的號碼是 " + number + "...";
        }

        System.out.println(temp_sentence);
        sendNotification(temp_sentence);

        count ++;

    }

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
}