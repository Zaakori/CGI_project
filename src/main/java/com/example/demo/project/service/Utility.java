package com.example.demo.project.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {

    public String[] rawAnswerFormatting(String rawAnswer){

        String trimmedAnswer = rawAnswer.substring(12,86);

        String[] splitTrimmedAnswer = trimmedAnswer.split(",");

        String sunrise = splitTrimmedAnswer[0].substring(22, splitTrimmedAnswer[0].length() - 7);
        String sunset = splitTrimmedAnswer[1].substring(21, splitTrimmedAnswer[1].length() - 7);

        String[] output = new String[2];
        output[0] = sunrise;
        output[1] = sunset;

        return output;
    }

    public String calculateNightLength(String sunrise, String sunset) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        long nightLengthInMillis;

        // sunset until midnight

        String midnightOne = "24:00:00";
        Date sunsetDateObj = format.parse(sunset);
        Date midnightOneDateObj = format.parse(midnightOne);
        nightLengthInMillis = midnightOneDateObj.getTime() - sunsetDateObj.getTime();

        // midnight until sunrise

        String midnightTwo = "00:00:00";
        Date midnightTwoDateObj = format.parse(midnightTwo);
        Date sunriseDateObj = format.parse(sunrise);
        nightLengthInMillis += sunriseDateObj.getTime() - midnightTwoDateObj.getTime();

        int seconds = (int) (nightLengthInMillis / 1000) % 60 ;
        int minutes = (int) ((nightLengthInMillis / (1000*60)) % 60);
        int hours   = (int) ((nightLengthInMillis / (1000*60*60)) % 24);

        String hoursString = hours + " tundi ";
        String minutesString = minutes + " minutit ";
        String secondsString = seconds + " sekundit";

        return hoursString + minutesString + secondsString;
    }
}
