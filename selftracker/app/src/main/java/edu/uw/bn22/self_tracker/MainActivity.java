package edu.uw.bn22.self_tracker;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements master_list.EntryListener, recording.DialogListener  {
    FragmentManager manager;
    FragmentTransaction ft;
    detail detail;
    master_list master_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creates the default view where summary is on the left and master list is on the right
        manager = getFragmentManager();
        ft = manager.beginTransaction();
        master_list = new master_list();
        ft.add(R.id.container_left, new summary());
        ft.add(R.id.container_right, master_list);
        ft.addToBackStack(null);
        ft.commit();

        Parse.initialize(this);
    }

    //Based on the user's selection on the list, it will bring up additional data
    @Override
    public void onSelected(bubble_tea entry) {
        detail = new detail();

        //Fills out each individual list item with more detailed data
        Bundle bundle = new Bundle();
        bundle.putString("rating", Integer.toString(entry.rating));
        bundle.putString("location", entry.location);
        bundle.putString("name", entry.name);
        bundle.putString("time", entry.time);
        detail.setArguments(bundle);


        //Changes the fragments so the list_view is on the left and the details is on the right
        manager = getFragmentManager();
        ft = manager.beginTransaction();
        Fragment a = getFragmentManager().findFragmentById(R.id.container_left);
        if (a instanceof master_list == false){
            master_list = new master_list();
            ft.replace(R.id.container_left, master_list);
        }
        ft.replace(R.id.container_right, detail);
        ft.addToBackStack(null);
        ft.commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //Allows the user to navigate back with the physical back button
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    // User touched the dialog's positive button
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        //Finds the latest Entry in database
        ParseQuery<ParseObject> query = ParseQuery.getQuery("bubble_tea");
        query.orderByDescending("createdAt").setLimit(1);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, com.parse.ParseException e) {
                if (object == null) {
                    Log.d("score", "The getFirst request failed.");
                } else {
                    Log.d("score", "Retrieved the object.");

                    //Updates the detail view with the latest entry
                    detail = new detail();
                    Bundle bundle = new Bundle();
                    bundle.putString("rating", Integer.toString(object.getInt("rating")));
                    bundle.putString("location", object.getString("location"));
                    bundle.putString("name", object.getString("name"));
                    Date date = object.getCreatedAt();
                    DateFormat df = new SimpleDateFormat("h:mm aa dd-MM-yyyy");
                    String reportDate = df.format(date);
                    bundle.putString("time", reportDate);
                    detail.setArguments(bundle);

                    //Changes the view so the master list is on the left and details of latest entry on the right
                    manager = getFragmentManager();
                    ft = manager.beginTransaction();
                    master_list = new master_list();
                    ft.replace(R.id.container_left, master_list);
                    ft.replace(R.id.container_right, detail);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });

        //Creates a toast to tell the user that the entry has been added
        Context context = getApplicationContext();
        String text = "New Entry Added!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        //Dismisses the dialog so it disappears
        dialog.dismiss();
    }

    // User touched the dialog's negative button
    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }

    //Returns to the opening screen of the app
    @Override
    public void moveSummary() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        Fragment a = getFragmentManager().findFragmentById(R.id.container_right);
        if (a instanceof master_list == false){
            master_list = new master_list();
            ft.replace(R.id.container_right, master_list);
        }
        Fragment b = getFragmentManager().findFragmentById(R.id.container_left);
        if (b instanceof summary == false) {
            ft.replace(R.id.container_left, new summary());
        }
        ft.addToBackStack(null);
        ft.commit();
    }
}
