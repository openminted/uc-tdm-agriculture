package com.example.kyriakos.capsella;

import android.app.Application;

/**
 * Created by Kyriakos on 16-Jun-17.
 */

public class User extends Application {

    private String mGlobaltoken;
    private String mGlobalBuffer;
    private String mGlobalUrl;
    private String mGlobalfullName;
    private int mGlobalCODE;



    public String getGlobalVarValue() {

        return mGlobaltoken;
    }

    public void setGlobalVarValue(String str) {

        mGlobaltoken = str;

    }


    public String getGlobalVarValue2() {

        return mGlobalBuffer;
    }


    public void setGlobalVarValue2(String str2) {

        mGlobalBuffer = str2;

    }


    public String getGlobalVarValueUrl() {

        return mGlobalUrl;
    }

    public void setGlobalVarValueUrl(String str3) {

        mGlobalUrl = str3;
    }





    public String getGlobalfullName() {

        return mGlobalfullName;
    }

    public void setGlobalfullName(String str4) {

        mGlobalfullName = str4;
    }





    public int getGlobalCODE() {

        return mGlobalCODE;
    }

    public void setGlobalCODE(int str5) {

        mGlobalCODE = str5;
    }


}
