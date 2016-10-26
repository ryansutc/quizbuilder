package com.example.w0143446.quizbuilder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by w0143446 on 10/11/2016.
 */
public class Welcome extends AppCompatActivity implements View.OnClickListener{
    Button btnStart;
    EditText etName;
    DataLink dl = new DataLink();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
        etName = (EditText) findViewById(R.id.etName);

    }

    @Override
    public void onClick(View v) {
        if (etName.getText().toString().equals("")) {
            Toast myToast = Toast.makeText(getApplicationContext(), "Please Enter a Name", Toast.LENGTH_SHORT);
            myToast.show();
        }
        else {


            Intent myIntent = new Intent(v.getContext(), MainActivity.class);
            String myfile = "udwords.txt";
            HashMap<String, String> fileData = dl.loadCSVData(myfile, getApplicationContext());
            if (fileData != null) {
                Toast myToast = Toast.makeText(getApplicationContext(), "successfully loaded data", Toast.LENGTH_LONG);
                myToast.show();
            } else {
                Toast myToast = Toast.makeText(getApplicationContext(), "unable to load data", Toast.LENGTH_LONG);
                myToast.show();
            }
            myIntent.putExtra("fileData", fileData);
            myIntent.putExtra("Name", etName.getText().toString());//put bundle in intent
            startActivity(myIntent); //expecting result back from activity
        }
    }
}
