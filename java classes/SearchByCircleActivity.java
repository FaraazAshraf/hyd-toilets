package com.ashraf.faraaz.hydtoilets;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class SearchByCircleActivity extends AppCompatActivity {

    private Spinner circleSpinner;
    LinearLayout searchByCircleLinearLayout;

    List<String> circlesList = Arrays.asList("Choose a circle","1-KAPRA","2-UPPAL","3-HAYATNAGAR","5-SAROORNAGAR","6-MALAKPET","7-SANTOSHNAGAR","8-CHANDRAYANGUTTA","9-CHARMINAR","10-FALAKNUMA","11-RAJENDRANAGAR","12-MEHDIPATNAM","13-KARWAN","14-GOSHAMAHAL","15-MUSHEERABAD","16-AMBERPET","17-KHAIRATABAD","18-JUBILEE HILLS","19-YOUSUFGUDA","20-SERILINGAMPALLY","21-CHANDA NAGAR","22-RAMACHANDRAPURAM & PATANCHERUVU","23-MOOSAPET","24-KUKATPALLY","25-QUTUBULLAPUR","26-GAJULARAMARAM","27-ALWAL","28-MALKAJIGIRI","29-SECUNDERABAD","30-BEGUMPET");


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_circle);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        circleSpinner = findViewById(R.id.circleSpinner);
        searchByCircleLinearLayout = findViewById(R.id.searchByCircleLinearLayout);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, circlesList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    //I took this idea from stackoverflow
                    return false;
                } else {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        circleSpinner.setAdapter(adapter);

        circleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    //do nothing because they selected hint
                }
                else {
                    searchByCircleLinearLayout.removeAllViews();
                    try {
                        new DisplayMatches(circlesList.get(position), searchByCircleLinearLayout, new InputStreamReader(getAssets().open("toilets.txt"))).start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    class DisplayMatches extends Thread {
        String circle;
        LinearLayout searchByCircleLinearLayout;
        InputStreamReader inputStreamReader;

        DisplayMatches(String circle, LinearLayout linearLayout, InputStreamReader inputStreamReader) {
            this.circle = circle;
            this.searchByCircleLinearLayout = linearLayout;
            this.inputStreamReader = inputStreamReader;
        }

        public void run() {
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line;
            final int[] count = {0};

            try {
                while((line = reader.readLine()) != null) {
                    if(line.contains(circle)) {
                        //currently TextView used. Need to change it to a button.
                        final String singleToilet = line;

//                        TextView detailsTextView = new TextView(SearchByCircleActivity.this);
//                        detailsTextView.setText("Address: \n" + address + "\n" +
//                                "Ward: " + ward);
//                        detailsTextView.setTextIsSelectable(true);
//                        detailsTextView.setId(View.generateViewId());
//                        detailsTextView.setVerticalScrollBarEnabled(true);
//                        detailsTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                        detailsTextView.setPadding(10,10,10,10);
//
//                        final RelativeLayout rl = new RelativeLayout(SearchByCircleActivity.this);
//                        rl.setBackgroundResource(R.drawable.red_border);
//                        rl.addView(detailsTextView, 2* (UtilsClass.getScreenWidth(SearchByCircleActivity.this)/3), LinearLayout.LayoutParams.WRAP_CONTENT);
//
//                        Button trackToiletButton = new Button(SearchByCircleActivity.this);
//                        trackToiletButton.setId(View.generateViewId());
//                        trackToiletButton.setBackgroundResource(R.drawable.my_button);
//                        trackToiletButton.setText("LOCATE");
//                        trackToiletButton.setTextColor(Color.WHITE);
//                        trackToiletButton.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                        trackToiletButton.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                new UtilsClass().onToiletSelected(SearchByCircleActivity.this, singleToilet);
//                            }
//                        });
//
//                        RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                        buttonParams.addRule(RelativeLayout.RIGHT_OF, detailsTextView.getId());
//                        buttonParams.addRule(RelativeLayout.CENTER_VERTICAL);
//                        buttonParams.setMargins(10,0,10,0);
//                        trackToiletButton.setLayoutParams(buttonParams);
//
//                        rl.addView(trackToiletButton);
//
//                        LinearLayout.LayoutParams marginParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                        marginParams.setMargins(10,10,10,10);
//                        rl.setLayoutParams(marginParams);
//
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                count[0]++;
//                                searchByCircleLinearLayout.addView(rl);
//                            }
//                        });
                        final CustomLayout customLayout = new CustomLayout(SearchByCircleActivity.this);
                        customLayout.init(SearchByCircleActivity.this, singleToilet);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                count[0]++;
                                searchByCircleLinearLayout.addView(customLayout);
                            }
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SearchByCircleActivity.this, "Found " + count[0] + " result(s)", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
