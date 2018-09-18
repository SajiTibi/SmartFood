package com.example.saji.smartfood;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    private Map<String, String> params;

    public LoginRequest(String email, String password, Response.Listener<String> listener) {
        super(Method.POST, Configs.LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put(Configs.USER_EMAIL, email);
        params.put(Configs.USER_PASSWORD, password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
