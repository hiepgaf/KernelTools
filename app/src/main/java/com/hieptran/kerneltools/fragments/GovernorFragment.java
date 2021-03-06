package com.hieptran.kerneltools.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.hieptran.kerneltools.R;
import com.hieptran.kerneltools.system.Shell;
import com.hieptran.kerneltools.utils.AdsTest;
import com.hieptran.kerneltools.utils.ProgressGenerator;
import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.CommandCapture;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import dmax.dialog.SpotsDialog;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by Hiep on 12/31/2015.
 */
public class GovernorFragment extends Fragment implements CompoundButton.OnCheckedChangeListener{
    //Final String
    private   String GETLISTPATH = "/sys/devices/system/cpu/cpu0/cpufreq";
    private   String GETLISTPATH_BKAV = "/sys/devices/system/cpu/cpufreq";
    private Spinner mGovernorSpiner;
    private TextView mCurrentGovernor;
    private LinearLayout mViewMain;
    private ArrayList<String> mArrayAvailableGovernor;
    ViewGroup mViewGroupTuning;
    String selectedGovernor,governor;
    String mCurrentGovernorText;
    private Button mBtnSetGovernor;
    private Button mBtnSave;
    private View rootView;
    private SwitchCompat swt;
    private ArrayList<TextView> mLsTextView;
    private ArrayList<EditText> mLsEditText;

    public GovernorFragment() {
    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_cpu_info, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.update_view){
           updateView();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.governor_fragment, container, false);
        mGovernorSpiner = (Spinner) rootView.findViewById(R.id.avai_gov_spinner);
        mCurrentGovernor = (TextView)rootView.findViewById(R.id.current_gov);
        mViewMain = (LinearLayout) rootView.findViewById(R.id.governor_main);
        mViewGroupTuning = (ViewGroup) rootView.findViewById(R.id.main_tuning);
        mBtnSetGovernor = (Button) rootView.findViewById(R.id.btn_set_gov);
        mBtnSetGovernor.setOnClickListener(set_gov_click_listener);
        mBtnSave = (Button) rootView.findViewById(R.id.btn_tunning);
        mBtnSave.setOnClickListener(save_click_listener);
        swt = (SwitchCompat) rootView.findViewById(R.id.Switch);
        swt.setOnCheckedChangeListener(this);
        setHasOptionsMenu(true);
        mLsEditText = new ArrayList<>();
        mLsTextView = new ArrayList<>();
        final MaterialDialog mMaterialDialog = new MaterialDialog(getContext());
        mMaterialDialog.setTitle("Update Governor Infomations ?")
                .setPositiveButton("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateView();
                        mMaterialDialog.dismiss();
                    }
                })
                .setNegativeButton("CANCEL", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();

                    }
                });

        mMaterialDialog.show();

        return rootView;
    }
    View.OnClickListener save_click_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String mPath;
            if(Build.MANUFACTURER.equalsIgnoreCase("bkav")||Build.MANUFACTURER.equalsIgnoreCase("lge"))
                mPath = GETLISTPATH_BKAV +"/"+_readCurrentGovernor()+"/";
            else
                mPath = GETLISTPATH+"/"+_readCurrentGovernor()+"/";
            for (int i = 0; i < mLsTextView.size(); i++) {
                try {
                    echoCommands(mLsEditText.get(i).getText().toString(),mLsTextView.get(i).getText().toString());
                    Log.d("HiepTHb", "Input : "+ mLsEditText.get(i).getText().toString() + "\n" + "Path: "+mPath+mLsTextView.get(i).getText() );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            updateView();
        }
    };
    //SetGovernor click
    View.OnClickListener set_gov_click_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            try {
                setGovernorCommands(selectedGovernor);
            } catch (Exception e){
                Log.d("HiepTHb","set_gov_click_listener Exception : "+ e.getMessage());
            }

            updateView();
        }
    };

    //Echo Commands

    private void echoCommands(String input, String path) throws Exception{
        String commandl;
        //Shell.sudo(commandl);
        if(Build.MANUFACTURER.equalsIgnoreCase("bkav") || Build.MANUFACTURER.equalsIgnoreCase("lge") )
            commandl = "echo " + input +
                    " > /sys/devices/system/cpu/cpufreq" +"/"+_readCurrentGovernor()+"/"+path;
        else
            commandl = "echo " + input +
                    " > /sys/devices/system/cpu/cpu0/cpufreq"+"/"+_readCurrentGovernor()+"/"+path;
        Log.d("HiepTHB","check commands: "+commandl);
        try {
            CommandCapture setGovernor1 = new CommandCapture(0,commandl);
            RootTools.getShell(true).add(setGovernor1);
        } catch (RootDeniedException | IOException rde) {
            rde.printStackTrace();
        } catch (TimeoutException te) {
            te.printStackTrace();
        }
    }
    //SetGovernor Run
    private void setGovernorCommands(String mGov) throws Exception{
        String commandl = "echo \"" + mGov +
                    "\" > /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor";
       Log.d("HiepTHb", "setGovernorCommands : "+commandl);
           // Shell.sudo(commandl);
        try {
            CommandCapture setGovernor1 = new CommandCapture(0,commandl);
            RootTools.getShell(true).add(setGovernor1);
        } catch (RootDeniedException | IOException rde) {
            rde.printStackTrace();
        } catch (TimeoutException te) {
            te.printStackTrace();
        }
    }
    //Update View State
    private void updateView() {
        AlertDialog updateProgress = new SpotsDialog(getContext(),"Please wait...");
        updateProgress.show();
        try {
            mCurrentGovernor.setText(_readCurrentGovernor());
            mCurrentGovernorText = _readCurrentGovernor();
            _readAvailableGovernorsForSpinner();
            getBinCommands(mCurrentGovernorText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            updateProgress.dismiss();
        }
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
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
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
    /* private void readListVarOfGov() throws Exception {
         File pathIntoGov = new File("/sys/devices/system/cpu/cpu0/cpufreq/interective");
         File file[] = pathIntoGov.listFiles();
         Log.d("HiepTHb", "Test size" + file.length);
       for(int i =0;i <file.length;i++) {
           mArrayAvailableGovernor.add(file[i].getName()+"-");
         }
         _selectGovernorSpinner(mArrayAvailableGovernor);
     }*/
    public String _readCurrentGovernor() {
        String currentGovernor = "";

        try {

            currentGovernor= Shell.exec("cat /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor").toString();

        } catch (Exception ex) {
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
            byte[] re = new byte[1024];
            int len;
            while ((len = in.read(re)) != -1) {
                sb.append(new String(re, 0, len));
                mArrayAvailableGovernor.add(new String(re, 0, len)+"");

            }
            in.close();
            process.destroy();
            Log.d("HiepTHB", sb.toString());
            _selectGovernorSpinner(mArrayAvailableGovernor);
            Toast.makeText(getContext(),mArrayAvailableGovernor.get(0)+"\n test \n"+mArrayAvailableGovernor.size(),Toast.LENGTH_LONG).show();
            return listOutputString;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    private void getBinCommands(String input) {
        String[] mCommandOutputLables = null;
        ArrayList<String> mCommandOutputValues = new ArrayList<>();
        String mInput ="";
        if(Build.MANUFACTURER.equalsIgnoreCase("bkav")||Build.MANUFACTURER.equalsIgnoreCase("lge"))
            mInput = GETLISTPATH_BKAV+"/"+input+"/";
        else
            mInput = GETLISTPATH+"/"+input+"/";


        try {
            mCommandOutputLables = Shell.exec("ls "+mInput).split("\\s+");

        } catch (Shell.ShellException e) {
            // Log.e(DriverActivity.LOG_TAG, e.getMessage());
        }
        mViewGroupTuning.removeAllViews();
        for(int i=0;i<mCommandOutputLables.length;i++) {
            String mLable = mCommandOutputLables[i].toString();
            View mv = getLayoutInflater(getArguments()).inflate(R.layout.tuning_view, null);
            TextView tv = (TextView) mv.findViewById(R.id.label_tuning);
            tv.setText(mLable);
            TextView tv2 = (TextView) mv.findViewById(R.id.desc_lable);
            StringBuilder stringDesc = new StringBuilder();

            EditText mSeekBar = (EditText) mv.findViewById(R.id.tuning_edittext);

            try {
                mSeekBar.setText(catCommandsOutput(mInput + mLable));

            }catch (Exception e) {

            }
            stringDesc.append(tv.getText() + " - description");
            tv2.setText(stringDesc.toString());
            mLsTextView.add(tv);
            mLsEditText.add(mSeekBar);
            mViewGroupTuning.addView(mv);

        }
        //return commands;
    }
    private String catCommandsOutput(String path) throws Exception{
        Log.d("catCommandsOutput", "Path: cat " + path);
        //Shell.sudo()
        return Shell.sudo("cat " + path).toString();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {

            case R.id.Switch:

                if(isChecked){
                    mViewGroupTuning.setEnabled(true);
                    mBtnSave.setEnabled(true);
                }else{
                    mBtnSave.setEnabled(false);
                    mViewGroupTuning.setEnabled(false);
                }
                break;

            default:
                break;
        }
    }


}