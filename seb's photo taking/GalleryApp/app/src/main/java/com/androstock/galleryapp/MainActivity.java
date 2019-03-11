package com.androstock.galleryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnStart;                        //Declaring btnStart as an instance of Button
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button) findViewById(R.id.btn_to_start);
        btnStart.setOnClickListener( new View.OnClickListener() {

            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this,   //On click, a new
                        StartActivity.class);                                 // activity is open
                startActivity(intent);
            }
        });





    }
}
