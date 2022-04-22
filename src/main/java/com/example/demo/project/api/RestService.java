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

    public String getPostsPlainJSON() {
        String url = "https://api.sunrise-sunset.org/json?lat=36.7201600&lng=-4.4203400&date=today";
        return this.restTemplate.getForObject(url, String.class);
    }

}
