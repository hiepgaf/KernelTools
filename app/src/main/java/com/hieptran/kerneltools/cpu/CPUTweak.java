package com.hieptran.kerneltools.cpu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.hieptran.kerneltools.MainActivity;
import com.hieptran.kerneltools.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by hieptran on 31/12/2015.
 */
public class CPUTweak extends Fragment {
    static Context context;
    private CPUManager mCPUmanager;
    private View mRootview;
    //String[] availableFrequenciesArray;
    String selectedGovernor, selectedMaximumFrequency, selectedMinimumFrequency;
    int selectedMaxFreq, selectedMinFreq;
    Button applySelectedCPUFrequencyButton;
    Spinner maxFreqSpinner, minFreqSpinner, governorSpinner;
    ArrayList<String> availableFrequencies, availableFrequenciesForSpinner,
            availableGovernorsForSpinner;
    int frequency;
    String selectedRealMaximumFrequency, selectedRealMinimumFrequency, governor, currentCPUFrequency;
    TextView currentGovernorTV;
    public  CPUTweak () {

    }
    public Context _context() {
        context = getContext();

        return context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         mRootview = inflater.inflate(R.layout.cpu_info_fragment, container, false);
        currentGovernorTV = (TextView) mRootview.findViewById(R.id.currentGovernor);
        governorSpinner = (Spinner) mRootview.findViewById(R.id.governorSpinner);
        applySelectedCPUFrequencyButton = (Button) mRootview.findViewById(
                R.id.applySelectedCPUFrequenciesButton);
        mCPUmanager = new CPUManager();
        //init
        currentGovernorTV.setText(mCPUmanager.readCurrentMaxCPUFreq());
        _currentCPUFrequencyTextViewUpdate();
        return mRootview;
    }
    public void _currentCPUFrequencyTextViewUpdate() {
        final TextView currentCPUFrequencyTV = (TextView) mRootview.findViewById(R.id.currentCPUFreq);
        Thread currentCPUFrequencyUpdate = new Thread() {
            @Override
            public void run() {
                try {
                    //noinspection InfiniteLoopStatement
                    while (true) {
                        Thread.sleep(600);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String currentCPUFrequencyString = _readCurrentCPUFrequency();
                                String finalCurrentCPUFreq = currentCPUFrequencyString.trim();
                                currentCPUFrequencyTV.setText(finalCurrentCPUFreq + " KHz");
                            } //run()
                        }); //MainActivity.this
                    } //WHILE
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } //try-catch
            } //run()
        }; //thread
        currentCPUFrequencyUpdate.start();
    } //cpuFreqTextViewUpdate
    public String _readCurrentCPUFrequency() {
        ProcessBuilder readOutCurrentCPUFrequency;
        String currentCPUFrequency = "";

        try {
            String[] currentCPUFrequencyFile = {"/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq"};
            readOutCurrentCPUFrequency = new ProcessBuilder(currentCPUFrequencyFile);
            Process readProcess = readOutCurrentCPUFrequency.start();
            InputStream readInCurrentCPUFrequency = readProcess.getInputStream();
            byte[] read = new byte[1024];
            while (readInCurrentCPUFrequency.read(read) != -1) {
                currentCPUFrequency = currentCPUFrequency + new String(read);
            }
            readInCurrentCPUFrequency.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Log.d("HiepTHb","Current Freq" + currentCPUFrequency);
        return currentCPUFrequency;
    }
}
