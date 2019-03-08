package com.example.chris.goodbuy2.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.chris.goodbuy2.Model.Product_item;
import com.example.chris.goodbuy2.R;
import com.example.chris.goodbuy2.Service.QueryTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayAdapter adapter;
    EditText Search;
    ImageButton search_btn;

    // 2090129 鵬 宣告產品個資訊變數--------------------------------------
    String searchProductURL;
    String searchProduct_all; // 存放從資料庫撈回來的資料
    List<Product_item> list_product;
    JSONArray jarr;
    //-------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //------ 設定 ActionBar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Search=findViewById(R.id.search);
        search_btn=findViewById(R.id.search_btn);
        search_btn.setOnClickListener(this);

        //------ 設定 Up 按鈕
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //------ 資料 配接至 ListView
        //ListView searchList = (ListView)findViewById(R.id.search_products);
        ////String[] products = {"歐萊禮", "馬力歐","深入淺出 Android", "無瑕的程式碼", "籃球"};
        //List<Product_item_detail> list = new ArrayList<>();
        //list.get(i).getComment_context().add()
        //adapter = new ArrayAdapter(SearchActivity.this, android.R.layout.simple_list_item_1, list);
        //searchList.setAdapter(adapter);

        // 2019 01 31 鵬 init-----------------------------------------------------
        searchProductURL = getString(R.string.localDataBase) + "search.php";
        initializeWidgets();
        //------------------------------------------------------------------------

        EditText editText = (EditText)findViewById(R.id.search);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(list_product != null) {
                    (SearchActivity.this).adapter.getFilter().filter(s); // 與搜尋紀錄資料比對過濾
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    // 2019 01 31 鵬 init var & 宣告method --------------------------------------------------------------------
    private void initializeWidgets() {

    }
    // 按下搜尋鈕後，呼叫此方法
    private void getSearchProduct(String searchStr)
    {
        list_product = new ArrayList<>();
        JSONObject obj = new JSONObject();
        try {
            obj.put("search_condition", searchStr);  // 重要 : 參數為使用者在搜尋列中輸入的整串字串
        } catch (Exception e) {
            Log.d("debug0 putError 84", e.toString());
        }

        String strobj = obj.toString();
        Log.d("debug0 show strobj", strobj);
        try {
            searchProduct_all = new QueryTask().execute(searchProductURL, strobj).get();
            Log.d("debug0 queryError 90", searchProduct_all);
        } catch (Exception e) {
            Log.d("debug0 queryError 92", e.toString());
        }

        try {
            jarr = new JSONArray(searchProduct_all);
            Log.d("debug0 queryError 96", jarr.toString());
            //Log.d("debug0 顯示 ", jarr.getJSONObject(0).getString("name"));
        } catch (Exception e) {
            Log.d("debug0 catch changeArray 98", e.toString());
        }

        if(jarr.length() == 0)
        {
            Toast.makeText(this, "搜尋不到資料", Toast.LENGTH_SHORT).show();
            //搜尋不到符合相關的產品
        }else {
            for(int i = 0 ; i < jarr.length() ; i++)
            {
                setSearchProductToList(jarr , i);
            }
        }


        //------ 資料 配接至 ListView
        ListView searchList = (ListView)findViewById(R.id.search_products);
        //String[] products = {"歐萊禮", "馬力歐","深入淺出 Android", "無瑕的程式碼", "籃球"};
//        List<Product_item_detail> list = new ArrayList<>();

        //list.get(i).getComment_context().add()
        //adapter = new ArrayAdapter(SearchActivity.this, android.R.layout.simple_list_item_1, list_product);
        //searchList.setAdapter(adapter);
    }

    private void setSearchProductToList(JSONArray arr , int i)
    {
        String imageURL = getResources().getString(R.string.localDataBaseImg);
        try {
            Product_item product_item = new Product_item();
            product_item.setProduct_id(arr.getJSONObject(i).getInt("product_id"));
            product_item.setProduct_name(arr.getJSONObject(i).getString("product_name"));
            product_item.setCategories_id(arr.getJSONObject(i).getInt("categories_id"));
            product_item.setLarge_categories(arr.getJSONObject(i).getString("large_categories"));
            product_item.setMiddle_categories(arr.getJSONObject(i).getString("middle_categories"));
            product_item.setSmall_categories(arr.getJSONObject(i).getString("small_categories"));
            product_item.setProduct_image(imageURL + arr.getJSONObject(i).getString("product_image"));
            product_item.setTotal_quantity(arr.getJSONObject(i).getInt("quantity"));
            product_item.setProduct_price(arr.getJSONObject(i).getInt("price"));
            product_item.setDescription(arr.getJSONObject(i).getString("description"));
            product_item.setSeller_id(arr.getJSONObject(i).getInt("seller_id"));
            product_item.setSpecification(arr.getJSONObject(i).getString("specification"));
//            product_item.setSeller_name(arr.getJSONObject(i).getString("name"));
            product_item.setGroup_id(arr.getJSONObject(i).getString("group_id"));
            list_product.add(product_item);
        }
        catch (Exception e)
        {
            Log.d("debug0 135" , e.toString());
        }
    }

//-------------------------------------------------------------------------------------------------------------

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.search_btn:
                if(!Search.getText().toString().trim().equals("") )
                {
                    Toast.makeText(this, "已搜尋"+Search.getText().toString(), Toast.LENGTH_SHORT).show();
                    getSearchProduct(Search.getText().toString());
                }else{
                    Toast.makeText(this, "請輸入想搜尋的內容", Toast.LENGTH_SHORT).show();
                }
                break;

        }

    }
}
