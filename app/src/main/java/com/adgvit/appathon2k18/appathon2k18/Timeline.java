package com.adgvit.appathon2k18.appathon2k18;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;

public class Timeline extends AppCompatActivity {

    RecyclerView recyclerView;
    TimelineAdapter tAdapter;
    RecyclerView.LayoutManager layoutManager;
    private ArrayList<TimelineItems> timelineItems = new ArrayList<>();
    private String[] times={"5:15 pm","5:30 pm","6:00 pm","6:15 pm","6:30 pm","7:15 pm","8:00 pm","10:30 pm","2:00 am",
            "3:15 pm","3:30 pm","4:00 pm","4:15 pm","4:45 pm","7:30 pm","8:30 pm","11:30 pm"};

    private String[] title={"Welcome to UniDev","Reporting Time","Commencement","Inauguration","Speakers take over","Break","Workshop is Resumed","Short Break","Wrap Up",
            "Registration Desk Setup","Reporting Time","Workshop Resumes","Break","Workshop Resumes","Break","Workshop Resumes","Workshop is Concluded"};

    private String[] details={"Registration Desks Setup","Reporting Time for Participants","Commencement of the Workshop","Inaugural address detailing chapter","Step by step walk-through","The time arrives for relaxation","Time to get back to Work","A short break for 30 mins!","Pack your bags and head home",
            "Registration Desks Setup","Reporting Time for Participants","The workshop resumes","Time for you to relax","Get back to work","Take a break","The final session","Hope to see you next year!"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        recyclerView=findViewById(R.id.recyclerid);
        int count = 0;
        for(String Name: title)
        {
            timelineItems.add(new TimelineItems(times[count],Name,details[count]));
            count++;
        }
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        tAdapter=new TimelineAdapter(timelineItems,this);
        recyclerView.setAdapter(tAdapter);
        Animation anim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move2);
        recyclerView.startAnimation(anim);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Timeline");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }
        return true;
    }
}
