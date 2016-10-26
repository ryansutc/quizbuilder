package com.example.w0143446.quizbuilder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

/**
 Result Class for end of quiz
 */
public class Result extends AppCompatActivity {
    TextView tvScore;
    TextView tvResult;
    Button btnTryAgain;
    Button btnHome;
    String result;
    HashMap<String, String> fileData;

    String scoreText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        tvScore = (TextView) findViewById(R.id.tvScore);
        tvResult = (TextView) findViewById(R.id.tvResult);
        btnTryAgain = (Button) findViewById(R.id.btnTryAgain);
        btnHome = (Button) findViewById(R.id.btnHome);

        Intent myintent = getIntent();
        scoreText = myintent.getStringExtra("ScoreText");
        fileData = (HashMap<String, String>) myintent.getSerializableExtra("fileData");
        result = myintent.getStringExtra("Result");
        System.out.println(result);
        tvScore.setText(scoreText);
        tvResult.setText(result);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), Welcome.class);
                startActivity(myIntent);
            }
        });

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), MainActivity.class);
                myIntent.putExtra("fileData", fileData);
                myIntent.putExtra("Style", "restart");
                startActivity(myIntent);
            }
        });
    }



}
