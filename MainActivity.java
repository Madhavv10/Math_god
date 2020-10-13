package com.example.math_god;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText etname;
    Button btn_name;
    static String name = "";
    HashMap<Integer,String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etname = findViewById(R.id.etname);
        btn_name = findViewById(R.id.btn_name);
        map = new HashMap<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    user auser = snapshot.getValue(user.class);
                    map.put(auser.getScore(),auser.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etname.getText().toString();
                if(map.containsValue(name)){
                    Toast.makeText(getApplicationContext(),"The name is already registered",Toast.LENGTH_SHORT).show();
                }else if(name.matches("")){
                    Toast.makeText(getApplicationContext(),"Pls Enter a name",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(MainActivity.this,play.class);
                    startActivity(intent);
                }

            }
        });

    }


}