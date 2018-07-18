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


    /** Tag for the log messages */
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
    public static List<NewsApp> fetchEarthquakeData(String requestUrl) {



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
    private static List<NewsApp> extractFeatureFromJson(String earthquakeJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(earthquakeJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding newsApps to
        List<NewsApp> newsApps = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);
            Log.e("HERE", "START JSON: " + baseJsonResponse );
            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or newsApps).
            JSONObject secondJsonResponse = baseJsonResponse.getJSONObject("response");
            Log.e("HERE", "SECOND JSON: " + secondJsonResponse );

            JSONArray earthquakeArray = secondJsonResponse.getJSONArray("results");
            Log.e("HERE RESULTS", "Results: " + earthquakeArray );

            //JSONObject properties = currentEarthquake.getJSONObject("properties");

            //JSONObject currentEarthquake = earthquakeArray.getJSONObject(0);
            //String location = currentEarthquake.getString("webTitle");
            //Log.e("HERE WebTitle", "Results: " + location );


            // For each earthquake in the earthquakeArray, create an {@link NewsApp} object
           for (int i = 0; i < earthquakeArray.length(); i++) {

                // Get a single newsApp at position i within the list of newsApps
                JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);

                // For a given newsApp, extract the JSONObject associated with the
                // key called "properties", which represents a list of all properties
                // for that newsApp.
                //JSONObject properties = currentEarthquake.getJSONObject("properties");

                // Extract the value for the key called "mag"
                //double magnitude = properties.getDouble("mag");

                // Extract the value for the key called "place"
                String location = currentEarthquake.getString("webTitle");

                // Extract the value for the key called "time"
                //long time = properties.getLong("time");

                // Extract the value for the key called "url"
                String url = currentEarthquake.getString("webUrl");

                // Create a new {@link NewsApp} object with the magnitude, location, time,
                // and url from the JSON response.
                NewsApp newsApp = new NewsApp(location, url);

                // Add the new {@link NewsApp} to the list of newsApps.
                newsApps.add(newsApp);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
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
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
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
        return output.toString();
    }

    }
