package com.example.android.newsapp;

public class NewsApp {


    // Section article appears in
    private String mSection;

    // Title of Article
    private String mTitle;

    // Url of article
    private String mUrl;

    // Date of article
    private String mDate;

    /*
     * Create a new earthquake object.
     *
     * @param pave is place of earthquake
     * @param vNumber is the corresponding Android version number (e.g. 2.3-2.7)
     * @param image is drawable reference ID that corresponds to the Android version
     * */
    public NewsApp(String section, String title, String url, String date)
    {
        mSection = section;

        mTitle = title;

        mUrl = url;

        mDate = date;
    }



    //returns section article appears in
    public String getSection() {
        return mSection;
    }

    //returns title of  article
    public String getTitle() {
        return mTitle;
    }

    //returns article's url
    public String getUrl()
    {
        return mUrl;
    }

    //returns article's url
    public String getDate()
    {
        return mDate;
    }

}
