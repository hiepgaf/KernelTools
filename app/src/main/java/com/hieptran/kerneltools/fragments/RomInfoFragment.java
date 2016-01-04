package com.hieptran.kerneltools.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.hieptran.kerneltools.R;

/**
 * Created by hieptran on 04/01/2016.
 */
public class RomInfoFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rom_info, container, false);

        //mCurrentGovernor.setText(_readCurrentGovernor());
        return rootView;
    }
}
