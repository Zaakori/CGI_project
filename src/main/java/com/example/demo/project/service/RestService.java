package com.example.demo.project.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


 // this class was mostly taken from https://attacomsian.com/blog/http-requests-resttemplate-spring-boot
@Service
public class RestService {

    private final RestTemplate restTemplate;

    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getPostsPlainJSON(float latitude, float longitude, String theDate) {

        // used API is https://sunrise-sunset.org/api

        String baseUrl = "https://api.sunrise-sunset.org/json?";
        String lat = "lat=" + latitude;
        String lon = "&lng=" + longitude;
        String date = "&date=" + theDate;

        String finalUrl = baseUrl + lat + lon + date + "&formatted=0";

        return this.restTemplate.getForObject(finalUrl, String.class);
    }
}
