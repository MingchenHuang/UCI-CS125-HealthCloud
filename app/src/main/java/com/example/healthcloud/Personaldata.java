package com.example.healthcloud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Vector;



public class Personaldata extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static int height, weight, age;
    public static String sex;
    public static double BMR = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personaldata);

        Spinner s = findViewById(R.id.spinner_sex);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sex_selection, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(this);

    }


    public void on_Comfirm_BTN_Click(View view)
    {
        EditText h = findViewById(R.id.edit_height);
        EditText w = findViewById(R.id.edit_weight);
        EditText a = findViewById(R.id.edit_age);

        height = Integer.parseInt(h.getText().toString());
        weight = Integer.parseInt(w.getText().toString());
        age = Integer.parseInt(a.getText().toString());


        String index = "Female";

        if (sex.equals( "Female" ) )  //Female BMR = 10W + 6.25H - 5A - 161
        {
            BMR = 10*weight + 6.25*height - 5*age - 161;
        }
        else //Male BMR = 10W + 6.25H - 5A + 5
        {
            BMR = 10*weight + 6.25*height - 5*age + 5;
            index = "Male";
        }



        //TextView temp = findViewById(R.id.txt_age);
        //temp.setText(Double.toString(BMR));


        //TextView temper = findViewById(R.id.txt_weight);
        //temper.setText(index);
        openMainActivity2();
    }

    public void openMainActivity2()
    {


        Intent intent = new Intent(this, Recom.class);
        intent.putExtra("weight", weight);
        intent.putExtra( "BMR", BMR);
        startActivity(intent);


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        sex = text;
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}