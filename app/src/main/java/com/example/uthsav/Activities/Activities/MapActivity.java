package com.example.uthsav.Activities.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.uthsav.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback
{
    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        LatLng ecvuLectureComplex = new LatLng(12.974402,77.585887);
        googleMap.addMarker(new MarkerOptions()
                .position(ecvuLectureComplex)
                .title("ECVU Lecture Complex"));

        LatLng ecvuMinchu = new LatLng(12.974993,77.586381);
        googleMap.addMarker(new MarkerOptions()
                .position(ecvuMinchu)
                .title("ECVU Minchu"));

        LatLng ecvuNandiniParlour = new LatLng(12.974570,77.585662);
        googleMap.addMarker(new MarkerOptions()
                .position(ecvuNandiniParlour)
                .title("ECVU Nandini Parlour"));

        LatLng ecvuOAT = new LatLng(12.974172,77.586027);
        googleMap.addMarker(new MarkerOptions()
                .position(ecvuOAT)
                .title("ECVU OAT"));

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ecvuLectureComplex, 18), 5000, null);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ecvuMinchu,18), 5000, null);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ecvuNandiniParlour,18), 5000, null);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ecvuOAT,18), 5000, null);


    }
}