package com.example.chris.goodbuy2.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chris.goodbuy2.Adapter.ProductItemAdapter;
import com.example.chris.goodbuy2.Model.Product_item;
import com.example.chris.goodbuy2.R;
import com.example.chris.goodbuy2.Service.QueryTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment {

    // 20190119 鵬 新增  變數宣告-------------------------------------------------------------------------
    private String selectProductURL;
    private String selectProduct_all; // 用來儲存從資料庫回來的資料
    JSONArray jarr;
    List<Product_item> list;
    // ----------------------------------------------------------------------------------------------------
    View view;
    private static final String SMALL_CATEGORY_NUM = "12";

    public static ProductsFragment newInstance(String smallcategoryNum){
        ProductsFragment productsFragment = new ProductsFragment();
        Bundle args = new Bundle();
        args.putString(SMALL_CATEGORY_NUM, smallcategoryNum);
        productsFragment.setArguments(args);
        return productsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_products, container, false);

        selectProductURL = getResources().getString(R.string.localDataBase) + "select_product.php";
        String select_itemURL = getResources().getString(R.string.localDataBase) + "select_product.php";

        initializeWidgets();

        // 20190119 鵬 加上  用來撈資料庫符合該小類id的產品--------------------------
        if(getArguments() != null){
            int smallCategories_id = Integer.valueOf(getArguments().getString(SMALL_CATEGORY_NUM)); // 等號後面需放入 點選小類的id
            getSmallCategoriesAllProduct(smallCategories_id);
        }
        //---------------------------------------------------------------------------


        // 資料適配
        setItemData();

        // Inflate the layout for this fragment
        return view;
    }

    private void initializeWidgets() {
        list = new ArrayList<>();
    }

    private void setItemData() {
        // 資料適配
        TextView textView1 = view.findViewById(R.id.product_count);
        textView1.setText(String.valueOf(list.size()) + "商品結果");

        ProductItemAdapter productItemAdapter = new ProductItemAdapter(
                getActivity(), list, R.layout.product_card_list_item_layout);
        RecyclerView recyclerView = view.findViewById(R.id.product_recyclerview);
        recyclerView.setAdapter(productItemAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    // 20190119 鵬 用來連接資料庫撈資料------------------------------------------------
    private void getSmallCategoriesAllProduct(int smallCategories_id) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("categories_id", smallCategories_id);  // 重要 : 第二個參數為 點選某小類的該小類id
        } catch (Exception e) {
            Log.d("debug0 putError", e.toString());
        }

        String strobj = obj.toString();
        try {
            selectProduct_all = new QueryTask().execute(selectProductURL, strobj).get();
            //Log.d("debug222 product",selectProduct_all);
        } catch (Exception e) {
            Log.d("debug0 queryError", e.toString());
        }

        if (selectProduct_all.equals("目前無該類產品")) // 如果撈回來的訊息是 目前無該類產品 代表目前無產品，故不往下執行
            return;

        try {
            jarr = new JSONArray(selectProduct_all);
        } catch (Exception e) {
            Log.d("debug0 catch changeArray", e.toString());
        }


        for (int i = 0; i < jarr.length(); i++) {
            setProductAll_data(jarr, i);
        }
    }

    // 20190119 鵬 把所有撈出來的小類產品 寫入到 array 中 ------------------------------------
    private void setProductAll_data(JSONArray arr, int i) {
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
            product_item.setSeller_name(arr.getJSONObject(i).getString("name"));
            product_item.setGroup_id( arr.getJSONObject(i).getString("group_id") );
            list.add(product_item);

            Log.d("debug0 create try", "try try 88888");
        } catch (Exception e) {
            Log.d("debug0 create catch", "QQQQQQ");
        }
    }
}
