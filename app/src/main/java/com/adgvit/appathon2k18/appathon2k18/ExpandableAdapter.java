package com.adgvit.appathon2k18.appathon2k18;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.HeterogeneousExpandableList;
import android.widget.ImageView;
import android.widget.TextView;

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
    private HashMap<String, List<String>> listDataChild;


    public ExpandableAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listChildData) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition)).get(childPosition);
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

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.listHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);
        LayoutInflater layoutInflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            sp=context.getSharedPreferences("key", 0);
            if(groupPosition==0) {

                convertView = layoutInflater.inflate(R.layout.list_timeline, null);
            }
            else if(groupPosition==1){

                    convertView = layoutInflater.inflate(R.layout.list_wifi, null);
            }
            else if(groupPosition==2) {
                convertView = layoutInflater.inflate(R.layout.list_food, null);
                ImageView imageView= (ImageView) convertView.findViewById(R.id.barcode);
                String prevEncodedImage=sp.getString("food","");
                if(!prevEncodedImage.equalsIgnoreCase("")){
                    byte[] b= Base64.decode(prevEncodedImage,Base64.DEFAULT);
                    Bitmap bitmap= BitmapFactory.decodeByteArray(b,0,b.length);
                    imageView.setImageBitmap(bitmap);
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
