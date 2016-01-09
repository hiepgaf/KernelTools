package com.hieptran.kerneltools.utils;

import com.hieptran.kerneltools.BuildConfig;

/**
 * Created by hieptran on 05/01/2016.
 */
public interface Constants {
    String TAG = "HiepTH - Kernel Tools";
    String VERSION_NAME = BuildConfig.VERSION_NAME;

    /**
     * Dinh nghia cac path
     * La duong dan mac dinh den cac tham so cua  CPU, Battery, GPU
     */

    //Thong tin chung
    String PROC_VERSION = "/proc/version";
    String PROC_CPUINFO = "/proc/cpuinfo";
    String PROC_MEMINFO = "/proc/meminfo";

    //Duong dan cac tham so cua CPU
    // CPU
    String CPU_CUR_FREQ = "/sys/devices/system/cpu/cpu%d/cpufreq/scaling_cur_freq";
    String CPU_TEMP_ZONE0 = "/sys/class/thermal/thermal_zone0/temp";
    String CPU_TEMP_ZONE1 = "/sys/class/thermal/thermal_zone1/temp";
    String CPU_CORE_ONLINE = "/sys/devices/system/cpu/cpu%d/online";
    String CPU_MAX_FREQ = "/sys/devices/system/cpu/cpu%d/cpufreq/scaling_max_freq";
    String CPU_MAX_FREQ_KT = "/sys/devices/system/cpu/cpu%d/cpufreq/scaling_max_freq_kt";
    String CPU_ENABLE_OC = "/sys/devices/system/cpu/cpu%d/cpufreq/enable_oc";
    String CPU_MIN_FREQ = "/sys/devices/system/cpu/cpu%d/cpufreq/scaling_min_freq";
    String CPU_MAX_SCREEN_OFF_FREQ = "/sys/devices/system/cpu/cpu%d/cpufreq/screen_off_max_freq";
    String CPU_MSM_CPUFREQ_LIMIT = "/sys/kernel/msm_cpufreq_limit/cpufreq_limit";
    String CPU_AVAILABLE_FREQS = "/sys/devices/system/cpu/cpu%d/cpufreq/scaling_available_frequencies";
    String CPU_TIME_STATE = "/sys/devices/system/cpu/cpufreq/stats/cpu%d/time_in_state";
    String CPU_TIME_STATE_2 = "/sys/devices/system/cpu/cpu%d/cpufreq/stats/time_in_state";
    String CPU_SCALING_GOVERNOR = "/sys/devices/system/cpu/cpu%d/cpufreq/scaling_governor";
    String CPU_AVAILABLE_GOVERNORS = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_available_governors";

    String CPU_GOVERNOR_TUNABLES = "/sys/devices/system/cpu/cpufreq";
    String CPU_GOVERNOR_TUNABLES_CORE = "/sys/devices/system/cpu/cpu%d/cpufreq";

    String CPU_MC_POWER_SAVING = "/sys/devices/system/cpu/sched_mc_power_savings";
    String CPU_WQ_POWER_SAVING = "/sys/module/workqueue/parameters/power_efficient";
    String CPU_AVAILABLE_CFS_SCHEDULERS = "/sys/devices/system/cpu/sched_balance_policy/available_sched_balance_policy";
    String CPU_CURRENT_CFS_SCHEDULER = "/sys/devices/system/cpu/sched_balance_policy/current_sched_balance_policy";

    String CPU_QUIET = "/sys/devices/system/cpu/cpuquiet";
    String CPU_QUIET_ENABLE = CPU_QUIET + "/cpuquiet_driver/enabled";
    String CPU_QUIET_AVAILABLE_GOVERNORS = CPU_QUIET + "/available_governors";
    String CPU_QUIET_CURRENT_GOVERNOR = CPU_QUIET + "/current_governor";

    String CPU_BOOST = "/sys/module/cpu_boost/parameters";
    String CPU_BOOST_ENABLE = CPU_BOOST + "/cpu_boost";
    String CPU_BOOST_ENABLE_2 = CPU_BOOST + "/cpuboost_enable";
    String CPU_BOOST_DEBUG_MASK = CPU_BOOST + "/debug_mask";
    String CPU_BOOST_MS = CPU_BOOST + "/boost_ms";
    String CPU_BOOST_SYNC_THRESHOLD = CPU_BOOST + "/sync_threshold";
    String CPU_BOOST_INPUT_MS = CPU_BOOST + "/input_boost_ms";
    String CPU_BOOST_INPUT_BOOST_FREQ = CPU_BOOST + "/input_boost_freq";
    String CPU_BOOST_WAKEUP = CPU_BOOST + "/wakeup_boost";
    String CPU_BOOST_HOTPLUG = CPU_BOOST + "/hotplug_boost";

}
