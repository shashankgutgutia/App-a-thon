package com.adgvit.appathon2k18.appathon2k18;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.HeterogeneousExpandableList;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Shashank Gutgutia on 05-02-2018.
 */

public class ExpandableAdapter extends BaseExpandableListAdapter implements HeterogeneousExpandableList{

    private Context context;
    private List<String> listDataHeader; // header titles
    SharedPreferences sp;
    // Child data in format of header title, child title
    //private HashMap<String, List<String>> listDataChild;


    public ExpandableAdapter(Context context, List<String> listDataHeader) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        //this.listDataChild = listChildData;
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        //return this.listDataChild.get(this.listDataHeader.get(groupPosition)).size();
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return "";
        //return this.listDataChild.get(this.listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.listHeader);
        ImageView imageView=convertView.findViewById(R.id.arrow);
        ImageView imageView2=convertView.findViewById(R.id.grpIcon);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        if (groupPosition==0){
            imageView2.setImageResource(R.drawable.ic_date_range_24px);
        }
        else if(groupPosition==1){
            imageView2.setImageResource(R.drawable.ic_signal_wifi_statusbar_2_bar_26x24px);
        }
        else if(groupPosition==2){
            imageView2.setImageResource(R.drawable.ic_restaurant_menu_24px);
        }
        else{
            imageView2.setImageResource(R.drawable.ic_watch_later_24px);
        }
        if (isExpanded) {
            imageView.setImageResource(R.drawable.ic_arrow_up_24px);
        } else {
            imageView.setImageResource(R.drawable.ic_arrow_drop_up_24px);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);
        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            sp=context.getSharedPreferences("key", 0);
            if(groupPosition==0) {
                convertView = layoutInflater.inflate(R.layout.list_timeline, null);
            }
            else if(groupPosition==1){
                    convertView = layoutInflater.inflate(R.layout.list_wifi, null);
                    String wifiname=sp.getString("wUser","");
                    String wifipass=sp.getString("wPass","");
                    if(!wifiname.equalsIgnoreCase("")||!wifipass.equalsIgnoreCase("") ){
                        TextView username=convertView.findViewById(R.id.userName);
                        TextView password=convertView.findViewById(R.id.passWord);
                        username.setText(wifiname);
                        password.setText(wifipass);
                        username.setVisibility(View.VISIBLE);
                        password.setVisibility(View.VISIBLE);
                    }

            }
            else if(groupPosition==2) {
                convertView = layoutInflater.inflate(R.layout.list_food, null);
                ImageView imageView= (ImageView) convertView.findViewById(R.id.barcode);
                String prevEncodedImage=sp.getString("food","");
                Boolean fstatus=sp.getBoolean("fcheck",false);
                if(!prevEncodedImage.equalsIgnoreCase("")){
                    byte[] b= Base64.decode(prevEncodedImage,Base64.DEFAULT);
                    Bitmap bitmap= BitmapFactory.decodeByteArray(b,0,b.length);
                    imageView.setImageBitmap(bitmap);
                }
                if(fstatus){
                    TextView status=convertView.findViewById(R.id.redeem);
                    status.setText(R.string.food_check);
                }

            }
            else{
                convertView = layoutInflater.inflate(R.layout.list_quiz, null);
            }


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
