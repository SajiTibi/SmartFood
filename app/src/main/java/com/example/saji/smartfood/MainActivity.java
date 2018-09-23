package com.example.saji.smartfood;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private static int numberOfPages;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    static UserModel loggedUser;
    public static ArrayList<UserModel> allUsers;
    private static final String[] PAGE_TITLES = new String[]{"Food Map", "Recipes", "About"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        Bundle extras = getIntent().getExtras();
        allUsers= new ArrayList<>();
        int userID = extras.getInt(Configs.USER_ID);
        int userType = extras.getInt(Configs.USER_TYPE);
        String fcmToken = extras.getString(Configs.FIREBASE_TOKEN);
        String emailAddress = extras.getString(Configs.USER_EMAIL);
        loggedUser = new UserModel(userID, emailAddress, userType,fcmToken);
        // to update FCM if outdated
        Tools.getInstance().checkAndUpdateMyFCM();
        loadAllUsers();
        if (userType == Configs.USER_FOODIE_ID) {
            numberOfPages = 2;
        } else {
            numberOfPages = 3;
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
            int lastPage = numberOfPages - 1;
            if (position < lastPage) {
                switch (position) {
                    case 0:
                        return new FoodMap();
                    case 1:
                        return new OrdersFragment();
                       // return new CookerRecipes();
                }
            } else {
                return new AboutTab();
            }
            return new FoodMap();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return PAGE_TITLES[position];
        }

        @Override
        public int getCount() {
            return numberOfPages;
        }
    }
    // we need to load all users to in recipes we can assign each recipe to loaded cooker instead
    // of searching for him then, need to do this in other way

    private void loadAllUsers() {
        StringRequest recipesRequest = new StringRequest(Request.Method.POST, Configs
                .ALL_USERS_RETRIEVAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Iterator keyz = jsonResponse.keys();
                        keyz.next();
                        while (keyz.hasNext()) {
                            JSONObject key = jsonResponse.getJSONObject((String) keyz.next());
                            int  uid = key.getInt(Configs.USER_ID);
                            String emailAddress = key.getString(Configs.USER_EMAIL);
                            int userType = key.getInt(Configs.USER_TYPE);
                            String fcmToken = key.getString(Configs.FIREBASE_TOKEN);
                            UserModel newUser = new UserModel(uid,emailAddress,userType,fcmToken);
                            allUsers.add(newUser);
                        }
                    }
                } catch (JSONException ignored) {

                }
            }
        }, null);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(recipesRequest);

    }
}
