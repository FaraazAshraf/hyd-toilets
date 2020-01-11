package com.ashraf.faraaz.hydtoilets;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class SearchByLocationFragment extends Fragment {

    LinearLayout searchByLocationLinearLayout;

    private SearchByLocationListener listener;
    public interface SearchByLocationListener {
        void onToiletSelected(String singleToilet);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PackageManager pm = getActivity().getPackageManager();
        int hasPerm = pm.checkPermission(
                Manifest.permission.ACCESS_FINE_LOCATION,
                getActivity().getPackageName());
        if (hasPerm != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(), "Permission missing", Toast.LENGTH_SHORT).show();
        }
        else {

            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Fetching your location...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            final ArrayList<String> allToilets = new UtilsClass().getAllToilets(getActivity());

            final double[] x1 = {0};
            final double[] y1 = {0};

            final LocationManager locationManager = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);

            LocationListener mLocationListener = new LocationListener() {
                @SuppressLint("ResourceType")
                public void onLocationChanged(Location location) {
                    locationManager.removeUpdates(this);
                    progressDialog.hide();

                    x1[0] = location.getLatitude();
                    y1[0] = location.getLongitude();

                    ArrayList<Integer> displayedIndices = new ArrayList<>();

                    for (int j = 0; j < 5; j++) {
                        int minIndex = 0;
                        double minDistance = Double.MAX_VALUE;
                        for (int i = 0; i < allToilets.size(); i++) {
                            if(displayedIndices.contains(i)) continue;

                            String toilet = allToilets.get(i);
                            double x2 = Double.parseDouble(toilet.split(",")[5]);
                            double y2 = Double.parseDouble(toilet.split(",")[6]);

                            double thisDistance = new UtilsClass().getDistanceFromLatLonInKm(x1[0], y1[0], x2, y2);

                            if(thisDistance < minDistance) {
                                minDistance = thisDistance;
                                minIndex = i;
                            }
                        }
                        final int finalMinIndex = minIndex;
                        displayedIndices.add(finalMinIndex);

                        String singleToilet = allToilets.get(finalMinIndex);
                        String id = singleToilet.split(",")[0];
                        String ward = singleToilet.split(",")[1];
                        String circle = singleToilet.split(",")[2];
                        String zone = singleToilet.split(",")[3];
                        String address = singleToilet.split(",")[4];

                        TextView detailsTextView = new TextView(getActivity());
                        detailsTextView.setText((minDistance + "").substring(0,4) + " km"+
                                "\nAddress: \n" + address + "\n" +
                                "Ward: " + ward);
                        detailsTextView.setTextIsSelectable(true);
                        detailsTextView.setId(View.generateViewId());
                        detailsTextView.setVerticalScrollBarEnabled(true);
                        detailsTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        detailsTextView.setPadding(10,10,10,10);

                        RelativeLayout rl = new RelativeLayout(getActivity());
                        rl.setBackgroundResource(R.drawable.red_border);
                        rl.addView(detailsTextView, 2*new UtilsClass().getScreenWidth(getActivity())/3, LinearLayout.LayoutParams.WRAP_CONTENT);

                        Button trackToiletButton = new Button(getActivity());
                        trackToiletButton.setId(View.generateViewId());
                        trackToiletButton.setBackgroundResource(R.drawable.locate_button_bg);
                        trackToiletButton.setText("LOCATE");
                        trackToiletButton.setTextColor(Color.WHITE);
                        trackToiletButton.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        trackToiletButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                
                                if(new UtilsClass().connectedToInternet(getActivity())) {
                                    String singleToilet = allToilets.get(finalMinIndex);
                                    listener.onToiletSelected(singleToilet);
                                }
                                else {
                                    Toast.makeText(getActivity(), "Internet required for this", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        buttonParams.addRule(RelativeLayout.RIGHT_OF, detailsTextView.getId());
                        buttonParams.addRule(RelativeLayout.CENTER_VERTICAL);
                        buttonParams.setMargins(10,0,10,0);
                        trackToiletButton.setLayoutParams(buttonParams);

                        rl.addView(trackToiletButton);

                        LinearLayout.LayoutParams marginParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        marginParams.setMargins(10,10,10,10);
                        rl.setLayoutParams(marginParams);

                        searchByLocationLinearLayout.addView(rl);
                    }
                }
                @Override
                public void onProviderDisabled(String provider) {
                    // TODO Auto-generated method stub
                }
                @Override
                public void onProviderEnabled(String provider) {
                    // TODO Auto-generated method stub
                }
                @Override
                public void onStatusChanged(String provider, int status,
                                            Bundle extras) {
                    // TODO Auto-generated method stub
                }
            };

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, mLocationListener);

        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_by_location, container, false);

        searchByLocationLinearLayout = v.findViewById(R.id.searchByLocationLinearLayout);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof SearchByLocationListener) {
            listener = (SearchByLocationListener)context;
        }
        else {
            throw new RuntimeException(context.toString() + " must implement SearchByLocationListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
