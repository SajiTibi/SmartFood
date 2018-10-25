package com.example.saji.smartfood;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Tools {

    private static final String TAG = Tools.class.getSimpleName();
    private static Tools tools = null;
    private Context context;

    private Tools() {
        context = SmartFood.getAppContext();
    }

    public static Tools getInstance() {
        if (tools == null) {
            tools = new Tools();
        }
        return tools;
    }

    public void checkAndUpdateMyFCM() {
        StringRequest strReq = new StringRequest(Request.Method.POST, Configs.TOKEN_RETRIEVAL_URL, new Response
                .Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject jsonResponse;
                try {
                    jsonResponse = new JSONObject(response);
                    if (jsonResponse.getBoolean("success")) {
                        Iterator keyz = jsonResponse.keys();
                        keyz.next(); // to avoid first element which is success boolean
                        while (keyz.hasNext()) {
                            JSONObject key = jsonResponse.getJSONObject((String) keyz.next());
                            String loadedFCMToken = key.getString(Configs.FIREBASE_TOKEN);
                            String currentFCMToken = FirebaseInstanceId.getInstance().getToken();
                            // if we have outdated fcm we add the new one
                            if (!loadedFCMToken.equals(currentFCMToken)) {
                                registerToken(currentFCMToken);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, null) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Configs.USER_ID, String.valueOf(MainActivity.loggedUser.userID));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strReq);
    }

    public void registerToken(final String token) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                Configs.REGISTER_TOKEN_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    System.out.println(response);
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.getBoolean("success")) {
                        Log.d(TAG, "Updated fcm token in db successfully");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, null) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Configs.FIREBASE_TOKEN, token);
                params.put(Configs.USER_ID, String.valueOf(MainActivity.loggedUser.getUserID()));
                System.out.println("to up" + String.valueOf(MainActivity.loggedUser.getUserID()));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strReq);
    }
}
