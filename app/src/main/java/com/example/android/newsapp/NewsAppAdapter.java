package com.example.android.newsapp;


import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/*
 * {@link NewsAppAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
 * based on a data source, which is a list of {@link NewsApp} objects.
 * */


public class NewsAppAdapter extends ArrayAdapter<NewsApp> {

    private static final String LOG_TAG = NewsAppAdapter.class.getSimpleName();

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context  The current context. Used to inflate the layout file.
     * @param newsApps A List of AndroidFlavor objects to display in a list
     */
    public NewsAppAdapter(Activity context, ArrayList<NewsApp> newsApps) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, newsApps);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The position in the list of data that should be displayed in the
     *                    list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.listview_newsapp, parent, false);
        }

        // Get the {@link NewsApp} object located at this position in the list
        NewsApp currentNewsApp = getItem(position);

        //get all the info from current NewsApp
        String title = currentNewsApp.getTitle();
        String section = currentNewsApp.getSection();
        String author = "By " + currentNewsApp.getAuthor();
        Log.e("ADAPTER", "AUTHOR: " + author);


        String date = currentNewsApp.getDate();
        //makes the date look a little nicer
        date = date.split("T")[0];


        //put info into the layout
        TextView newsTitle = (TextView) listItemView.findViewById(R.id.article);
        newsTitle.setText(title);

        TextView newsSection = (TextView) listItemView.findViewById(R.id.section);
        newsSection.setText(section);

        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        dateView.setText(date);

        TextView authorView = (TextView) listItemView.findViewById(R.id.author);
        authorView.setText(author);

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }


}



