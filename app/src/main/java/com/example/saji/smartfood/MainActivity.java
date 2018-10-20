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
    private static final int COOK_PAGES_NUM = 5;
    private static final int FOODIE_PAGES_NUM = 2;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    static UserModel loggedUser;
    public static ArrayList<UserModel> allUsers;
    public static ArrayList<RegularRecipe> allRecipes;

    private String[] PAGE_TITLES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        Bundle extras = getIntent().getExtras();
        allUsers = new ArrayList<>();
        allRecipes= new ArrayList<>();
        int userID = extras.getInt(Configs.USER_ID);
        int userType = extras.getInt(Configs.USER_TYPE);
        String fcmToken = extras.getString(Configs.FIREBASE_TOKEN);
        String emailAddress = extras.getString(Configs.USER_EMAIL);
        double userLongitude = extras.getDouble(Configs.USER_LONGITUDE);
        double userLatitude = extras.getDouble(Configs.USER_LATITUDE);

        PAGE_TITLES = (userType == Configs.USER_COOKER_ID) ? new String[]{"Food Map", "Dishes",
                "Orders","MyCart", "About"} : new String[]{"Food Map", "About"};
        loggedUser = new UserModel(userID, emailAddress, userType, fcmToken,userLongitude,userLatitude);

        // to update FCM if outdated
        Tools.getInstance().checkAndUpdateMyFCM();
        loadAllUsers();
        loadAllRecipes();
        if (userType == Configs.USER_FOODIE_ID) {
            numberOfPages = FOODIE_PAGES_NUM;
        } else {
            numberOfPages = COOK_PAGES_NUM;
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
                        return new CookerRecipes();
                    case 2:
                        return new OrdersFragment();
                    case 3:
                        return new MyCart();
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
                            int uid = key.getInt(Configs.USER_ID);
                            String emailAddress = key.getString(Configs.USER_EMAIL);
                            int userType = key.getInt(Configs.USER_TYPE);
                            String fcmToken = key.getString(Configs.FIREBASE_TOKEN);
                            double userLongitude = Double.parseDouble(key.getString(Configs.USER_LONGITUDE));
                            double userLatitude = Double.parseDouble(key.getString(Configs
                                    .USER_LATITUDE));
                            UserModel newUser = new UserModel(uid, emailAddress, userType,
                                    fcmToken,userLongitude,userLatitude);
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
    private void loadAllRecipes() {
        StringRequest recipesRequest = new StringRequest(Request.Method.POST, Configs
                .ALL_RECIPES_RETRIEVAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    // we got response as json array within nesed json array so we iterate
                    // throught them all to get all recipes
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Iterator keyz = jsonResponse.keys();
                        keyz.next();
                        while (keyz.hasNext()) {
                            JSONObject key = jsonResponse.getJSONObject((String) keyz.next());

                            String recipeName = key.getString(Configs.RECIPE_NAME);
                            String recipeDescription = key.getString(Configs.RECIPE_DESCRIPTION);
                            double recipePrice = key.getDouble(Configs.RECIPE_PRICE);
                            int recipeCookerID = key.getInt(Configs.RECIPE_COOKER_ID);
                            int recipeID = key.getInt(Configs.RECIPE_ID);
                            UserModel cooker = findCooker(recipeCookerID);
                            if (cooker == null) {
                                System.out.println("ID" + recipeCookerID);
                                System.out.println("Cant find cooker in all users");
                                return;
                            }
                            RegularRecipe recipe = new RegularRecipe(recipeID, recipeCookerID,
                                    recipeName, cooker, recipeDescription, recipePrice, cooker.getUserLongitude(),
                                    cooker.getUserLatitude());
                            allRecipes.add(recipe);
                        }
                    }
                } catch (JSONException e) {

                }
            }
        }, null);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(recipesRequest);

    }

    private UserModel findCooker(int recipeCookerID) {
        for (UserModel user : MainActivity.allUsers) {
            if (user.userID == recipeCookerID) {
                return user;
            }
        }
        return null;
    }
}
