package com.example.healthcloud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Vector;

public class Recom extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recom);

        TextView cases = findViewById(R.id.txt_case);
        //cases.setText("hi world!");


        Bundle bundle = this.getIntent().getExtras();
        double BMR = bundle.getDouble("BMR");
        int weight = bundle.getInt("weight");

        //Intent intent = getIntent();
        //System.out.println("hello");
//        double BMR = Double.parseDouble( intent.getStringExtra("BMR") );
//        int weight = Integer.parseInt( intent.getStringExtra("weight"));


        Recommandation recom = new Recommandation(weight, (int) BMR);
        Vector<String> options = recom.get_options_set();
        String content = "";
        for(int i = 0; i < options.size(); i++)
        {
            if (i == 0)
            {
                content = options.get(i);
            }
            else
            {
                content += "       " + options.get(i);
            }
        }
        cases.setText(content);
        System.out.println("\n\n\n output:\n\n"+options);

    }
}