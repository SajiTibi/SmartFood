package com.example.saji.smartfood;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    private AutoCompleteTextView mEmail;
    private EditText mPassword1;
    private EditText mPassword2;
    private Location lastLocation;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mEmail = findViewById(R.id.registration_email);
        mPassword1 = findViewById(R.id.registration_pwd_1);
        mPassword2 = findViewById(R.id.registration_pwd_2);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        LocationManager locationManager = (LocationManager) getSystemService
                (getApplicationContext().LOCATION_SERVICE);

        String provider = locationManager.getBestProvider(criteria, true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lastLocation = locationManager.getLastKnownLocation(provider);
        final Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    registerButton.setBackground(getDrawable(R.drawable.button_unclicked_drawable));
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    registerButton.setBackground(getDrawable(R.drawable.button_clicked_drawable));
                    RadioGroup userType = findViewById(R.id.user_type_group);
                    final int selectedButtonID = userType.getCheckedRadioButtonId();
                    if (mPassword1.getText().toString().equals(mPassword2.getText().toString())) {
                        // since there is only two selections we will pass 1 if foodie, 0 if cooker
                        attemptRegister(selectedButtonID == R.id.foodie_button ? 1 : 0);
                    } else {
                        Snackbar.make(view, "Make sure both passwords match", Snackbar.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
    }

    /**
     * tries to register user
     *
     * @param userType 1 is foodie, 0 is cooker
     */
    private void attemptRegister(int userType) {
        StringRequest sr = new RegisterRequest(mEmail.getText().toString(), mPassword1.getText()
                .toString(), userType,lastLocation, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println(response);
                    JSONObject responseJSON = new JSONObject(response);
                    boolean success = responseJSON.getBoolean("success");
                    if (success) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage("You've been registered, You will be redirected to " +
                                "login screen").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(loginIntent);
                            }
                        })
                                .create()
                                .show();

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage("Register Failed: email already is taken")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        requestQueue.add(sr);
    }
}
