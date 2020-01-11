package com.ashraf.faraaz.hydtoilets;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


public class OptionsFragment extends Fragment {

    private FragmentSelectionListener listener;
    interface FragmentSelectionListener {
        void onFragmentSelected(String fragmentName);
    }

    Button searchByLocationButton;
    Button searchByCircleButton;

    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_options, container, false);
        searchByLocationButton = v.findViewById(R.id.searchByLocationButton);
        searchByCircleButton = v.findViewById(R.id.searchByCircleButton);

        searchByCircleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fragmentName = "SearchByCircleFragment";
                listener.onFragmentSelected(fragmentName);
            }
        });

        searchByLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager pm = getActivity().getPackageManager();
                int hasPerm = pm.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, getActivity().getPackageName());
                if (hasPerm != PackageManager.PERMISSION_GRANTED) {
                    new UtilsClass().checkLocationPermission(getActivity());
                    new UtilsClass().displayLocationSettingsRequest(getActivity(), getActivity());
                }
                else {
                    new UtilsClass().displayLocationSettingsRequest(getActivity(), getActivity());
                    String fragmentName = "SearchByLocationFragment";
                    listener.onFragmentSelected(fragmentName);
                }
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof FragmentSelectionListener) {
            listener = (FragmentSelectionListener) context;
        }
        else {
            throw new RuntimeException(context + " should implement FragmentSelectionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
