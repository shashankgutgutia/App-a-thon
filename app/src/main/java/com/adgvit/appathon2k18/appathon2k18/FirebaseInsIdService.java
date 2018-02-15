package com.adgvit.appathon2k18.appathon2k18;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Shashank Gutgutia on 14-02-2018.
 */

public class FirebaseInsIdService extends FirebaseInstanceIdService {
    private static final String TAG="Myfirebaseinsidservice";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG,"New token:"+refreshedToken);
    }
}
