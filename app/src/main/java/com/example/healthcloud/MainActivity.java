package com.example.healthcloud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private String myyear, mymonth, mydateofmonth, date;
    private CalendarView calendar;
    private DatabaseReference myRef,mRef2;
    private FirebaseDatabase database;
    private RecyclerView mRe;
    private TaskRecyclerViewAdapter mAp;
    private RecyclerView.LayoutManager mLay;
    private ArrayList<Task> tasklist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Schedule");
        tasklist=new ArrayList<>();


        //BottonNavigationView set up
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.schedule2);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.schedule2:
                        return true;
                    case R.id.personaldata:
                        startActivity(new Intent(getApplicationContext(), Personaldata.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.running:
                        startActivity(new Intent(getApplicationContext(), Running.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        //floating button
        FloatingActionButton add_task = findViewById(R.id.add_button);
        add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Addtask.class));
                overridePendingTransition(0,0);
            }
        });



        calendar = findViewById(R.id.calendarView2);
        if(myyear == null){
            Calendar c = Calendar.getInstance();
            myyear = String.valueOf(c.get(Calendar.YEAR));
            mymonth = String.valueOf(c.get(Calendar.MONTH));
            mydateofmonth = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        }

        String date = myyear+","+mymonth+","+mydateofmonth;
        database = FirebaseDatabase.getInstance();
////        String mail = (String) getIntent().getExtras().get("email");
        myRef = database.getReference();

        myRef.child(date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                /*               Object map = dataSnapshot.getValue();*/
                tasklist.clear();
                /*                System.out.println(map);*/
                for(DataSnapshot d : dataSnapshot.getChildren())
                {
                    Task s = d.getValue(Task.class);
                    tasklist.add(s);
                }
                mAp.notifyDataSetChanged();

            }

            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        buildRecyclerView();
        System.out.println(tasklist.size()+"aaaaaaaaaaaaaaaaaaaaaaaa");
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                myyear = String.valueOf(year);
                mymonth = String.valueOf(month);
                mydateofmonth = String.valueOf(dayOfMonth);
                String date = myyear+","+mymonth+","+mydateofmonth;
                mRef2= myRef.child(date);

                mRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        /*               Object map = dataSnapshot.getValue();*/

                        tasklist.clear();
                        /*                System.out.println(map);*/
                        for(DataSnapshot d : dataSnapshot.getChildren())
                        {
                            Task s = d.getValue(Task.class);
                            tasklist.add(s);
                        }
                        mAp.notifyDataSetChanged();

                    }

                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }

                });


                buildRecyclerView();
            }
        });

        FloatingActionButton recommend = findViewById(R.id.recommendbutton);
        recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tasklist.add(new Task("Lunch","meal",date,3,11,30,12,30,690,1));
                tasklist.add(new Task("nap","other",date,1,13,30,14,00,810,1));
                tasklist.add(new Task("running","exercise",date,3,19,00,19,30,1140,1));
                buildRecyclerView();
            }
        });


    }

    private void buildRecyclerView() {
        mRe= findViewById(R.id.recyclerview);
        mRe.setHasFixedSize(true);
        mLay=new LinearLayoutManager(this);
        mAp =new TaskRecyclerViewAdapter(tasklist);
        mRe.setAdapter(mAp);
        mRe.setLayoutManager(mLay);
    }

}