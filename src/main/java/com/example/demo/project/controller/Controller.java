package com.example.demo.project.controller;

import com.example.demo.project.service.Utility;
import com.example.demo.project.service.RestService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@CrossOrigin
public class Controller {

    private final RestService service = new RestService(new RestTemplateBuilder());
    private final Utility utility = new Utility();

    @RequestMapping( method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<String> provideToClient(@RequestBody String request) throws ParseException {

        String[] requestArray = request.split(",");

        String trimmedLat = requestArray[0].substring(13, requestArray[0].length() - 1);
        String trimmedLong = requestArray[1].substring(13, requestArray[1].length() - 1);
        String date = requestArray[2].substring(8, requestArray[2].length() - 2);

        float latitude = Float.parseFloat(trimmedLat);
        float longitude = Float.parseFloat(trimmedLong);

        String rawAnswer = service.getPostsPlainJSON(latitude, longitude, date);

        String[] formattedAnswer = utility.rawAnswerFormatting(rawAnswer);

        String sunrise = formattedAnswer[0];
        String sunset = formattedAnswer[1];
        String nightLength = utility.calculateNightLength(sunrise, sunset);

        return new ResponseEntity<>("Sinu vastus (GMT + 0 ajas): päikesetõus " + sunrise + ", päikeseloojang " + sunset + ". " +
                "Öö pikkus " + nightLength + ".", HttpStatus.OK);
    }
}
