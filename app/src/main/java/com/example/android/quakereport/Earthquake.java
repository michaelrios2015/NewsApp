package com.example.android.quakereport;

public class Earthquake {

    /** Magnitude of the earthquake */
    private double mMagnitude;
    
    // Place of Earthquake
    private String mPlace;

    /** Time of the earthquake */
    private long mTimeInMilliseconds;

    // Place of Earthquake
    private String mUrl;

    

    /*
     * Create a new earthquake object.
     *
     * @param pave is place of earthquake
     * @param vNumber is the corresponding Android version number (e.g. 2.3-2.7)
     * @param image is drawable reference ID that corresponds to the Android version
     * */
    public Earthquake(double magnitude, String place, long TimeInMilliseconds, String url)
    {
        mPlace = place;
        mTimeInMilliseconds = TimeInMilliseconds;
        mMagnitude = magnitude;
        mUrl = url;
    }


    /**
     * Get Magnitude
     */
    public double getMagnitude() {
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
    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    public String getUrl() {
        return mUrl;
    }


}
