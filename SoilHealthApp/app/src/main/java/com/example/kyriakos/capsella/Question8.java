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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by Kyriakos on 04-Jun-17.
 */

public class Question8 extends Activity{

    EditText mEdit;


    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.question8);
        mEdit = (EditText) findViewById(R.id.EditTextQ8);



        Button NextButton = (Button) findViewById(R.id.Q8NextButton);
        NextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                  if(mEdit.getText().toString().length() != 0) {
                      String IntegerCheck = mEdit.getText().toString();
                       try {
                           Integer.parseInt(IntegerCheck);
                           int NumberOfLayers = Integer.parseInt(String.valueOf(mEdit.getText()));
                          // int LayerCounter = NumberOfLayers;

                           //String Question8 = getString(R.string.Q8);
                           String fileName = "spade.txt";
                           saveDataToFile(NumberOfLayers, fileName);

                           Intent myIntent = new Intent(view.getContext(), NumberOfLayers.class);
                           startActivityForResult(myIntent, 0);
                           finish();
                       }
                       catch(NumberFormatException e){
                           Toast.makeText(getApplicationContext(),getResources().getString(R.string.Mtoast), Toast.LENGTH_SHORT).show();

                      }
                  }
                  else {
                      Toast.makeText(getApplicationContext(),getResources().getString(R.string.Mtoast2), Toast.LENGTH_SHORT).show();

                  }


            }
        });



    }




    public void saveDataToFile(int answer, String fileName) {

        try {
            File myFile = new File("/sdcard/SpadeTest.txt");
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile,true);
            OutputStreamWriter myOutWriter =
                    new OutputStreamWriter(fOut);
            myOutWriter.append("  " + "\"laynum\":" + " " +answer+ "," + "\n" );
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
//            outputStreamWriter.append("  " + "\"laynum\":" + " " +answer+ "," + "\n" );
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
