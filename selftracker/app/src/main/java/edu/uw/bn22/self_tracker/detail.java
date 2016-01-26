package edu.uw.bn22.self_tracker;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class detail extends Fragment {


    public detail() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        //Updates the detail page with the information from the EntryListener
        Bundle bundle = getArguments();
        if(bundle != null) {
            TextView nameView = (TextView) rootView.findViewById(R.id.addCount);
            TextView ratingView = (TextView) rootView.findViewById(R.id.txtRating);
            TextView locationView = (TextView) rootView.findViewById(R.id.txtCount);
            TextView timeView = (TextView) rootView.findViewById(R.id.txtTime);

            nameView.setText(bundle.getString("name"));
            timeView.setText(bundle.getString("time"));
            ratingView.setText(bundle.getString("rating"));
            locationView.setText(bundle.getString("location"));
        }

        return rootView;
    }
}
