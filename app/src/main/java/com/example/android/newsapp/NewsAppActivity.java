/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.widget.TextView;

public class NewsAppActivity extends AppCompatActivity implements LoaderCallbacks<List<NewsApp>> {


    private static final String LOG_TAG = NewsAppActivity.class.getName();

    /**
     * URL for earthquake data from the USGS dataset
     */
    private static final String USGS_REQUEST_URL =
            "http://content.guardianapis.com/search?q=debates&api-key=0c3390e8-f337-4ad6-a644-2221603f91c6";


    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int NEWSAPP_LOADER_ID = 1;

    /**
     * Adapter for the list of earthquakes
     */
    private NewsAppAdapter mAdapter;

    private TextView mEmptyStateTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsapp_activity);


        // Find a reference to the {@link ListView} in the layout
        ListView NewsAppListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new NewsAppAdapter(this, new ArrayList<NewsApp>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        NewsAppListView.setAdapter(mAdapter);


        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        NewsAppListView.setEmptyView(mEmptyStateTextView);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected earthquake.
        NewsAppListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                NewsApp currentNewsApp = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri NewsAppUri = Uri.parse(currentNewsApp.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, NewsAppUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }

        });

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWSAPP_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }


    @Override
    public Loader<List<NewsApp>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        Log.e("LOAD", "Creating");
        return new NewsAppLoader(this, USGS_REQUEST_URL);

    }

    @Override
    public void onLoadFinished(Loader<List<NewsApp>> loader, List<NewsApp> newsApps) {
        // Clear the adapter of previous earthquake data
        Log.e("LOAD", "Finished");
        mAdapter.clear();

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        mEmptyStateTextView.setText(R.string.no_newsapps);
        // If there is a valid list of {@link NewsApp}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (newsApps != null && !newsApps.isEmpty()) {
            mAdapter.addAll(newsApps);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsApp>> loader) {
        // Loader reset, so we can clear out our existing data.
        Log.e("LOAD", "Reset");
        mAdapter.clear();
    }


}