package com.example.allen.routemaker;

import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Allen on 2016/7/3.
 */

public class LongPressLocationSource implements LocationSource, GoogleMap.OnMapLongClickListener {

    private OnLocationChangedListener mListener;

    private boolean mPaused;

    Location location = new Location("LongPressLocationProvider");

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
    }

    @Override
    public void deactivate() {
        mListener = null;
    }

    @Override
    public void onMapLongClick(LatLng point) {
        if (mListener != null && !mPaused) {
            location.setLatitude(point.latitude);
            location.setLongitude(point.longitude);
            location.setAccuracy(100);
            mListener.onLocationChanged(location);
        }
    }

    public  Location getLocation(){
        return  location;
    }
}
