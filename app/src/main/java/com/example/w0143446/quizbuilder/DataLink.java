package com.example.w0143446.quizbuilder;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by w0143446 on 10/22/2016.
 */
public class DataLink {

    //no constructor. how to make singleton?

    public  HashMap<String, String> loadCSVData(String filename, Context appContext) {
        /*
        Public method loads comma seperated data (.csv, .txt) from
        an external text file into memory as hashmaps and arraylists
         */
        AssetManager am = appContext.getAssets(); //now aware of project folder config and gets assets within

        //create input stream reader
        InputStream is = null;
        try {
            is = am.open(filename);
            System.out.println("file in assets is open");

        } catch(IOException e) {
            System.out.println("error opening file");
            //return "Error opening text file";
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String s;
        String mykey = new String();
        String myval = new String();
        HashMap<String, String> hmData = new HashMap<String, String>();

        try  {
            while ((s = br.readLine()) != null) {
                myval = s.substring(0,s.indexOf(','));
                mykey = s.substring(s.indexOf(',') + 1, s.length());
                hmData.put(mykey,myval); //populate hashMap
            }
        }
        catch(IOException e) {
            System.out.println("error reading file with buffered reader");
            //return "Error reading text file.";
        }

        return hmData;
        //setQuestionArray(hmData);
        //setAnswerArray(hmData);
    }
}
