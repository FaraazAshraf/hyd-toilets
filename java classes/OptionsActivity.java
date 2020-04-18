package com.ashraf.faraaz.hydtoilets;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class OptionsActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Button searchByLocationButton = findViewById(R.id.searchByLocationButton);
        Button searchByCirlceButton = findViewById(R.id.searchByCircleButton);

        searchByLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager pm = getPackageManager();
                int hasPerm = pm.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, getPackageName());
                if (hasPerm != PackageManager.PERMISSION_GRANTED) {
                    new com.ashraf.faraaz.hydtoilets.UtilsClass().checkLocationPermission(OptionsActivity.this);
                    new com.ashraf.faraaz.hydtoilets.UtilsClass().displayLocationSettingsRequest(OptionsActivity.this, OptionsActivity.this);
                }
                else {
                    new com.ashraf.faraaz.hydtoilets.UtilsClass().displayLocationSettingsRequest(OptionsActivity.this, OptionsActivity.this);
                    startActivity(new Intent(OptionsActivity.this, SearchByLocationActivity.class));
                }
            }
        });

        searchByCirlceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OptionsActivity.this, SearchByCircleActivity.class));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == com.ashraf.faraaz.hydtoilets.UtilsClass.LOCATION_PERMISSION_CODE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show();
                //permission granted. proceed with accessing it and calculating closest and all that bizniz
                startActivity(new Intent(OptionsActivity.this, SearchByLocationActivity.class));
            }
            else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(requestCode + " " + resultCode + " " + data);
        System.out.println("THIS IS OUTPUT THAT YOU WANT");
    }
}
