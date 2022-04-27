package com.example.demo.project.service;

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

        // used API is https://sunrise-sunset.org/api, on the website is said ATTRIBUTION IS REQUIRED, so check that out
        // example url https://api.sunrise-sunset.org/json?lat=36.7201600&lng=-4.4203400&date=2022-04-15

        String baseUrl = "https://api.sunrise-sunset.org/json?";
        String lat = "lat=" + latitude;
        String lon = "&lng=" + longitude;
        String date = "&date=" + theDate;

        String finalUrl = baseUrl + lat + lon + date + "&formatted=0";

        return this.restTemplate.getForObject(finalUrl, String.class);
    }
}
