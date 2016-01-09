package com.hieptran.kerneltools.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hieptran on 08/01/2016.
 */
public class CPU implements Constants {
    private static int cores;
    private static int bigCore = -1;
    private static int LITTLEcore = -1;
    private static Integer[][] mFreqs;
    public static int getCoreCount() {
        return cores == 0 ? cores = Runtime.getRuntime().availableProcessors() : cores;
    }
    public static float[] getCpuUsage() {
        return null;
    }
    //Lop Usage dinh nghia cac phuong thuc get Up va Idle time
    private static class Usage {

    }

}
