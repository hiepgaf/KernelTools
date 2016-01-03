package com.hieptran.kerneltools.utils;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.hieptran.kerneltools.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

/**
 * Created by HiepTran on 1/3/2016.
 */
public class AdsTest extends Activity {
    AdRequest adRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ads_test);
        AdView mAdView = (AdView) findViewById(R.id.adView);
         adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public static void addInterstitialAd(final Activity context, final int id) {
        final InterstitialAd interstitial = new InterstitialAd(context);
        interstitial.setAdUnitId(context.getString(id));
        interstitial.loadAd(new AdRequest.Builder().build());
        interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Call displayInterstitial() function
                if (interstitial.isLoaded()) {
                    interstitial.show();
                }
            }
        });
    }
    //Get MD5 of input string
    public static final String md5(final String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {

        }
        return "";
    }
    //get devices id
    public String getDeviceID() {


            String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
            String deviceId = md5(android_id).toUpperCase();

           // boolean isTestDevice = mAdRequest.isTestDevice(getContext());

            Log.v("HiepTHb", "is Admob Test Device ? " + deviceId + " "); //to confirm it worked
        return deviceId;

    }
}
