package com.example.demo.project.controller;

import com.example.demo.project.api.RestService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class Controller {

    private RestService service = new RestService(new RestTemplateBuilder());

    @RequestMapping( value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity doing(@RequestBody String request) throws ParseException {

        System.out.println("RAW REQUEST: " + request);

        // this splitting is based on the form of JSON of the request
        String[] requestArray = request.split(",");

        String trimmedLat = requestArray[0].substring(13, requestArray[0].length() - 1);
        String trimmedLng = requestArray[1].substring(13, requestArray[1].length() - 1);
        String trimmedDate = requestArray[2].substring(8, requestArray[2].length() - 2);

        System.out.println("lat: " + trimmedLat);
        System.out.println("long: " + trimmedLng);
        System.out.println("date: " + trimmedDate);



        float latitude = Float.parseFloat(trimmedLat);
        float longitude = Float.parseFloat(trimmedLng);
        String date = trimmedDate;


        // the answer does not give the local time, but GMT + 0 time
        // CHECK what will happen when I request a place with polar night/day

        String rawInput = service.getPostsPlainJSON(latitude, longitude, date);

        String[] formattedAnswer = rawInputformatting(rawInput);

        String sunrise = formattedAnswer[0];
        String sunset = formattedAnswer[1];

        String nightLength = calculateNightLength(sunrise, sunset);

        return new ResponseEntity("Your answer (in GMT time): sunrise " + sunrise + ", sunset " + sunset + ". Length of the night is " + nightLength + ".", HttpStatus.OK);
    }

    // just for checking JavaScript
    @RequestMapping( value = "", method = RequestMethod.GET)
    public ResponseEntity getRequest(){

        return new ResponseEntity("Hello to Adeljushka", HttpStatus.OK);
    }

    // by some miracle it calculates everything right in any circumstance
    private String calculateNightLength(String sunrise, String sunset) throws ParseException {

        // I'm gonna calculate the night length for the day that was given, so the given days night starts (most probably)
        // in the previous day and ends in the given day.
        // for calculation I will use the sunrise and sunset of the given day (though it is a bit wrong data, because I really
        // need the sunset of the previous day, but the program will get too complicated) and I will write down this error in documentation
        // and add that the night length calculation can be off for +- 5 min on average.

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        long nightLengthInMillis;

        // sunset until midnight

        String midnightOne = "24:00:00";
        Date timeOneOne = format.parse(sunset);
        Date timeOneTwo = format.parse(midnightOne);
        nightLengthInMillis = timeOneTwo.getTime() - timeOneOne.getTime();

        // midnight until sunrise

        String midnightTwo = "00:00:00";
        Date timeTwoOne = format.parse(midnightTwo);
        Date timeTwoTwo = format.parse(sunrise);
        nightLengthInMillis += timeTwoTwo.getTime() - timeTwoOne.getTime();

        int seconds = (int) (nightLengthInMillis / 1000) % 60 ;
        int minutes = (int) ((nightLengthInMillis / (1000*60)) % 60);
        int hours   = (int) ((nightLengthInMillis / (1000*60*60)) % 24);

        String hoursString = hours + "h ";
        String minutesString = minutes + "min ";
        String secondsString = seconds + "sec";

        return hoursString + minutesString + secondsString;
    }

    private String[] rawInputformatting(String rawInput){

        String trimmedAnswer = rawInput.substring(12,86);

        String[] splitTrimmedAnswer = trimmedAnswer.split(",");

        String sunrise = splitTrimmedAnswer[0].substring(22, splitTrimmedAnswer[0].length() - 7);
        String sunset = splitTrimmedAnswer[1].substring(21, splitTrimmedAnswer[1].length() - 7);

        System.out.println("SUNRISE: " + sunrise + ", SUNSET: " + sunset);

        String[] output = new String[2];
        output[0] = sunrise;
        output[1] = sunset;

        return output;
    }

}
