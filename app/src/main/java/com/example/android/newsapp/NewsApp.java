package com.example.android.newsapp;

public class NewsApp {

    /** Magnitude of the earthquake */
    private String mMagnitude;
    
    // Place of NewsApp
    private String mPlace;

    /** Time of the earthquake */
    private String mTimeInMilliseconds;

    // Place of NewsApp
    private String mUrl;

    

    /*
     * Create a new earthquake object.
     *
     * @param pave is place of earthquake
     * @param vNumber is the corresponding Android version number (e.g. 2.3-2.7)
     * @param image is drawable reference ID that corresponds to the Android version
     * */
    public NewsApp(String place, String url)
    {
        mPlace = place;
        //mTimeInMilliseconds = TimeInMilliseconds;
        //mMagnitude = magnitude;
        mUrl = url;
    }


    /**
     * Get Magnitude
     */
    public String getMagnitude() {
        return mMagnitude;
    }

    /**
     * Get the place of the earthquake
     */
    public String getPlace() {
        return mPlace;
    }

    /**
     /**
     * Returns the time of the earthquake.
     */
    public String getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    public String getUrl() {
        return mUrl;
    }


}
