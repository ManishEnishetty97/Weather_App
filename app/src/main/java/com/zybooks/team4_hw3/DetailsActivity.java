package com.zybooks.team4_hw3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * author  Manish Enishetty
 * CID     C22384538
 * MailID  menishe@clemson.edu
 */
import android.os.Bundle;

public class DetailsActivity extends AppCompatActivity {


    public static final String EXTRA_CITY_ID = "cityId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Terminate if two panes are displaying since ListActivity should be displaying both panes
        boolean isTwoPanes = getResources().getBoolean(R.bool.twoPanes);
        if (isTwoPanes) {
            finish();
            return;
        }
        setContentView(R.layout.activity_details);

        // find the details_fragment_container using fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.details_fragment_container);


        // Get the values of City ID from the Details fragment
        if (fragment == null) {
            int cityId = getIntent().getIntExtra(EXTRA_CITY_ID, 1);
            fragment = DetailsFragment.newInstance(cityId);
            fragmentManager.beginTransaction()
                    .add(R.id.details_fragment_container, fragment)
                    .commit();
        }
    }
}