package com.example.chris.goodbuy2.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chris.goodbuy2.Adapter.CategoryGridviewAdapter;
import com.example.chris.goodbuy2.SetView.CustomGridView;
import com.example.chris.goodbuy2.Data.Category_data;
import com.example.chris.goodbuy2.R;

public class ClassificationActivity extends AppCompatActivity implements View.OnClickListener {

    Intent intent_search, intent_product;
    LinearLayout linear_category_women, linear_category_cosmetic, linear_category_3c, linear_category_menclothes, linear_category_sports, linear_category_books, linear_category_appliance, linear_category_life;
    CustomGridView hide_womenclothes, hide_cosmetics, hide_3c, hide_menclothes, hide_sports, hide_books, hide_appliance, hide_life;
    CategoryGridviewAdapter categoryGridviewAdapter;
    ImageView image_women, image_cosmetics, image_computer, image_men, image_sports, image_books, image_appliance, image_life;
    TextView textView_women, textView_cosmetics, textView_computer, textView_men, textView_sports, textView_books, textView_appliance, textView_life;
    ImageView triangle_women, triangle_cosmetics, triangle_computer, triangle_men, triangle_sports, triangle_books, triangle_appliance, triangle_life;
    Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classification);

        //----------------------- ActionBar 設定 -----------------------//
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        intent_search = new Intent(ClassificationActivity.this, ShoppingCartActivity.class);

        //----------------------- Category 設定 -----------------------//
        res = getResources();
        InitializeCatrgory();
        setCategoryAdater(hide_womenclothes, Category_data.category_womenclothes);
        setCategoryAdater(hide_cosmetics, Category_data.category_cosmetic);
        setCategoryAdater(hide_3c, Category_data.category_3c);
        setCategoryAdater(hide_menclothes, Category_data.category_menclothes);
        setCategoryAdater(hide_sports, Category_data.category_sports);
        setCategoryAdater(hide_books, Category_data.category_books);
        setCategoryAdater(hide_appliance, Category_data.category_appliance);
        setCategoryAdater(hide_life, Category_data.category_life);
        setAutoCategorySelected();
    }

    //----------------------- Category 設定 -----------------------//
    private void setCategoryAdater(final CustomGridView customGridView, String[] items) {
        intent_product = new Intent(ClassificationActivity.this, ProductsActivity.class);
        categoryGridviewAdapter = new CategoryGridviewAdapter(this, items);
        customGridView.setAdapter(categoryGridviewAdapter);
        customGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (customGridView.getId()) {
                    case R.id.hide_womenclothes:
                        switch (position) {
                            case 0:
                                intent_product.putExtra("MidddleCategoryName", "女裝");
                                intent_product.putExtra("LargeCategoryName", "womenclothes");
                                startActivity(intent_product);
                                break;
                            case 1:
                                intent_product.putExtra("MidddleCategoryName", "女仕配件");
                                intent_product.putExtra("LargeCategoryName", "womenclothes");
                                startActivity(intent_product);
                                break;
                            case 2:
                                intent_product.putExtra("MidddleCategoryName", "女鞋襪");
                                intent_product.putExtra("LargeCategoryName", "womenclothes");
                                startActivity(intent_product);
                                break;
                        }
                        break;
                    case R.id.hide_cosmetics:
                        switch (position) {
                            case 0:
                                intent_product.putExtra("MidddleCategoryName", "保養");
                                intent_product.putExtra("LargeCategoryName", "cosmetics");
                                startActivity(intent_product);
                                break;
                            case 1:
                                intent_product.putExtra("MidddleCategoryName", "沐浴清潔");
                                intent_product.putExtra("LargeCategoryName", "cosmetics");
                                startActivity(intent_product);
                                break;
                            case 2:
                                intent_product.putExtra("MidddleCategoryName", "彩妝");
                                intent_product.putExtra("LargeCategoryName", "cosmetics");
                                startActivity(intent_product);
                                break;
                        }
                        break;
                    case R.id.hide_3c:
                        switch (position) {
                            case 0:
                                intent_product.putExtra("MidddleCategoryName", "手機平板");
                                intent_product.putExtra("LargeCategoryName", "computer");
                                startActivity(intent_product);
                                break;
                            case 1:
                                intent_product.putExtra("MidddleCategoryName", "桌上電腦");
                                intent_product.putExtra("LargeCategoryName", "computer");
                                startActivity(intent_product);
                                break;
                            case 2:
                                intent_product.putExtra("MidddleCategoryName", "筆記型電腦");
                                intent_product.putExtra("LargeCategoryName", "computer");
                                startActivity(intent_product);
                                break;
                            case 3:
                                intent_product.putExtra("MidddleCategoryName", "電玩");
                                intent_product.putExtra("LargeCategoryName", "computer");
                                startActivity(intent_product);
                                break;
                            case 4:
                                intent_product.putExtra("MidddleCategoryName", "相機");
                                intent_product.putExtra("LargeCategoryName", "computer");
                                startActivity(intent_product);
                                break;
                            case 5:
                                intent_product.putExtra("MidddleCategoryName", "電腦零組件");
                                intent_product.putExtra("LargeCategoryName", "computer");
                                startActivity(intent_product);
                                break;
                            case 6:
                                intent_product.putExtra("MidddleCategoryName", "手機周邊");
                                intent_product.putExtra("LargeCategoryName", "computer");
                                startActivity(intent_product);
                                break;
                            case 7:
                                intent_product.putExtra("MidddleCategoryName", "資訊周邊");
                                intent_product.putExtra("LargeCategoryName", "computer");
                                startActivity(intent_product);
                                break;
                            case 8:
                                intent_product.putExtra("MidddleCategoryName", "智慧穿戴");
                                intent_product.putExtra("LargeCategoryName", "computer");
                                startActivity(intent_product);
                                break;
                        }
                        break;
                    case R.id.hide_menclothes:
                        switch (position) {
                            case 0:
                                intent_product.putExtra("MidddleCategoryName", "男裝");
                                intent_product.putExtra("LargeCategoryName", "manclothes");
                                startActivity(intent_product);
                                break;
                            case 1:
                                intent_product.putExtra("MidddleCategoryName", "男仕配件");
                                intent_product.putExtra("LargeCategoryName", "manclothes");
                                startActivity(intent_product);
                                break;
                            case 2:
                                intent_product.putExtra("MidddleCategoryName", "男鞋襪");
                                intent_product.putExtra("LargeCategoryName", "manclothes");
                                startActivity(intent_product);
                                break;
                        }
                        break;
                    case R.id.hide_sports:
                        switch (position) {
                            case 0:
                                intent_product.putExtra("MidddleCategoryName", "機能服飾");
                                intent_product.putExtra("LargeCategoryName", "sports");
                                startActivity(intent_product);
                                break;
                            case 1:
                                intent_product.putExtra("MidddleCategoryName", "運動健身");
                                intent_product.putExtra("LargeCategoryName", "sports");
                                startActivity(intent_product);
                                break;
                            case 2:
                                intent_product.putExtra("MidddleCategoryName", "舒壓按摩");
                                intent_product.putExtra("LargeCategoryName", "sports");
                                startActivity(intent_product);
                                break;
                            case 3:
                                intent_product.putExtra("MidddleCategoryName", "玩具");
                                intent_product.putExtra("LargeCategoryName", "sports");
                                startActivity(intent_product);
                                break;
                        }
                        break;
                    case R.id.hide_books:
                        switch (position) {
                            case 0:
                                intent_product.putExtra("MidddleCategoryName", "文學休閒");
                                intent_product.putExtra("LargeCategoryName", "books");
                                startActivity(intent_product);
                                break;
                            case 1:
                                intent_product.putExtra("MidddleCategoryName", "生活家庭");
                                intent_product.putExtra("LargeCategoryName", "books");
                                startActivity(intent_product);
                                break;
                            case 2:
                                intent_product.putExtra("MidddleCategoryName", "知識理財");
                                intent_product.putExtra("LargeCategoryName", "books");
                                startActivity(intent_product);
                                break;
                            case 3:
                                intent_product.putExtra("MidddleCategoryName", "文具用品");
                                intent_product.putExtra("LargeCategoryName", "books");
                                startActivity(intent_product);
                                break;
                        }
                        break;
                    case R.id.hide_appliance:
                        switch (position) {
                            case 0:
                                intent_product.putExtra("MidddleCategoryName", "大家電");
                                intent_product.putExtra("LargeCategoryName", "appliance");
                                startActivity(intent_product);
                                break;
                            case 1:
                                intent_product.putExtra("MidddleCategoryName", "生活家電");
                                intent_product.putExtra("LargeCategoryName", "appliance");
                                startActivity(intent_product);
                                break;
                            case 2:
                                intent_product.putExtra("MidddleCategoryName", "視聽娛樂");
                                intent_product.putExtra("LargeCategoryName", "appliance");
                                startActivity(intent_product);
                                break;
                        }
                        break;
                    case R.id.hide_life:
                        switch (position) {
                            case 0:
                                intent_product.putExtra("MidddleCategoryName", "餐廚用品");
                                intent_product.putExtra("LargeCategoryName", "life");
                                startActivity(intent_product);
                                break;
                            case 1:
                                intent_product.putExtra("MidddleCategoryName", "居家裝修");
                                intent_product.putExtra("LargeCategoryName", "life");
                                startActivity(intent_product);
                                break;
                            case 2:
                                intent_product.putExtra("MidddleCategoryName", "生活百貨");
                                intent_product.putExtra("LargeCategoryName", "life");
                                startActivity(intent_product);
                                break;
                        }
                        break;
                }
            }
        });
    }

    private void InitializeCatrgory() {
        linear_category_women = findViewById(R.id.linear_category_women);
        linear_category_cosmetic = findViewById(R.id.linear_category_cosmetic);
        linear_category_3c = findViewById(R.id.linear_category_3c);
        linear_category_menclothes = findViewById(R.id.linear_category_menclothes);
        linear_category_sports = findViewById(R.id.linear_category_sports);
        linear_category_books = findViewById(R.id.linear_category_books);
        linear_category_appliance = findViewById(R.id.linear_category_appliance);
        linear_category_life = findViewById(R.id.linear_category_life);

        hide_womenclothes = findViewById(R.id.hide_womenclothes);
        hide_cosmetics = findViewById(R.id.hide_cosmetics);
        hide_3c = findViewById(R.id.hide_3c);
        hide_menclothes = findViewById(R.id.hide_menclothes);
        hide_sports = findViewById(R.id.hide_sports);
        hide_books = findViewById(R.id.hide_books);
        hide_appliance = findViewById(R.id.hide_appliance);
        hide_life = findViewById(R.id.hide_life);

        image_women = findViewById(R.id.image_women);
        image_cosmetics = findViewById(R.id.image_cosmetics);
        image_computer = findViewById(R.id.image_computer);
        image_men = findViewById(R.id.image_men);
        image_sports = findViewById(R.id.image_sports);
        image_books = findViewById(R.id.image_books);
        image_appliance = findViewById(R.id.image_appliance);
        image_life = findViewById(R.id.image_life);

        textView_women = findViewById(R.id.textview_women);
        textView_cosmetics = findViewById(R.id.textview_cosmetics);
        textView_computer = findViewById(R.id.textview_computer);
        textView_men = findViewById(R.id.textview_men);
        textView_sports = findViewById(R.id.textview_sports);
        textView_books = findViewById(R.id.textview_books);
        textView_appliance = findViewById(R.id.textview_appliance);
        textView_life = findViewById(R.id.textview_life);

        triangle_women = findViewById(R.id.triangle_women);
        triangle_cosmetics = findViewById(R.id.triangle_cosmetics);
        triangle_computer = findViewById(R.id.triangle_computer);
        triangle_men = findViewById(R.id.triangle_men);
        triangle_sports = findViewById(R.id.triangle_sports);
        triangle_books = findViewById(R.id.triangle_books);
        triangle_appliance = findViewById(R.id.triangle_appliance);
        triangle_life = findViewById(R.id.triangle_llife);

        setCategoryListener();
    }

    private void setCategoryListener() {
        linear_category_women.setOnClickListener(this);
        linear_category_cosmetic.setOnClickListener(this);
        linear_category_3c.setOnClickListener(this);
        linear_category_menclothes.setOnClickListener(this);
        linear_category_sports.setOnClickListener(this);
        linear_category_books.setOnClickListener(this);
        linear_category_appliance.setOnClickListener(this);
        linear_category_life.setOnClickListener(this);
    }

    private void setCategoryVisible(int womenNum, int cosmeticsNum, int computerNum, int menNum, int sportNum, int bookNum, int applianceNum, int lifeNum) {
        hide_womenclothes.setVisibility(womenNum);
        hide_cosmetics.setVisibility(cosmeticsNum);
        hide_3c.setVisibility(computerNum);
        hide_menclothes.setVisibility(menNum);
        hide_sports.setVisibility(sportNum);
        hide_books.setVisibility(bookNum);
        hide_appliance.setVisibility(applianceNum);
        hide_life.setVisibility(lifeNum);

        triangle_women.setVisibility(womenNum);
        triangle_cosmetics.setVisibility(cosmeticsNum);
        triangle_computer.setVisibility(computerNum);
        triangle_men.setVisibility(menNum);
        triangle_sports.setVisibility(sportNum);
        triangle_books.setVisibility(bookNum);
        triangle_appliance.setVisibility(applianceNum);
        triangle_life.setVisibility(lifeNum);
    }

    private void ChangeColor(int color1, int color2, int color3, int color4, int color5, int color6, int color7, int color8) {
        image_women.setColorFilter(color1);
        image_cosmetics.setColorFilter(color2);
        image_computer.setColorFilter(color3);
        image_men.setColorFilter(color4);
        image_sports.setColorFilter(color5);
        image_books.setColorFilter(color6);
        image_appliance.setColorFilter(color7);
        image_life.setColorFilter(color8);

        textView_women.setTextColor(color1);
        textView_cosmetics.setTextColor(color2);
        textView_computer.setTextColor(color3);
        textView_men.setTextColor(color4);
        textView_sports.setTextColor(color5);
        textView_books.setTextColor(color6);
        textView_appliance.setTextColor(color7);
        textView_life.setTextColor(color8);
    }

    private void setHideViewVisiable(int id) {
        switch (id) {
            case R.id.linear_category_women:
                setCategoryVisible(View.VISIBLE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE);
                ChangeColor(res.getColor(R.color.green), res.getColor(R.color.orange), res.getColor(R.color.orange),
                        res.getColor(R.color.orange), res.getColor(R.color.orange), res.getColor(R.color.orange),
                        res.getColor(R.color.orange), res.getColor(R.color.orange));
                break;
            case R.id.linear_category_cosmetic:
                setCategoryVisible(View.GONE, View.VISIBLE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE);
                ChangeColor(res.getColor(R.color.orange), res.getColor(R.color.green), res.getColor(R.color.orange),
                        res.getColor(R.color.orange), res.getColor(R.color.orange), res.getColor(R.color.orange),
                        res.getColor(R.color.orange), res.getColor(R.color.orange));
                break;
            case R.id.linear_category_3c:
                setCategoryVisible(View.GONE, View.GONE, View.VISIBLE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE);
                ChangeColor(res.getColor(R.color.orange), res.getColor(R.color.orange), res.getColor(R.color.green),
                        res.getColor(R.color.orange), res.getColor(R.color.orange), res.getColor(R.color.orange),
                        res.getColor(R.color.orange), res.getColor(R.color.orange));
                break;
            case R.id.linear_category_menclothes:
                setCategoryVisible(View.GONE, View.GONE, View.GONE, View.VISIBLE, View.GONE, View.GONE, View.GONE, View.GONE);
                ChangeColor(res.getColor(R.color.orange), res.getColor(R.color.orange), res.getColor(R.color.orange),
                        res.getColor(R.color.green), res.getColor(R.color.orange), res.getColor(R.color.orange),
                        res.getColor(R.color.orange), res.getColor(R.color.orange));
                break;
            case R.id.linear_category_sports:
                setCategoryVisible(View.GONE, View.GONE, View.GONE, View.GONE, View.VISIBLE, View.GONE, View.GONE, View.GONE);
                ChangeColor(res.getColor(R.color.orange), res.getColor(R.color.orange), res.getColor(R.color.orange),
                        res.getColor(R.color.orange), res.getColor(R.color.green), res.getColor(R.color.orange),
                        res.getColor(R.color.orange), res.getColor(R.color.orange));
                break;
            case R.id.linear_category_books:
                setCategoryVisible(View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.VISIBLE, View.GONE, View.GONE);
                ChangeColor(res.getColor(R.color.orange), res.getColor(R.color.orange), res.getColor(R.color.orange),
                        res.getColor(R.color.orange), res.getColor(R.color.orange), res.getColor(R.color.green),
                        res.getColor(R.color.orange), res.getColor(R.color.orange));
                break;
            case R.id.linear_category_appliance:
                setCategoryVisible(View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.VISIBLE, View.GONE);
                ChangeColor(res.getColor(R.color.orange), res.getColor(R.color.orange), res.getColor(R.color.orange),
                        res.getColor(R.color.orange), res.getColor(R.color.orange), res.getColor(R.color.orange),
                        res.getColor(R.color.green), res.getColor(R.color.orange));
                break;
            case R.id.linear_category_life:
                setCategoryVisible(View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.VISIBLE);
                ChangeColor(res.getColor(R.color.orange), res.getColor(R.color.orange), res.getColor(R.color.orange),
                        res.getColor(R.color.orange), res.getColor(R.color.orange), res.getColor(R.color.orange),
                        res.getColor(R.color.orange), res.getColor(R.color.green));
                break;
        }
    }

    private void setAutoCategorySelected() {
        Intent intent = getIntent();
        String test = intent.getStringExtra("producttoall");
        if(test != null){
            switch (test) {
                case "women":
                    setHideViewVisiable(R.id.linear_category_women);
                    break;
                case "cosmetics":
                    setHideViewVisiable(R.id.linear_category_cosmetic);
                    break;
                case "computer":
                    setHideViewVisiable(R.id.linear_category_3c);
                    break;
                case "appliance":
                    setHideViewVisiable(R.id.linear_category_appliance);
                    break;
                case "men":
                    setHideViewVisiable(R.id.linear_category_menclothes);
                    break;
                case "sports":
                    setHideViewVisiable(R.id.linear_category_sports);
                    break;
                case "books":
                    setHideViewVisiable(R.id.linear_category_books);
                    break;
                case "all":
                    break;
            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_category_women:
                if (hide_womenclothes.getVisibility() == View.GONE) {
                    setHideViewVisiable(R.id.linear_category_women);
                } else {
                    hide_womenclothes.setVisibility(View.GONE);
                    image_women.setColorFilter(res.getColor(R.color.orange));
                    textView_women.setTextColor(res.getColor(R.color.orange));
                    triangle_women.setVisibility(View.GONE);
                }
                break;
            case R.id.linear_category_cosmetic:
                if (hide_cosmetics.getVisibility() == View.GONE) {
                    setHideViewVisiable(R.id.linear_category_cosmetic);
                } else {
                    hide_cosmetics.setVisibility(View.GONE);
                    image_cosmetics.setColorFilter(res.getColor(R.color.orange));
                    textView_cosmetics.setTextColor(res.getColor(R.color.orange));
                    triangle_cosmetics.setVisibility(View.GONE);
                }
                break;
            case R.id.linear_category_3c:
                if (hide_3c.getVisibility() == View.GONE) {
                    setHideViewVisiable(R.id.linear_category_3c);
                } else {
                    hide_3c.setVisibility(View.GONE);
                    image_computer.setColorFilter(res.getColor(R.color.orange));
                    textView_computer.setTextColor(res.getColor(R.color.orange));
                    triangle_computer.setVisibility(View.GONE);
                }
                break;
            case R.id.linear_category_menclothes:
                if (hide_menclothes.getVisibility() == View.GONE) {
                    setHideViewVisiable(R.id.linear_category_menclothes);
                } else {
                    hide_menclothes.setVisibility(View.GONE);
                    image_men.setColorFilter(res.getColor(R.color.orange));
                    textView_men.setTextColor(res.getColor(R.color.orange));
                    triangle_men.setVisibility(View.GONE);
                }
                break;
            case R.id.linear_category_sports:
                if (hide_sports.getVisibility() == View.GONE) {
                    setHideViewVisiable(R.id.linear_category_sports);
                } else {
                    hide_sports.setVisibility(View.GONE);
                    image_sports.setColorFilter(res.getColor(R.color.orange));
                    textView_sports.setTextColor(res.getColor(R.color.orange));
                    triangle_sports.setVisibility(View.GONE);
                }
                break;
            case R.id.linear_category_books:
                if (hide_books.getVisibility() == View.GONE) {
                    setHideViewVisiable(R.id.linear_category_books);
                } else {
                    hide_books.setVisibility(View.GONE);
                    image_books.setColorFilter(res.getColor(R.color.orange));
                    textView_books.setTextColor(res.getColor(R.color.orange));
                    triangle_books.setVisibility(View.GONE);
                }
                break;
            case R.id.linear_category_appliance:
                if (hide_appliance.getVisibility() == View.GONE) {
                    setHideViewVisiable(R.id.linear_category_appliance);
                } else {
                    hide_appliance.setVisibility(View.GONE);
                    image_appliance.setColorFilter(res.getColor(R.color.orange));
                    textView_appliance.setTextColor(res.getColor(R.color.orange));
                    triangle_appliance.setVisibility(View.GONE);
                }
                break;
            case R.id.linear_category_life:
                if (hide_life.getVisibility() == View.GONE) {
                    setHideViewVisiable(R.id.linear_category_life);
                } else {
                    hide_life.setVisibility(View.GONE);
                    image_life.setColorFilter(res.getColor(R.color.orange));
                    textView_life.setTextColor(res.getColor(R.color.orange));
                    triangle_life.setVisibility(View.GONE);
                }
                break;
        }
    }


    //----------------------- ActionBar 設定 -----------------------//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon_sesrch:
                startActivity(intent_search);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
