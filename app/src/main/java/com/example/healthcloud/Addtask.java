package com.example.healthcloud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.content.Intent;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;



public class Addtask extends AppCompatActivity {

    private boolean isChecking = true;
    private EditText titletext;
    private RadioGroup radiogroupone,radiogrouptwo;
    private RadioButton workbutton, studybutton, exercisebutton, mealbutton, playbutton,otherbutton;
    private RadioGroup intensitygroup;
    private RadioButton easybutton, midiumbutton, hardbutton;
    private CalendarView calendar;
    private TimePicker starttime, endtime;
    private String myyear, mymonth, mydateofmonth, date;
    private DatabaseReference mdatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Task");

        //initialize
        titletext = findViewById(R.id.editTexttitle);
        radiogroupone = findViewById(R.id.radiogroupone);
        radiogrouptwo = findViewById(R.id.radiogrouptwo);
        workbutton = findViewById(R.id.workbutton);
        studybutton = findViewById(R.id.studybutton);
        exercisebutton = findViewById(R.id.exercisebutton);
        mealbutton = findViewById(R.id.mealbutton);
        playbutton = findViewById(R.id.playbutton);
        otherbutton = findViewById(R.id.otherbutton);
        intensitygroup = findViewById(R.id.radiogroupthree);
        easybutton = findViewById(R.id.easybbutton);
        midiumbutton = findViewById(R.id.midiumbutton);
        hardbutton = findViewById(R.id.hardbutton);
        calendar = findViewById(R.id.calendarView);
        starttime = findViewById(R.id.starttimepicker);
        endtime = findViewById(R.id.endtimepicker);

        mdatabase = FirebaseDatabase.getInstance().getReference();

        radiogroupone.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId != -1 && isChecking){
                    isChecking = false;
                    radiogrouptwo.clearCheck();
                }
                isChecking = true;
            }
        });

        radiogrouptwo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId != -1 && isChecking){
                    isChecking = false;
                    radiogroupone.clearCheck();
                }
                isChecking = true;
            }
        });

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                myyear = String.valueOf(year);
                mymonth = String.valueOf(month);
                mydateofmonth = String.valueOf(dayOfMonth);
            }
        });


    }

    public void savebutton(View view){
        Boolean titlechecker = false;
        Boolean typechecker = false;
        Boolean intensitychecker = false;
        Boolean timechecker = false;

        String title = titletext.getText().toString();

        if (!title.isEmpty()){
            titlechecker = true;
        }

        String type = "";

        switch (radiogroupone.getCheckedRadioButtonId()){
            case R.id.workbutton:
                type = "work";
                break;
            case R.id.studybutton:
                type = "study";
                break;
            case R.id.exercisebutton:
                type = "exerices";
                break;
            default:
                ;
        }


        switch (radiogrouptwo.getCheckedRadioButtonId()){
            case R.id.mealbutton:
                type = "meal";
                break;
            case R.id.playbutton:
                type = "play";
                break;
            case R.id.otherbutton:
                type = "other";
                break;
            default:
                ;
        }


        if(!type.isEmpty()){
            typechecker = true;
        }


        int intensity = 0;

        switch (intensitygroup.getCheckedRadioButtonId()){
            case R.id.easybbutton:
                intensity = 1;
                break;
            case R.id.midiumbutton:
                intensity = 2;
                break;
            case R.id.hardbutton:
                intensity = 3;
                break;
        }

        if(intensity>0){
           intensitychecker = true;
        }

        if(myyear == null){
            Calendar c = Calendar.getInstance();
            myyear = String.valueOf(c.get(Calendar.YEAR));
            mymonth = String.valueOf(c.get(Calendar.MONTH));
            mydateofmonth = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        }

        date = myyear+","+mymonth+","+mydateofmonth;

        int starttimehour, starttimeminute, endtimehour, endtimeminute;

        starttimehour = starttime.getCurrentHour();
        starttimeminute = starttime.getCurrentMinute();
        endtimehour = endtime.getCurrentHour();
        endtimeminute = endtime.getCurrentMinute();

        if(starttimehour<endtimehour){
            timechecker = true;
        }
        else if(starttimehour == endtimehour){
            if(starttimeminute<endtimeminute){
                timechecker = true;
            }
        }

        int ordertime = 60*starttimehour+starttimeminute;

        if(!titlechecker){
            Toast.makeText(getApplicationContext(),"Title is empty", Toast.LENGTH_LONG).show();
        }

        if(!typechecker){
            Toast.makeText(getApplicationContext(),"Type is unselected", Toast.LENGTH_LONG).show();
        }

        if(!intensitychecker){
            Toast.makeText(getApplicationContext(),"Intensity is unselected", Toast.LENGTH_LONG).show();
        }

        if(!timechecker){
            Toast.makeText(getApplicationContext(),"start time must set before end time", Toast.LENGTH_LONG).show();
        }

        if(titlechecker && typechecker && intensitychecker && timechecker){
            Task newtask = new Task(title,type,date,intensity,starttimehour,starttimeminute,endtimehour,endtimeminute,ordertime,0);
            mdatabase.child(date).child(title).setValue(newtask);

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            overridePendingTransition(0,0);
        }





    }

    public void cancelbutton(View view){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        overridePendingTransition(0,0);
    }
}