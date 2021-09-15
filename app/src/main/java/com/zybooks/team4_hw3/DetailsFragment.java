package com.zybooks.team4_hw3;
/**
 * author  Manish Enishetty
 * CID     C22384538
 * MailID  menishe@clemson.edu
 */
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


public class DetailsFragment extends Fragment {

    private  final String  WEBAPI_BASE_URL = "https://api.openweathermap.org/data/2.5/weather"; // Openweather API link
    private final String API_KEY="787cc0ef61dd05a6416785229fcabaf7"; // API Key

    private Locations mLocations;
    TextView loc_temp,loc_desc,loc_date; //Create the text views for viewing temperature, weather description, date
    ImageButton weather; // Used for getting image of weather
    private static Context ctx; //Create a context ctx



    // Create a new Instace of the Details Fragment . This loads whenever the fragments loads.
    // This will load the details into the fragment and return the fragment
    public static DetailsFragment newInstance(int cityId) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt("cityId", cityId);
        fragment.setArguments(args);
        return fragment;

    }


    //OnCreation of fragment get the cityID which is clicked in List Fragment using getInstance()
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the band ID from the intent that started DetailsActivity
        int cityId = 1;
        if (getArguments() != null) {
            cityId = getArguments().getInt("cityId");
        }

        mLocations = LocationsDatabase.getInstance(getContext()).getLocations(cityId);
        // Get the first location
      //  mLocations = LocationsDatabase.getInstance(getContext()).getLocations(1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        ctx = getActivity().getBaseContext(); // set the context to the Base Activity


        View view = inflater.inflate(R.layout.fragment_details, container, false);
        TextView nameTextView = view.findViewById(R.id.location_name);
        nameTextView.setText(mLocations.getCityName()); // Set the location name to the View

        // Allocate the views from fragment_details with the java objects created.
        loc_temp=view.findViewById(R.id.temp);
        loc_desc=view.findViewById(R.id.desc);
        loc_date=view.findViewById(R.id.date);
        weather=view.findViewById(R.id.weather);

        // This is for displaying the images for the different description of the weather.
        HashMap hashMap=new HashMap();
        hashMap.put("1",R.drawable.sky);
        hashMap.put("2",R.drawable.rainy);
        hashMap.put("3",R.drawable.fog);
        hashMap.put("4",R.drawable.sunny);

        // Now we create a RequestQueue using Volley and pass the ctx.
        RequestQueue queue = Volley.newRequestQueue(ctx);
        Log.d(String.valueOf(queue),"Queue reached");
        String cityCode=mLocations.getCityCode(); // get the city code for calling openweather API
        String url = Uri.parse(WEBAPI_BASE_URL).buildUpon().appendQueryParameter("id", cityCode).build().toString(); // Append the citycode
        String url2=Uri.parse(url).buildUpon().appendQueryParameter("appid",API_KEY).build().toString(); // Append the API Key
        String final_url= url2+"&units=imperial"; // To get the value as Fahrenheit use imperial
        System.out.println(final_url); // print the url in logcat for verification


        //Create the JsonObjectRequest object and pass the URL for calling API
        JsonObjectRequest jor= new JsonObjectRequest(Request.Method.GET, final_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(String.valueOf(response), "response reached");

                        try {
                            Log.d(String.valueOf(response), "entered try block");

                            JSONObject main_object = response.getJSONObject("main"); // main is an JSON object so we use getJSONObject to get value  it
                            JSONArray array = response.getJSONArray("weather"); // weather is JSONArray
                            JSONObject object = array.getJSONObject(0); // get the first object in weather array.

                            String temp = String.valueOf(main_object.getDouble("temp")); // get the temperature  from the weather array object
                            String desc = object.getString("description"); // Get the Weather description

                            loc_desc.setText(desc); // Set the description to the view

                            Calendar cal = Calendar.getInstance();
                            String currentdate=new SimpleDateFormat("dd-MMM").format(cal.getTime());
                            loc_date.setText(currentdate);

                            Double temp_int = Double.parseDouble(temp);
                            String temp_s=String.valueOf(temp_int);
                            String temp_f=temp_s+"°F";
                            loc_temp.setText(temp_f); // Set the temperature to the view


                            int[] i =new int[10];
                            int p=0;
                            for (int j = 1;j < 10;j++){
                                i[j]=j;
                            }

                            // This is for setting an image for particular weather description

                            if (desc.contains("rain")|| desc.contains("light")|| desc.contains("drizzle")){
                                p=i[2];
                            }
                            else if(desc.contains("haze")|| desc.contains("sunny")||desc.contains("clear")){
                                p=i[1];
                            }else if(desc.contains("fog")){
                                p=i[3];
                            }
                            switch(p){

                                case 1:
                                    weather.setImageResource((Integer)hashMap.get("1"));
                                    break;
                                case 2:
                                    weather.setImageResource((Integer)hashMap.get("2"));
                                    break;
                                case 3:
                                    weather.setImageResource((Integer)hashMap.get("3"));
                                    break;
                                default:
                                    weather.setImageResource((Integer)hashMap.get("4"));
                                    break;
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        queue.add(jor); // Add the JsonObjectRequest object to the Request queue.
        return view; // return the view with all values attached to it.
    }
}
