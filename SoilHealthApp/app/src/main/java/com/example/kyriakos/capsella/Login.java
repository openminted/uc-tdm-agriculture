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


import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class Login extends AppCompatActivity {

    EditText mEdit;
    EditText mEdit1;
    Button htmlgetbutton;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);




        htmlgetbutton = (Button) findViewById(R.id.loginbutton);
        mEdit = (EditText) findViewById(R.id.user);
        mEdit1 = (EditText) findViewById(R.id.pass);



        htmlgetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = mEdit.getText().toString();
                System.out.println(username);
                String password = mEdit1.getText().toString();
                System.out.println(password);
                final String url = "https://capsella-services.madgik.di.uoa.gr:8443/capsella_authentication_service/authenticate?username="+ username + "&password=" + password;


                try {
                    dopostRequest(url);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                if (flag == false) {
                    Intent myIntent = new Intent(view.getContext(), UserInformationZero.class);
                    startActivityForResult(myIntent, 0);
                    finish();
                } else {

                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.Mtoast5), Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


    public void dopostRequest(String url) throws IOException, NoSuchAlgorithmException {


        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Map<String, String> params = new HashMap<String, String>();

        JSONObject parameter = new JSONObject(params);


        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };


            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();


            RequestBody body = RequestBody.create(JSON, parameter.toString());
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("content-type", "application/json; charset=utf-8")
                    .build();

            okHttpClient.newCall(request).enqueue(new Callback() {


                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    String s = response.body().string();
                    System.out.println(s);
                    System.out.println(s.length());
                    if(s.length()>20 && s.length() <1000) {

                        try {
                            JSONObject json_response = new JSONObject(s);
                            String json_response_token = (String) json_response.get("token");
                            String token = json_response_token;
                            System.out.println(token);

                            if (token != null) {

                                User ob = ((User) getApplicationContext());
                                ob.setGlobalVarValue(token);

                                flag = true;
                            }



                            JSONObject json_response_user = (JSONObject) json_response.get("user");
                            String json_response_fullName = (String) json_response_user.get("fullName");
                            String fullName = json_response_fullName;
                            System.out.println(fullName);


                            User ob = ((User) getApplicationContext());
                            ob.setGlobalfullName(fullName);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
}
                    }





            });


        } catch (KeyManagementException e) {
            e.printStackTrace();
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
        }, 2000);
    }


}

