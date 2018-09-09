package com.example.saji.smartfood;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    private String email;
    private String password;
    private int userType;
    RegisterRequest(String email, String password, int userType, Response.Listener<String>
            listener) {
        super(Method.POST, Configs.REGISTER_REQUEST_URL, listener, null);
        this.email=email;
        this.password=password;
        this.userType=userType;
    }
    @Override
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Configs.USER_EMAIL, email);
        params.put(Configs.USER_PASSWORD, password);
        params.put(Configs.USER_TYPE, String.valueOf(userType));
        return params;
    }
}
