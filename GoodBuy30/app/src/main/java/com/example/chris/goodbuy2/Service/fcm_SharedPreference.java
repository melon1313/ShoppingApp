package com.example.chris.goodbuy2.Service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
///////////////////////////////////////////////////////////
//FCM(推播用)/////////////////////////////////////////////
//////////////////////////////////////////////////////////
public class fcm_SharedPreference {
    private static final String SHARED_PREF_NAME = "FCMSharedPref";
    private static final String TAG_TOKEN = "tagtoken";

    private static fcm_SharedPreference mInstance;
    private static Context mCtx;

    private fcm_SharedPreference(Context context) {
        mCtx = context;
    }

    public static synchronized fcm_SharedPreference getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new fcm_SharedPreference(context);
        }
        return mInstance;
    }

    //this method will save the device token to shared preferences
    public boolean saveDeviceToken(String token){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        //MODE_PRIVATE
        //只允許本應用程式內存取，這是最常用參數。
        //MODE_MULTI_PROCESS
        //允許多個行程同時存取這個設定檔，這個設定在Android 2.3(含)以前都是預設啟用的，但2.3之後得要指定這個參數才允許多行程同時存取設定檔。
        //MODE_WORLD_READABLE
        //讓手機中的所有app都能讀取這個設定檔，因為風險性太高，從API 17版開始就不建議使用這個參數了。
        //MODE_WORLD_WRITEABLE
        //讓手機中的所有app都能存取、寫入這個設定檔，已經不建議使用了，原因與上一個參數相同。
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_TOKEN, token);
        editor.apply();
        Log.d("FCM001","test:token"+editor.toString());
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getDeviceToken(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_TOKEN, null);
    }
}
