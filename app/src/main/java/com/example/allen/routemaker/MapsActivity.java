package com.example.allen.routemaker;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.allen.routemaker.FetchAddressIntentService.*;
import static java.security.AccessController.getContext;

public class MapsActivity extends AppCompatActivity implements
        OnMapReadyCallback {

    private LongPressLocationSource mLocationSource;
    private AddressResultReceiver mResultReceiver;
    private String TAG = "allenMessage";
    public static String mAddressOutput;
    private int number;
    ArrayList<String> savedAddresses = new ArrayList<String>();


    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mLocationSource = new LongPressLocationSource();
        mResultReceiver = new AddressResultReceiver(null);

        startIntentService();

        Bundle extras = getIntent().getExtras();

        if(extras == null) {
            number = 1;
        } else {
            number = extras.getInt("theNumber");
        }

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onStart(){
        super.onStart();
        if (savedAddresses != null){
            savedAddresses.clear();
        }
    }


    @Override
    public void onMapReady(GoogleMap map) {
        map.setLocationSource(mLocationSource);
        map.setOnMapLongClickListener(mLocationSource);
        checkLocationPermission();
        map.setMyLocationEnabled(true);
    }

    protected void startIntentService() {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, mResultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLocationSource.getLocation());
        startService(intent);
    }


    private class AddressResultReceiver extends ResultReceiver {

        AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            mAddressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
            savedAddresses.add(mAddressOutput);
            showToast(mAddressOutput);
        }
    }

    public void showToast(final String address){
        runOnUiThread(new Runnable() {
            public void run() {
                if(address != null && !address.isEmpty()) {
                    Toast.makeText(MapsActivity.this, address, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void SaveAddress(View view) {
        startIntentService();
    }

    public void ToMyPlan(View view){
        Intent intent = new Intent(this, MyPlanActivity.class);
        intent.putExtra("addressList",savedAddresses);
        intent.putExtra("number",number);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }

}


