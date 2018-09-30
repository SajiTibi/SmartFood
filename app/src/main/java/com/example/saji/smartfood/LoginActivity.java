package com.example.saji.smartfood;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        final Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    mEmailSignInButton.setBackground(getDrawable(R.drawable.button_clicked_drawable));
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    mEmailSignInButton.setBackground(getDrawable(R.drawable.button_unclicked_drawable));
                    attemptLogin();
                }
                return false;
            }
        });

        final Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    registerButton.setBackground(getDrawable(R.drawable.button_clicked_drawable));
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    registerButton.setBackground(getDrawable(R.drawable.button_unclicked_drawable));
                    Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                    LoginActivity.this.startActivity(registerIntent);
                }
                return false;
            }
        });


        Pair<String, String> lastUser = getLastUser();
        if (!lastUser.first.equals("") && !lastUser.second.equals("")) {
            findViewById(R.id.email_sign_in_button).setVisibility(View.INVISIBLE);
            findViewById(R.id.register_button).setVisibility(View.INVISIBLE);
            mEmailView.setText(lastUser.first);
            mPasswordView.setText(lastUser.second);
            attemptLogin();
        }
    }

    private Pair<String, String> getLastUser() {
        final String NONE = "";
        final String PREF = "User";

        SharedPreferences prefs = getSharedPreferences(PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String savedUsername = prefs.getString("Username", NONE);
        String savedKey = prefs.getString("Key", NONE);
        String savedToken = prefs.getString("Token", NONE);
        if (savedUsername.equals(NONE) || savedKey.equals(NONE) || savedToken.equals(NONE)) {
            editor.putString("Username", NONE);
            editor.putString("Key", NONE);
            editor.putString("Token", NONE);
            editor.commit();
        }

        Pair<String, String> UP = new Pair<>(savedUsername, savedKey);
        return UP;
    }

    private void attemptLogin() {
        StringRequest sr = new LoginRequest(mEmailView.getText().toString(),
                mPasswordView.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("response: " + response);
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        int userID = jsonResponse.getInt(Configs.USER_ID);
                        int userType = jsonResponse.getInt(Configs.USER_TYPE);
                        String fcmToken = jsonResponse.getString(Configs.FIREBASE_TOKEN);
                        double userLongitude = Double.parseDouble(jsonResponse.getString(Configs
                                .USER_LONGITUDE));
                        double userLatitude = Double.parseDouble(jsonResponse.getString(Configs
                                .USER_LATITUDE));

                        //saving user, password & token to next Login
                        final String NONE = "";
                        final String PREF = "User";
                        SharedPreferences prefs = getSharedPreferences(PREF, MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("Username", mEmailView.getText().toString());
                        editor.putString("Key", mPasswordView.getText().toString());
                        editor.putString("Token", fcmToken);
                        editor.commit();

                        // forwarding userID and user type to main screen activity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        // passing user information to main activity
                        intent.putExtra(Configs.USER_ID, userID);
                        intent.putExtra(Configs.USER_EMAIL, mEmailView.getText().toString());
                        intent.putExtra(Configs.USER_TYPE, userType);
                        intent.putExtra(Configs.FIREBASE_TOKEN,fcmToken);
                        intent.putExtra(Configs.USER_LONGITUDE,userLongitude);
                        intent.putExtra(Configs.USER_LATITUDE, userLatitude);

                        LoginActivity.this.startActivity(intent);
                        finish(); // to prevent user from coming back to login page when
                        // pressing back button
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(sr);
    }

}

