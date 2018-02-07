package com.adgvit.appathon2k18.appathon2k18;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.util.Property;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.HeterogeneousExpandableList;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.jpardogo.android.googleprogressbar.library.FoldingCirclesDrawable;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import com.sothree.slidinguppanel.ScrollableViewHelper;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeScreen extends AppCompatActivity {

    Bitmap bitmap1;
    public final static int QRcodeWidth = 500 ;
    SharedPreferences sp;
    String value;
    GoogleProgressBar mBar;
    ExpandableListView expListView;
    FrameLayout frameLayout;
    SlidingUpPanelLayout slidingUpPanelLayout;
    Button panelbtn;
    TextView setuptext;
    LinearLayout linearLayout;
    LinearLayout linearLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mBar = (GoogleProgressBar) findViewById(R.id.google_progress2);
        mBar.setIndeterminateDrawable(new FoldingCirclesDrawable.Builder(this).colors(getResources().getIntArray(R.array.progressLoader)).build());
        mBar.setVisibility(View.INVISIBLE);
        expListView = (ExpandableListView) findViewById(R.id.modulesList);
        slidingUpPanelLayout = findViewById(R.id.sliding_layout);
        setuptext=findViewById(R.id.setup);
        linearLayout=findViewById(R.id.proglayout);
        linearLayout2=findViewById(R.id.lists);

        List<String> listDataHeader = new ArrayList<String>();
        HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();

        listDataHeader.add("Timeline");
        listDataHeader.add("Wifi");
        listDataHeader.add("Food");
        listDataHeader.add("Quiz");

        List<String> tm = new ArrayList<String>();
        List<String> wf = new ArrayList<String>();
        List<String> fd = new ArrayList<String>();
        List<String> qz = new ArrayList<String>();

        tm.add("First");
        wf.add("Second");
        fd.add("Second");
        qz.add("Second");

        listDataChild.put(listDataHeader.get(0), tm); // Header, Child data
        listDataChild.put(listDataHeader.get(1), wf);
        listDataChild.put(listDataHeader.get(2), fd);
        listDataChild.put(listDataHeader.get(3), qz);
        ExpandableAdapter listAdapter = new ExpandableAdapter(getApplicationContext(), listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
        panelbtn= findViewById(R.id.dragup);
        panelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (slidingUpPanelLayout.getPanelState()==SlidingUpPanelLayout.PanelState.EXPANDED){
                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                }
                else{
                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                }
            }
        });

        slidingUpPanelLayout.setScrollableViewHelper(new NestedScrollableViewHelper());
        linearLayout.setOnTouchListener(new RelativeLayoutTouchListener());
    }

    @Override
    public void onBackPressed() {
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

    public class RelativeLayoutTouchListener implements View.OnTouchListener {


        private float downY, upY;


        public void onTopToBottomSwipe() {
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            // activity.doSomething();
        }

        public void onBottomToTopSwipe() {
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        }

        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    downY = event.getY();
                    return true;
                }
                case MotionEvent.ACTION_UP: {
                    upY = event.getY();

                    float deltaY = downY - upY;


                    // swipe vertical?
                        // top or down
                        if (deltaY < 0) {
                            this.onTopToBottomSwipe();
                            return true;
                        }
                        if (deltaY > 0) {
                            this.onBottomToTopSwipe();
                            return true;
                        }


                    return false; // no swipe horizontally and no swipe vertically
                }// case MotionEvent.ACTION_UP:
            }
            return false;
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        sp= HomeScreen.this.getSharedPreferences("key", 0);
        value=sp.getString("food","");
        if(value.isEmpty()) {
            new ImageLoading().execute();
        }
    }


    public class NestedScrollableViewHelper extends ScrollableViewHelper {
        public int getScrollableViewScrollPosition(View scrollableView, boolean isSlidingUp) {
            if (scrollableView instanceof NestedScrollView) {
                if(isSlidingUp){
                    return scrollableView.getScrollY();
                } else {
                    NestedScrollView nsv = ((NestedScrollView) scrollableView);
                    View child = nsv.getChildAt(0);
                    return (child.getBottom() - (nsv.getHeight() + nsv.getScrollY()));
                }
            } else {
                return 0;
            }
        }
    }

    class ImageLoading extends AsyncTask<Void,Void,Void> {

        //ProgressDialog progressDialog=new ProgressDialog(HomeScreen.this);

        @Override
        protected void onPreExecute() {
           // progressDialog.setMessage("Loading...Please Wait");
           // progressDialog.setTitle("Welcome to App-A-Thon!");
           // progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
           // progressDialog.show();
            frameLayout=findViewById(R.id.homeLayout);
            frameLayout.getBackground().setAlpha(90);
            linearLayout2.setAlpha(0.4f);
            linearLayout.setAlpha(1);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            mBar.setVisibility(View.VISIBLE);
            setuptext.setVisibility(View.VISIBLE);
            mBar.setFocusable(true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            setuptext.setVisibility(View.INVISIBLE);
            mBar.setVisibility(View.INVISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            frameLayout.getBackground().setAlpha(140);
            linearLayout2.setAlpha(1);
            //progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... params) {
            String partreg = sp.getString("Reg_Num", "");
            String attendance;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            String qrattend = partreg + "_food";
            try {
                bitmap1 = TextToImageEncode(qrattend);
                bitmap1.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();
                attendance = Base64.encodeToString(b, Base64.DEFAULT);
                SharedPreferences.Editor sedt1 = sp.edit();
                sedt1.putString("food", attendance);
                sedt1.apply();

            } catch (WriterException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ? ContextCompat.getColor(HomeScreen.this,R.color.QRCodeBlack):ContextCompat.getColor(HomeScreen.this,R.color.colorWhite);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}
