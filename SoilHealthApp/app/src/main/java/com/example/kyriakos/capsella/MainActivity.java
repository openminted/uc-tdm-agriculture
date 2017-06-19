//This program is free software: you can redistribute it and/or modify
//        * it under the terms of version 3 of the GNU General Public License as published by
//        * the Free Software Foundation, or (at your option) any later version.
//        *
//        * This program is distributed in the hope that it will be useful,
//        * but WITHOUT ANY WARRANTY; without even the implied warranty of
//        * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//        * GNU General Public License for more details.
//        *
//        * You should have received a copy of the GNU General Public License
//        *License



package com.example.kyriakos.capsella;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Locale;

import static android.R.attr.data;


public class MainActivity extends Activity {


    private RadioGroup radioGroupQuestion1;
    private RadioButton radioButtonQuestion1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        radioGroupQuestion1 = (RadioGroup) findViewById(R.id.radioGroupQ1);


        Button NextButton = (Button) findViewById(R.id.Q1NextButton);
        NextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


                int selectedId = radioGroupQuestion1.getCheckedRadioButtonId();
                radioButtonQuestion1 = (RadioButton) findViewById(selectedId);
                String AnswerQ1 = radioButtonQuestion1.getText().toString();
                String fileName = "spade.txt";
                saveDataToFile(AnswerQ1, fileName);


                Intent myIntent = new Intent(view.getContext(), Question2.class);
                startActivityForResult(myIntent, 0);
                finish();
            }
        });


        Button SkipButton = (Button) findViewById(R.id.Q1SkipButton);
        SkipButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent myIntent = new Intent(view.getContext(), Question2.class);
                startActivityForResult(myIntent, 0);
                finish();
            }
        });




    }


    public void saveDataToFile(String answer, String fileName) {

       // Log.d("Checks", "Trying to save data");
/*
        try {
            // Set up the file directory
            String filePath = Environment.getExternalStorageDirectory().toString() + "/Data Folder";
            File fileDirectory = new File(filePath);
            fileDirectory.mkdirs();
            Log.d("Checks", "Directory created");

            // Set up the file itself
            File textFile = new File(fileDirectory, fileName);
            textFile.createNewFile();
            Log.d("Checks", "File created");

            // Write to the file
            FileOutputStream fileOutputStream = new FileOutputStream(textFile,true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.append("  " + "\"fcov\":" + " " + "\""+answer+"\"," + "\n" );
            outputStreamWriter.close();
            fileOutputStream.close();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

*/
        try {
            File myFile = new File("/sdcard/SpadeTest.txt");
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile,true);
            OutputStreamWriter myOutWriter =
                    new OutputStreamWriter(fOut);
            myOutWriter.append("  " + "\"fcov\":" + " " + "\""+answer+"\"," + "\n" );
            myOutWriter.close();
            fOut.close();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }


    }



    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again if you want to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

                                      @Override
                                      public void run() {
                                          doubleBackToExitPressedOnce=false;

                                      }
                                  }

                , 2000);
    }

}




