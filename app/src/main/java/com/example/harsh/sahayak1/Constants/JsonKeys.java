package com.example.harsh.sahayak1.Constants;

/**
 * Created by harsh on 18-01-2017.
 */

public class JsonKeys {
    public static final  String JsonURL = "http://api.railwayapi.com/v2/";
    public static final  String apikey = "/apikey/mghg44ea29/";

    //Live Train Status API
    public static final String LIVE_TRAIN_URL = JsonURL+"live/train/";
    //Station Suggested API
    public static final String sugStation = JsonURL+"suggest_station/name/";

    //Fare Enquiry API
    public static final String FARE_URL = JsonURL+"fare/train/";

    //Search Train Enquiry
    public static final String SEARCH_URL = JsonURL+"between/source/";

    //Trains Arrival At Station
    public static final String ARRIVAL_URL = JsonURL+"arrivals/station/";

    //Pnr No API
    public static final String PNR_URL = JsonURL+"pnr_status/pnr/";
}
