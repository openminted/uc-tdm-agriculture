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
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by Kyriakos on 06-Jun-17.
 */

public class Question15 extends Activity {

    private RadioButton radioButtonQuestion;
    private RadioButton radioButtonQuestion2;
    private RadioButton radioButtonQuestion3;
    private RadioButton radioButtonQuestion4;

    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

            super.onCreate(savedInstanceState);
            setContentView(R.layout.question15);


        radioButtonQuestion = (RadioButton) findViewById(R.id.radioButton);
        radioButtonQuestion2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButtonQuestion3 = (RadioButton) findViewById(R.id.radioButton3);
        radioButtonQuestion4 = (RadioButton) findViewById(R.id.radioButton4);
        String fileName = "spade.txt";
        saveDataToFile2("", fileName);


        Button NextButton = (Button) findViewById(R.id.Q15NextButton);
        NextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {



                if(radioButtonQuestion.isChecked()) {
                    if(radioButtonQuestion2.isChecked() || radioButtonQuestion3.isChecked() || radioButtonQuestion4.isChecked() ) {
                        String AnswerQ5 = radioButtonQuestion.getText().toString();
                        String fileName = "spade.txt";
                        saveDataToFile(AnswerQ5, fileName);
                    }
                    else{
                        String AnswerQ5 = radioButtonQuestion.getText().toString();
                        String fileName = "spade.txt";
                        saveDataToFile1(AnswerQ5, fileName);
                    }
                }

                if(radioButtonQuestion2.isChecked()) {
                    if(radioButtonQuestion3.isChecked() || radioButtonQuestion4.isChecked()) {
                        String AnswerQ5 = radioButtonQuestion2.getText().toString();
                        String fileName = "spade.txt";
                        saveDataToFile(AnswerQ5, fileName);
                    }
                    else{
                        String AnswerQ5 = radioButtonQuestion2.getText().toString();
                        String fileName = "spade.txt";
                        saveDataToFile1(AnswerQ5, fileName);
                    }
                }

                if(radioButtonQuestion3.isChecked()) {
                    if(radioButtonQuestion4.isChecked()){
                        String AnswerQ5 = radioButtonQuestion3.getText().toString();
                        String fileName = "spade.txt";
                        saveDataToFile(AnswerQ5, fileName);
                    }
                    else{
                        String AnswerQ5 = radioButtonQuestion3.getText().toString();
                        String fileName = "spade.txt";
                        saveDataToFile1(AnswerQ5, fileName);

                    }
                }


                if(radioButtonQuestion4.isChecked()) {
                    String AnswerQ5 = radioButtonQuestion4.getText().toString();
                    String fileName = "spade.txt";
                    saveDataToFile1(AnswerQ5, fileName);
                }


                Intent myIntent = new Intent(view.getContext(), Question16.class);
                startActivityForResult(myIntent, 0);
                finish();

            }
        });

        Button SkipButton = (Button) findViewById(R.id.Q15SkipButton);
        SkipButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(radioButtonQuestion.isChecked() || radioButtonQuestion2.isChecked() || radioButtonQuestion3.isChecked() || radioButtonQuestion4.isChecked() ) {

                    Intent myIntent = new Intent(view.getContext(), Question16.class);
                    startActivityForResult(myIntent, 0);
                    finish();
                }

                else{
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.Mtoast6),Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void saveDataToFile(String answer, String fileName) {

        try {
            File myFile = new File("/sdcard/SpadeTest.txt");
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile,true);
            OutputStreamWriter myOutWriter =
                    new OutputStreamWriter(fOut);
            myOutWriter.append("    "  +"\""+answer+"\","+ "\n");
            myOutWriter.close();
            fOut.close();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

//        Log.d("Checks", "Trying to save data");
//
//        try {
//            // Set up the file directory
//            String filePath = Environment.getExternalStorageDirectory().toString() + "/Data Folder";
//            File fileDirectory = new File(filePath);
//
//            Log.d("Checks", "Directory created");
//
//            // Set up the file itself
//            File textFile = new File(fileDirectory, fileName);
//            Log.d("Checks", "File created");
//
//            // Write to the file
//            FileOutputStream fileOutputStream = new FileOutputStream(textFile, true);
//            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
//            outputStreamWriter.append("    "  +"\""+answer+"\","+ "\n");
//
//            outputStreamWriter.close();
//            fileOutputStream.close();
//
//        } catch (Exception e) {
//            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//        }

    }


    public void saveDataToFile1(String answer, String fileName) {

        try {
            File myFile = new File("/sdcard/SpadeTest.txt");
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile,true);
            OutputStreamWriter myOutWriter =
                    new OutputStreamWriter(fOut);
            myOutWriter.append( "    "+"\""+answer+"\""+ "\n" +"  ]," + "\n");
            myOutWriter.close();
            fOut.close();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

//        Log.d("Checks", "Trying to save data");
//
//        try {
//            // Set up the file directory
//            String filePath = Environment.getExternalStorageDirectory().toString() + "/Data Folder";
//            File fileDirectory = new File(filePath);
//
//            Log.d("Checks", "Directory created");
//
//            // Set up the file itself
//            File textFile = new File(fileDirectory, fileName);
//            Log.d("Checks", "File created");
//
//            // Write to the file
//            FileOutputStream fileOutputStream = new FileOutputStream(textFile, true);
//            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
//            outputStreamWriter.append( "    "+"\""+answer+"\""+ "\n" +"  ]," + "\n");
//
//            outputStreamWriter.close();
//            fileOutputStream.close();
//
//        } catch (Exception e) {
//            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//        }

    }


    public void saveDataToFile2(String answer, String fileName) {

        try {
            File myFile = new File("/sdcard/SpadeTest.txt");
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile,true);
            OutputStreamWriter myOutWriter =
                    new OutputStreamWriter(fOut);
            myOutWriter.append("  " + "\"typor[" + NumberOfLayers.i + "]\"" + ":" +" [" + "\n");
            myOutWriter.close();
            fOut.close();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

//        Log.d("Checks", "Trying to save data");
//
//        try {
//            // Set up the file directory
//            String filePath = Environment.getExternalStorageDirectory().toString() + "/Data Folder";
//            File fileDirectory = new File(filePath);
//
//            Log.d("Checks", "Directory created");
//
//            // Set up the file itself
//            File textFile = new File(fileDirectory, fileName);
//            Log.d("Checks", "File created");
//
//            // Write to the file
//            FileOutputStream fileOutputStream = new FileOutputStream(textFile, true);
//            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
//            outputStreamWriter.append("  " + "\"typor[" + NumberOfLayers.i + "]\"" + ":" +" [" + "\n");
//
//            outputStreamWriter.close();
//            fileOutputStream.close();
//
//        } catch (Exception e) {
//            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//        }

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
