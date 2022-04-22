package com.example.demo.project.controller;

import com.example.demo.project.api.RestService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class Controller {

    private RestService service = new RestService(new RestTemplateBuilder());

    @RequestMapping( value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE )
    public String doing(@RequestBody String request){

        String[] requestArray = request.split(" ");

        System.out.println(requestArray[2]);
        System.out.println(requestArray[4]);
        System.out.println(requestArray[6]);

        String trimmedLat = requestArray[2].substring(1, requestArray[2].length() - 4);
        String trimmedLng = requestArray[4].substring(1, requestArray[4].length() - 4);
        String trimmedDate = requestArray[6].substring(1, requestArray[6].length() - 4);

        System.out.println(trimmedDate);

        float latitude = Float.parseFloat(trimmedLat);
        float longitude = Float.parseFloat(trimmedLng);
        String date = trimmedDate;


        // the answer does not give the local time, but GMT + 0 time

        String answer = service.getPostsPlainJSON(latitude, longitude, date);

        return "Your answer: " + answer.substring(11,56) + "}";

    }
}
