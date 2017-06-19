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

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kyriakos on 07-Jun-17.
 */

public class UserInformation extends Activity {
    public static int Q15 = 0;

    EditText mEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_information);


        mEdit = (EditText) findViewById(R.id.EditTextUI);

        final Button NextButton = (Button) findViewById(R.id.UINextButton);
        NextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (mEdit.getText().toString().length() != 0) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Date cDate = new Date();
                    String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
                    String fileName = "spade.txt";
                    String InTextUI = mEdit.getText().toString();
                    saveDataToFile1(InTextUI, fDate, fileName);

                    Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                    startActivityForResult(myIntent, 0);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.Mtoast3), Toast.LENGTH_SHORT).show();

                }
            }
        });







    }





    public void saveDataToFile1(String Latitude, String Longitude, String fileName) {


        Log.d("Checks", "Trying to save data");
/*
        try {
            // Set up the file directory
            String filePath = Environment.getExternalStorageDirectory().toString() + "/Data Folder";
            File fileDirectory = new File(filePath);

            Log.d("Checks", "Directory created");

            // Set up the file itself
            File textFile = new File(fileDirectory, fileName);
            Log.d("Checks", "File created");

            // Write to the file
            FileOutputStream fileOutputStream = new FileOutputStream(textFile, true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.append("  " + "\"name\":" + " " + "\"" + Latitude + "\"," + "\n");
            outputStreamWriter.append("  " + "\"date\":" + " " + "\"" + Longitude + "\"," + "\n");
            // outputStreamWriter.append(jsonStr);
            outputStreamWriter.close();
            fileOutputStream.close();

            // Toast.makeText(getApplicationContext(), "Done writing to SD card", Toast.LENGTH_SHORT).show();
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
            myOutWriter.append("  " + "\"name\":" + " " + "\"" + Latitude + "\"," + "\n" );
            myOutWriter.append("  " + "\"date\":" + " " + "\"" + Longitude + "\"," + "\n");

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
                                          doubleBackToExitPressedOnce = false;

                                      }
                                  }

                , 2000);
    }
}