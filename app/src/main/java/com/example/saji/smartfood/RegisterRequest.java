package com.example.saji.smartfood;

import android.location.Location;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    private String email;
    private String password;
    private int userType;
    private Location location;

    RegisterRequest(String email, String password, int userType, Location location, Response
            .Listener<String>
            listener) {
        super(Method.POST, Configs.REGISTER_REQUEST_URL, listener, null);
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.location = location;
    }

    @Override
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Configs.USER_EMAIL, email);
        params.put(Configs.USER_PASSWORD, password);
        params.put(Configs.USER_TYPE, String.valueOf(userType));
        params.put(Configs.USER_LONGITUDE, String.valueOf(location.getLongitude()));
        params.put(Configs.USER_LATITUDE, String.valueOf(location.getLatitude()));
        return params;
    }
}
