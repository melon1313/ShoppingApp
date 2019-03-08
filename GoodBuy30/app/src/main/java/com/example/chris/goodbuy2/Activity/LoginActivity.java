package com.example.chris.goodbuy2.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chris.goodbuy2.Service.QueryTask;
import com.example.chris.goodbuy2.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;


public class LoginActivity extends AppCompatActivity {

    //-----20190116 鵬 加上
    static String sign_data;
    private String register_data;
    //-----20190118  鵬 加上
    private String forgetPwd_data;

    //Bundle bundleMemberInfo = new Bundle(); //存放登入會員的資訊 key -> member_id 為 使用者id ， key -> member_email 為使用者mail ， key -> member_name 為使用者名稱
    //----------------------

    Button signUpButton, singnOkbtn, signupCancelbtn;
    TextInputLayout accountInputLayout, passwordInputLayout, singup_account_layout, singup_password_layout, singup_nickname_layout, forget_password_layout;
    EditText accountEdittext, passwordEdittext, singup_nickname_editext, singup_account_edittext, singup_password_edittext, forget_password_edittext;
    Intent intent;
    ImageView fb_login_button, logo, cross_btn;
    TextView forgetPasswordButton, nosignUpyetButton;
    Dialog dialog;

    String accountstring, passwordstring;

    // 鵬---------------------------------------------------
    //宣告變數
    String signInURL ; // sign in php
    String registerURL ; // register php
    String registerMailURL ; // 註冊成功會寄通知信;
    String forgetPwdMailURL; // 忘記密碼通知信; 20190118 鵬加上
    // ------------------------------------------------------

    //FB登入
    CallbackManager callbackManager;
    LoginButton loginButton;
    private static final String EMAIL = "email";
    private static final String USER_POSTS = "user_posts";
    private static final String AUTH_TYPE = "rerequest";
    //FB登入
    boolean accountisError;
    boolean passwordisError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // 以下為CM以外網路測試path
//    signInURL ="http://10.0.2.2/api/member_signIn.php"; // sign in php
//    registerURL = "http://10.0.2.2/api/member_register.php"; // register php
//    registerMailURL = "http://10.0.2.2/api/registerSuccessMail.php"; // 註冊成功會寄通知信;
//    forgetPwdMailURL = "http://10.0.2.2/api/forget_pwd.php"; // 忘記密碼通知信; 20190118 鵬加上
        //直接到string.xml修改
        signInURL =getResources().getString(R.string.localDataBase)+"member_signIn.php"; // sign in php
        registerURL = getResources().getString(R.string.localDataBase)+"member_register.php"; // register php
        registerMailURL =getResources().getString(R.string.localDataBase)+"registerSuccessMail.php"; // 註冊成功會寄通知信;
        forgetPwdMailURL = getResources().getString(R.string.localDataBase)+"forget_pwd.php"; // 忘記密碼通知信; 20190118 鵬加上

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); // hide statusbar
        intent = new Intent(LoginActivity.this, HomeActivity.class);

        initializeWidgets();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        registerfacebook();
        setWidgetsOnclickListener();

        //------ 在 edittext 判斷 是否有輸入正確格式與資料
        accountEdittext.addTextChangedListener(accountWatcher);
        passwordEdittext.addTextChangedListener(passwordWatcher);


    }
    //------------ 元件事件監聽 -----------//
    private void setWidgetsOnclickListener(){
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        cross_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        fb_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 寫入fb 登入方式
                loginButton.performClick();
            }
        });

        forgetPasswordButton.setOnClickListener(forgetpasswordMethod);

        nosignUpyetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singnupDialogWidgetsInitialize();
                //------ button 事件監聽
                singnOkbtn.setOnClickListener(signUpDialogOkClicked); // 註冊按鈕監聽
                signupCancelbtn.setOnClickListener(signUpDialogCancelClicked); // 取消按鈕監聽

                //------ 在 edittext 判斷 是否有輸入正確格式與資料
                singup_nickname_editext.addTextChangedListener(singnupNicknameWatcher);
                singup_account_edittext.addTextChangedListener(singupAccountWatcher);
                singup_password_edittext.addTextChangedListener(singnupPasswordWatcher);

                dialog.show();
            }
        });
    }

    //------------ 忘記密碼的方法 Dialog -----------//
    private View.OnClickListener forgetpasswordMethod = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 寫入忘記密碼程式
            dialog.setContentView(R.layout.forget_password_layout);
            Button checkBtn = (Button) dialog.findViewById(R.id.forgetpassword_check_btn);
            Button cacelBtn = (Button) dialog.findViewById(R.id.forgetpassword_cancel_btn);
            forget_password_edittext = (EditText) dialog.findViewById(R.id.forgetpassword_edittext);
            forget_password_layout = (TextInputLayout)dialog.findViewById(R.id.forget_password_layout);
            dialog.show();
            checkBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //---------------------- 20190118 鵬 增加 忘記密碼要連接PHP -----------------------------------
                    JSONObject obj = new JSONObject();
                    try{
                        obj.put("email", forget_password_edittext.getText().toString());
                    }catch (Exception e)
                    {
//                        Log.d("debug222 putError",e.toString());
                    }
                    String strobj = obj.toString();
                    try {
                        forgetPwd_data = new QueryTask().execute(forgetPwdMailURL , strobj).get();
//                        Log.d("debug222 fpd",forgetPwd_data);
                    }catch(Exception e)
                    {
//                        Log.d("debug222 Query error",e.toString());
                    }

                    char mes = forgetPwd_data.charAt(forgetPwd_data.length()-1); // 20190118 鵬增加 取得回傳的最後一個字元
                    //--------------------------------------------------------------------------------------------------------//



                    if(forget_password_edittext.getText().toString().isEmpty() || forgetPwd_data.equals("無此帳號") ){ //後面多加與資料庫的帳號判斷
                        forget_password_layout.setError("查無此帳號");
                    }else if(mes=='出') {
                        dialog.setContentView(R.layout.sent_mail_layout);
                        Button checkBtn = (Button) dialog.findViewById(R.id.sent_mail_check_btn);
                        TextView textView = (TextView) dialog.findViewById(R.id.text2);
                        textView.setText("~請到信箱收取新密碼~");
                        dialog.show();
                        checkBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                            }
                        });
                    }
                }
            });
            cacelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
        }
    };

    //------------ signUp Dialog -----------//
    //------ 註冊 Button 與 sent_mail_btn
    private View.OnClickListener signUpDialogOkClicked = new View.OnClickListener() {
        boolean isvalid = true;

        @Override
        public void onClick(View v) {
            //------ Nick Name
            if (singup_nickname_editext.getText().toString().isEmpty()) {
                singup_nickname_layout.setError("暱稱不能空白喔~");
            } else {
                singup_nickname_layout.setErrorEnabled(false);
                isvalid = true;
            }

            //------ Account
            if (checkAccountInputFormatError(singup_account_edittext.getText().toString())) {
                singup_account_layout.setError("帳號格式錯誤 Ex: bear@mail.com");
                isvalid = false;
            } else {
                singup_account_layout.setErrorEnabled(false);
                //isvalid = true;
            }

            //------ Password
            if (checkPasswordInputFormatError(singup_password_edittext.getText().toString())) {
                singup_password_layout.setError("需輸入大於六位數子與字母");
                isvalid = false;

            } else {
                singup_password_layout.setErrorEnabled(false);
                //isvalid = true;  //20190116 鵬 註解，這裡不可以加，否則當上面帳號判斷 格式錯誤時，isvalid 雖然被設為false，但只要密碼符合 就又會被改成true，就會run到最下面的 if(isvaild) 區塊
            }

            // 20190116 鵬 ----------------------------------------------------------
            if (!isvalid) {
                return;
            }
            String nickName = singup_nickname_editext.getText().toString();
            String account = singup_account_edittext.getText().toString();
            String pwd = singup_password_edittext.getText().toString();

            int successCode = registerSuccessFail(nickName, account, pwd);
            Log.d("debug222 successCode", "" + successCode);
            if (successCode == 1) {
                Toast.makeText(LoginActivity.this, "帳號已存在", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(LoginActivity.this, "註冊成功", Toast.LENGTH_SHORT).show();

            //-------------------------------------------------------------------------

            if (isvalid) {
                registerSuccessMail(nickName, account, pwd); // 20190116鵬 加上
                dialog.setContentView(R.layout.sent_mail_layout);
                Button checkBtn = (Button) dialog.findViewById(R.id.sent_mail_check_btn);
                dialog.show();
                checkBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
            }
        }
    };
    //------ 取消 Button
    private View.OnClickListener signUpDialogCancelClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Toast.makeText(LoginActivity.this, "取消註冊", Toast.LENGTH_SHORT);
            dialog.cancel();
        }
    };

    //------------ Editext 設定 -----------//
    //------ editext 暱稱及時判斷
    private TextWatcher singnupNicknameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!(s.toString().isEmpty())) {
                singup_nickname_layout.setErrorEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
//            accountisError = CheckAccocuntError();
//            if(accountisError){
//                accountInputLayout.setError("帳號輸入錯誤");
//            }
        }
    };

    //------ editext 帳號及時判斷
    private TextWatcher accountWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!checkAccountInputFormatError(s.toString())) {
                accountInputLayout.setErrorEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
//            accountisError = CheckAccocuntError();
//            if(accountisError){
//                accountInputLayout.setError("帳號輸入錯誤");
//            }
        }
    };
    private TextWatcher singupAccountWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!checkAccountInputFormatError(s.toString())) {
                singup_account_layout.setErrorEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
//            accountisError = CheckAccocuntError();
//            if(accountisError){
//                accountInputLayout.setError("帳號輸入錯誤");
//            }
        }
    };

    //------ editext 密碼及時判斷
    private TextWatcher passwordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!checkPasswordInputFormatError(s.toString())) {
                passwordInputLayout.setErrorEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
//            passwordisError = CheckPasswordError();
//            if(passwordisError){
//                passwordInputLayout.setError("帳號輸入錯誤");
//            }
        }
    };
    private TextWatcher singnupPasswordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!checkPasswordInputFormatError(s.toString())) {
                singup_password_layout.setErrorEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
//            passwordisError = CheckPasswordError();
//            if(passwordisError){
//                passwordInputLayout.setError("帳號輸入錯誤");
//            }
        }
    };

    //------------ 元件初始化 -----------//
    private void initializeWidgets() {
        logo = (ImageView)findViewById(R.id.logo);
        cross_btn = (ImageView)findViewById(R.id.cross_btn);
        signUpButton = (Button) findViewById(R.id.signUpButton);

        accountInputLayout = (TextInputLayout) findViewById(R.id.input_layout_account);
        passwordInputLayout = (TextInputLayout) findViewById(R.id.input_layout_password);

        accountEdittext = (EditText) findViewById(R.id.account_field);
        passwordEdittext = (EditText) findViewById(R.id.password_field);

        fb_login_button = (ImageView) findViewById(R.id.fb_login_button);

        forgetPasswordButton = (TextView) findViewById(R.id.forgetpassword_button);
        nosignUpyetButton = (TextView) findViewById(R.id.noSignUpYet_button);

        // dialog
        dialog = new Dialog(LoginActivity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false); //無法利用回上一頁按鈕離開對話框
    }

    private void singnupDialogWidgetsInitialize() {
        // 註冊 Dialog
        dialog.setContentView(R.layout.singnup_layout);
        singnOkbtn = (Button) dialog.findViewById(R.id.singnUp_btn);
        signupCancelbtn = (Button) dialog.findViewById(R.id.cancel_btn);
        singup_account_layout = (TextInputLayout) dialog.findViewById(R.id.singup_account_layout);
        singup_password_layout = (TextInputLayout) dialog.findViewById(R.id.singup_password_layout);
        singup_nickname_layout = (TextInputLayout) dialog.findViewById(R.id.singup_nickname_layout);
        singup_nickname_editext = (EditText) dialog.findViewById(R.id.singup_nickname_editext);
        singup_account_edittext = (EditText) dialog.findViewById(R.id.singup_account_edittext);
        singup_password_edittext = (EditText) dialog.findViewById(R.id.singup_password_edittext);
    }

    //------------ 登入方法判斷 -----------//
    private void signIn() {
        boolean isvalid = true;

        accountstring = accountEdittext.getText().toString();
        passwordstring = passwordEdittext.getText().toString();

        // Account
        if (checkAccountInputFormatError(accountstring)) {
            accountInputLayout.setError("帳號格式錯誤 Ex: bear@mail.com");
            isvalid = false;

        } else {
            accountInputLayout.setErrorEnabled(false);
        }

        // Password
        if (checkPasswordInputFormatError(passwordstring)) {
            passwordInputLayout.setError("需輸入大於六位數字與字母");
            isvalid = false;

        } else {
            passwordInputLayout.setErrorEnabled(false);
        }

        // 20190116 鵬  呼叫後端 php 來判斷帳號密碼是否正確 -------------------
        if (!isvalid)
            return;
        int errorInfo = signIn_checkDataCorrect(accountstring, passwordstring);
        switch (errorInfo) {
            case 1:
                Toast.makeText(LoginActivity.this, "帳號或密碼錯誤", Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(LoginActivity.this, "查無帳號", Toast.LENGTH_LONG).show();
                break;
        }
        if (errorInfo > 0)
            return;

        // check
        if (isvalid) {
            Toast.makeText(LoginActivity.this, "登入成功", Toast.LENGTH_SHORT).show();
            //intent.putExtras(bundleMemberInfo); // 20190116 鵬 加上//修改成SharePreference
            startActivity(intent);  //開始登入
        }
    }

    //------------ 將帳密判斷邏輯寫在這裡 ------------//
    // 20190115 鵬------------------------------
    private int signIn_checkDataCorrect(String account, String pwd) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("email", account); // 輸入的email
            obj.put("pwd", pwd);  // 輸入的密碼

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String strjson = obj.toString();

        try {
            this.sign_data = new QueryTask().execute(signInURL, strjson).get();
            if (this.sign_data.equals("帳號或密碼錯誤")) {
                return 1;
            } else if (this.sign_data.equals("無此帳號")) {
                return 2;
            }
            setSignInInfo(this.sign_data);
        } catch (Exception e) {
            Log.d("debug222 Exception", e.toString());
        }
        //Log.d("debug22222- return" , sign_data);

        return 0;
    }

    // 20190116 鵬------------------------------
    private void setSignInInfo(String info) {
        //存放登入會員的資訊 key -> member_id 為 使用者id ， key -> member_email 為使用者mail ， key -> member_name 為使用者名稱
        SharedPreferences login_check = getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor login_save = login_check.edit();
        try {
            Log.d("debug222 try", "try block");

            JSONArray jarr = new JSONArray(info);

            Log.d("debug00 debug222  showArray", jarr.toString());

            for (int i = 0; i < jarr.length(); i++) {
                login_save.putString("member_id", jarr.getJSONObject(i).getString("member_id"));
                login_save.putString("member_email", jarr.getJSONObject(i).getString("email"));
                login_save.putString("member_name", jarr.getJSONObject(i).getString("name"));
                login_save.putString("member_token",jarr.getJSONObject(i).getString("token"));
                login_save.apply();
                //測試是否有存到token
                //Log.d( "debug00123",login_check.getString("member_token","null"));
//                bundleMemberInfo.putInt("member_id", jarr.getJSONObject(i).getInt("member_id") );
//                bundleMemberInfo.putString("member_email", jarr.getJSONObject(i).getString("email") );
//                bundleMemberInfo.putString("member_name",jarr.getJSONObject(i).getString("name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("debug222 e mes", e.toString());
            Log.d("debug222", "Wrong55555");
        }
    }

    // 20190116 鵬------------------------------
    private int registerSuccessFail(String nickname, String account, String pwd) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("nickname", nickname);     // 輸入的使用者姓名
            obj.put("email", account); // 輸入的email
            obj.put("pwd", pwd);  // 輸入的密碼
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String strjson = obj.toString();
        try {
            register_data = new QueryTask().execute(registerURL, strjson).get();
        } catch (Exception e) {
            Log.d("debug222 registerTask", e.toString());
        }

        if (register_data.equals("帳號已存在")) {
            return 1;
        }

        return 0;
    }

    private void registerSuccessMail(String nickname, String account, String pwd) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("nickname", nickname);
            obj.put("account", account);
            obj.put("pwd", pwd);
        } catch (Exception e) {
            Log.d("debug222 mail exception", e.toString());
        }
        String strobj = obj.toString();
        new QueryTask().execute(registerMailURL, strobj);
    }

    //------ 判斷帳號格式
    private boolean checkAccountInputFormatError(String string) {
        if (string.isEmpty() || !string.matches("^[_a-z0-9-]+([.][_a-z0-9-]+)*@[a-z0-9-]+([.][a-z0-9-]+)*$")) {
            Log.d("debug222 successCode", "格式不符");
            return true;
        } else {
            Log.d("debug222 successCode", "格式符合");
            return false;
        }
    }

    //------ 判斷密碼格式
    private boolean checkPasswordInputFormatError(String string) {
        if (string.isEmpty() || !PwdisCorrect(string) || string.length() <= 5) {
            return true;
        } else {
            return false;
        }
    }

    //專門判斷密碼格式函式
    private boolean PwdisCorrect(String pwd) {
        char a; // 宣告自元變數，用來存密碼中的每個字元，以利判斷該字元是否在格式內
        String res = ""; // 用來串每個字元的結果，定義如下； n 代表數字(num)，w代表英文字(word)
        boolean checkRes = false; // checkRes 用來回傳結果用，若是 false代表有問題
        for (int i = 0; i < pwd.length(); i++) {
            a = pwd.charAt(i);
            if ((a >= 48) && (a <= 57))
                res += "n";
            else if (((a >= 65) && (a <= 90)) || ((a >= 97) && (a <= 122)))
                res += "w";
            else // 若是進到這裡，代表有輸入的字元是在英數以外的，則直接中斷後續程式碼
                return false;
        }

        for (int j = 0; j < res.length(); j++) {
            if (j < res.length() - 1) {
                if (res.charAt(j) == res.charAt(j + 1))
                    checkRes = false;
                else    // 只要當下資源和後一個字元不一樣，代表有英數混合，直接把 checkRes 設成 true 並結束程式
                {
                    checkRes = true;
                    break;
                }
            }
        }
        return checkRes;
    }

    private void registerfacebook() {
        try {
            callbackManager = CallbackManager.Factory.create();
            loginButton = (LoginButton) findViewById(R.id.login_button);
            loginButton.setReadPermissions(Arrays.asList(EMAIL));
            loginButton.setAuthType(AUTH_TYPE);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("debug0123", e.toString());
        }
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                setResult(RESULT_OK);
                //傳值<以下為傳值內容>
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
//                                    以下列判斷式判斷怎樣回傳狀況。
                            if (response.getConnection().getResponseCode() == 200) {
                                long id = object.getLong("id");
                                String name = object.getString("name");
                                String email = object.getString("email");
                                Profile profile = Profile.getCurrentProfile();
                                Uri userPhoto = profile.getProfilePictureUri(300, 300);
                                //導入圖片的方式
//                                        Glide.with(MainActivity.this)
//                                                .load(userPhoto.toString())
//                                                .crossFade()
//                                                .into()//裡面放imageview
                                //用String.format(Locale.TAIWAN, "\nID:%s\nName:%s\nE-mail:%s\nphoto:%s",id, name, email,userPhoto.toString())將jsonobject的內容導出來。
                                //其中uesrPhoto可以導出大頭貼的網址，可藉由此串聯大頭貼。
                                Log.d("debug05", String.format(Locale.TAIWAN, "\nID:%s\nName:%s\nE-mail:%s\nphoto:%s", id, name, email, userPhoto.toString()));
                                JSONObject obj = new JSONObject();
                                try {
                                    //存放登入會員的資訊 key -> member_id 為 使用者id ， key -> member_email 為使用者mail ， key -> member_name 為使用者名稱
                                    SharedPreferences login_check=getSharedPreferences("login",Context.MODE_PRIVATE);
                                    SharedPreferences.Editor login_save =login_check.edit();
                                    obj.put("nickname", String.format(Locale.TAIWAN, "%s", name));
                                    obj.put("email", String.format(Locale.TAIWAN, "%s", id));
                                    obj.put("pwd", String.format(Locale.TAIWAN, "%s", email));
                                    //obj.put("token_check_user",login_check.getString("member_token","null"));
                                    Log.d("debug00email exception", login_check.getString("member_token","null"));

                                    login_save.putString("member_email", String.format(Locale.TAIWAN, "%s", email));
                                    login_save.putString("member_name",String.format(Locale.TAIWAN, "%s", name));
                                    login_save.putString("member_status","facebook");
                                    String strobj = obj.toString();
                                    String a = new QueryTask().execute(registerURL, strobj).get();
                                    String b = new QueryTask().execute(signInURL, strobj).get();
                                    JSONArray jarr=new JSONArray(b);
                                    login_save.putString("member_token",jarr.getJSONObject(0).getString("token"));
                                    login_save.putString("member_id",jarr.getJSONObject(0).getString("member_id"));
                                    login_save.apply();
                                    Log.d("debug00 a", a);
                                    Log.d("debug00 a", strobj);
                                    Log.d("debug00 b", b);
                                    Log.d("debug00 t",login_check.getString("member_token","null"));
                                } catch (Exception e) {
                                    Log.d("debug00email exception", e.toString());
                                }


                                startActivity(intent);
                            }
                        } catch (Exception e) {
                            Log.d("debud04fb_output", e.toString());
                        }

                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
                Log.d("debug05", "testmessage44");
//                        finish();
                Log.d("debug05", "testmessage55");
            }

            @Override
            public void onCancel() {
                // App code
                Log.d("debug05", "testmessage66");
                try {
                    setResult(RESULT_CANCELED);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("debud04", e.toString());
                }

            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.d("debug056", exception.toString());
            }

        });
    }

    ;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("debug05", "testmessage77");
        try {
            super.onActivityResult(requestCode, resultCode, data);
            callbackManager.onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("debud01", e.toString());
        }
    }
}
