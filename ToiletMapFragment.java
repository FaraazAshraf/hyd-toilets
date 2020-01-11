package com.ashraf.faraaz.hydtoilets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


public class ToiletMapFragment extends Fragment {

    LinearLayout singleToiletLinearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_toilet_map, container, false);

        String singleToilet = getArguments().getString("singleToilet");

        String coords = singleToilet.split(",")[5] + "," + singleToilet.split(",")[6];

        if(new UtilsClass().connectedToInternet(getActivity())) {
            WebView webView = v.findViewById(R.id.webView);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url){
                    // do your handling codes here, which url is the requested url
                    // probably you need to open that url rather than redirect:
                    view.loadUrl(url);
                    return false; // then it is not handled by default action
                }
            });
            webView.setWebChromeClient(new WebChromeClient() {
                public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                    callback.invoke(origin, true, false);
                }
            });
            webView.loadUrl("https://www.google.com/maps/place/" + coords + "/@" + coords + ",15z");
        }
        else {
            Toast.makeText(getActivity(), "No internet, map cannot be displayed", Toast.LENGTH_SHORT).show();
        }

        return v;
    }
}
