package com.example.chris.goodbuy2.Service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.chris.goodbuy2.Activity.HomeActivity;
import com.example.chris.goodbuy2.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;
import java.util.concurrent.ExecutionException;
///////////////////////////////////////////////////////////
//FCM(推播用)/////////////////////////////////////////////
//////////////////////////////////////////////////////////

public class MyMessagingService extends FirebaseMessagingService {
    String msg_importURL ="http://192.168.1.198/final_report/notification.php";
    private  static final String TAG= "MyFirebaseMsgService";
    //接受Firebase訊息。
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("debug00","002");
        showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
        Log.d("debug00","001");
        if (remoteMessage.getData().size()>0){
            try {
                Log.d("debug00","000");
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                sendPushNotification(json);
                Log.d("debug00",json.toString());
            }catch (Exception e){
                Log.e(TAG,"Exception:"+e.getMessage());
            }
        }
    }
    //this method will display the notification
    //We are passing the JSONObject that is received from
    //firebase cloud messaging
    private void sendPushNotification(JSONObject json) {
        //optionally we can display the json into log
        Log.e(TAG, "Notification JSON " + json.toString());
        try {
            //getting the json data
            JSONObject data = json.getJSONObject("data");
            Log.d("debug005","005");
            //parsing json data
            String title = data.getString("title");
            String message = data.getString("message");
            String imageUrl = data.getString("image");
            Log.d("debug005","006");
            //creating MyNotificationManager object
            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());

            //creating an intent for the notification
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            final int not_nu =generateRandom();
            //if there is no image
            if(imageUrl.equals("null")){
                //displaying small notification
                mNotificationManager.showSmallNotification(title, message, intent);
                Log.d("debug005","007");
            }else{
                //if there is an image
                //displaying a big notification
                mNotificationManager.showBigNotification(title, message, imageUrl, intent);
            }
            //將通知與主頁面的Firebase做連結。
            NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"MyNotifications")
                    .setContentTitle(title)
                    .setSmallIcon(R.drawable.login_logo)
                    .setAutoCancel(true)
                    .setContentText(message);
            NotificationManagerCompat manager =NotificationManagerCompat.from(this);
            manager.notify(not_nu,builder.build());
            Log.d("debug005",title+": 0 :"+message);

        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    private int generateRandom() {
        Random random= new Random();
        return random.nextInt(9999-1000)+1000;
    }


    //    將訊息傳入通知。
    public void showNotification(String title,String message){
        //將通知與主頁面的Firebase做連結。
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"MyNotifications")
                .setContentTitle(title)
                .setSmallIcon(R.drawable.login_logo)
                .setAutoCancel(true)
                .setContentText(message);
        NotificationManagerCompat manager =NotificationManagerCompat.from(this);
        manager.notify(9999,builder.build());
        Log.d("FCM00",title+": 0 :"+message);
        JSONObject obj = new JSONObject();
        SharedPreferences login_check=getSharedPreferences("login",Context.MODE_PRIVATE);
        try {
            obj.put("title",title);
            obj.put("message",message);
            obj.put("receiver_id",login_check.getString("member_id","null") );
        } catch (JSONException e) {
            Log.d("debug0001",e.toString());
        }
        String strobj = obj.toString();
        String fcm_title;
        String fcm_msg;
        String fcm_time;
        try {
            String a=new QueryTask().execute(msg_importURL , strobj).get();
            Log.d("debug000000000",a);
            JSONArray jarr = new JSONArray(a);

            for (int i = 0; i < jarr.length(); i++) {
                fcm_title = jarr.getJSONObject(i).getString("title");
                if (fcm_title!= null)
                    Log.d("debug001"+i,fcm_title);
                fcm_msg = jarr.getJSONObject(i).getString("message");
                if (fcm_msg!= null)
                    Log.d("debug001"+i,fcm_msg);
                fcm_time = jarr.getJSONObject(i).getString("send_time");
                if (fcm_time!= null)
                    Log.d("debug001"+i,fcm_time);
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch (JSONException e) {
            e.printStackTrace();
        }
//        StringBuilder sb=new StringBuilder();
//        SharedPreferences fcm_sh=getSharedPreferences("fcm_msg",Context.MODE_PRIVATE);
//        String fcm_data=fcm_sh.getString("data","Data");
//        sb.append(fcm_data);
//        SharedPreferences.Editor fcm_shedit=fcm_sh.edit();
//        fcm_shedit.putString("fcm_title",title);
//        fcm_shedit.putString("fcm_content",message);
//
//        sb.append("\ntitle:"+title+"\n"+"message"+message);
//        fcm_shedit.putString("data",sb.toString());
//        fcm_shedit.apply();
    }
}
