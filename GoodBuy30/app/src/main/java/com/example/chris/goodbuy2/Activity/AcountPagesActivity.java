package com.example.chris.goodbuy2.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.example.chris.goodbuy2.R;
import com.example.chris.goodbuy2.Service.QueryTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AcountPagesActivity extends AppCompatActivity implements View.OnClickListener {
    //    String accessAcount,dialog_uri;
    String accessAcount;
    TextView change_name,chnge_pwd,change_contact,change_info_description,change_info_status
            ,info_name,info_contact,bigtTitleAcount,change_info_edittext2;
    Dialog check_info_dialog,check_login;
    Button send_btn,cancel_btn;
    Boolean doubleCheckPwd =false;
    EditText change_info_edittext;
    private String action_mode;
    SharedPreferences login_check;
    String dialog_uri,dialog_check_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acount_pages);
        dialog_uri=getResources().getString(R.string.localDataBase)+"my_account.php";
        dialog_check_uri=getResources().getString(R.string.localDataBase)+"check_loginForChangePWD.php";
        login_check = getSharedPreferences("login", Context.MODE_PRIVATE);
        initializeWidgets();


    }
    private void initializeWidgets()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        change_name=findViewById(R.id.change_name);
        chnge_pwd=findViewById(R.id.change_pwd);
        change_contact=findViewById(R.id.change_contact);
        info_name=findViewById(R.id.name_acount);
        info_contact=findViewById(R.id.contact_acount);
        bigtTitleAcount=findViewById(R.id.big_title_acount);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        logincheck();//確認從哪邊進入，不正確的地方會隱藏可修改的按鈕。
        chnge_pwd.setOnClickListener(this);
        change_name.setOnClickListener(this);
        change_contact.setOnClickListener(this);

        load_data();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void logincheck()
    {
        Boolean result;
        Intent intent = this.getIntent();
        accessAcount =intent.getStringExtra("member_id");
        if(login_check.getString("member_id", "null").equals(accessAcount))
        {
            //從個人帳戶頁進入
            change_name.setVisibility(View.VISIBLE);
            change_contact.setVisibility(View.VISIBLE);
            chnge_pwd.setVisibility(View.VISIBLE);
            if(login_check.getString("member_status", "null").equals("facebook"))
            {
                //驗證是否為FB登入是的話修改密碼不會顯示
                chnge_pwd.setVisibility(View.GONE);
            }

        }else
        {
            //隨意頁面進入
            change_name.setVisibility(View.GONE);
            change_contact.setVisibility(View.GONE);
            chnge_pwd.setVisibility(View.GONE);
        }
    }

    private void load_data()
    {

        try {
            JSONObject obj=new JSONObject();
            String strobj;
            String result;
            obj.put("member_id",accessAcount);
            obj.put("token_check_user",login_check.getString("member_token","null"));
            obj.put("action",0);
            strobj = obj.toString();
            Log.d("debug00_acount",strobj);
            result = new QueryTask().execute(dialog_uri,strobj).get();
            Log.d("debug00_acount",result);
            JSONArray jarr=new JSONArray(result);
            info_name.setText(jarr.getJSONObject(0).getString("name"));
            info_contact.setText(jarr.getJSONObject(0).getString("email"));
            bigtTitleAcount.setText(jarr.getJSONObject(0).getString("name"));
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("debug00_acount",e.toString());
        }

    }

    private void checkdialog(final String action_mode) {
        // 修改資料 Dialog

        try {
            check_info_dialog=new Dialog(this);
            check_info_dialog.setContentView(R.layout.change_info_layout);
            send_btn = (Button) check_info_dialog.findViewById(R.id.send_btn);
            cancel_btn = (Button) check_info_dialog.findViewById(R.id.cancel_btn);
            change_info_description = (TextView) check_info_dialog.findViewById(R.id.change_info_description);
            change_info_status = (TextView) check_info_dialog.findViewById(R.id.change_info_status);
            change_info_edittext = (EditText) check_info_dialog.findViewById(R.id.change_info_edittext);
            change_info_edittext2= (EditText) check_info_dialog.findViewById(R.id.change_info_edittext2);
            switch (action_mode){
                case "1":
                    change_info_status.setVisibility(View.GONE);
                    change_info_edittext2.setVisibility(View.VISIBLE);
                    change_info_description.setText("請輸入密碼:");
                    change_info_edittext.setHint("請輸入原來的密碼");
                    change_info_edittext2.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    change_info_edittext2.invalidate();
                    change_info_edittext.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    change_info_edittext.invalidate();
                    break;
                case "0":
                    change_info_status.setVisibility(View.GONE);
                    change_info_edittext.setHint("請輸入新的名稱:");
                    change_info_description.setText("請輸入新的名稱:");
                    change_info_edittext.invalidate();
                    break;
                case "2":
                    change_info_status.setVisibility(View.GONE);
                    change_info_edittext.setHint("請輸入新的聯絡信箱:");
                    change_info_description.setText("請輸入新的聯絡信箱:");
                    change_info_edittext.invalidate();

                    break;
            }
            check_info_dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("debug00_acount00",e.toString());
        }

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject obj=new JSONObject();
                String strobj=new String();
                String result=new String();
                Log.d("debug00_acount","01");
                try {
                    switch (action_mode){
                        case "1":
                            obj.put("email",login_check.getString("member_email", "null"));
                            obj.put("member_id",login_check.getString("member_id", "null"));
                            obj.put("token_check_user",login_check.getString("member_token","null"));
                            obj.put("pwd",change_info_edittext.getText());
                            obj.put("action", 1);
                            obj.put("new_pwd", change_info_edittext2.getText());
                            change_info_status.setVisibility(View.GONE);
                            change_info_edittext2.setVisibility(View.VISIBLE);
                            change_info_description.setText("請輸入原始密碼");
                            strobj=obj.toString();
                            Log.d("debug00_acount_chPwd00str",strobj);
                            result=new QueryTask().execute(dialog_uri,strobj).get();
                            Log.d("debug00_acount_chPwd01re",result);
                            if(result.equals("修改成功"))
                            {
                                Log.d("debug00_acount_chPwd01re",result);
                                check_info_dialog.cancel();
                            }
                            else{
                                Log.d("debug00_acount_chPwd01re",result+"測試是否有成功");
                                change_info_status.setVisibility(View.VISIBLE);
                                change_info_status.setText("密碼異常，請重新輸入");
                            }
                            Toast.makeText(AcountPagesActivity.this, result, Toast.LENGTH_SHORT).show();
                            break;
                        case "0":
                            change_info_status.setVisibility(View.GONE);
                            change_info_description.setText("請輸入新的名稱:");
                            obj.put("new_name",change_info_edittext.getText());
                            obj.put("member_id",login_check.getString("member_id", "null"));
                            obj.put("token_check_user",login_check.getString("member_token","null"));
                            Log.d("debug00_acount0",login_check.getString("member_token","null"));
                            obj.put("action",2);
                            strobj=obj.toString();
                            result=new QueryTask().execute(dialog_uri,strobj).get();
                            Log.d("debug00_acount0",result);
                            if(result.equals("修改成功"))
                            {
                                check_info_dialog.cancel();
                                info_name.setText(change_info_edittext.getText());
                                bigtTitleAcount.setText(change_info_edittext.getText());
                                bigtTitleAcount.invalidate();
                                info_name.invalidate();
                            }
                            Toast.makeText(AcountPagesActivity.this, result, Toast.LENGTH_SHORT).show();
                            break;
                        case "2":
                            change_info_status.setVisibility(View.GONE);
                            change_info_description.setText("請輸入新的聯絡信箱:");
                            obj.put("contact",change_info_edittext.getText());
                            obj.put("member_id",login_check.getString("member_id", "null"));
                            obj.put("token_check_user",login_check.getString("member_token","null"));
                            Log.d("debug00_acount0",login_check.getString("member_token","null"));
                            obj.put("action",3);
                            strobj=obj.toString();
                            result=new QueryTask().execute(dialog_uri,strobj).get();
                            Log.d("debug00_acount2",result);
                            if(result.equals("修改成功"))
                            {
                                check_info_dialog.cancel();
                                info_contact.setText(change_info_edittext.getText());
                                info_contact.invalidate();

                            }
                            Toast.makeText(AcountPagesActivity.this, result, Toast.LENGTH_SHORT).show();
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("debug00_acount_Exception",e.toString());
                }

            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_info_dialog.cancel();
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.change_name:
                action_mode = "0";
                Log.d("debug00_acount",action_mode+"001");
                checkdialog(action_mode);
                break;
            case R.id.change_pwd:
                action_mode = "1";
                Log.d("debug00_acount",action_mode+"002");
                checkdialog(action_mode);
                break;
            case R.id.change_contact:
                action_mode = "2";
                Log.d("debug00_acount",action_mode+"003");
                checkdialog(action_mode);
                break;
        }

    }
}
