package com.example.chris.goodbuy2.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.chris.goodbuy2.Activity.AddItemToSell;
import com.example.chris.goodbuy2.R;
import com.example.chris.goodbuy2.Service.QueryTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




/**
 * A simple {@link Fragment} subclass.
 */
public class SellerFragment extends Fragment {

    private ListView listView;
    private String[] itemname= {"新增商品"};
    private Integer[] imageId ={R.drawable.list_icon_seller};
    //private ImageView addProduct;

    //引入變數(向資料索取資料用)

    JSONObject obj=new JSONObject();
    String strobj;
    //引入變數(向資料索取資料用)

    //宣告容器裝載從資料庫取出的東西
    String[] product_ids;
    String[] product_detail;
    String[] order_datetime;
    String[] pay_type;
    int[] buyer_id;
    String[] buyer_name;
    String[] buy_quantity;
    String[] product_name;
    String[] delivery_address;
    //宣告容器裝載從資料庫取出的東西



    public SellerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_seller, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listView = (ListView)view.findViewById(R.id.seller_listview_id);

        //addProduct=(ImageView)view.findViewById(R.id.AddProduct_btn);

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
//                        getShoppingList();
//                        Log.v("mmm", " position : "+ position);
                        SharedPreferences login_check= getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                        Intent intent=new Intent(getActivity(), AddItemToSell.class);
                        intent.putExtra("member_id",login_check.getString("member_id","null"));
                        startActivity(intent);
                        break;
//                    case 1:
//                        Log.v("mmm", " position : "+ position);
//                        break;
//                    default:
//                        Log.v("mmm", " position : nothing");
//                        break;
                }
            }
        });
//        addProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPreferences login_check= getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
//                Intent intent=new Intent(getActivity(), AddItemToSell.class);
//                intent.putExtra("member_id",login_check.getString("member_id","null"));
//                startActivity(intent);
//
//            }
//        });
    }
    private void getShoppingList()
    {

        String ListURL =getResources().getString( R.string.localDataBase)+"shoppingList.php";
        SharedPreferences login_check=getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        try {
            //將撈取資料的條件轉換成json
            ArrayList<String> aaa=new ArrayList<>();
            obj.put("member_id", login_check.getString("member_id", "null"));
            obj.put("action", "1");
            obj.put("token_check_user",login_check.getString("member_token","null"));
            obj.put("test",aaa);
            strobj = obj.toString();
            //向資料庫取得資料
            String List = new QueryTask().execute(ListURL, strobj).get();
            Log.d("debug00buyer_test",List);
            JSONArray jarr = new JSONArray(List);
            product_ids=new String[jarr.length()];
            product_detail=new String[jarr.length()];
            order_datetime=new String[jarr.length()];
            pay_type=new String[jarr.length()];
            buyer_id=new int[jarr.length()];
            buyer_name=new String[jarr.length()];
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
                buyer_id[i]=jarr.getJSONObject(i).getInt("buyer_id");
                buyer_name[i]=jarr.getJSONObject(i).getString("buyer_name");
                buy_quantity[i]=jarr.getJSONObject(i).getString("buy_quantity");
                product_name[i]=jarr.getJSONObject(i).getString("product_name");
                delivery_address[i]=jarr.getJSONObject(i).getString("delivery_address");
                Log.d("debug00buyer_test","\nproduct_id: "+product_ids[i]+
                        "\nproduct_detail: "+product_detail[i]+
                        "\norder_datetime: "+order_datetime[i]+
                        "\npay_type: "+pay_type[i]+
                        "\nbuyer_id: "+buyer_id[i]+
                        "\nbuyer_name: "+buyer_name[i]+
                        "\nbuy_quantity: "+buy_quantity[i]+
                        "\nproduct_name: "+product_name[i]+
                        "\ndelivery_address: "+delivery_address[i]
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
