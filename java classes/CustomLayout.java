package com.ashraf.faraaz.hydtoilets;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class CustomLayout extends ConstraintLayout {
    public CustomLayout(Context context) {
        super(context);
    }
    public void init(Activity activity, String singleToilet) {
        inflate(getContext(), R.layout.custom_layout, this);
        setTextViews(singleToilet);
        setListeners(activity, singleToilet);
    }

    public void init(Activity activity, String singleToilet, double minDistance) {
        inflate(getContext(), R.layout.custom_layout, this);
        setTextViews(singleToilet);
        setTextViews(minDistance);
        setListeners(activity, singleToilet);
    }

    private void setTextViews(String singleToilet) {
        TextView locationTextView, wardTextView, circleTextView;
        locationTextView = findViewById(R.id.locationTextView);
        wardTextView = findViewById(R.id.wardTextView);
        circleTextView = findViewById(R.id.circleTextView);

        String ward = singleToilet.split(",")[1];
        String circle = singleToilet.split(",")[2];
        String location = singleToilet.split(",")[4];

        locationTextView.setText(location);
        wardTextView.setText(ward);
        circleTextView.setText(circle);

        TextView distanceTextView = findViewById(R.id.distanceTextView);
        distanceTextView.setVisibility(INVISIBLE);
    }

    private void setTextViews(double minDistance) {
        TextView distanceTextView = findViewById(R.id.distanceTextView);
        distanceTextView.setVisibility(View.VISIBLE);
        distanceTextView.setText("Distance: " + String.format("%.2f", minDistance) + "km");
    }

    private void setListeners(final Activity activity, final String singleToilet) {
        Button b = findViewById(R.id.locateButton);
        b.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new UtilsClass().onToiletSelected(activity, singleToilet);
            }
        });
//        ConstraintLayout customConstraintLayout = findViewById(R.id.customConstraintLayout);

//        TextView locationTextView, wardTextView, circleTextView;
//        locationTextView = findViewById(R.id.locationTextView);
//        wardTextView = findViewById(R.id.wardTextView);
//        circleTextView = findViewById(R.id.circleTextView);
//
//        TextView locationLabelTextView, wardLabelTextView, circleLabelTextView;
//        locationLabelTextView = findViewById(R.id.locationTextView);
//        wardLabelTextView = findViewById(R.id.wardLabelTextView);
//        circleLabelTextView = findViewById(R.id.circleLabelTextView);

        ViewGroup viewGroup = findViewById(R.id.customConstraintLayout);
        viewGroup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new UtilsClass().onToiletSelected(activity, singleToilet);
            }
        });
    }
}
