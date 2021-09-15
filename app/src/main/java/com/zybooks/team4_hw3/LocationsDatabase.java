package com.zybooks.team4_hw3;

/**
 * author  Manish Enishetty
 * CID     C22384538
 * MailID  menishe@clemson.edu
 */
import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

public class LocationsDatabase {
    private static LocationsDatabase mLocationsDatabase;
    private List<Locations> mLocations;

    public static LocationsDatabase getInstance(Context context) {
        if (mLocationsDatabase == null) {
            mLocationsDatabase = new LocationsDatabase(context);
        }
        return mLocationsDatabase;
    }

    private LocationsDatabase(Context context) {
        mLocations = new ArrayList<>();
        Resources res = context.getResources();
        String[] location_names = res.getStringArray(R.array.locations_names);
        String[] cityCodes=res.getStringArray(R.array.citycodes);

       try{ for (int i = 0; i < location_names.length; i++) {
            mLocations.add(new Locations(i+1,location_names[i],cityCodes[i]));
        }
       }
       catch (Exception e){
           e.printStackTrace();
       }

    }

    public List<Locations> getLocations()
    {
        return mLocations;
    }

    public Locations getLocations(int id){
        for (Locations location:mLocations){
            if(location.getCityId()==id){
                return location;
            }
        }
        return null;
    }

}


