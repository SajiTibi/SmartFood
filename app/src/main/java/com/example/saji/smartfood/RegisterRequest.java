package com.example.saji.smartfood;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://silicotic-vent.000webhostapp.com/Register.php";
    private String email;
    private String password;
    public RegisterRequest(String email, String password, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        this.email=email;
        this.password=password;
    }
    @Override
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("email_address",email);
        params.put("user_password", password);
        return params;
    }
}
