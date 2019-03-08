package com.example.chris.goodbuy2.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.chris.goodbuy2.Adapter.ProductItemAdapter;
import com.example.chris.goodbuy2.Model.Product_item;
import com.example.chris.goodbuy2.R;
import com.example.chris.goodbuy2.Service.QueryTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyFavoriteActivity extends AppCompatActivity {

    // 20190126 鵬 加上 取得 呼叫 我的最愛PHP 的路徑 -----------------------------------------
    private String favoriteURL ;
    JSONArray jarr ;
    private String favoriteResult;
    private int[] id; // 存 favorite tb內的流水id
    private String[]  join_date; // 存加入最愛商品的日期
    private int[] product_id; // 商品id
    private String[] product_name ; // 商品名稱
    private String[] product_image; // 商品圖片路徑
    private int[] quantity; // 商品剩餘數量

    private List<Product_item> product_itemList;

    String member_id ; // 會員id
    //--------------------------------------------------------------------

    RecyclerView favorite_recyclerview;
    private LinearLayout no_love_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorite);

        Toolbar toolbar = (Toolbar) findViewById(R.id.favorite_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("我的喜愛");

        product_itemList = new ArrayList<>();

        // Inflate the layout for this fragment
        favoriteURL = getResources().getString(R.string.localDataBase) + "my_favorite.php";
        Log.d("debug0 TTT", "onCreateView: ");
        // ------2019 02 11 鵬  從  SharedPreferences 取得 member_id------------------------
        SharedPreferences login_check = getSharedPreferences("login", Context.MODE_PRIVATE);
        member_id = login_check.getString("member_id","null");
        Log.d("debug0 member id", member_id);
        actionFavorite(0, member_id, 0); //Integer.valueOf(member_id)
        //------------------------------------------------------------------------

        favorite_recyclerview = findViewById(R.id.favorite_recyclerview);
        no_love_layout = findViewById(R.id.no_love_layout);

        ProductItemAdapter adapter1 = new ProductItemAdapter(this, product_itemList, R.layout.product_card_list_item_layout);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        favorite_recyclerview.setLayoutManager(gridLayoutManager);
        favorite_recyclerview.setAdapter(adapter1);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //----------------------------------------------------------------------------------------------------------------------
/* 20190126 鵬 加上，要準備呼叫最愛的php，分別有三個參數，第一個參數為要做的動作，若要顯示最愛請帶入0，若要刪除請帶入 1，第二個參數為 會員id，第三個參數為產品id，若第一個
                    參數 用 0 ， 則第三個參數就帶0，若第一個參數為1代表要刪除該產品id，則第三個參數就帶入按下刪除的該產品id
 */
    public void actionFavorite(int action , String member_id , int productId)
    {
        SharedPreferences login_check= getSharedPreferences("login", Context.MODE_PRIVATE);
        String returnResult;
        JSONObject obj = new JSONObject();
        try{
            obj.put("action",action);
            obj.put("member_id",member_id);
            obj.put("product_id",productId);
            obj.put("token_check_user",login_check.getString("member_token","null"));
        }catch (Exception e)
        {
            Log.d("debug0" , e.toString());
        }

        String strobj = obj.toString();
        try{
            favoriteResult = new QueryTask().execute(favoriteURL , strobj).get();
        }catch(Exception e)
        {
            Log.d("debug0",e.toString());
        }
        Log.d("debug0 favoriteResult", favoriteResult);
        if(action == 0) {
            returnResult = getFavorite_info();
            if(returnResult.equals("沒有最愛商品") )
            {
                // 這裡是沒有任何最愛商品時，看要怎麼呈現囉
                favorite_recyclerview.setVisibility(View.GONE);
                no_love_layout.setVisibility(View.VISIBLE);
                return;
            }
            else if(returnResult.equals("最愛資訊設置完畢") )
            {
                //這裡已得到所有的陣列資訊，看你要那些資訊就自行取用塞入吧
            }
        }
        else if(action == 1)
        {
            returnResult = deleteResult();
            if(returnResult.equals("刪除失敗"))
            {
                //看有沒有要做甚麼，沒做就空著吧
            }
            else if(returnResult.equals("刪除成功"))
            {
                //刪除成功後要顯示什麼或是幹嘛
            }
        }

    }

    private String getFavorite_info()
    {
        if(favoriteResult.equals("沒有最愛商品"))
        {
            return "沒有最愛商品";
        }

        try{
            jarr = new JSONArray(favoriteResult);
            Log.d("debug0 show", jarr.length()+"");
        }catch(Exception e)
        {
            Log.d("debug0 Exception",e.toString());
        }

        id = new int[jarr.length()]; // 存 favorite tb內的流水id
        join_date = new String[jarr.length()]; // 存加入最愛商品的日期
        product_id = new int[jarr.length()]; // 商品id
        product_name = new String[jarr.length()] ; // 商品名稱
        product_image = new String[jarr.length()]; // 商品圖片路徑
        quantity = new int[jarr.length()]; // 商品剩餘數量

        for(int i = 0 ; i < jarr.length() ; i++)
        {
            setFavoriteArrayAll_info( jarr , i);
        }

        return "最愛資訊設置完畢";
    }

    private void setFavoriteArrayAll_info(JSONArray arr , int i)
    {
        String imageURL = getString(R.string.localDataBaseImg);
        try{
            id[i] = arr.getJSONObject(i).getInt("id");
            join_date[i] = arr.getJSONObject(i).getString("join_date");
            product_id[i] = arr.getJSONObject(i).getInt("product_id");
            product_name[i] = arr.getJSONObject(i).getString("product_name");
            product_image[i] = imageURL + arr.getJSONObject(i).getString("product_image");
            quantity[i] = arr.getJSONObject(i).getInt("quantity");

            Product_item product_item = new Product_item();

            product_item.setProduct_id(arr.getJSONObject(i).getInt("product_id"));
            product_item.setJoin_date(arr.getJSONObject(i).getString("join_date"));
            product_item.setProduct_name(arr.getJSONObject(i).getString("product_name"));
            product_item.setProduct_image(imageURL + arr.getJSONObject(i).getString("product_image"));
            product_item.setTotal_quantity(arr.getJSONObject(i).getInt("quantity"));
            product_item.setProduct_price(arr.getJSONObject(i).getInt("price"));

            product_itemList.add(product_item);
        }
        catch (Exception e)
        {
            Log.d("debug0 catch",e.toString());
        }
    }

    private String deleteResult()
    {
        if( favoriteResult.equals("刪除失敗"))
        {
            return "刪除失敗";
        }

        return "刪除成功";
    }

//--------------------------------------------------------------------------------------------------------------------------

}
