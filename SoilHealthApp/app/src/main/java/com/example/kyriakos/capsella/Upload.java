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

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static junit.framework.Assert.assertTrue;

public class Upload extends Activity {
    // GUI controls
    EditText txtData;
    Button UploadButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload);


        // bind GUI elements with local controls
        txtData = (EditText) findViewById(R.id.txtData);


        UploadButton = (Button) findViewById(R.id.UploadButton);
        UploadButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {



                try {
                    File myFile = new File("/sdcard/SpadeTest.txt");
                    FileInputStream fIn = new FileInputStream(myFile);
                    BufferedReader myReader = new BufferedReader(
                            new InputStreamReader(fIn));
                    String aDataRow = "";
                    String aBuffer = "";
                    while ((aDataRow = myReader.readLine()) != null) {
                        aBuffer += aDataRow + "\n";
                    }

                    User ob1 = ((User) getApplicationContext());
                    ob1.setGlobalVarValue2(aBuffer);
                   // txtData.setText(aBuffer);




                    myReader.close();





                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }

                User ob = ((User) getApplicationContext());
                String userget = ob.getGlobalVarValue();
                System.out.println("TOKEN");
                System.out.println(userget);

                User ob1 = ((User) getApplicationContext());
                String bufferget = ob1.getGlobalVarValue2();
                System.out.println("BUFFER");
                System.out.println(bufferget);




                String url2 ="https://capsella-services.madgik.di.uoa.gr:8443/data-manager-service/datasets";

                try {
                    dopostRequest(url2,userget);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }


                User ob21 = ((User) getApplicationContext());
                int userCODE = ob21.getGlobalCODE();
                System.out.println("CODE");
                System.out.println(userCODE);


                if(userCODE == 200) {
                    Toast.makeText(getBaseContext(), "Your Spade test has been uploaded", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getBaseContext(), "Error with the upload please try again", Toast.LENGTH_SHORT).show();

                }





            }
        });


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

    void dopostRequest(String url, String token) throws IOException, NoSuchAlgorithmException {


        User ob = ((User) getApplicationContext());
        String userfullname = ob.getGlobalfullName();
        System.out.println("FULLNAME");
        System.out.println(userfullname);


        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Map<String, String> metadata = new HashMap<String, String>();

        metadata.put("content-type", "csv");
        metadata.put("description", "Description of Spade Test");
        metadata.put("access", "public");
        metadata.put("dataset_name", "testDatasetName");
        metadata.put("tags", "newtags");
        metadata.put("author",userfullname);

        JSONObject meta = new JSONObject(metadata);
        System.out.println(meta.toString());

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

            String urltest ="https://capsella-services.madgik.di.uoa.gr:8443/data-manager-service/datasets";
            //String met = "{\"content-type\":\"csv\",\"description\":\"Description of Spade Test\",\"access\":\"public\",\"dataset_name\":\"testDatasetName\",\"tags\":\"newtags\"}";

            String met = meta.toString();





            RequestBody body = RequestBody.create(JSON, "");
            String url0=urltest + "?metadata=" + URLEncoder.encode(met,"UTF-8");
            System.out.println(url0);
            Request request = new Request.Builder()
                    .url(url0)
                    .post(body)
                    .addHeader("Authorization", "Bearer " + token)
                    .addHeader("group", "soil_app")
                    .addHeader("content-type", "application/json; charset=utf-8")

                    .build();


            okHttpClient.newCall(request).enqueue(new Callback() {


                @Override
                public void onFailure(Call call, IOException e) {

                }
                @Override

                public void onResponse(Call call, Response response) throws IOException {

                    System.out.println("XXXXXXXXXXXXXXXXXXX");

                    String s1 = response.body().string();
                    System.out.println("Response Body :" + s1);



                    try {
                        JSONObject json_response = new JSONObject(s1);
                        String json_response_uuid = (String) json_response.get("location");
                        String uuid = json_response_uuid;
                        System.out.println("XXXXXXXXXXXXXXXXXXX");

                        System.out.println("UUID" + uuid);


                        User ob = ((User) getApplicationContext());
                        ob.setGlobalVarValueUrl(uuid);


                        try {
                            dopostRequest2();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }


            });


        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

    }


    void dopostRequest2() throws IOException, NoSuchAlgorithmException {


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



            User ob = ((User) getApplicationContext());
            String userget = ob.getGlobalVarValue();
            System.out.println("TOKEN");
            System.out.println(userget);

            User ob1 = ((User) getApplicationContext());
            String bufferget = ob1.getGlobalVarValue2();
            System.out.println("BUFFER");
            System.out.println(bufferget);

            User ob2 = ((User) getApplicationContext());
            String urlget = ob2.getGlobalVarValueUrl();
            System.out.println("UUID DO2");
            System.out.println(urlget);





              final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");


            OkHttpClient okHttpClient = builder.build();





            File myFile = new File("/sdcard/SpadeTest.txt");

            RequestBody formBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("uploadfile","SpadeTest.txt",RequestBody.create(MEDIA_TYPE_MARKDOWN,new File("/sdcard/SpadeTest.txt")))
                    .build();

            System.out.println("BF req");

           Request request = new Request.Builder()
                    .url(urlget)
                    .post(formBody)
                    .addHeader("Authorization", "Bearer " + userget)
                    .addHeader("group", "soil_app")
                    .addHeader("content-type", "application/json; charset=utf-8")
                    .build();


            okHttpClient.newCall(request).enqueue(new Callback() {


                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {


                    System.out.println("RESPONSE CODE");
                    System.out.println(response.code());


                    User ob = ((User) getApplicationContext());
                    ob.setGlobalCODE(response.code());




                }


            });


        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

    }


}