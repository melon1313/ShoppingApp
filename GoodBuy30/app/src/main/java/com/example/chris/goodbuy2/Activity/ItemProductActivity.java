package com.example.chris.goodbuy2.Activity;

import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chris.goodbuy2.Adapter.Item_Product_Image_Adapter;
import com.example.chris.goodbuy2.Adapter.ProductItemAdapter;
import com.example.chris.goodbuy2.Adapter.RatingRecyclerviewAdpter;
import com.example.chris.goodbuy2.Dialog.ItemProductBottomSheetDialog;
import com.example.chris.goodbuy2.Model.Product_item;
import com.example.chris.goodbuy2.Model.Product_item_detail;
import com.example.chris.goodbuy2.R;
import com.example.chris.goodbuy2.Service.QueryTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

public class ItemProductActivity extends AppCompatActivity implements ItemProductBottomSheetDialog.BottomSheetListener, View.OnClickListener {

    private boolean ischangeIcon = false;
    Product_item product_item;
    Product_item_detail product;


    //------------ Set Images ------------//
    private ViewPager viewPager;
    private int currentIndex;
    private Item_Product_Image_Adapter itemProductImageAdapter;

    //------------ Set Product information ------------//
    private ItemProductBottomSheetDialog itemProductBottomSheetDialog_buyDirect;//BottomSheet_BuyDirect
    private ItemProductBottomSheetDialog itemProductBottomSheetDialog_addCart;//BottomSheet_addCart
    private TextView textView_currentPage, textView_totalPage, noRating_textview,
            rating_text1, rating_text2, rating_quantity2, rating_quantity1, seeAll_textview;
    private ImageView imageView_like;
    private boolean likeisClicked, isNoRating, isRecommended;
    //private LinearLayout rating_layout, rating_layout2;
    private double scoreAverage;
    private AppCompatRatingBar ratingBar1, ratingBar2;
    private int length;
    private Intent intent;
    private Intent intents;

//    TextView textView;

    //---------------------------------------------------------------------------------------------------
    // 2090129 鵬 宣告產品個資訊變數--------------------------------------
    String recommendProductURL;
    String recommendProduct_all; // 存放從資料庫撈回來的資料
    JSONArray jarr, jarr_for_evaluation_commendProduct;
    ArrayList<String> product_image = new ArrayList<>(); // 產品圖片路徑
    ArrayList<String> product_image2 = new ArrayList<>(); // 產品圖片路徑2
    ArrayList<String> product_image3 = new ArrayList<>(); // 產品圖片路徑3
    ArrayList<String> product_all_image = new ArrayList<>(); // 產品圖片的集合，例如該產品有三張，這裡長度就有3

    // 20190131 鵬 宣告 評價相關 和 該賣家推薦商品的資訊變數
    String evaluation_and_recommendProductURL;
    String evaluation_and_recommendProduct; // 存放從資料庫撈回來的資料
    //    ArrayList<Integer> evaluatiion_member       = new ArrayList<>(); // 評價人的id
//    ArrayList<String>  evaluatiion_member_name  = new ArrayList<>(); // 評價人的 名稱
//    ArrayList<String> comment_datetime          = new ArrayList<>(); // 評價時間
//    ArrayList<String> comment_context           = new ArrayList<>(); // 評價內容
//    ArrayList<Double> score                     = new ArrayList<>(); // 評價分數
//
//    ArrayList<Integer> commendProductId    = new ArrayList<>(); // 推薦商品的id
//    ArrayList<String>  commendProductName  = new ArrayList<>(); // 推薦商品的產品名稱
//    ArrayList<String>  commendproductImage = new ArrayList<>(); // 推薦商品的產品路徑
//    ArrayList<Integer> commendProductPrice = new ArrayList<>(); //推薦商品的價格
//---------------------------------------------------------------------------------------------------
//---------------------------------------------------------
//    用來存商品資料
    SharedPreferences login_check;
    String modifyFavoriteURL;//用來調整我的最愛
//    用來存商品資料

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_product);

        product = new Product_item_detail();
        product_item = (Product_item) getIntent().getSerializableExtra("product_item");

        // 20190129 鵬  init var & call function --------------------------------------------------
        recommendProductURL = getString(R.string.localDataBase) + "product_details.php";
        getProductDetails(product_item.getProduct_id()); // 這裡請放入 產品id 喔~~~~~~
        //-----------------------------------------------------------------------------------------

        // 20190131 鵬 init var -------------------------------------------------------------------
        evaluation_and_recommendProductURL = getString(R.string.localDataBase) + "evaluation_for_product.php";
        getEvaluatiion_and_getCommentProduct(product_item.getProduct_id());
        //----------------------------------------------------------------------------------------

        setActionBar();
        setImages();

        //------------ Set Product information ------------//
        TextView textView_name = findViewById(R.id.product_name_id);
        TextView textView_price = findViewById(R.id.product_price_id);
        textView_name.setText(product.getProduct_name());

        TextView seller_name = findViewById(R.id.seller_name_context);
        TextView seller_name_title = findViewById(R.id.seller_name_title);

        seller_name_title.setText("賣家名稱: " + product.getSeller_name());

        seller_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplication(), AcountPagesActivity.class);
                intent.putExtra("member_id",String.valueOf(product.getSeller_id()) );
                startActivity(intent);
            }
        });

        textView_price.setText("$" + product.getPrice());
//        noRating_textview = findViewById(R.id.noRating_textview_id);
//        rating_layout = findViewById(R.id.rating_layout);
//        rating_layout2 = findViewById(R.id.rating_layout2);
        //Log.v("yyy", product_item.getSpecification());

        //------ BottomSheet
        // buyDirect
        itemProductBottomSheetDialog_buyDirect = ItemProductBottomSheetDialog.newInstance(
                product.getQuantity(), product.getSpecification(), product_all_image.get(0), true, String.valueOf(product.getPrice()));
        // addCart
        itemProductBottomSheetDialog_addCart = ItemProductBottomSheetDialog.newInstance(
                product.getQuantity(), product.getSpecification(), product_all_image.get(0), false, String.valueOf(product.getPrice()));


        LinearLayout specification_layout = findViewById(R.id.specification_layout);
        LinearLayout like_layout = findViewById(R.id.like_layout_id);
        LinearLayout add_cart_layout = findViewById(R.id.add_cart_layout_id);
        LinearLayout direct_buy_layout = findViewById(R.id.direct_buy_layout_id);
        imageView_like = findViewById(R.id.like_image_id);
        likeisClicked = false;

        specification_layout.setOnClickListener(this);
        add_cart_layout.setOnClickListener(this);
        direct_buy_layout.setOnClickListener(this);
        like_layout.setOnClickListener(this);

        //商品資訊
        TextView category_detail = findViewById(R.id.category_detail);
        TextView product_description = findViewById(R.id.product_description);
        //------商品分類
        category_detail.setText("" + product.getLarge_categories() + " > "
                + product.getMiddle_categories() + " > " + product.getSmall_categories());
        //------商品描述
        product_description.setText("" + product.getDescription());
//        try {
//            product_description.setText("" + product.getQuantity().get(0) + " " + product.getSpecification().get(0));
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.d("abc",e.toString());
//        }

//        textView = findViewById(R.id.test);
//        LinearLayout linearLayout = findViewById(R.id.specification_layout);
//        linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ItemProductBottomSheetDialog itemProductBottomSheetDialog = new ItemProductBottomSheetDialog();
//                itemProductBottomSheetDialog.show(getSupportFragmentManager(), "itemProductBottomSheetDialog");
//            }
//        });
//        // 商品評價
//        ratingBar1 = findViewById(R.id.ratingBar1);
//        ratingBar2 = findViewById(R.id.ratingBar2);
//        rating_text1 = findViewById(R.id.rating_text1);
//        rating_text2 = findViewById(R.id.rating_text2);
//        rating_quantity1 = findViewById(R.id.rating_quantity1);
//        rating_quantity2 = findViewById(R.id.rating_quantity2);
//        seeAll_textview = findViewById(R.id.seeAll_textview);
//        intent = new Intent(this, RatingActivity.class);
//
//        if (isNoRating) {
//            noRating_textview.setVisibility(View.VISIBLE);
//            rating_layout.setVisibility(View.GONE);
//            rating_layout2.setVisibility(View.GONE);
//        } else {
//            String rating = String.format("%.1f", scoreAverage);
//            ratingBar1.setRating(Float.valueOf(rating));
//            ratingBar2.setRating(Float.valueOf(rating));
//            rating_text1.setText(rating + "/5");
//            rating_text2.setText(rating + "/5");
//            rating_quantity1.setVisibility(View.VISIBLE);
//            rating_quantity2.setVisibility(View.VISIBLE);
//            rating_quantity1.setText("(" + length + ")");
//            rating_quantity2.setText("(" + length + ")");
//
//            RatingRecyclerviewAdpter ratingRecyclerviewAdpter = new RatingRecyclerviewAdpter(this, product);
//            RecyclerView recyclerView = findViewById(R.id.rating_recycleview);
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
//            recyclerView.setAdapter(ratingRecyclerviewAdpter); // 資料適配
//            if (length >= 3){
//                rating_layout2.setOnClickListener(this);
//                seeAll_textview.setVisibility(View.VISIBLE);
//            }else {
//                seeAll_textview.setVisibility(View.GONE);
//            }
//
//            //測試用商評總評價顯示
//            //rating_layout2.setOnClickListener(this);
//
//        }

        intents = new Intent(this, ShoppingCartActivity.class);

        String s = "";
        for (double i : product.getScore()) {
            s += String.valueOf(i);
        }
        //Toast.makeText(this, "這是評分們" + s, Toast.LENGTH_LONG).show();

        //------ 為你推薦
        if (isRecommended){
            RecyclerView recycler_view_recommend = findViewById(R.id.recycler_view_recommend);
            List<Product_item> product_items = new ArrayList<>();
            for(int i = 0; i < product.getCommendProductId().size(); i++){
                Product_item product_item = new Product_item();
                product_item.setProduct_price(product.getCommendProductPrice().get(i));
                product_item.setProduct_id(product.getCommendProductId().get(i));
                product_item.setProduct_name(product.getCommendProductName().get(i));
                product_item.setProduct_image(product.getCommendproductImage().get(i));
                product_items.add(product_item);
            }
            LinearLayoutManager linearLayoutManager
                    = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            ProductItemAdapter productItemAdapter = new ProductItemAdapter(
                    this, product_items, R.layout.recommend_item_product_layout);
            recycler_view_recommend.setLayoutManager(linearLayoutManager);
            recycler_view_recommend.setAdapter(productItemAdapter);
        }
    }

    @Override
    public void onButtonClicked(String text) {
//        textView.setText(text);
    }

    @Override
    public void onClick(View v) {
        //購物車用
        login_check=getSharedPreferences("login",Context.MODE_PRIVATE);
        SharedPreferences.Editor product_id_save =login_check.edit();
        product_id_save.putString("ID", String.valueOf(product_item.getProduct_id()));
        product_id_save.apply();
        switch (v.getId()) {
            case R.id.specification_layout:
                if (login_check.getString("member_id", "null") != "null")
                {
                itemProductBottomSheetDialog_buyDirect.show(getSupportFragmentManager(), "itemProductBottomSheetDialog");
                }else
                {
                    Toast.makeText(this, "請先登入帳號!!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.add_cart_layout_id:
                if (login_check.getString("member_id", "null") != "null")
                {
                itemProductBottomSheetDialog_addCart.show(getSupportFragmentManager(), "itemProductBottomSheetDialog");
                }else
                {
                 Toast.makeText(this, "請先登入帳號!!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.direct_buy_layout_id:
                if (login_check.getString("member_id", "null") != "null")
                {
                itemProductBottomSheetDialog_buyDirect.show(getSupportFragmentManager(), "itemProductBottomSheetDialog");
                }else
                {
                    Toast.makeText(this, "請先登入帳號!!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.like_layout_id:
                //我的最愛修改相關//20190209 byJacky
                modifyFavoriteURL = getResources().getString(R.string.localDataBase)+"my_favorite.php";
                //我的最愛修改相關//20190209 byJacky
                if (login_check.getString("member_id", "null") != "null")
                {
                    if (likeisClicked) {
                        imageView_like.setImageResource(R.drawable.ic_like);
                        AddOrCancel_favorite();
                        Toast.makeText(this, "將商品移除最愛", Toast.LENGTH_SHORT).show();
                        likeisClicked = false;
                    } else
                        {
                        imageView_like.setImageResource(R.drawable.ic_like_clicked);
                        AddOrCancel_favorite();
                        Toast.makeText(this, "將商品加到最愛", Toast.LENGTH_SHORT).show();
                        likeisClicked = true;
                        }
                }else
                    {
                    Toast.makeText(this, "請先登入帳號!!", Toast.LENGTH_SHORT).show();
                    }
                break;
//            case R.id.rating_layout2:
//                intent.putExtra("rating_product", product);
//                startActivity(intent);
//                break;
        }
    }


    //----------------------- 商品圖設定 -----------------------//
    private void setImages() {
        //img = new String[]{product.getProduct_image(), product.getProduct_image2(), product.getProduct_image3()};
        viewPager = findViewById(R.id.item_product_viewpager);
        textView_currentPage = findViewById(R.id.current_imageNum);
        textView_totalPage = findViewById(R.id.total_imageNum);

        textView_totalPage.setText(String.valueOf(product_all_image.size()));
        textView_currentPage.setText("1");

        itemProductImageAdapter = new Item_Product_Image_Adapter(this, product_all_image);

        //綁定適配器
        viewPager.setAdapter(itemProductImageAdapter);
        if (product_all_image.size() > 1) {
            viewPager.addOnPageChangeListener(new ViewPagerChangeListener());
            //選擇一個較大的條目選中
            currentIndex = product_all_image.size() * 1000;
            viewPager.setCurrentItem(currentIndex);
        }
    }

    // viewpager 商品圖設定
    class ViewPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int position) {
            currentIndex = position;
            textView_currentPage.setText(String.valueOf(position % product_all_image.size() + 1));
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }


    //----------------------- ActionBar 設定 -----------------------//
    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.prodcut_item_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.product_item_collapsingToolbarLayout);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.product_item_appBarlayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(product_item.getProduct_name());

                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_green_24dp);

                    ischangeIcon = true;
                    invalidateOptionsMenu();
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" "); //carefull there should a space between double quote otherwise it wont work

                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_circle_back);

                    ischangeIcon = false;
                    invalidateOptionsMenu();
                    isShow = false;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.product_item_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.product_item_shopping_cart:
                login_check=getSharedPreferences("login",Context.MODE_PRIVATE);
                if (login_check.getString("member_id", "null") != "null")
                {
                    startActivity(intents);
                }else
                {
                    Toast.makeText(this, "請先登入帳號!!", Toast.LENGTH_SHORT).show();
                }
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (ischangeIcon) {
            menu.findItem(R.id.product_item_shopping_cart).setIcon(R.drawable.ic_shopping_cart_green);
        } else {
            menu.findItem(R.id.product_item_shopping_cart).setIcon(R.drawable.icon_shoppingcart);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    // -------------------------------------鵬 開始--------------------------------------------------------------------------
    //2019 01 29 鵬 連接php取得該產品所有資訊
    private void getProductDetails(int pid) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("product_id", pid);  // 重要 : 第二個參數為 點選某小類的該小類id
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

        try {
            jarr = new JSONArray(recommendProduct_all);
        } catch (Exception e) {
            Log.d("debug0 catch changeArray", e.toString());
        }

        for (int i = 0; i < jarr.length(); i++) {
            setIntoProductInfoList(jarr, i);
        }

        if (product_image.size() > 0)
            product_all_image.add(product_image.get(0));
        if (product_image2.size() > 0)
            product_all_image.add(product_image2.get(0));
        if (product_image3.size() > 0)
            product_all_image.add(product_image3.get(0));
    }

    //2019 01 29 鵬 set 該產品的各項資訊 set 到 product 中
    private void setIntoProductInfoList(JSONArray arr, int i) {
        String imageURL = getResources().getString(R.string.localDataBaseImg);
        try {
            product.setProduct_id(arr.getJSONObject(0).getInt("product_id"));
            product.setProduct_name(arr.getJSONObject(0).getString("product_name"));
            product.setGroup_id(arr.getJSONObject(0).getString("group_id"));
            product.setCategories_id(arr.getJSONObject(0).getInt("categories_id"));
            product.setLarge_categories(arr.getJSONObject(0).getString("large_categories"));
            product.setMiddle_categories(arr.getJSONObject(0).getString("middle_categories"));
            product.setSmall_categories(arr.getJSONObject(0).getString("small_categories"));
            product.getQuantity().add(arr.getJSONObject(i).getInt("quantity"));
            product.setPrice(arr.getJSONObject(0).getInt("price"));
            product.setSeller_id(arr.getJSONObject(0).getInt("seller_id"));
            /////////////////////////////
            //20190210新增 Jacky
            product.setSeller_name(arr.getJSONObject(0).getString("seller_name"));
            //20190210新增 Jacky
            ////////////////////////////
            product.getSpecification().add(arr.getJSONObject(i).getString("specification"));
            product.setDescription(arr.getJSONObject(0).getString("description"));
            product.setStatus(arr.getJSONObject(0).getInt("status"));

            if (!arr.getJSONObject(0).getString("product_image").equals("nopic"))
                product_image.add(imageURL + arr.getJSONObject(0).getString("product_image"));
            if (!arr.getJSONObject(0).getString("product_image2").equals("nopic"))
                product_image2.add(imageURL + arr.getJSONObject(0).getString("product_image2"));
            if (!arr.getJSONObject(0).getString("product_image3").equals("nopic"))
                product_image3.add(imageURL + arr.getJSONObject(0).getString("product_image3"));

        } catch (Exception e) {
            Log.d("debug0 getJSONObject", e.toString());
        }
    }

    // 2019 01 31 鵬 取得該商品之評價 及 該商品的賣家所賣的其他商品(推薦用)
    private void getEvaluatiion_and_getCommentProduct(int pid) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("product_id", pid);  // 重要 : 第二個參數為 點選某小類的該小類id
        } catch (Exception e) {
            Log.d("debug0 putError", e.toString());
        }

        String strobj = obj.toString();

        try {
            evaluation_and_recommendProduct = new QueryTask().execute(evaluation_and_recommendProductURL, strobj).get();
        } catch (Exception e) {
            Log.d("debug0 queryError 377", e.toString());
        }
        Log.d("debug0 evaluation_and_recommendProduct 379", evaluation_and_recommendProduct);
        try {
            jarr_for_evaluation_commendProduct = new JSONArray(evaluation_and_recommendProduct);
        } catch (Exception e) {
            Log.d("debug0 catch changeArray 383", e.toString());
        }

        for (int i = 0; i < jarr_for_evaluation_commendProduct.length(); i++) {
            Log.d("debug0 jarr_for_evaluation_commendProduct L 387", i + "\t" + jarr_for_evaluation_commendProduct.length());
            setEvaluationAndCommendProduct(jarr_for_evaluation_commendProduct, i);
        }
    }

    private void setEvaluationAndCommendProduct(JSONArray arr, int i) {

        String imageURL = getResources().getString(R.string.localDataBaseImg);
        if (i == 0) {
            try {
                Log.d("debug0 into try 394", " in try block");
//                int evaluation_length = arr.getJSONArray(i) ;
                JSONArray jar = arr.getJSONArray(i);
                Log.d("debug0 evaluation_length 397", jar.length() + "");
                if (jar.length() > 0) {

                    length = jar.length();
                    isNoRating = false;
                    for (int j = 0; j < length; j++) {
                        product.getEvaluatiion_member().add(jar.getJSONObject(j).getInt("member_id"));
                        product.getEvaluatiion_member_name().add(jar.getJSONObject(j).getString("commenter_name"));
                        product.getComment_datetime().add(jar.getJSONObject(j).getString("comment_datetime"));
                        product.getComment_context().add(jar.getJSONObject(j).getString("comment_context"));
                        product.getScore().add(jar.getJSONObject(j).getDouble("score"));

//                        evaluatiion_member.add(jar.getJSONObject(j).getInt("member_id"));
//                        evaluatiion_member_name.add(jar.getJSONObject(j).getString("commenter_name"));
//                        comment_datetime.add(jar.getJSONObject(j).getString("comment_datetime"));
//                        comment_context.add(jar.getJSONObject(j).getString("comment_context"));
//                        score.add(jar.getJSONObject(j).getDouble("score"));
                        scoreAverage += jar.getJSONObject(j).getDouble("score");
                    } //Log.d("debug0 evaluatiion_member_name 407" , evaluatiion_member_name.get(0));
                    scoreAverage = scoreAverage / length;
                    //Toast.makeText(this, "有評價" + evaluatiion_member_name.get(0) + scoreAverage, Toast.LENGTH_SHORT).show();
//                    Log.v("zzxxx", "拉拉拉" + product.getEvaluatiion_member_name().get(0));
//                    Log.v("zzxxx", "拉拉拉" + product.getEvaluatiion_member_name().get(1));
//                    Log.v("zzxxx", "拉拉拉" + length);
//                    Toast.makeText(this, "長度:" + length, Toast.LENGTH_LONG).show();
                } else {
                    // 這裡是沒有評價的時候要做的顯示
                    Log.d("debug0 沒有評價 414", "沒有評價捏");
                    isNoRating = true;
                }
            } catch (Exception e) {
                Log.d("debug0 setEvaluation ERROR i=0 418", e.toString());
            }
        } else if (i == 1) {
            try {
                JSONArray jar = arr.getJSONArray(i);
                Log.d("debug0 recommend 425 jar length", jar.length() + "");
                if (jar.length() > 0) {
                    for (int j = 0; j < jar.length(); j++) {
                        product.getCommendProductId().add(jar.getJSONObject(j).getInt("product_id"));
                        product.getCommendProductName().add(jar.getJSONObject(j).getString("product_name"));
                        product.getCommendproductImage().add(imageURL + jar.getJSONObject(j).getString("product_image"));
                        product.getCommendProductPrice().add(jar.getJSONObject(j).getInt("price"));
                        isRecommended = true;
//                        commendProductId.add( jar.getJSONObject(j).getInt("product_id") );
//                        commendProductName.add( jar.getJSONObject(j).getString("product_name") );
//                        commendproductImage.add(imageURL +  jar.getJSONObject(j).getString("product_image") );
//                        commendProductPrice.add( jar.getJSONObject(j).getInt("price") );
                    }
                } else {
                    //這裡是若是此產品頁面的賣家無其他推薦商品時要做的!!!
                    isRecommended = false;
                    Log.d("debug0 推薦商品 438", " 推薦商品喔");
                }
            } catch (Exception e) {
                Log.d("debug0 coommendPro ERROR i=1 443", e.toString());
            }
        }

    }
    // 我的最愛新增及取消//20190209 byJacky
    private void AddOrCancel_favorite()
    {
        JSONObject obj =new JSONObject();
        try {
            obj.put("member_id",login_check.getString("member_id","Null"));
            obj.put("token_check_user",login_check.getString("member_token","null"));
            obj.put("product_id",login_check.getString("ID","null"));
            obj.put("action","2");
            String strobj   =  obj.toString();
            Log.d("debug00_Item_favorite00",strobj);
            String result = new QueryTask().execute(modifyFavoriteURL,strobj).get();
            Log.d("debug00_Item_favorite",result);
            //若正確跑完result會寫修改成功
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 我的最愛新增及取消//20190209 byJacky


}
