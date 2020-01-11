package com.ashraf.faraaz.hydtoilets;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

public class OptionsActivity extends AppCompatActivity implements OptionsFragment.FragmentSelectionListener, SearchByLocationFragment.SearchByLocationListener, SearchByCircleFragment.SearchByCircleListener {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        OptionsFragment optionsFragment = new OptionsFragment();
        fragmentTransaction.add(R.id.optionsFrameLayout, optionsFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == UtilsClass.LOCATION_PERMISSION_CODE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show();
                //permission granted. proceed with accessing it and calculating closest and all that bizniz
            }
            else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onFragmentSelected(String fragmentName) {
        if(fragmentName.equals("SearchByCircleFragment")) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right);
            fragmentTransaction.add(R.id.optionsFrameLayout, new SearchByCircleFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        else if(fragmentName.equals("SearchByLocationFragment")) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right);
            fragmentTransaction.add(R.id.optionsFrameLayout, new SearchByLocationFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        else {
            throw new RuntimeException(fragmentName + " was neither SearchByLocation nor SearchByCircle");
        }
    }

    @Override
    public void onToiletSelected(String singleToilet) {

        ToiletMapFragment singleToiletFragment = new ToiletMapFragment();
        Bundle singleToiletBundle = new Bundle();
        singleToiletBundle.putString("singleToilet", singleToilet);
        singleToiletFragment.setArguments(singleToiletBundle);

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_down, R.anim.slide_out_down);
        fragmentTransaction.add(R.id.optionsFrameLayout, singleToiletFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(requestCode + " " + resultCode + " " + data);
        System.out.println("THIS IS OUTPUT THAT YOU WANT");
    }
}
