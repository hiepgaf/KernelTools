package com.hieptran.kerneltools;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Hiep on 12/30/2015.
 */
public class AboutActivity extends Fragment{
    private Context mContext;
    public AboutActivity(Context context) {
        // Empty constructor required for fragment subclasses
        mContext = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.about, container, false);
        return rootView;
    }
}
