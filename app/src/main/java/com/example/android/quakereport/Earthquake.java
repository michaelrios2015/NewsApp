package com.example.android.quakereport;

public class Earthquake {

    // Magnitude
    private String mMagnitude;
    
    // Place of Earthquake
    private String mPlace;

    // date of earthquake
    private String mDate;

    

    /*
     * Create a new earthquake object.
     *
     * @param pave is place of earthquake
     * @param vNumber is the corresponding Android version number (e.g. 2.3-2.7)
     * @param image is drawable reference ID that corresponds to the Android version
     * */
    public Earthquake(String magnitude, String place, String date)
    {
        mPlace = place;
        mDate = date;
        mMagnitude = magnitude;
    }

    /**
     * Get the place of the earthquake
     */
    public String getPlace() {
        return mPlace;
    }

    /**
     * Get the date of the earthquake
     */
    public String getDate() {
        return mDate;
    }

    /**
     * Get the image resource ID
     */
    public String getMagnitude() {
        return mMagnitude;
    }


}
