package edu.uw.bn22.self_tracker;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class master_list extends Fragment  {
    private static final String TAG = "Tracker Fragment";
    private ArrayAdapter<bubble_tea> adapter; //adapter for list view
    private EntryListener callback;

    //Creates a EntryListener that listens for clicks in the list view
    public interface EntryListener {
        public void onSelected(bubble_tea s);
        public void moveSummary();
    }

    public master_list() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try {
            callback = (EntryListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement EntryListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);


        //Initializes the adapter that contains the content for the list view
        ArrayList<bubble_tea> list = new ArrayList<bubble_tea>();
        adapter = new ArrayAdapter<bubble_tea>(
                getActivity(), R.layout.list_item, list);

        AdapterView listView = (AdapterView)rootView.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        //Passes the item that the user clicks upon on the list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bubble_tea entry = (bubble_tea) parent.getItemAtPosition(position);
                Log.i(TAG, "selected: " + entry);

                //swap the fragments to show the detail
                ((EntryListener) getActivity()).onSelected(entry);
            }
        });

        //Populates the list view from Parse.com
        ParseQuery<ParseObject> query = ParseQuery.getQuery("bubble_tea");
        query.orderByDescending("createdAt").setLimit(100);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, com.parse.ParseException e) {
                if (e == null) {
                    for (ParseObject object : objects) {
                        String name = object.getString("name");
                        String location = object.getString("location");
                        int rating = object.getInt("rating");
                        Date date = object.getCreatedAt();
                        DateFormat df = new SimpleDateFormat("h:mm aa dd-MM-yyyy");
                        String reportDate = df.format(date);
                        bubble_tea entry = new bubble_tea(rating, name, location, reportDate);
                        adapter.add(entry);
                    }
                } else {
                    Log.v(TAG, "Parse try again");
                }
            }
        });

        //Shows a popup that allows the user to add data to Parse.com
        Button button = (Button) rootView.findViewById(R.id.btnAdd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new recording().show(getFragmentManager(), "dialog");
            }
        });

        //Returns to the summary screen when clicked
        Button button1 = (Button) rootView.findViewById(R.id.btnSummary);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EntryListener) getActivity()).moveSummary();
            }
        });

        return rootView;
    }

}
