package com.example.math_god;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class play extends AppCompatActivity {

    TextView tvtimer;
    TextView tvques;
    TextView tvscore;
    Button btn_scoreboard;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    boolean again;
    user user1;
    int max_id = 0;

    Button playagain;
    Random random;
    CountDownTimer countdown;
    CountDownTimer timer;
    int rbtn;
    int rnum1;
    int rnum2;
    int min =0;
    int max =0;
    int score = 0;
    int total = 0;
    int answer;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public void playagain(View view){
        start();
    }


    public void start(){
        //tvques.setText("The Test has started Bro!!!");
        score = 0;
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        button4.setVisibility(View.VISIBLE);
        tvtimer.setVisibility(View.VISIBLE);
        tvscore.setVisibility(View.VISIBLE);
        tvques.setVisibility(View.VISIBLE);
        tvscore.setText("Score : 0");

        timer = new CountDownTimer(61000 + 100,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long x = millisUntilFinished/1000;
                String time = Long.toString(x);
                tvtimer.setText("Time left : "+time+"s");
            }

            @Override
            public void onFinish() {
                tvtimer.setVisibility(View.INVISIBLE);
                playagain.setVisibility(View.VISIBLE);
                //tvscore.setVisibility(View.INVISIBLE);
                //tvques.setVisibility(View.INVISIBLE);
                button1.setVisibility(View.INVISIBLE);
                button2.setVisibility(View.INVISIBLE);
                button3.setVisibility(View.INVISIBLE);
                button4.setVisibility(View.INVISIBLE);
                if(again){
                    user1.setScore(score);
                    databaseReference.child(String.valueOf(max_id)).setValue(user1);

                }else{
                    user1 = new user(MainActivity.name,score);
                    databaseReference.child(String.valueOf(max_id+1)).setValue(user1);
                    tvques.setText("The test has finished.");
                    again = true;
                }

            }
        }.start();

        setquestion();


    }

    public void response(View view){
        int tag = Integer.parseInt(view.getTag().toString());
        if(tag == rbtn){
            score+=4;
            //Toast.makeText(this, "Right Answer!!", Toast.LENGTH_SHORT).show();
        }else{
            score-=2;
        }
        tvscore.setText("Score : "+Integer.toString(score));
        setquestion();
    }

    public void setquestion(){
        random = new Random();
        rnum1 = random.nextInt(100);
        rnum2 = random.nextInt(100);
        rbtn = random.nextInt(4)+1;
        if(rnum2 > rnum1){
            min = rnum1;
            max = rnum2;
        }else{
            min = rnum2;
            max = rnum1;
        }
        answer = rnum1*rnum2;
        int canswer = (((answer)/100)*10 + random.nextInt(10)+1)*10 + answer%10;
        tvques.setText(rnum1 +" * "+rnum2 +"= ?");
        if(rbtn == 1){
            button1.setText(Integer.toString(answer));
            button2.setText(Integer.toString(answer + random.nextInt(10)+1));
            button3.setText(Integer.toString(canswer));
            button4.setText(Integer.toString(answer + random.nextInt(20)+1));
        }else if(rbtn == 2){
            button2.setText(Integer.toString(answer));
            button1.setText(Integer.toString(canswer));
            button3.setText(Integer.toString(answer - random.nextInt(10)-1));
            button4.setText(Integer.toString(answer + random.nextInt(20)+1));
        }else if(rbtn == 3){
            button3.setText(Integer.toString(answer));
            button2.setText(Integer.toString(canswer));
            button1.setText(Integer.toString(answer - random.nextInt(10)-1));
            button4.setText(Integer.toString(answer + random.nextInt(20)+1));
        }else{
            button4.setText(Integer.toString(answer));
            button2.setText(Integer.toString(answer + random.nextInt(10)+1));
            button3.setText(Integer.toString(answer - random.nextInt(10)-1));
            button1.setText(Integer.toString(canswer));
        }


    }

    public void scoreboard(View view){
        Intent intent = new Intent(play.this,leaderboard.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        tvtimer = findViewById(R.id.tvtimer);
        tvques = findViewById(R.id.tvques);
        tvscore = findViewById(R.id.tvscore);
        tvtimer.setVisibility(View.INVISIBLE);
        tvscore.setVisibility(View.INVISIBLE);
        //tvques.setVisibility(View.INVISIBLE);
        btn_scoreboard = findViewById(R.id.btn_scoreboard);
        playagain = findViewById(R.id.playagain);
        playagain.setVisibility(View.INVISIBLE);
        again = false;

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);



        databaseReference = firebaseDatabase.getInstance().getReference().child("Users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    max_id = (int) snapshot.getChildrenCount();
                } else {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        countdown = new CountDownTimer(3100,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long x = millisUntilFinished/1000;
                String time = Long.toString(x);
                tvques.setText("Starting in "+time);
            }

            @Override
            public void onFinish() {
                play.this.start();
            }
        }.start();

    }
}