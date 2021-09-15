package com.zybooks.team4_hw3;

/**
 * author  Manish Enishetty
 * CID     C22384538
 * MailID  menishe@clemson.edu
 */
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ListActivity extends AppCompatActivity implements ListFragment.OnLocationSelectedListener {

    private static final String KEY_CITY_ID = "cityID";
    private int mCityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //This is  onCreate method for List Activity, we will set the activity_list.xml for Content
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mCityId=-1;
        FragmentManager fragmentManager = getSupportFragmentManager(); // Create a fragment
        Fragment fragment = fragmentManager.findFragmentById(R.id.list_fragment_container); //Search for the list_fragment_container

        if (fragment == null) {
            fragment = new ListFragment();
            fragmentManager.beginTransaction()   //Initiating a Fragment transaction
                    .add(R.id.list_fragment_container, fragment)
                    .commit();
        }
        // Replace DetailsFragment if state saved when going from portrait to landscape
        if (savedInstanceState != null && savedInstanceState.getInt(KEY_CITY_ID) != 0
                && getResources().getBoolean(R.bool.twoPanes)) {
            mCityId = savedInstanceState.getInt(KEY_CITY_ID);
            Fragment bandFragment = DetailsFragment.newInstance(mCityId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_fragment_container, bandFragment)
                    .commit();
        }
    }

    // we will save the cityID for retrieving when required using getInt()
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Save state when something is selected
        if (mCityId != -1) {
            savedInstanceState.putInt(KEY_CITY_ID, mCityId);
        }
    }

    //As this class is implementing ListFragment Interface we have to override the methods in the interface.
    @Override
    public void onLocationSelected(int cityId) {
        mCityId = cityId;

        if (findViewById(R.id.details_fragment_container) == null) {

            Intent intent = new Intent(this, DetailsActivity.class);
            // we will call the Details Activity class and pass the cityID using putExtra()
            intent.putExtra(DetailsActivity.EXTRA_CITY_ID, cityId);
            startActivity(intent);
        } else {
            // Running on tablet, so replace previous fragment (if one exists) with a new fragment
            Fragment locFragment = DetailsFragment.newInstance(cityId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_fragment_container, locFragment)
                    .commit();
        }
    }
}