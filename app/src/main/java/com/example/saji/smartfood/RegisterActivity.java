package com.example.saji.smartfood;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mEmail = findViewById(R.id.registration_email);
        mPassword1 = findViewById(R.id.registration_pwd_1);
        mPassword2 = findViewById(R.id.registration_pwd_2);
        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPassword1.getText().toString().equals(mPassword2.getText().toString())) {
                    attemptRegister();
                } else {
                    Snackbar.make(view, "Make sure both passwords match", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void attemptRegister() {
        StringRequest sr = new RegisterRequest(mEmail.getText().toString(), mPassword1.getText()
                .toString(), new Response.Listener<String>() {
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
