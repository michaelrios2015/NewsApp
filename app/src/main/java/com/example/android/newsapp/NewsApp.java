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

    // Author of the article
    private String mAuthor;

    /*
     * Create a new NewsApp object.
     *
     * @param section is section of newspaper with article
     * @param title is the Title of the article
     * @param url is the url of the article
     * @param author is the author of the article
     * */
    public NewsApp(String section, String title, String url, String date, String author) {
        mSection = section;

        mTitle = title;

        mUrl = url;

        mDate = date;

        mAuthor = author;
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
    public String getUrl() {
        return mUrl;
    }

    //returns article's publication date
    public String getDate() {
        return mDate;
    }

    //returns article's author
    public String getAuthor() {
        return mAuthor;
    }

}
