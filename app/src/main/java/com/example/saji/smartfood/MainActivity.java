package com.example.saji.smartfood;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int NUM_PAGES = 3;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    static UserModel loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        int userID = extras.getInt(Configs.USER_ID);
        int userType = extras.getInt(Configs.USER_TYPE);
        String emailAddress = extras.getString(Configs.USER_EMAIL);
        loggedUser = new UserModel(userID, emailAddress, userType);
        if (userType == Configs.USER_FOODIE_ID) {
            setContentView(R.layout.foodie_home_page);
        }
        mPager = findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 1:
                    return new FoodMap();
                case 2:
                    return new CookerRecipes();
                case 3:
                    // about not showing maybe count starts from 0
                    return new AboutTab();
            }
            return new FoodMap();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
