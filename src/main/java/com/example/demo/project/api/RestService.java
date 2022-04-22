package com.example.demo.project.api;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


 // https://attacomsian.com/blog/http-requests-resttemplate-spring-boot
@Service
public class RestService {

    private final RestTemplate restTemplate;

    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getPostsPlainJSON(float latitude, float longitude, String theDate) {

        // example url https://api.sunrise-sunset.org/json?lat=36.7201600&lng=-4.4203400&date=2022-04-15

        String baseUrl = "https://api.sunrise-sunset.org/json?";
        String lat = "lat=" + latitude;
        String lon = "&lng=" + longitude;
        String date = "&date=" + theDate;

        String finalUrl = baseUrl + lat + lon + date;

        return this.restTemplate.getForObject(finalUrl, String.class);
    }

}
