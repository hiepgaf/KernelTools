package com.hieptran.kerneltools;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.hieptran.kerneltools.utils.AdsTest;

/**
 * Created by Hiep on 12/30/2015.
 */
public class AboutActivity extends Fragment {
    private TextView mInfo;
    private ImageView mAboutIcon;
    AdRequest adRequest;
    public AboutActivity() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.about, container, false);
        mAboutIcon = (ImageView) rootView.findViewById(R.id.about_icon);
        mAboutIcon.setOnClickListener(about_icon_click);
        AdView mAdView = (AdView) rootView.findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        return rootView;
    }
    View.OnClickListener about_icon_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           // Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://forum.xda-developers.com/member.php?u=6965840"));
            Intent i = new Intent(getContext(), AdsTest.class);
            startActivity(i);
        }
    };
}
