package com.awesome.food_app.smartfood;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. This call is initiated by the
     * InstanceID provider.
     */
    @Override
    public void onNewToken(String token) {
        Log.e(TAG, "onTokenRefresh");
        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
        Tools.getInstance().registerToken(token);
    }


}
