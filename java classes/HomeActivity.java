package com.ashraf.faraaz.hydtoilets;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        new GoToOptionsActivityAfterWaiting2s().start();
    }

    class GoToOptionsActivityAfterWaiting2s extends Thread {
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startActivity(new Intent(HomeActivity.this, OptionsActivity.class));
            finish();
        }
    }
}
