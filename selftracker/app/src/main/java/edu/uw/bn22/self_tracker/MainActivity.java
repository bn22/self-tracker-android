package edu.uw.bn22.self_tracker;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements master_list.OnHourSleepListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getFragmentManager();

        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.container_left, new summary());
        ft.add(R.id.container_right, new master_list());
        ft.commit();
    }

    @Override
    public void onHourSleepSelected(String movie) {
        detail detail = new detail();

        Bundle bundle = new Bundle();
        detail.setArguments(bundle);

        //swap the fragments
        getFragmentManager().beginTransaction()
                .replace(R.id.container_right, detail)
                .replace(R.id.container_left, new master_list())
                .addToBackStack(null)
                .commit();

    }

    //for support class Activity
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
