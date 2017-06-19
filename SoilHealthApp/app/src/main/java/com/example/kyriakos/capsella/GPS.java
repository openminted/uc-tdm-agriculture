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
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;



public class GPS extends Activity {

boolean counter = false ;
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps);


        Button NextButton = (Button) findViewById(R.id.GPSButton);
        NextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);


            }
        });
        final Button NextButton1 = (Button) findViewById(R.id.GPSNextButton);

        NextButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (counter == true){

                    Intent myIntent = new Intent(view.getContext(),UserInformation.class);
                    startActivityForResult(myIntent, 0);
                    finish();
               }
               else {

                    Toast.makeText(getApplicationContext(), "Please enable your gps ", Toast.LENGTH_SHORT).show();

                }
            }
        });

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, new GPS.Listener());
        // Have another for GPS provider just in case.
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, new GPS.Listener());
        // Try to request the location immediately
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location == null) {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        if (location != null) {
            handleLatLng(location.getLatitude(), location.getLongitude());
        }
       // Toast.makeText(getApplicationContext(), getResources().getString(R.string.Mtoast), Toast.LENGTH_LONG).show();


    }

    private void handleLatLng(double latitude, double longitude) {
        Log.v("TAG", "(" + latitude + "," + longitude + ")");

    }


    private class Listener implements LocationListener {
        public void onLocationChanged(Location location) {
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            if (counter == false){
            if(Language_Choose.i == 0) {
                String fileName = "SpadeTest";
                saveDataToFile(latitude, longitude, fileName);
                Language_Choose.i = 1;
            }
           }

            handleLatLng(latitude, longitude);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }



    public void saveDataToFile(Double Latitude, Double Longitude, String fileName) {




        try {
            File myFile = new File("/sdcard/SpadeTest.txt");
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append("{" + "\n" + "  " + "\"lat\":" + " " + "\"" + Latitude + "\"," + "\n" );
            myOutWriter.append("  " + "\"lon\":" + " " + "\"" + Longitude + "\"," + "\n");

            myOutWriter.close();
            fOut.close();
            counter = true;

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }







/*
        JSONObject j = new JSONObject();
        try {
            j.put("lat", Latitude);
            j.put("lon", Longitude);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JSONArray jsonArray = new JSONArray();

        jsonArray.put(j);

        JSONObject studentsObj = new JSONObject();
        try {
            studentsObj.put("\n",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String jsonStr = studentsObj.toString();

*/
       /* Log.d("Checks", "Trying to save data");

            try {

                   // Set up the file directory
                   String filePath = Environment.getExternalStorageDirectory().toString() + "/Data Folder";
                   File fileDirectory = new File(filePath);

                   Log.d("Checks", "Directory created");

                   // Set up the file itself
                   File textFile = new File(fileDirectory, fileName);
                   Log.d("Checks", "File created");


                   // Write to the file
                   FileOutputStream fileOutputStream = new FileOutputStream(textFile);
                   OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                   outputStreamWriter.append("{" + "\n" + "  " + "\"lat\":" + " " + "\"" + Latitude + "\"," + "\n");
                   outputStreamWriter.append("  " + "\"lon\":" + " " + "\"" + Longitude + "\"," + "\n");
                   // outputStreamWriter.append(jsonStr);
                   outputStreamWriter.close();
                   fileOutputStream.close();
                   counter = true;

                // Toast.makeText(getApplicationContext(), "Done writing to SD card", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }

             */
        }





    }

