package com.hieptran.kerneltools;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mOptionList;
    private String[] mOptionTitles;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    String mIMEI;
    private String ADID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mTitle = mDrawerTitle = getSupportActionBar().getTitle();
        mOptionTitles = getResources().getStringArray(R.array.list_options);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mOptionList = (ListView) findViewById(R.id.left_drawer);
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mOptionList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mOptionTitles));
        mOptionList.setOnItemClickListener(new DrawerItemClickListener());
        mIMEI = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId().toString();
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon

        mDrawerToggle = new ActionBarDrawerToggle(
                MainActivity.this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle("List Option");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void selectItem(int position) {
        // Fragment fragment;
        FragmentManager fragmentManager = getFragmentManager();

        switch (position) {
            case 1:
                Fragment fragment = new CPUInfo();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                mOptionList.setItemChecked(position, true);
                setTitle(mTitle);
                mDrawerLayout.closeDrawer(mOptionList);

                break;
            //default:  // update selected item and title, then close the drawer

        }
        //
        Log.d("HiepTHb", "Test position" + position + " - " + mOptionTitles[position]);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(title);
    }

    public  class CPUInfo extends Fragment {
        TextView CPUInfoTV, deviceManufacturerModelTV;
        String realManufacturer, valueManufacturer, model;

        public CPUInfo() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_cpu_info, container, false);
           // getADID();
            CPUInfoTV = (TextView) rootView.findViewById(R.id.CPUInfoTV);
            CPUInfoTV.setText(_getCPUInfo());

            deviceManufacturerModelTV = (TextView) rootView.findViewById(R.id.deviceManufacturerModelTV);
            realManufacturer = _manufacturer();
            model = _model();
            deviceManufacturerModelTV.setText(_valueManufacturer(realManufacturer) + " " + model);
            return rootView;
        }

        public String _getCPUInfo() {
            StringBuilder stringBuffer = new StringBuilder();
            //noinspection deprecation
            stringBuffer.append("ABI: ").append(Build.CPU_ABI).append("\n");
            stringBuffer.append("Device Name: "+android.os.Build.MODEL).append("\n");
            stringBuffer.append("Product Name: "+android.os.Build.PRODUCT).append("\n");
            stringBuffer.append("Manufacturer Name: " + android.os.Build.MANUFACTURER).append("\n");
            stringBuffer.append("Product Name: "+ android.os.Build.PRODUCT).append("\n");
            stringBuffer.append("Brand Name:"+ android.os.Build.BRAND).append("\n");
            stringBuffer.append("IMEI: "+ mIMEI).append("\n");
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

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
}
