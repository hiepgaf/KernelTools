package com.hieptran.kerneltools.system;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hieptran.kerneltools.R;

/**
 * Created by Hiep on 12/31/2015.
 */
public class SystemInfoFragment extends Fragment {
    private TextView mModel;
    private TextView mManufacturer;
    private TextView mBoard;
    private TextView mHardWare;
    private TextView mScreenSz;
    private TextView mScreenDst;
    private TextView mTotalRAM;
    private TextView mAvaiRAM;
    private TextView mAvaiStorage;
    private TextView mTotalStorage;


    public SystemInfoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_device_info, container, false);
       mModel = (TextView) rootView.findViewById(R.id.device_model);
       mManufacturer = (TextView) rootView.findViewById(R.id.device_manu);
       mBoard = (TextView) rootView.findViewById(R.id.device_board);
       mHardWare = (TextView) rootView.findViewById(R.id.device_hardware);
       mScreenSz = (TextView) rootView.findViewById(R.id.device_scr_sz);
       mScreenDst = (TextView) rootView.findViewById(R.id.device_scr_dst);
       mTotalRAM = (TextView) rootView.findViewById(R.id.device_total_ram);
       mAvaiRAM = (TextView) rootView.findViewById(R.id.device_avai_ram);
       mTotalStorage = (TextView) rootView.findViewById(R.id.device_total_storage);
       mAvaiStorage = (TextView) rootView.findViewById(R.id.device_avai_storage);
       _setStaticVar();
        return rootView;
    }
   public void _setStaticVar() {
	   
   }
}
