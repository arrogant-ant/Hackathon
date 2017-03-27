package com.jaya.hackthaonproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by vikash.agarwal on 26/03/17.
 */
public class FCMInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "FCMInstanceIDService";

    @Override
    public void onTokenRefresh() {
        Log.d(TAG, "Refreshed token: ");
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("fcmPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("fcm_token",refreshedToken);
        editor.commit();
    }

    private void sendRegistrationToServer(String refreshedToken) {

        System.out.print("token  :"+refreshedToken);
    }

}
