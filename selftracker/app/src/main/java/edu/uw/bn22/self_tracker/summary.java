package edu.uw.bn22.self_tracker;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class summary extends Fragment {


    public summary() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_summary, container, false);
        final TextView countView = (TextView) rootView.findViewById(R.id.addCount);
        final TextView aboveView = (TextView) rootView.findViewById(R.id.addAbove);

        //Grabs all the data from Parse.com
        ParseQuery<ParseObject> query = ParseQuery.getQuery("bubble_tea");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, com.parse.ParseException e) {
                if (e == null) {
                    int count = objects.size();
                    countView.setText(Integer.toString(count));
                } else {
                    Log.v("Summary", "Parse try again");
                }
            }
        });

        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("bubble_tea");
        query1.whereGreaterThan("rating", 5);
        query1.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, com.parse.ParseException e) {
                if (e == null) {
                    int count1 = objects.size();
                    aboveView.setText(Integer.toString(count1));
                } else {
                    Log.v("Summary", "Parse try again");
                }
            }
        });

        return rootView;
    }
}
