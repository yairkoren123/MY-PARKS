package com.example.myparks;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.myparks.adapter.CustomInfoWindow;
import com.example.myparks.data.Repository;
import com.example.myparks.modle.Park;
import com.example.myparks.modle.ParkViewModel;
import com.example.myparks.util.Util;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback ,
        GoogleMap.OnInfoWindowClickListener {


    private GoogleMap mMap;
    private ParkViewModel parkViewModel;
    private List<Park> parkList;
    private CardView cardView;
    private EditText stateCodeEt;
    private ImageButton searchButton;
    private String code = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        parkViewModel = new ViewModelProvider(this)
                .get(ParkViewModel.class);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // the button menu navigation
        BottomNavigationView bottomNavigationView =
                findViewById(R.id.button_navigation);
        mapFragment.getMapAsync(this);

        // the search State
        cardView = findViewById(R.id.card_view1);
        stateCodeEt = findViewById(R.id.floating_state_value_et);
        searchButton = findViewById(R.id.flating_search_button);




        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();
            if(id == R.id.maps_nav_bottom){
                if (cardView.getVisibility() == View.INVISIBLE ||
                cardView.getVisibility() == View.GONE){
                    cardView.setVisibility(View.VISIBLE);
                }
                parkList.clear();
                // show map view
                mMap.clear();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.map,mapFragment)
                        .commit();
                // to the marks again
                mapFragment.getMapAsync(this);
                return true;
            }else if (id == R.id.Parks_nab_bottom){

                // show park fragment
                selectedFragment = ParksFragment.newInstance();
                // dont show the search bar
                cardView.setVisibility(View.GONE);

            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.map,selectedFragment)
                    .commit();
            return true;
        });



        searchButton.setOnClickListener(view -> {
            // we want to clear all the object
            parkList.clear();

            Util.hideKeyboard(this); // he pass view but this work?
            String stateCode = stateCodeEt.getText().toString().trim();
            if(!TextUtils.isEmpty(stateCode)){
                code = stateCode;
                parkViewModel.selectCode(code);
            }
            // calling onMapReady to reRUN again
            onMapReady(mMap); // refresh the map!
            stateCodeEt.setText("");
        });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // the custom info window pop up when we click on Marker
        mMap.setInfoWindowAdapter(new CustomInfoWindow(getApplicationContext()));
        mMap.setOnInfoWindowClickListener(this); // go to onInfoWindowClick function

        parkList = new ArrayList<>();
        parkList.clear();

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // change the color of the marker when click on the marker
                // ... its will return because its clear
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_GREEN));

                Log.d("bigmarker", "onMarkerClick: "+marker.getTitle());

                return false;
            }
        });

        populateMap();


    }

    private void populateMap() {
        mMap.clear(); // importent !! its del all the other markers in the map
        Repository.getParks(parks -> {
            parkList = parks;
            for (Park park : parks){
                //Log.d("park", "onMapReady: "+park.getFullName());

                LatLng location = new LatLng(Double.parseDouble(park.getLatitude()),
                        Double.parseDouble(park.getLongitude()));


                MarkerOptions markerOptions =
                        new MarkerOptions()
                            .position(location)
                            .title(park.getFullName())
                            .icon(BitmapDescriptorFactory.defaultMarker(
                                    BitmapDescriptorFactory.HUE_VIOLET))
                            .snippet(park.getStates());

                Marker marker = mMap.addMarker(markerOptions);
                marker.setTag(park);

                mMap.addMarker(markerOptions);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,5)); // zoom in map
                Log.d("parks", "onMapReady: +"+ park.getFullName());


            }
            parkViewModel.setSelectedParks(parkList);
            Log.d("SIZE", "populateMap: "+ parkList.size());



        },code); // get the url becouse we pass it in the line : parkViewModel.selectCode(code);

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Log.d("markerClick", "onInfoWindowClick: " + marker.getTitle());
        cardView.setVisibility(View.GONE);
        // go to detailsFragment
        goToDetailsFragment(marker);

    }



    private void goToDetailsFragment(Marker marker) {
        parkViewModel.setSelectedPark((Park) marker.getTag());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.map,DetailsFragment.newInstance())
        .commit();
    }
}