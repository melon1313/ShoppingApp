package com.example.chris.goodbuy2.Fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chris.goodbuy2.Activity.AcountPagesActivity;
import com.example.chris.goodbuy2.Activity.LoginActivity;
import com.example.chris.goodbuy2.Activity.MyFavoriteActivity;
import com.example.chris.goodbuy2.R;
import com.example.chris.goodbuy2.Service.QueryTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class BuyerFragment extends Fragment {

    private ListView listView;
    //    private Integer[] itemname= {R.string.buy_list, R.string.buy_love, R.string.buy_eveluation,
//            R.string.buy_myaccount, R.string.buy_service};
    private String[] itemname= {"我的帳戶", "客服中心"};
    private Integer[] imageId ={R.drawable.list_icon_account, R.drawable.list_icon_seervice};
    //客服中心頁面
    private Dialog severCenter_dialog;
    private Button OK_severCenter_btn;
    private TextView title_Service_center_En,title_Service_center_Ch,content_service_center,title_Service_Center_email_title,mail_service_center;
    private TextInputLayout sc_content_service_center,emailtitle_service_center,emailContent_service_center;
    //引入變數(向資料索取資料用)

    JSONObject obj=new JSONObject();
    String strobj;
    //引入變數(向資料索取資料用)

    //宣告容器裝載從資料庫取出的東西
    String[] product_ids;
    String[] product_detail;
    String[] order_datetime;
    String[] pay_type;
    int[] seller_id;
    String[] seller_name;
    String[] buy_quantity;
    String[] product_name;
    String[] delivery_address;
    //宣告容器裝載從資料庫取出的東西

    private Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buyer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        intent = new Intent(getActivity(), MyFavoriteActivity.class);

        listView = (ListView)view.findViewById(R.id.buyer_listview_id);
        initializeWidgets();
        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        for(int i = 0; i <imageId.length; i++){
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("image", imageId[i]);
            item.put("text", itemname[i]);
            items.add(item);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), items, R.layout.buyer_listview, new String[]{"image", "text"}, new int[]{R.id.buyer_list_img, R.id.buyer_list_text1});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                       // getShoppingList();
                        //Log.v("mmm", " position : "+ position);
                        SharedPreferences login_check= getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                        Intent intent=new Intent(getActivity(), AcountPagesActivity.class);
                        intent.putExtra("member_id",login_check.getString("member_id","null"));
                        startActivity(intent);
                        Log.v("mmm", " position : "+ position);
                        break;
                    case 1:
                        //Log.v("mmm", " position : "+ position);
                        //startActivity(intent);
                        severCenterDialogWidgetsInitialize();
                        severCenter_dialog.show();
                        Log.v("mmm", " position : "+ position);
                        break;
//                    case 2:
//                        Log.v("mmm", " position : "+ position);
//                        break;
//                    case 3:
//                        SharedPreferences login_check= getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
//                        Intent intent=new Intent(getActivity(), AcountPagesActivity.class);
//                        intent.putExtra("member_id",login_check.getString("member_id","null"));
//                        startActivity(intent);
//                        Log.v("mmm", " position : "+ position);
//                        break;
//                    case 4:
//                        severCenterDialogWidgetsInitialize();
//                        severCenter_dialog.show();
//                        Log.v("mmm", " position : "+ position);
//                        break;
                    default:
                        Log.v("mmm", " position : nothing");
                        break;
                }
            }
        });
    }
    private void getShoppingList()
    {

        String ListURL =getResources().getString( R.string.localDataBase)+"shoppingList.php";
        SharedPreferences login_check=getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        try {
            //將撈取資料的條件轉換成json
            obj.put("member_id", login_check.getString("member_id", "null"));
            obj.put("action", "0");
            obj.put("token_check_user",login_check.getString("member_token","null"));
            strobj = obj.toString();
            //向資料庫取得資料
            String List = new QueryTask().execute(ListURL, strobj).get();
            Log.d("debug00buyer_test",List);
            JSONArray jarr = new JSONArray(List);
            product_ids=new String[jarr.length()];
            product_detail=new String[jarr.length()];
            order_datetime=new String[jarr.length()];
            pay_type=new String[jarr.length()];
            seller_id=new int[jarr.length()];
            seller_name=new String[jarr.length()];
            buy_quantity=new String[jarr.length()];
            product_name=new String[jarr.length()];
            delivery_address=new String[jarr.length()];
            Log.d("debug00buyer_test","00");
            for (int i = 0; i < jarr.length(); i++) {
                Log.d("debug00buyer_test","01");
                product_ids[i]=jarr.getJSONObject(i).getString("product_id");
                product_detail[i]=jarr.getJSONObject(i).getString("product_detail");
                order_datetime[i]=jarr.getJSONObject(i).getString("order_datetime");
                pay_type[i]=jarr.getJSONObject(i).getString("pay_type");
                seller_id[i]=jarr.getJSONObject(i).getInt("seller_id");
                seller_name[i]=jarr.getJSONObject(i).getString("seller_name");
                buy_quantity[i]=jarr.getJSONObject(i).getString("buy_quantity");
                product_name[i]=jarr.getJSONObject(i).getString("product_name");
                delivery_address[i]=jarr.getJSONObject(i).getString("delivery_address");
                Log.d("debug00buyer_test","\nproduct_id: "+product_ids[i]+
                        "\nproduct_detail: "+product_detail[i]+
                        "\norder_datetime: "+order_datetime[i]+
                        "\npay_type: "+pay_type[i]+
                        "\nseller_name: "+seller_name[i]+
                        "\nseller_id: "+seller_id[i]+
                        "\nbuy_quantity: "+buy_quantity[i]+
                        "\nproduct_name: "+product_name[i]+
                        "\ndelivery_address: "+delivery_address[i]
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void severCenterDialogWidgetsInitialize() {
        // 客服中心 Dialog
        try {
            severCenter_dialog.setContentView(R.layout.service_center_layout);
            OK_severCenter_btn = (Button) severCenter_dialog.findViewById(R.id.ok_service_center);
            content_service_center = (TextView) severCenter_dialog.findViewById(R.id.content_service_center);
            title_Service_Center_email_title =(TextView) severCenter_dialog.findViewById(R.id.title_Service_Center_email_title);
            mail_service_center =(TextView) severCenter_dialog.findViewById(R.id.mail_service_center);
            sc_content_service_center = (TextInputLayout) severCenter_dialog.findViewById(R.id.sc_content_service_center);
            OK_severCenter_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    severCenter_dialog.cancel();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("debug00",e.toString());
        }
    }
    private void initializeWidgets()
    {
        severCenter_dialog = new Dialog(getActivity());
        severCenter_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        severCenter_dialog.setCancelable(false); //無法利用回上一頁按鈕離開對話框
    }


}
