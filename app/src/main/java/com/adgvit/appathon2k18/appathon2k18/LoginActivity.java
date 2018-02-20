package com.adgvit.appathon2k18.appathon2k18;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private GestureDetectorCompat gestureObject;
    private IntentIntegrator qrScan;
    public static String name, regno, userId;
    public boolean login = false;
    JSONObject obj;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        gestureObject = new GestureDetectorCompat(this, new LearnGesture());

        qrScan = new IntentIntegrator(this);

        sp = getSharedPreferences("key", 0);
        login = Boolean.parseBoolean(sp.getString("datavalue",""));
        Log.i("value of login", String.valueOf(login));
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        if(login) {
            Intent in = new Intent(LoginActivity.this, HomeScreen.class);
            startActivity(in);
        } else {
            qrScan.initiateScan();
        }
    }

    public class LearnGesture extends GestureDetector.SimpleOnGestureListener {

        //SimpleGestureListener is the listener for what type of swipe we want

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            sp = getSharedPreferences("key", 0);
            login = Boolean.parseBoolean(sp.getString("datavalue",""));
            if(login) {
                Intent i = new Intent(LoginActivity.this, HomeScreen.class);
                startActivity(i);
            }
            else {
                if(event2.getY() < event1.getY()) {
                    //down to up swipe
                    //check for authentication here
                    qrScan.initiateScan();
                }
                else if(event2.getY() > event1.getY()) {
                    //up to down swipe
                }
                return true;
            }
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                //if qrCode is empty
                Toast.makeText(this, "Result not found", Toast.LENGTH_LONG).show();
            } else {
                //if qrCode has data
                try {
                    //converting the data to JSON
                    obj = new JSONObject(result.getContents());
                    userId=obj.toString();
                    //set the values that are returned to the text views
                    name = obj.getString("name");
                    regno = obj.getString("regno");
                    login = true;
                    SharedPreferences.Editor sedt = sp.edit();
                    sedt.putString("datavalue", String.valueOf(login));
                    sedt.putString("Name", String.valueOf(name));
                    sedt.putString("Reg_Num", String.valueOf(regno));
                    sedt.apply();
                    Intent intent=new Intent(LoginActivity.this, HomeScreen.class);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                    //If control comes here
                    //It means that encoded format doesn't match
                    //ie, User scanned a QR code which isn't in our DB
                    Toast.makeText(this, "Wrong QR Code", Toast.LENGTH_LONG).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

