
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
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Kyriakos on 27-May-17.
 */

public class Language_Choose extends Activity{


    public static int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_language);







        Button Grbutton = (Button) findViewById(R.id.GrButton);
        Grbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Locale mLocale = new Locale("el");
                Locale.setDefault(mLocale);
                Configuration config = getBaseContext().getResources().getConfiguration();
                if (!config.locale.equals(mLocale)) {
                    config.locale = mLocale;
                    getBaseContext().getResources().updateConfiguration(config, null);
                    Intent myIntent = new Intent(v.getContext(), Login.class);
                    startActivityForResult(myIntent, 0);
                    finish();
                }

            }


        });


        Button ItButton = (Button) findViewById(R.id.ItButton);
        ItButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Locale mLocale = new Locale("it");
                Locale.setDefault(mLocale);
                Configuration config = getBaseContext().getResources().getConfiguration();
                if (!config.locale.equals(mLocale)) {
                    config.locale = mLocale;
                    getBaseContext().getResources().updateConfiguration(config, null);
                }
                Intent myIntent = new Intent(v.getContext(), Login.class);
                startActivityForResult(myIntent, 0);
                finish();
            }


        });


        Button EnButton = (Button) findViewById(R.id.EnButton);
        EnButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Locale mLocale = new Locale("en");
                Locale.setDefault(mLocale);
                Configuration config = getBaseContext().getResources().getConfiguration();
                if (!config.locale.equals(mLocale)) {
                    config.locale = mLocale;
                    getBaseContext().getResources().updateConfiguration(config, null);
                }
                Intent myIntent = new Intent(v.getContext(), Login.class);
                startActivityForResult(myIntent, 0);
                finish();
            }


        });



    }
}


