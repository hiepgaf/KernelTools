package com.hieptran.kerneltools.cpu;


import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hieptran.kerneltools.R;

public class CPUTweakActivity extends Fragment {
    private TextView mInfo;
    private ImageView mAboutIcon;
    private Activity mContext;
    private String mTiles;
    public static int CURRENT_ID =0;

    public CPUTweakActivity( ) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_cputweak, container, false);
        final ViewPager mViewPager = (ViewPager) rootView.findViewById(R.id.cpu_pager);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                CURRENT_ID = position;
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
                return new CPUTweak();
            else
                return new CPUInfoFragment();
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
