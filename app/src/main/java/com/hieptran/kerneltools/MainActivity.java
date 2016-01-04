package com.hieptran.kerneltools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.hieptran.kerneltools.activity.RomTweakPagerActivity;
import com.hieptran.kerneltools.cpu.CPUTweakActivity;
import com.hieptran.kerneltools.fragments.DevicesInfoFragment;
import com.hieptran.kerneltools.fragments.GovernorFragment;
import com.hieptran.kerneltools.fragments.RomInfoFragment;
import com.hieptran.kerneltools.fragments.SystemInfoFragment;
import com.stericson.RootTools.RootTools;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mOptionList;
    private String[] mOptionTitles;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    static String mIMEI;
    private String ADID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new TaskForRoot(this).execute();
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
        //mIMEI = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId().toString();
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
        if (savedInstanceState == null) {
            selectItem(4);
        }
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
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (position) {
            case 0:

                Fragment device_info_fragment = new DevicesInfoFragment();
                fragmentManager.beginTransaction().replace(R.id.content_frame, device_info_fragment).commit();
                mOptionList.setItemChecked(position, true);
                setTitle("Devices Infomation");
                mDrawerLayout.closeDrawer(mOptionList);
                break;
            case 1:

                Fragment system_info_fragment = new SystemInfoFragment();
                fragmentManager.beginTransaction().detach(system_info_fragment);
                fragmentManager.beginTransaction().replace(R.id.content_frame, system_info_fragment).commit();
                mOptionList.setItemChecked(position, true);
                setTitle("System Infomation");
                mDrawerLayout.closeDrawer(mOptionList);
                break;
            case 2:
                mDrawerLayout.closeDrawer(mOptionList);
                setTitle("CPU Tweak");
                AlertDialog dialog = new SpotsDialog(this,"Waiting for get \n Governor infomation ");
                dialog.show();
                Fragment cpu_tweak_fragment = new GovernorFragment();
                fragmentManager.beginTransaction().detach(cpu_tweak_fragment);
                fragmentManager.beginTransaction().replace(R.id.content_frame, cpu_tweak_fragment).commit();
                mOptionList.setItemChecked(position, true);
                dialog.dismiss();
                break;
            case 3:
                Fragment rom_pager_fragment = new RomTweakPagerActivity();
                fragmentManager.beginTransaction().detach(rom_pager_fragment);

                fragmentManager.beginTransaction().replace(R.id.content_frame, rom_pager_fragment).commit();
                mOptionList.setItemChecked(position, true);
                setTitle("ROM Tweak");
                mDrawerLayout.closeDrawer(mOptionList);
                break;
            default:
                Fragment about = new AboutActivity();

                fragmentManager.beginTransaction().replace(R.id.content_frame, about).commit();
                mOptionList.setItemChecked(position, true);
                setTitle("Kernel Tools");
                mDrawerLayout.closeDrawer(mOptionList);
                break;

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

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mOptionList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
    private class TaskForRoot extends AsyncTask<Void,Void,Boolean> {
        Context mconText;
        AlertDialog dialog  ;
        public TaskForRoot(Context mContext) {
            mconText = mContext;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
             dialog = new SpotsDialog(mconText,"Getting Root Permission...");

            dialog.show();



        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return RootTools.isRootAvailable();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            dialog.dismiss();            if(aBoolean) {
                Toast.makeText(mconText, "Da lay quyen root", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(mconText, "thietbi chua root", Toast.LENGTH_SHORT).show();

        }
    }

}
