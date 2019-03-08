package com.example.chris.goodbuy2.Activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.chris.goodbuy2.Fragment.AccountFragment;
import com.example.chris.goodbuy2.Fragment.FavoriteFragment;
import com.example.chris.goodbuy2.Fragment.HomeFragment;
import com.example.chris.goodbuy2.Fragment.NoticficationFragment;
import com.example.chris.goodbuy2.R;
import com.example.chris.goodbuy2.Service.QueryTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HomeActivity extends AppCompatActivity {

    //------ 導覽列宣告
    private BottomNavigationView bottomNavigationView;

    //------ 分頁宣告
    private FrameLayout frameLayout; // 裝載分頁的容器
    private HomeFragment homeFragment;
    private FavoriteFragment favoriteFragment;
    private NoticficationFragment noticficationFragment;
    private AccountFragment accountFragment;
    String token_SaveURL;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        token_SaveURL=getResources().getString(R.string.localDataBase)+"token_Save.php";

        //讀取登入狀態
         final SharedPreferences login_check=getSharedPreferences("login",Context.MODE_PRIVATE);
         final SharedPreferences.Editor login_save = login_check.edit();
        try {
            //FCM(推播設定)-設定版本條件，因為在OREO版本中要做通知設定。
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                NotificationChannel channel= new NotificationChannel("MyNotifications","MyNotifications",NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManager manager =getSystemService(NotificationManager.class);
                manager.createNotificationChannel(channel);}
            if(login_check.getString("member_id","null") != "null"){
            //開啟的同時啟用索取token
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {

                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (task.isSuccessful())
                            {
                                JSONObject obj = new JSONObject();
                                String token =task.getResult().getToken();
                                Log.d("debug002_token",token);
                                login_save.putString("token",token);
                                login_save.apply();
                                try {
                                    obj.put("email",login_check.getString("member_email","null"));
                                    obj.put("token",token);
                                    String strobj = obj.toString();
                                    String check_FCM_token = new QueryTask().execute(token_SaveURL, strobj).get();
                                    Log.d("debug002_msg_tokenSave",check_FCM_token);
                                    if(check_FCM_token.equals("成功")) {
                                        Log.d("debug002", "token 已經確認及更新 成功");
                                    } else{
                                        Log.d("debug002","token 確認及更新 失敗");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                            else{
                                Log.d("FCM00",task.getException().toString());
                            }
                        }
                    });}
            //負責連結Firebase。
            FirebaseMessaging.getInstance().subscribeToTopic("general")
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            String msg = "Successful";
                            if (!task.isSuccessful()) {
                                msg = "Failed"; }
                            //顯示連結狀況
                            //Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_LONG).show();
                            Log.d("debug00",msg);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("FCM00",e.toString());
        }


        //----------------------- 導覽列 -----------------------//
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.main_nav);// 取得 "導覽列" 物件參考

        //------ 導覽頁面
        frameLayout = (FrameLayout)findViewById(R.id.home_fragment_framelayout);// 取得 "framelayout" 物件參考
        homeFragment = new HomeFragment(); // 熊城
        favoriteFragment = new FavoriteFragment(); // 喜愛
        noticficationFragment = new NoticficationFragment(); // 通知
        accountFragment = new AccountFragment(); // 我的
        setFragment(homeFragment); // 初始導覽頁面為home頁

        //------ 設定 導覽列 "監聽"
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        setSateToolbarColor(HomeActivity.this, R.color.green);
                        setFragment(homeFragment);
                        return true;
                    case R.id.nav_favorite:
                             check_login_token(favoriteFragment, R.color.green);
//                            if(login_check.getString("member_id","null") != "null"){
//                            setSateToolbarColor(HomeActivity.this, R.color.colorAccent);
//                            setFragment(favoriteFragment);
//                        Toast.makeText(HomeActivity.this, login_check.getString("member_id","null") , Toast.LENGTH_SHORT).show();}
//                            else {
//                                startActivity(new Intent(HomeActivity.this, LoginActivity.class));};
                        return true;
                    case R.id.nav_notification:
                        check_login_token(noticficationFragment, R.color.green);
//                        if(login_check.getString("member_id","null") != "null"){
//                        setSateToolbarColor(HomeActivity.this, R.color.green);
//                        setFragment(noticficationFragment);}
//                        else {
//                            startActivity(new Intent(HomeActivity.this, LoginActivity.class));};
                        return true;
                    case R.id.nav_account:
                        check_login_token(accountFragment, R.color.black);
//                        if(login_check.getString("member_id","null") != "null"){
//                        setSateToolbarColor(HomeActivity.this, R.color.black);
//                        try {
//                            setFragment(accountFragment);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            Log.d("Errrrrrrro", e.toString());
//                        }}
//                        else {
//                                startActivity(new Intent(HomeActivity.this, LoginActivity.class));};
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    //----------------------- 導覽列 -----------------------//
    //------ 導覽頁換頁方法
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home_fragment_framelayout, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    //---- 導覽列顏色設定
    public void setSateToolbarColor(AppCompatActivity appCompatActivity, Integer color){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(ContextCompat.getColor(appCompatActivity, color));
        }
    }

    //----------------------- 鎖住 back 鍵 -----------------------//
    @Override
    public void onBackPressed() {
        return;
    }

    private void check_login_token(Fragment fragment, int color)
    {
        String tokenCheckURL= getResources().getString(R.string.localDataBase)+"homeCheck.php";
        JSONObject obj=new JSONObject();
        SharedPreferences login_check = getSharedPreferences("login", Context.MODE_PRIVATE);
        if (login_check.getString("member_id","null")!="null" && login_check.getString("member_token", "null")!=null) {
            try {
                obj.put("member_id",login_check.getString("member_id","null"));
                obj.put("token_check_user",login_check.getString("member_token", "null"));
                String strobj =obj.toString();
                Log.d("debug00 home1",strobj);
                String A = new QueryTask().execute(tokenCheckURL, strobj).get();
                Log.d("debug00 home1","01");
                Log.d("debug00 home1",A);
                if( A.equals( "OK"))
                {
                    setSateToolbarColor(HomeActivity.this, color);
                    setFragment(fragment);
                }else {
                    Log.d("debug00 home","00");
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));};
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));}
        }
    }

