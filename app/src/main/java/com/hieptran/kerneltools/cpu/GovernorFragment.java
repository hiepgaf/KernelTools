package com.hieptran.kerneltools.cpu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.hieptran.kerneltools.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Hiep on 12/31/2015.
 */
public class GovernorFragment extends Fragment {
    private Spinner mGovernorSpiner;
    private TextView mCurrentGovernor;
    private LinearLayout mViewMain;
    private ArrayList<String> mArrayAvailableGovernor;
    String selectedGovernor,governor;
    private ArrayList<String> mListVarOfGov;
    public GovernorFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.governor_fragment, container, false);
        mGovernorSpiner = (Spinner) rootView.findViewById(R.id.avai_gov_spinner);
        mCurrentGovernor = (TextView)rootView.findViewById(R.id.current_gov);
        mViewMain = (LinearLayout) rootView.findViewById(R.id.governor_main);
        try {
            getCMDOutputString(new String[]{"/system/bin/ls", "/sys/devices/system/cpu/cpu0/"});
           // _readAvailableGovernorsForSpinner();
          //  readListVarOfGov();
        } catch (Exception e) {
            e.printStackTrace();
        }


        mCurrentGovernor.setText(_readCurrentGovernor());
        return rootView;
    }
    public void _readAvailableGovernorsForSpinner() throws Exception {
        File scalingAvailableGovernorsFile = new File(
                "/sys/devices/system/cpu/cpu0/cpufreq/scaling_available_governors");
        mArrayAvailableGovernor = new ArrayList<>();
        Scanner scanner4 = new Scanner(scalingAvailableGovernorsFile);
        while (scanner4.hasNext()) {
            governor = scanner4.next();
            mArrayAvailableGovernor.add(governor);
        }
        _selectGovernorSpinner(mArrayAvailableGovernor);
    }
    private String _selectGovernorSpinner(final List<String> availableGovernorsForSpinner) {
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
                availableGovernorsForSpinner);
        mGovernorSpiner.setAdapter(adapter4);
        mGovernorSpiner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position,
                                               long id) {
                        selectedGovernor = availableGovernorsForSpinner.get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

        return selectedGovernor;
    }
    private void readListVarOfGov() throws Exception {
        //mArrayAvailableGovernor.addAll(getCMDOutputString(new String[]{"/system/bin/ls", "/sys/devices/system/cpu/cpu0/cpufreq/"+governor}));
      for(int i =0;i <(getCMDOutputString(new String[]{"/system/bin/ls", "/sys/devices/system/cpu/cpu0/cpufreq/interactive"}).size());i++ ) {
          Log.d("TestOut",getCMDOutputString(new String[]{"/system/bin/ls", "/sys/devices/system/cpu/cpu0/cpufreq/interactive"}).get(i).toString());
      }

      /*  File file[] = pathIntoGov.listFiles(new CpuFilter());
        Log.d("HiepTHb", "Test size" + file.length);
      for(int i =0;i <file.length;i++) {
            TextView tv = new TextView(getActivity());
          tv.setText(file[i].getName() + "");
          mViewMain.addView(tv);
          mArrayAvailableGovernor.add("/sys/devices/system/cpu/cpu0/cpufreq/" + governor);
        }*/
    }
    public String _readCurrentGovernor() {
        ProcessBuilder readOutCurrentGovernor;
        String currentGovernor = "";

        try {
            String[] currentGovernorFile = {"/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor"};
            readOutCurrentGovernor = new ProcessBuilder(currentGovernorFile);
            Process readProcess4 = readOutCurrentGovernor.start();
            InputStream readInCurrentGovernor = readProcess4.getInputStream();
            byte[] read4 = new byte[1024];
            while (readInCurrentGovernor.read(read4) != -1) {
                currentGovernor = currentGovernor + new String(read4);
            }
            readInCurrentGovernor.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return currentGovernor;
    }
    /**
     * HiepTHb - Doc danh sach out put khi su dung ls/cat/...
     * Get command output string.
     */
    public  ArrayList getCMDOutputString(String[] args) {
    	ArrayList<String> listOutputString = new ArrayList<>();
        mArrayAvailableGovernor = new ArrayList<>();
        try {
            ProcessBuilder cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            StringBuilder sb = new StringBuilder();
            byte[] re = new byte[64];
            int len;
            while ((len = in.read(re)) != -1) {
                sb.append(new String(re, 0, len));
                mArrayAvailableGovernor.add(new String(re, 0, len));

            }
            in.close();
            process.destroy();
            Log.d("HiepTHB",sb.toString());
            return listOutputString;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
