package com.example.android.newsapp;


import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/*
 * {@link AndroidFlavorAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
 * based on a data source, which is a list of {@link AndroidFlavor} objects.
 * */


public class NewsAppAdapter extends ArrayAdapter<NewsApp> {

    private static final String LOG_TAG = NewsAppAdapter.class.getSimpleName();

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context        The current context. Used to inflate the layout file.
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
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.listview_newsapp, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        NewsApp currentNewsApp = getItem(position);

        String title = currentNewsApp.getTitle();
        String section = currentNewsApp.getSection();

        String date = currentNewsApp.getDate();
        date = date.split("T")[0];

        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat(date);
        //Log.e("HERE", "DATE " + SimpleDateFormat());
        //Date dateObject = new Date(currentNewsApp.getDate());

        TextView newsTitle = (TextView) listItemView.findViewById(R.id.primary_location);
        //primaryLocationView.setText(section);
        newsTitle.setText(title);

        TextView newsSection = (TextView) listItemView.findViewById(R.id.location_offset);
        newsSection.setText(section);


        // Find the TextView with view ID date
        //TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        //dateView.setText(date);
        //Format the date string (i.e. "Mar 3, 1984")
        //String formattedDate = formatDate(dateObject);
        // Display the date of the current earthquake in that TextView
        //dateView.setText(formattedDate);

        // Find the TextView with view ID time
        //TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        // Format the time string (i.e. "4:30PM")
       // String formattedTime = formatTime(dateObject);
        // Display the time of the current earthquake in that TextView
        //timeView.setText(formattedTime);


        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }


}



