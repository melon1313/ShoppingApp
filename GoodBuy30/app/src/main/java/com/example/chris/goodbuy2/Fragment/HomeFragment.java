package com.example.chris.goodbuy2.Fragment;

//import android.app.ActionBar;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chris.goodbuy2.Activity.ClassificationActivity;
import com.example.chris.goodbuy2.Adapter.HomeItemProductAdapter;
import com.example.chris.goodbuy2.Activity.LoginActivity;
import com.example.chris.goodbuy2.Adapter.ProductItemAdapter;
import com.example.chris.goodbuy2.Model.Product_item;
import com.example.chris.goodbuy2.R;
import com.example.chris.goodbuy2.Activity.SearchActivity;
import com.example.chris.goodbuy2.Activity.ShoppingCartActivity;
import com.example.chris.goodbuy2.Activity.TotalMessageActivity;
import com.example.chris.goodbuy2.Adapter.ViewPagerAdapter;
import com.example.chris.goodbuy2.Service.QueryTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 */

public class HomeFragment extends Fragment {

    // 20190119 鵬 增加  宣告用來存首頁推薦商品的資訊 ---------------------------------------

    String recommendProductURL;
    String recommendProduct_all; // 存放從資料庫撈回來的資料
    int[] product_id; // 產品在tb內的流水id號
    String[] product_name; // 產品名稱
    int[] categories_id; // 產品的分類 id
    String large_categories[]; // 產品的大類
    String middle_categories[]; // 產品的中類
    String small_categories[];  // 產品的小類
    String product_image[]; // 產品圖片路徑
    int total_quantity[];  // 產品總數量
    int product_price[];  // 產品價錢
    String[] description; // 產品描述
    int[] seller_id; // 賣家id
    String[] specification; // 產品規格
    JSONArray jarr;
    //AD參數宣告
    //將getAD_data()放入Create中就會直接倒出資料<取三筆>。
    String ad_getURL;
    String All_ads;
    String[] ad_ids; //廣告ID
    String[] ad_pics;//廣告圖片
    String[] ad_members;//廣告所有人
    //----------------------------------------------------------------------------------------


    //------------ 逛逛熊好買(分類) 設定 ------------//
    Intent intentToClassification;
    ImageView imageView_womenclothes, imageView_makeup, imageView_3c, imageView_appliance,
            imageView_menclothes, imageView_sports, imageView_books, imageView_all;

    //------------ ActionBar 設定 ------------//
    Intent intent_ShoppingCart, intent_Message, intent_Search;

    //----------------------- Advertisement 設定 -----------------------//
    ViewPager viewPager;
    PagerAdapter adapter;
    //int[] img;
    ArrayList<String> images;
    private static int currentIndex;
    private Handler handler;
    private ScheduledExecutorService scheduledExecutorService;


    //客服中心頁面
    private Dialog severCenter_dialog;
    private Button OK_severCenter_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recommendProductURL = getResources().getString(R.string.localDataBase) + "homePageRecommendProduct.php";
        ad_getURL = getResources().getString(R.string.localDataBase) + "ad_choose.php";

        initializeWidgets();

        //------------ 為你推薦商品 ------------//
        //------ 獲取商品資訊
        List<Product_item> product_items = null;

            getSmallCategoriesAllLastProduct100();
            images = new ArrayList<>();
            getAD_data();
            //------ 商品資料配置與呈現
            //HomeItemProductAdapter adapter1 = new HomeItemProductAdapter(getContext(), product_name, product_image, product_id);
            product_items = new ArrayList<>();

        for (int i = 0; i < product_name.length; i++) {
            Product_item product_item = new Product_item();
            product_item.setProduct_id(product_id[i]);
            product_item.setProduct_name(product_name[i]);
            product_item.setProduct_image(product_image[i]);
            product_item.setProduct_price(product_price[i]);
            product_items.add(product_item);
        }
        ProductItemAdapter adapter1 = new ProductItemAdapter(getContext(), product_items, R.layout.product_card_list_item_layout);
        final RecyclerView recyclerView = view.findViewById(R.id.home_recyclerView);
        recyclerView.setAdapter(adapter1);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        final NestedScrollView nestedScrollView = view.findViewById(R.id.nested_scroll_view);

        //----------------------- go to top (Fab)  -----------------------//
        final FloatingActionButton FAB = (FloatingActionButton) view.findViewById(R.id.upFAB);
        FAB.hide();
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerView != null) {
                    //recyclerView.smoothScrollToPosition(0);
                    //gridLayoutManager.smoothScrollToPosition(recyclerView, null, 0);
                    gridLayoutManager.scrollToPositionWithOffset(0, 0);
                    nestedScrollView.fullScroll(View.FOCUS_UP);
                }

            }
        });

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY > 2){
                    FAB.show();
                }else {
                    FAB.hide();
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //------------ 逛逛熊好買(分類) 設定 ------------//
        intentToClassification = new Intent(getActivity(), ClassificationActivity.class);
        classficationClicked(view, imageView_womenclothes, R.id.classification_womenclothes);
        classficationClicked(view, imageView_makeup, R.id.icon_makeup);
        classficationClicked(view, imageView_3c, R.id.icon_3c);
        classficationClicked(view, imageView_appliance, R.id.icon_appliance);
        classficationClicked(view, imageView_menclothes, R.id.icon_menclothes);
        classficationClicked(view, imageView_sports, R.id.icon_sports);
        classficationClicked(view, imageView_books, R.id.icon_books);
        classficationClicked(view, imageView_all, R.id.icon_all);

        //----------------------- ActionBar 設定 -----------------------//
        setHasOptionsMenu(true); // ActionBar 設定 !!!!!重要!!!!!!
        Toolbar toolbar = view.findViewById(R.id.home_tool_bar_fragment);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar); // set ActionBar
        //((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        //------ shopping cart and messages  and search Activity variable setting
        intent_ShoppingCart = new Intent(getActivity(), ShoppingCartActivity.class);
        intent_Message = new Intent(getActivity(), TotalMessageActivity.class);
        intent_Search = new Intent(getActivity(), SearchActivity.class);

//        ImageView imageView = view.findViewById(R.id.search_imageview);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(intent_Search);
//            }
//        }); // search Listener


        //----------------------- Advertisement 設定 -----------------------//
        //img = new int[]{R.drawable.ad1, R.drawable.ad2, R.drawable.ad3, R.drawable.ad4, R.drawable.ad5};
        //images.add();////////////////////////
        // viewpager code

            viewPager = (ViewPager) view.findViewById(R.id.pager);
            adapter = new ViewPagerAdapter(getActivity(), images);
            //綁定適配器
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new ViewPagerChangeListner());
            //設定viewpager初始頁面
            currentIndex = images.size() * 1000;
            viewPager.setCurrentItem(currentIndex, true);
            //handler設定
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what) {
                        case 1:
                            //刷新圖片
                            viewPager.setCurrentItem(currentIndex);
                            break;
                    }
                }
            };

    }

    class ViewPagerChangeListner implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int position) {
            currentIndex = position;
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }


    @Override
    public void onStart() {
        super.onStart();
        //當介面可見的時候，每隔十秒切換一次圖片
        //初始化自動輪播定時器
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //切換圖片
                currentIndex++;
                handler.sendEmptyMessage(1);

            }
        }, 3, 3, TimeUnit.SECONDS);
    }

    @Override
    public void onStop() {
        super.onStop();
        //當介面不可見的時候，停止切換圖片
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
        }
    }


    //----------------------- ActionBar 設定 -----------------------//
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_toobar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //讀取登入狀態
        SharedPreferences login_check = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        switch (item.getItemId()) {
            case R.id.shopping_cart:
                check_login_token(intent_ShoppingCart);
                return true;
            case R.id.speach:
                severCenter_dialog.setContentView(R.layout.service_center_layout);
                severCenter_dialog.show();
                OK_severCenter_btn = (Button) severCenter_dialog.findViewById(R.id.ok_service_center);
                OK_severCenter_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        severCenter_dialog.cancel();
                    }
                });
                //check_login_token(intent_Message); //intent_Search
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initializeWidgets()
    {
        severCenter_dialog = new Dialog(getActivity());
        severCenter_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        severCenter_dialog.setCancelable(false); //無法利用回上一頁按鈕離開對話框
    }

    //------------ 逛逛熊好買(分類) 設定 ------------//
    private void classficationClicked(View view, ImageView imageView, final int id) {
        imageView = view.findViewById(id);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (id) {
                    case R.id.classification_womenclothes:
                        intentToClassification.putExtra("producttoall", "women");
                        startActivity(intentToClassification);
                        break;
                    case R.id.icon_makeup:
                        intentToClassification.putExtra("producttoall", "cosmetics");
                        startActivity(intentToClassification);
                        break;
                    case R.id.icon_3c:
                        intentToClassification.putExtra("producttoall", "computer");
                        startActivity(intentToClassification);
                        break;
                    case R.id.icon_appliance:
                        intentToClassification.putExtra("producttoall", "appliance");
                        startActivity(intentToClassification);
                        break;
                    case R.id.icon_menclothes:
                        intentToClassification.putExtra("producttoall", "men");
                        startActivity(intentToClassification);
                        break;
                    case R.id.icon_sports:
                        intentToClassification.putExtra("producttoall", "sports");
                        startActivity(intentToClassification);
                        break;
                    case R.id.icon_books:
                        intentToClassification.putExtra("producttoall", "books");
                        startActivity(intentToClassification);
                        break;
                    case R.id.icon_all:
                        intentToClassification.putExtra("producttoall", "all");
                        startActivity(intentToClassification);
                        break;
                    default:
                        intentToClassification.putExtra("producttoall", "all");
                        startActivity(intentToClassification);
                }

            }
        });
    }

    //-------------------------------------- 鵬 ---------------------------------------------------------------------
    // 20190119 鵬 用來連接資料庫撈資料------------------------------------------------
    private void getSmallCategoriesAllLastProduct100() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("categories_id", 0);  // 重要 : 第二個參數為 點選某小類的該小類id
        } catch (Exception e) {
            Log.d("debug0 putError", e.toString());
        }

        String strobj = obj.toString();
        try {
            recommendProduct_all = new QueryTask().execute(recommendProductURL, strobj).get();
            //Log.d("debug222 product",selectProduct_all);
        } catch (Exception e) {
            Log.d("debug0 queryError", e.toString());
        }

        if (recommendProduct_all.equals("無產品")) // 如果撈回來的訊息是 目前無產品 代表目前無產品，故不往下執行
            return;

        try {
            jarr = new JSONArray(recommendProduct_all);
        } catch (Exception e) {
            Log.d("debug0 catch changeArray", e.toString());
        }

        product_id = new int[jarr.length()];  // 產品在tb內的流水id號 OK
        product_name = new String[jarr.length()]; // 產品名稱  OK
        categories_id = new int[jarr.length()]; // 產品的分類 id OK
        large_categories = new String[jarr.length()]; // 產品的大類
        middle_categories = new String[jarr.length()]; // 產品的中類
        small_categories = new String[jarr.length()];  // 產品的小類
        product_image = new String[jarr.length()]; // 產品圖片路徑 OK
        total_quantity = new int[jarr.length()];  // 產品總數量
        product_price = new int[jarr.length()];  // 產品價錢
        description = new String[jarr.length()]; // 產品描述
        seller_id = new int[jarr.length()]; // 賣家id
        specification = new String[jarr.length()]; // 產品規格

        for (int i = 0; i < jarr.length(); i++) {
            setLastProduct100_data(jarr, i);
        }
    }

    // 20190119 鵬 把所有撈出來的小類產品 寫入到 array 中 ------------------------------------
    private void setLastProduct100_data(JSONArray arr, int i) {
        String imageURL = getResources().getString(R.string.localDataBaseImg);
        try {
            product_id[i] = arr.getJSONObject(i).getInt("product_id");
            product_name[i] = arr.getJSONObject(i).getString("product_name");
            categories_id[i] = arr.getJSONObject(i).getInt("categories_id");
            large_categories[i] = arr.getJSONObject(i).getString("large_categories");
            middle_categories[i] = arr.getJSONObject(i).getString("middle_categories");
            small_categories[i] = arr.getJSONObject(i).getString("small_categories");
            product_image[i] = imageURL + arr.getJSONObject(i).getString("product_image");
            total_quantity[i] = arr.getJSONObject(i).getInt("quantity");
            product_price[i] = arr.getJSONObject(i).getInt("price");
            description[i] = arr.getJSONObject(i).getString("description");
            seller_id[i] = arr.getJSONObject(i).getInt("seller_id");
            specification[i] = arr.getJSONObject(i).getString("specification");

            Log.d("debug0 create try", "try try 88888");
        } catch (Exception e) {
            Log.d("debug0 create catch", "QQQQQQ");
        }
    }

    private void check_login_token(Intent intent) {
        String tokenCheckURL = getResources().getString(R.string.localDataBase) + "homeCheck.php";
        JSONObject obj = new JSONObject();
        SharedPreferences login_check = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        if (login_check.getString("member_id", "null") != "null") {
            try {
                obj.put("member_id", login_check.getString("member_id", "null"));
                obj.put("token_check_user", login_check.getString("member_token", "null"));
                String strobj = obj.toString();
                Log.d("debug00 home", "222222222222");
                Log.d("debug00 home", strobj);
                String A = new QueryTask().execute(tokenCheckURL, strobj).get();
                Log.d("debug00 home123", A);
                Log.d("debug00 home123", "OK");
                Log.d("debug00 home", "33");
                if (A.equals("OK")) {
                    Log.d("debug00 home", "01");
                    startActivity(intent);
                } else {
                    Log.d("debug00 home", "00");
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.d("debug00 home", "11");
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    private void getAD_data() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("nullobj", 0);  // 為方便使用建立json隨意傳直，此支PHP不用值也可以執行。
        } catch (Exception e) {
            Log.d("debug00 getAD", e.toString());
        }
        String strobj = obj.toString();
        try {
            All_ads = new QueryTask().execute(ad_getURL, strobj).get();
            Log.d("debug00_ad00", All_ads);
            JSONArray jarr = new JSONArray(All_ads);
            ad_ids = new String[jarr.length()];
            ad_pics = new String[jarr.length()];
            ad_members = new String[jarr.length()];
            for (int i = 0; i < jarr.length(); i++) {
                ad_ids[i] = jarr.getJSONObject(i).getString("id");
                ad_pics[i] = getResources().getString(R.string.localDataBaseImg) + jarr.getJSONObject(i).getString("ad_image_path");
                images.add(ad_pics[i]);
                ad_members[i] = jarr.getJSONObject(i).getString("member_id");
                Log.d("debug00_ad","ID:"+ad_ids[i]+" PATH:"+ images.get(i)+" member_id:"+ad_members+"\n");
            }

        } catch (Exception e) {
            Log.d("debug00 getAD01", e.toString());
        }


    }
    //------------------------------------------------------------------------------------------------------------------------
}
