package com.jaya.hackthaonproject;

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
    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
    Log.d(TAG, "Refreshed token: " + refreshedToken);
    sendRegistrationToServer(refreshedToken);
  }

  private void sendRegistrationToServer(String refreshedToken) {

    System.out.print("token  :"+refreshedToken);
  }

}
