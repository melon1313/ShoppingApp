package com.example.chris.goodbuy2.Service;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class QueryTask extends AsyncTask< String, Void, String > {

    @Override
    protected String doInBackground(String... strings) {

        String strurl = strings[0]; // strings[0] 為 要導向 的 php
        String strjson = strings[1]; //  strings[1] 為 string 的 json

        String result;
        StringBuilder param = new StringBuilder();
        Log.d("debug222" , "into doInBackground");
        try {
            param.append("info_json=").append( URLEncoder.encode(strjson, "UTF-8") );
            Log.d("debug222999","in try show json" + strjson);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.d("debug222 catch00",e.toString());
        }
        finally {
        }

        try {
            URL urlObj = new URL( strurl );
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            Log.d("debug222","Wrong1111111");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(8000);
            conn.setReadTimeout(8000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf( param.toString().length() ));

            // Post
            DataOutputStream out = new DataOutputStream( conn.getOutputStream() );
            out.writeBytes( param.toString() );
            out.flush();
            out.close();
            Log.d("debug222","Wrong22222222");
            InputStream in = conn.getInputStream() ;
            result = fetchStream( in );
        }
        catch ( Exception e ) {
            result = e.getMessage();
            Log.d("debug222Exception",e.toString());
        }
        return result;
    }


    private String fetchStream(InputStream in) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String nextLine = "";
            while ((nextLine = reader.readLine()) != null) {
                sb.append(nextLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("debug22277777"," fetchStream  sb get");
        return sb.toString();
    }

}
