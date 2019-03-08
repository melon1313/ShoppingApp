package com.example.chris.goodbuy2.Service;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
///////////////////////////////////////////////////////////
//FCM(推播用)/////////////////////////////////////////////
//////////////////////////////////////////////////////////

public class FcmVolley {

    //建立傳輸管道，好用的http client library
    private static FcmVolley mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private FcmVolley(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized FcmVolley getInstance(Context context) {
        //確認是否已有產生連線設定
        if (mInstance == null) {
            mInstance = new FcmVolley(context);
            Log.d("debug001","002");
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        //檢驗連線狀況
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
            Log.d("debug002","002");
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
