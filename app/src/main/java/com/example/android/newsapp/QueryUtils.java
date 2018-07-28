package com.example.android.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {


    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();


    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the USGS dataset and return a list of {@link NewsApp} objects.
     */
    public static List<NewsApp> fetchNewsAppData(String requestUrl) {

        Log.e("LOAD", "fetch");
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link NewsApp}s
        List<NewsApp> newsApps = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link NewsApp}s
        return newsApps;
    }

    /**
     * Return a list of {@link NewsApp} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<NewsApp> extractFeatureFromJson(String newsAppJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(newsAppJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding newsApps to
        List<NewsApp> newsApps = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(newsAppJSON);
            Log.e("HERE", "START JSON: " + baseJsonResponse);
            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or newsApps).
            JSONObject secondJsonResponse = baseJsonResponse.getJSONObject("response");
            Log.e("HERE", "SECOND JSON: " + secondJsonResponse);

            JSONArray newsAppArray = secondJsonResponse.getJSONArray("results");
            Log.e("HERE RESULTS", "Results: " + newsAppArray);

            // For each NewsApp in the NewsAppArray, create an {@link NewsApp} object
            for (int i = 0; i < newsAppArray.length(); i++) {

                Log.e("HERE ", "For Loop newsAppArray length = " + newsAppArray.length());
                // Get a single newsApp at position i within the list of newsApps
                JSONObject currentNewsApp = newsAppArray.getJSONObject(i);
                Log.e("HERE ", "currentNewsApp = " + currentNewsApp);

                //Get tags array
                JSONArray currentNewsAppJSONArray = currentNewsApp.getJSONArray("tags");
                Log.e("HERE TAGS", "Results: " + currentNewsAppJSONArray);

                String author;

                if (currentNewsAppJSONArray.length() == 0){

                    author = "unknown";
                    Log.e("HERE TAGS", "NO AUTHOR ");

                }else{
                    JSONObject authorObject = currentNewsAppJSONArray.getJSONObject(0);
                    Log.e("HERE AUTHOR", "No Author" );

                    author = authorObject.getString("webTitle");
                    Log.e("HERE Author", "Results: " + author);
                }







                //String section = "section";
                String section = currentNewsApp.getString("sectionName");
                Log.e("HERE Section", "Results: " + section);

                // Extract the value for the key called "webTitle"
                String title = currentNewsApp.getString("webTitle");

                // Extract the value for the key called "webUrl"
                String url = currentNewsApp.getString("webUrl");

                String date = currentNewsApp.getString("webPublicationDate");
                Log.e("HERE DATE", "Results: " + date);


                // Create a new {@link NewsApp} object with the section, title, date, author
                // and url from the JSON response.
                NewsApp newsApp = new NewsApp(section, title, url, date, author);

                // Add the new {@link NewsApp} to the list of newsApps.
                newsApps.add(newsApp);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the guardian JSON results", e);
        }

        // Return the list of newsApps
        return newsApps;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the gaurdian JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        Log.e("here", "STRING BUILDER" + output.toString());
        return output.toString();
    }

}
