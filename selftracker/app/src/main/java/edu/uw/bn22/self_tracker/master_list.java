package edu.uw.bn22.self_tracker;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class master_list extends Fragment {
    private static final String TAG = "MovieFragment";
    private ArrayAdapter<String> adapter; //adapter for list view
    private OnHourSleepListener callback;

    public interface OnHourSleepListener {
        public void onHourSleepSelected(String s);
    }

    public master_list() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try {
            callback = (OnHourSleepListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnHourSleepListener");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        ArrayList<String> list = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(
                getActivity(), R.layout.list_item, list);

        AdapterView listView = (AdapterView)rootView.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String movie = (String) parent.getItemAtPosition(position);
                Log.i(TAG, "selected: " + movie);

                //swap the fragments to show the detail
                ((OnHourSleepListener) getActivity()).onHourSleepSelected(movie);

            }
        });

        adapter.add("Yes");
        adapter.add("No");

        Button button = (Button) rootView.findViewById(R.id.btnAdd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                LayoutInflater inflater = getActivity().getLayoutInflater();
                builder.setView(inflater.inflate(R.layout.fragment_recording, null));

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return rootView;
    }

}
