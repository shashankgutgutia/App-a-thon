package com.adgvit.appathon2k18.appathon2k18;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Shashank Gutgutia on 08-02-2018.
 */

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {

    ArrayList<TimelineItems> arrayList=new ArrayList<>();
    private Context context;

    public TimelineAdapter(ArrayList<TimelineItems> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public TimelineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_card,parent,false);
        ViewHolder viewholder=new ViewHolder(v);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(TimelineAdapter.ViewHolder holder, int position) {
        holder.times.setText(arrayList.get(position).getTime());
        holder.heads.setText(arrayList.get(position).getHeading());
        holder.desc.setText(arrayList.get(position).getDescrpt());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView times;
        public TextView heads;
        public TextView desc;

        public ViewHolder(View itemView) {
            super(itemView);
            times= (TextView) itemView.findViewById(R.id.time);
            heads= (TextView) itemView.findViewById(R.id.title);
            desc= (TextView) itemView.findViewById(R.id.location);
        }
    }
}
