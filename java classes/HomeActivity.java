package com.ashraf.faraaz.hydtoilets;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SupportMapFragment mDummyMapInitializer = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.toiletMapView2);
        mDummyMapInitializer.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Log.d("DUMMY MAP", "onMapReady");
            }
        });
        startActivity(new Intent(HomeActivity.this, OptionsActivity.class));
        finish();
    }
}
