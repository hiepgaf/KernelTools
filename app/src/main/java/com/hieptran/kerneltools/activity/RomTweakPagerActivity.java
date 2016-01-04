package com.hieptran.kerneltools.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hieptran.kerneltools.R;
import com.hieptran.kerneltools.cpu.CPUTweak;
import com.hieptran.kerneltools.fragments.GovernorFragment;
import com.hieptran.kerneltools.fragments.RomInfoFragment;
import com.hieptran.kerneltools.fragments.RomTweakFragment;

/**
 * Created by hieptran on 04/01/2016.
 */
public class RomTweakPagerActivity extends Fragment{
    public RomTweakPagerActivity( ) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_rom_pager, container, false);
        final ViewPager mViewPager = (ViewPager) rootView.findViewById(R.id.rom_pager);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setAdapter(new SlidePagerAdapter(getActivity().getSupportFragmentManager()));

        return rootView;
    }

    /* PagerAdapter class */
    public class SlidePagerAdapter extends FragmentPagerAdapter {
        public SlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {

			/*
			 * IMPORTANT: This is the point. We create a RootFragment acting as
			 * a container for other fragments
			 */
            if (position == 1)
                return new RomTweakFragment();
            else
                return new RomInfoFragment();
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
