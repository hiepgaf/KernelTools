package com.hieptran.devices.info;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.hieptran.kerneltools.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CpuInfoActivity extends AppCompatActivity {
    TextView CPUInfoTV, deviceManufacturerModelTV;
    String realManufacturer, valueManufacturer, model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu_info);
        CPUInfoTV = (TextView) findViewById(R.id.CPUInfoTV);
        CPUInfoTV.setText(_getCPUInfo());

        deviceManufacturerModelTV = (TextView) findViewById(R.id.deviceManufacturerModelTV);
        realManufacturer = _manufacturer();
        model = _model();
        deviceManufacturerModelTV.setText(_valueManufacturer(realManufacturer) + " " + model);
    }
    public String _getCPUInfo() {
        StringBuilder stringBuffer = new StringBuilder();
        //noinspection deprecation
        stringBuffer.append("ABI: ").append(Build.CPU_ABI).append("\n");
        if (new File("/proc/cpuinfo").exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(
                        new FileReader(new File("/proc/cpuinfo")));
                String aLine;
                while ((aLine = bufferedReader.readLine()) != null) {
                    stringBuffer.append(aLine).append("\n");
                }
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            CPUInfoTV.setText(getString(R.string.device_not_support).toString());
        }
        return stringBuffer.toString();
    }

    public String _manufacturer() {
        return Build.MANUFACTURER;
    }

    public String _model() {
        return Build.MODEL;
    }

    public String _valueManufacturer(String RealModel) {
        switch (RealModel) {
            case "samsung":
                valueManufacturer = "Samsung";
                break;
        }

        return valueManufacturer;
    }
}
