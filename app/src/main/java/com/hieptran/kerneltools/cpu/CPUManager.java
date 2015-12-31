package com.hieptran.kerneltools.cpu;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by hieptran on 31/12/2015.
 */
public class CPUManager {
    private static final String PRE_PATH = "/sys/devices/system/cpu/cpu0/";
    private Scanner file_scanner;

    public String[] readAvailableFreq() throws Exception {
        String file_path = PRE_PATH + "cpufreq/scaling_available_frequencies";
        File scalingAvailableFrequenciesFile = new File(file_path);
        ArrayList<String> availableFrequencies = new ArrayList<String>();
        file_scanner = new Scanner(scalingAvailableFrequenciesFile);
        while (file_scanner.hasNext()) {
            availableFrequencies.add(file_scanner.next());
        }
        String[] availableFrequenciesArray = new String[availableFrequencies.size()];
        availableFrequenciesArray = availableFrequencies.toArray(availableFrequenciesArray);

        return availableFrequenciesArray;
    }

    public String[] readAvailableGovernor() throws Exception {
        String file_path = PRE_PATH + "cpufreq/scaling_available_governors";
        File scalingAvailableGovernorFile = new File(file_path);
        ArrayList<String> availableGovernor = new ArrayList<String>();
        file_scanner = new Scanner(scalingAvailableGovernorFile);
        while (file_scanner.hasNext()) {
            availableGovernor.add(file_scanner.next());
        }
        String[] availableGovernorArray = new String[availableGovernor.size()];
        availableGovernorArray = availableGovernor.toArray(availableGovernorArray);

        return availableGovernorArray;
    }


    public String readCurrentMaxCPUFreq() {
        ProcessBuilder readOutCurrentMaxCPUFrequency;
        String currentMaxCPUFrequency = "";

        try {
            String[] currentMaxCPUFrequencyFile = {"/system/bin/cat",
                    PRE_PATH+"cpufreq/scaling_max_freq"};
            readOutCurrentMaxCPUFrequency = new ProcessBuilder(currentMaxCPUFrequencyFile);
            Process readProcess2 = readOutCurrentMaxCPUFrequency.start();
            InputStream readInCurrentMaxCPUFrequency = readProcess2.getInputStream();
            byte[] read2 = new byte[1024];
            while (readInCurrentMaxCPUFrequency.read(read2) != -1) {
                currentMaxCPUFrequency = currentMaxCPUFrequency + new String(read2);
            }
            readInCurrentMaxCPUFrequency.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return currentMaxCPUFrequency;
    }

    public String readCurrentMinCPUFreq() {
        ProcessBuilder readOutCurrentMinCPUFrequency;
        String currentMinCPUFrequency = "";

        try {
            String[] currentMinCPUFrequencyFile = {"/system/bin/cat",
                    PRE_PATH+"cpufreq/scaling_min_freq"};
            readOutCurrentMinCPUFrequency = new ProcessBuilder(currentMinCPUFrequencyFile);
            Process process3 = readOutCurrentMinCPUFrequency.start();
            InputStream readInCurrentMinCPUFrequency = process3.getInputStream();
            byte[] read3 = new byte[1024];
            while (readInCurrentMinCPUFrequency.read(read3) != -1) {
                currentMinCPUFrequency = currentMinCPUFrequency + new String(read3);
            }
            readInCurrentMinCPUFrequency.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return currentMinCPUFrequency;
    }

    public void applySelectedMinCPUFreq() {

    }

    public void applySelectedMaxCPUFreq() {

    }

    public void applySelectedGovernor() {

    }
}
