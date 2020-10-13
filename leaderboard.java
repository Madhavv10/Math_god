package com.example.math_god;


import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class leaderboard extends AppCompatActivity {
    ListView score_list;
    ArrayList<String> list;
    HashMap<String,Integer> map;
    ArrayList<Integer> scores;
    int k=1;
    TextView tvtitle;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderbpard);
        score_list = findViewById(R.id.score_list);
        tvtitle = findViewById(R.id.tvtitle);
        list = new ArrayList<>();
        map = new HashMap<>();
        scores = new ArrayList<>();




        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    user auser = snapshot.getValue(user.class);
                    scores.add((auser.getScore()));
                    map.put(auser.getName(),auser.getScore());
                    String text = Integer.toString(k) +".   "+ auser.getName() +"   Score :"+ auser.getScore();
                    Log.i("Names ",text);
                }
                ranking(scores,map);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    public void ranking(ArrayList scores,HashMap map){
        list.clear();
        String temp=" ";
        Log.i("Scorelist size : ",Integer.toString(scores.size()));
        Collections.sort(scores,Collections.reverseOrder());

        //int ts1 = (int) scores.get(0);
        //int temp2 = ts1;



        String text = "";
        int t2 = (int) scores.get(0);
        //list.add(text);
        for(int i=0;i<scores.size();i++){
            int ts = (int) scores.get(i);
            Iterator it = map.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry)it.next();
                //text = k + "   "+entry.getKey()+"   Score : "+entry.getValue();
                //Log.i("Entries for playlist ",text);

                if(entry.getValue().equals(ts)){

                    if(ts == t2){
                        text = k + "   "+entry.getKey()+"   Score : "+entry.getValue();
                        Log.i("Entries for playlist ",text);
                        list.add(text);
                        it.remove();
                    }else{
                        k++;
                        t2 = ts;
                        text = k + "   "+entry.getKey()+"   Score : "+entry.getValue();
                        Log.i("Entries for playlist ",text);
                        list.add(text);
                        it.remove();
                    }

                }
            }

        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_leaderbpard,R.id.tvtitle, list);
        score_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}