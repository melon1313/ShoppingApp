package com.example.chris.goodbuy2.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chris.goodbuy2.Adapter.ShoppingCartAdapter;
import com.example.chris.goodbuy2.Adapter.ShoppingCartProductsAdapter;
import com.example.chris.goodbuy2.Model.ShoppingCart_product_item;
import com.example.chris.goodbuy2.R;
import com.example.chris.goodbuy2.Service.QueryTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ShoppingCartActivity extends AppCompatActivity implements View.OnClickListener {
    private String shoppingCartURL;
    private String modifyShopppingCartURL;
    private SharedPreferences login_check;

    private String ID;
    private String M_token;

    private List<ShoppingCart_product_item> shoppingCartProductItems;
    private ArrayList<String> member_ids;

    private Dialog dialog;

    private CheckBox mIvCircle;
    private TextView mAllPriceTxt;
    private TextView nSumPrice;
    private ShoppingCartAdapter shoppingCartAdapter;
    private LinearLayout linearLayout;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        shoppingCartProductItems = new ArrayList<>();
        member_ids = new ArrayList<>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        shoppingCartURL = getResources().getString(R.string.localDataBase) + "shoppingCart.php";
        modifyShopppingCartURL = getResources().getString(R.string.localDataBase) + "update_shoppingCart.php";
        login_check = getSharedPreferences("login", Context.MODE_PRIVATE);
        ID = login_check.getString("member_id", "Null");
        loadDataforShoppingCart();

        RecyclerView recyclerView = findViewById(R.id.shopping_cart_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        shoppingCartAdapter = new ShoppingCartAdapter(this, shoppingCartProductItems, modifyShopppingCartURL);
        recyclerView.setLayoutManager(layoutManager);
        if (shoppingCartProductItems.size() != 0){
            recyclerView.setAdapter(shoppingCartAdapter);
        }


        mIvCircle = (CheckBox) findViewById(R.id.iv_cricle);
        mAllPriceTxt = (TextView) findViewById(R.id.all_price);
        nSumPrice = (TextView) findViewById(R.id.sum_price_txt);
        mIvCircle.setOnClickListener(this);

        shoppingCartAdapter.setListener(new ShoppingCartAdapter.ShopCallBackListener() {
            @Override
            public void callBack(List<ShoppingCart_product_item> list) {
                //在這裡重新遍歷已經改狀態後的資料，
                // 這裡不能break跳出，因為還需要計算後面點選商品的價格和數目，所以必須跑完整個迴圈
                double totalPrice = 0;

                //勾選商品的數量，不是該商品購買的數量
                int num = 0;
                //所有商品總數，和上面的數量做比對，如果兩者相等，則說明全選
                int totalNum = 0;
                for(int i = 0; i < shoppingCartProductItems.size(); i++){
                    //獲取商家裡的商品
                    List<String> products = shoppingCartProductItems.get(i).getIsCheckLists();
                    for(int j = 0; j < products.size(); j++){
                        totalNum += 1;
                        if(Integer.valueOf(products.get(j)) == 1){
                            int quantities = Integer.valueOf(shoppingCartProductItems.get(i).getShoppingCart_product_quantities().get(j));
                            int price = Integer.valueOf(shoppingCartProductItems.get(i).getShoppingCart_product_unit_price().get(j));
                            totalPrice += price * quantities;
                            num += 1;
                        }
                    }
                }

                if (num < totalNum){
                    //不是全部選中
                    mIvCircle.setChecked(false);
                }else {
                    //是全部選中
                    mIvCircle.setChecked(true);
                }

                mAllPriceTxt.setText("合計: " + totalPrice);
            }

            @Override
            public void refreshActivity() {
                finish();
                startActivity(getIntent());
            }
        });

        //去買單
        linearLayout = findViewById(R.id.sum_price);
        linearLayout.setOnClickListener(this);

    }

    public void loadDataforShoppingCart() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("member_id", ID);
            obj.put("token_check_user", login_check.getString("member_token", "null"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String strobj = obj.toString();


        try {
            String shoppong_array = new QueryTask().execute(shoppingCartURL, strobj).get();
            if (shoppong_array == "登入狀態異常") {

            }
            JSONArray jarr = new JSONArray(shoppong_array);
//            String[] product_ids = new String[jarr.length()];
//            String[] member_ids = new String[jarr.length()];
//            String[] product_names = new String[jarr.length()];
//            String[] product_imgs = new String[jarr.length()];
//            String[] unit_prices = new String[jarr.length()];
//            String[] quantities = new String[jarr.length()];
//            String[] seller_ids = new String[jarr.length()];
//            String[] sell_names = new String[jarr.length()];

            Boolean isNotDouble;
            for (int i = 0; i < jarr.length(); i++) {
                isNotDouble = true;
                ShoppingCart_product_item product_item = new ShoppingCart_product_item();
                String seller_id = jarr.getJSONObject(i).getString("seller_id");
                //Log.v("fffff",  "name: " + shoppingCartProductItems.get(i).getSeller_id()+ "size:" +shoppingCartProductItems.size() + "id: " + seller_id);
                for (int j = 0; j < shoppingCartProductItems.size(); j++) {
                    if (shoppingCartProductItems.get(j) != null) {
                        // 注意必須轉成 int 再做比較
                        if (Integer.valueOf(shoppingCartProductItems.get(j).getSeller_id()) == Integer.valueOf(seller_id)) {
                            Log.v("qqqqq", "嗚嗚嗚");
                            shoppingCartProductItems.get(j).getShoppintCart_product_ids().add(jarr.getJSONObject(i).getString("product_id"));
                            shoppingCartProductItems.get(j).getShoppintCart_product_names().add(jarr.getJSONObject(i).getString("product_name"));
                            shoppingCartProductItems.get(j).getShoppingCart_product_unit_price().add(jarr.getJSONObject(i).getString("unit_price"));
                            shoppingCartProductItems.get(j).getShoppingCart_product_quantities().add(jarr.getJSONObject(i).getString("quantity"));
                            shoppingCartProductItems.get(j).getShoppingCart_product_images().add(getResources().getString(R.string.localDataBaseImg)+jarr.getJSONObject(i).getString("product_image"));
                            //20190209Jacky新增/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                            shoppingCartProductItems.get(j).getShoppingCart_product_speciations().add(jarr.getJSONObject(i).getString("specification"));
                            shoppingCartProductItems.get(j).getIsCheckLists().add("0");
                            //20190209Jacky新增/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                            isNotDouble = false;
                            break;
                        }
                    }
                }
                if (isNotDouble) {
                    Log.v("qqqqq", "八八八");
                    product_item.setSeller_id(jarr.getJSONObject(i).getString("seller_id"));
                    product_item.setSeller_name(jarr.getJSONObject(i).getString("seller_name"));
                    product_item.setMember_id(jarr.getJSONObject(i).getString("member_id"));
                    product_item.getShoppintCart_product_ids().add(jarr.getJSONObject(i).getString("product_id"));
                    product_item.getShoppintCart_product_names().add(jarr.getJSONObject(i).getString("product_name"));
                    product_item.getShoppingCart_product_images().add(jarr.getJSONObject(i).getString("product_image"));
                    product_item.getShoppingCart_product_unit_price().add(jarr.getJSONObject(i).getString("unit_price"));
                    product_item.getShoppingCart_product_quantities().add(jarr.getJSONObject(i).getString("quantity"));
                    //20190209Jacky新增/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    product_item.getShoppingCart_product_speciations().add(jarr.getJSONObject(i).getString("specification"));
                    product_item.getIsCheckLists().add("0");
                    //20190209Jacky新增/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    shoppingCartProductItems.add(product_item);
                }

//                product_ids[i]=jarr.getJSONObject(i).getString("product_id");
//                member_ids[i]=jarr.getJSONObject(i).getString("member_id");
//                product_names[i]=jarr.getJSONObject(i).getString("product_name");
//                product_imgs[i]=jarr.getJSONObject(i).getString("product_image");
//                unit_prices[i]=jarr.getJSONObject(i).getString("unit_price");
//                quantities[i]=jarr.getJSONObject(i).getString("quantity");
//                seller_ids[i]=jarr.getJSONObject(i).getString("seller_id");
//                sell_names[i]=jarr.getJSONObject(i).getString("seller_name");
//                Log.d("debug00 shopppingCart","\n產品ID:"+product_ids[i]+"\n買家ID:"+member_ids[i]+"\n商品名"+product_names[i]+"\n商品圖片:"+product_imgs[i]+"\n單位價格"+unit_prices[i]+"\n數量:"+quantities[i]+"\n賣家ID:"+seller_ids[i]+"\n賣家姓名"+sell_names[i]);
            }
            //Toast.makeText(this, "dddddd" + shoppingCartProductItems.get(2).getSeller_name(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //刪除功能測試
    public void deleDataforShoppingCart(String... id) {
        //id[0]=>product_id
        //id[1]=>member_id
        JSONObject obj = new JSONObject();
        try {
            obj.put("product_id", id[0]);
            obj.put("member_id", id[1]);
            obj.put("action", "1");
            obj.put("token_check_user",login_check.getString("member_token","null"));
            String modifyIdInfo = obj.toString();
            String A = new QueryTask().execute(modifyShopppingCartURL, modifyIdInfo).get();
            Toast.makeText(this, "B000" + A, Toast.LENGTH_SHORT).show();
            Log.d("debug00_shoppingCart", "B000" + A);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("debug00_shoppingCart", e.toString());

        }
    }

    //修改數量
    public void modifyDataforShopppingCart(String... info) {
        //info[0]=>product_id
        //info[1]=>member_id
        //info[2]=>quantity 傳入更改後的數值
        //info[3]=>totalPrice 傳入修改的數值
        JSONObject obj = new JSONObject();
        try {
            obj.put("product_id", info[0]);
            obj.put("member_id", info[1]);
            obj.put("quantity", info[2]);
            obj.put("total_price", info[3]);
            obj.put("action", "2");
            String modifyIdInfo = obj.toString();
            String A = new QueryTask().execute(modifyShopppingCartURL, modifyIdInfo).get();
            Toast.makeText(this, "T000" + A, Toast.LENGTH_SHORT).show();
            Log.d("debug00_shoppingCart", "T000" + A);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("debug00_shoppingCart", e.toString());
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_cricle:
//                Toast.makeText(this, "拉~"+mIvCircle.isChecked(), Toast.LENGTH_SHORT).show();
                checkSeller(mIvCircle.isChecked());
                shoppingCartAdapter.notifyDataSetChanged();
                break;
            case R.id.sum_price:
                // 寫入去買單程式
                dialog = new Dialog(this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false); //無法利用回上一頁按鈕離開對話框
                dialog.setContentView(R.layout.buy_layout);
                Button checkBtn = (Button) dialog.findViewById(R.id.forgetpassword_check_btn);
                Button cacelBtn = (Button) dialog.findViewById(R.id.forgetpassword_cancel_btn);
                EditText forget_password_edittext = (EditText) dialog.findViewById(R.id.forgetpassword_edittext);
                EditText editText = (EditText)dialog.findViewById(R.id.forgetpassword_edittext);

                dialog.show();

                checkBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //用兩個迴圈去跑 i 跟 j
                        if(Integer.valueOf(shoppingCartProductItems.get(0).getIsCheckLists().get(0)) == 1){
                            Log.v("bbbbbb", "拉拉: "+ shoppingCartProductItems.get(0).getShoppintCart_product_names().get(0));
                        }
                        dialog.cancel();
                    }
                });

                cacelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                break;
        }
    }

    private void checkSeller(boolean bool){
        int totalPrice = 0;
        int num = 0;
        //Toast.makeText(this, "拉~"+bool, Toast.LENGTH_SHORT).show();
        for(int i = 0; i < shoppingCartProductItems.size(); i++){
            //遍歷商家，改變狀態
            shoppingCartProductItems.get(i).setIsCheck(bool);

            //遍歷商品，改變狀態
            List<String> products = shoppingCartProductItems.get(i).getIsCheckLists();
            for(int j = 0; j < products.size(); j++){
                if (bool){
                    products.set(j, "1");
                    if(Integer.valueOf(products.get(j)) == 1){
                        int quantities = Integer.valueOf(shoppingCartProductItems.get(i).getShoppingCart_product_quantities().get(j));
                        int price = Integer.valueOf(shoppingCartProductItems.get(i).getShoppingCart_product_unit_price().get(j));
                        totalPrice += price * quantities;
                        num++;
                    }
                }else {
                    products.set(j, "0");
                }
            }
        }
        if(bool){
            mAllPriceTxt.setText("合計：$" + totalPrice);
        }else {
            mAllPriceTxt.setText("合計：$" + 0);
        }
    }
}
