package com.zybooks.team4_hw3;

/**
 * author  Manish Enishetty
 * CID     C22384538
 * MailID  menishe@clemson.edu
 */
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.List;


public class ListFragment extends Fragment {


    // For the activity to implement
    public interface OnLocationSelectedListener {
        void onLocationSelected(int cityId);
    }

    // Reference to the activity
    private OnLocationSelectedListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.location_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Send locations to recycler view
        LocationAdapter  adapter = new LocationAdapter(LocationsDatabase.getInstance(getContext()).getLocations());
        recyclerView.setAdapter(adapter);

        return view;
    }

    // LocationHolder class implements onClickListener for the recycle view
    private class LocationHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Locations mLocations;
        private TextView mNameTextView;

        //This will inflate list_item_locations and find the ID of the selected item
        public LocationHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_locations, parent, false));
            itemView.setOnClickListener(this);
            mNameTextView = itemView.findViewById(R.id.locationName);
        }

        //This will bind  the cityname from location obtained from above method
        public void bind(Locations locations) {
            mLocations = locations;
            mNameTextView.setText(mLocations.getCityName());
        }

        @Override
        public void onClick(View view) {
            // Tell ListActivity what band was clicked
            mListener.onLocationSelected(mLocations.getCityId());
        }
    }

    //This will be used to implement ListAdapter
    private class LocationAdapter extends RecyclerView.Adapter<LocationHolder> {

        private List<Locations> mLocations;

        public LocationAdapter(List<Locations> locations) {
            mLocations = locations;
        }

        @Override
        public LocationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new LocationHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(LocationHolder holder, int position) {
            Locations locations = mLocations.get(position);
            holder.bind(locations);
        }

        @Override
        public int getItemCount() {
            return mLocations.size();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLocationSelectedListener) {
            mListener = (OnLocationSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLocationSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Notify activity of location selection
            String cityId = (String) view.getTag();
            mListener.onLocationSelected(Integer.parseInt(cityId));
        }
    };
}

