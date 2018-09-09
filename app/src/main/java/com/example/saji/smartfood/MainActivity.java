package com.example.saji.smartfood;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static  int numberOfPages;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    static UserModel loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        Bundle extras = getIntent().getExtras();
        int userID = extras.getInt(Configs.USER_ID);
        int userType = extras.getInt(Configs.USER_TYPE);
        String emailAddress = extras.getString(Configs.USER_EMAIL);
        loggedUser = new UserModel(userID, emailAddress, userType);
        if (userType == Configs.USER_FOODIE_ID) {
            numberOfPages = 2;
        }else{
            numberOfPages= 3;
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
                case 0:
                    return new FoodMap();
                case 1:
                    return new AboutTab();
                case 2:
                    return new CookerRecipes();
            }
            return new FoodMap();
        }

        @Override
        public int getCount() {
            return numberOfPages;
        }
    }
}