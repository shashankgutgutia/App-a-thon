package com.adgvit.appathon2k18.appathon2k18;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class Quiz1 extends AppCompatActivity
{
    Map<String,String> quiz1,quiz1_option,quiz1_ans;
    Button start,optiona,optionb,optionc,optiond,next;
    TextView q,luck,timer;
    String[] arr;
    int count=1,score;
    Button last_button;
    String sel_ans;
    View va,vb,vc,vd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        optiona=findViewById(R.id.button2);
        optionb=findViewById(R.id.button3);
        optionc=findViewById(R.id.button4);
        optiond=findViewById(R.id.button5);
        next=findViewById(R.id.button6);
        q=findViewById(R.id.tv_q);
        timer=findViewById(R.id.tv_timer1);

        quiz1=new HashMap<>();
        quiz1_option=new HashMap<>();
        quiz1_ans=new HashMap<>();



//Create a quiz map.
//check from firebase which quiz to be started, accordingly make quiz=quiz1||quiz2||quiz3
//same goes for quiz_sel and quiz_ans.
//quiz, quiz_sel, quiz_ans will replace quiz1, quiz1_sel,quiz1_ans here.



        quiz1.put("1","what is a hack a thon?");
        quiz1.put("2","What is ADG?");
        quiz1.put("3","what language for android app dev");

        quiz1_option.put("1","a,b,c,d");
        quiz1_option.put("2","apple developers group,adobe developers group,ajax developers group,no idea");

        quiz1_ans.put("1","a");
        quiz1_ans.put("2","a");


        q.setText(quiz1.get("1"));
        arr=quiz1_option.get("1").split(",");
        for(int i=0;i<4;i++)
        {
            optiona.setText(arr[0]);
            optionb.setText(arr[1]);
            optionc.setText(arr[2]);
            optiond.setText(arr[3]);
        }

        new CountDownTimer(60000,1000)
        {

            @Override
            public void onTick(long l)
            {
//                timer_minute.setText(""+l/60000);
                timer.setText(""+String.format("%d : %d",TimeUnit.MILLISECONDS.toMinutes(l),TimeUnit.MILLISECONDS.toSeconds(l)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l))));
                if(TimeUnit.MILLISECONDS.toMinutes(l)==0 && TimeUnit.MILLISECONDS.toSeconds(l)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l))<10)
                {
                    timer.setTextColor(getResources().getColor(R.color.timer));
                }

            }

            @Override
            public void onFinish() {
                optiona.setVisibility(View.INVISIBLE);
                optionb.setVisibility(View.INVISIBLE);
                optionc.setVisibility(View.INVISIBLE);
                optiond.setVisibility(View.INVISIBLE);
                timer.setVisibility(View.INVISIBLE);
                next.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),"Your time's up!",Toast.LENGTH_LONG).show();
                onBackPressed();

            }
        }.start();

        last_button=optiona;
        optiona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                optiona.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                if(last_button!=optiona)
                {
                    last_button.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                }
                last_button=optiona;
                sel_ans="a";
            }
        });

        optionb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionb.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                if(last_button!=optionb)
                {
                    last_button.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                }
                last_button=optionb;
                sel_ans="b";
            }
        });

        optionc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionc.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                if(last_button!=optionc)
                {
                    last_button.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                }
                last_button=optionc;
                sel_ans="c";
            }
        });

        optiond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optiond.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                if(last_button!=optiond)
                {
                    last_button.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                }
                last_button=optiond;
                sel_ans="d";
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                    String answer = quiz1_ans.get("" + count);
                    if (answer.equalsIgnoreCase(sel_ans))
                        score++;
                    count++;

                    if(count<3)
                    {
                        last_button.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                        q.setText(quiz1.get("" + count));
                        arr = quiz1_option.get("" + count).split(",");
                        for (int i = 0; i < 4; i++) {
                            optiona.setText(arr[0]);
                            optionb.setText(arr[1]);
                            optionc.setText(arr[2]);
                            optiond.setText(arr[3]);
                            next.setText("Submit");
                        }
                    }
                    else
                        {
                            Toast.makeText(getApplicationContext(),""+score,Toast.LENGTH_LONG).show();
                            onBackPressed();
                            //Establish firebase connection and push the score to the database.
                            //End the current activity and take the user out of the quiz.
                            //take the user back to the menu.
                        }

            }

        });



    }


}
