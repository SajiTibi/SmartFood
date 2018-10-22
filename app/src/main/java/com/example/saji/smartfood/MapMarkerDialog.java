package com.example.saji.smartfood;

import android.annotation.SuppressLint;
import android.support.v4.app.DialogFragment;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MapMarkerDialog extends DialogFragment {

    View view;
    int cookerID = 0;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private String[] page_titles;
    private ArrayList<RegularRecipe> cookerDishes = new ArrayList<>();


    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.home_page, container, false);
        view.setMinimumWidth(1200);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        LocationManager locationManager = (LocationManager) getActivity().getSystemService
                (getContext().LOCATION_SERVICE);

        page_titles = new String[]{"Dishes", "Reviews"};
        mPager = view.findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        return view;
    }

    public void setCookerID(int cookerID) {
        this.cookerID = cookerID;
    }

    public void setCookerDishes(ArrayList<RegularRecipe> cookerDishes) {
        this.cookerDishes = cookerDishes;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    public ScreenSlidePagerAdapter(FragmentManager fm) {
        super(fm);
    }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            if(position == 0) {
                DishesMarkerFragment dialog = new DishesMarkerFragment();
                dialog.setCookerDishes(cookerDishes);
                dialog.setCookerID(cookerID);
                return dialog;
            } else {
                ReviewsMarkerFragment reviewsFragment = new ReviewsMarkerFragment(cookerID);
                reviewsFragment.setButtonVisibility(View.VISIBLE);
                return reviewsFragment;
            }
        }

        @Override
        public CharSequence getPageTitle ( int position){
            return page_titles[position];
        }

        @Override
        public int getCount() {
            final int PAGES = 2;
            return PAGES;
        }
    }
}
