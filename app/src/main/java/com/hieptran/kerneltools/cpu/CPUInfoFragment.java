package com.hieptran.kerneltools.cpu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.hieptran.kerneltools.R;

/**
 * Created by hieptran on 31/12/2015.
 */
public class CPUInfoFragment extends Fragment {
    private String title;
    private Spinner mGovernorSpiner;
    private Spinner mAvailableCPUFreqSpiner;
    private int page;
    public  CPUInfoFragment () {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cpu_info_fragment, container, false);

        return view;
    }
}
