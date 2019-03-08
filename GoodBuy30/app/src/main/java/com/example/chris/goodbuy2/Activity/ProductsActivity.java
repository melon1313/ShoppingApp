package com.example.chris.goodbuy2.Activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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
import android.widget.Switch;
import android.widget.TextView;

import com.example.chris.goodbuy2.Adapter.ProductItemAdapter;
import com.example.chris.goodbuy2.Fragment.ProductsFragment;
import com.example.chris.goodbuy2.Model.Product_item;
import com.example.chris.goodbuy2.Service.QueryTask;
import com.example.chris.goodbuy2.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {


    String MidddleCategoryName, LargeCategoryName;//smallcategoryNum,


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Intent intent = getIntent();
        //smallcategoryNum = intent.getStringExtra("smallcategoryNum");
        MidddleCategoryName = intent.getStringExtra("MidddleCategoryName");
        LargeCategoryName = intent.getStringExtra("LargeCategoryName");

        initializeWidgets();
    }

    private void initializeWidgets() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        //actionBar.setIcon(R.drawable.ic_icon_account);
//        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        actionBar.setCustomView(R.layout.actionbar_title_layout);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle(MidddleCategoryName);

        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), MidddleCategoryName, LargeCategoryName);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        private String MidddleCategoryName, LargeCategoryName;

        public SectionsPagerAdapter(FragmentManager supportFragmentManager, String MidddleCategoryName, String LargeCategoryName) {
            super(supportFragmentManager);
            this.MidddleCategoryName = MidddleCategoryName;
            this.LargeCategoryName = LargeCategoryName;
        }

        @Override
        public Fragment getItem(int position) {
            TabLayout tabLayout = findViewById(R.id.tabs);
            switch (LargeCategoryName) {
                case "womenclothes":
                    switch (MidddleCategoryName) {
                        case "女裝":
                            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("12"); //女上衣
                                case 1:
                                    return ProductsFragment.newInstance("13"); // 褲/裙
                                case 2:
                                    return ProductsFragment.newInstance("14"); // 女大衣外套
                                case 3:
                                    return ProductsFragment.newInstance("15"); // 洋裝禮服
                                case 4:
                                    return ProductsFragment.newInstance("16"); // 貼身衣物
                            }
                        case "女仕配件":
                            tabLayout.setTabMode(TabLayout.MODE_FIXED);
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("17"); // 女皮飾
                                case 1:
                                    return ProductsFragment.newInstance("18"); // 女包
                                case 2:
                                    return ProductsFragment.newInstance("19"); // 女配件
                            }
                        case "女鞋襪":
                            tabLayout.setTabMode(TabLayout.MODE_FIXED);
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("20"); // 女運動鞋
                                case 1:
                                    return ProductsFragment.newInstance("21"); // 女休閒鞋
                                case 2:
                                    return ProductsFragment.newInstance("22"); // 高跟鞋
                                case 3:
                                    return ProductsFragment.newInstance("23"); // 女襪子
                            }
                    }
                case "cosmetics":
                    tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                    switch (MidddleCategoryName) {
                        case "保養":
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("130"); // 臉部保養
                                case 1:
                                    return ProductsFragment.newInstance("131"); // 眼部保養
                                case 2:
                                    return ProductsFragment.newInstance("132"); // 唇部保養
                                case 3:
                                    return ProductsFragment.newInstance("133"); // 手足保養
                                case 4:
                                    return ProductsFragment.newInstance("134"); // 身體保養
                            }
                        case "沐浴清潔":
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("135"); // 洗/護/潤髮
                                case 1:
                                    return ProductsFragment.newInstance("136"); // 肌膚清潔
                                case 2:
                                    return ProductsFragment.newInstance("137"); // 臉部清潔
                                case 3:
                                    return ProductsFragment.newInstance("138"); // 口腔清潔
                            }
                        case "彩妝":
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("139"); // 臉部彩妝
                                case 1:
                                    return ProductsFragment.newInstance("140"); // 眼眉彩妝
                                case 2:
                                    return ProductsFragment.newInstance("141"); // 唇部彩妝
                                case 3:
                                    return ProductsFragment.newInstance("142"); // 指甲彩繪
                                case 4:
                                    return ProductsFragment.newInstance("143"); // 美妝用品
                            }

                    }

                case "computer":
                    tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                    switch (MidddleCategoryName) {
                        case "手機平板":
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("24");
                                case 1:
                                    return ProductsFragment.newInstance("25");
                                case 2:
                                    return ProductsFragment.newInstance("26");
                                case 3:
                                    return ProductsFragment.newInstance("27");
                                case 4:
                                    return ProductsFragment.newInstance("28");
                                case 5:
                                    return ProductsFragment.newInstance("29");
                                case 6:
                                    return ProductsFragment.newInstance("30");
                            }
                        case "桌上電腦":
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("31");
                                case 1:
                                    return ProductsFragment.newInstance("32");
                                case 2:
                                    return ProductsFragment.newInstance("33");
                                case 3:
                                    return ProductsFragment.newInstance("34");
                                case 4:
                                    return ProductsFragment.newInstance("35");
                                case 5:
                                    return ProductsFragment.newInstance("36");
                            }
                        case "筆記型電腦":
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("37");
                                case 1:
                                    return ProductsFragment.newInstance("38");
                                case 2:
                                    return ProductsFragment.newInstance("39");
                                case 3:
                                    return ProductsFragment.newInstance("40");
                                case 4:
                                    return ProductsFragment.newInstance("41");
                                case 5:
                                    return ProductsFragment.newInstance("42");
                            }
                        case "電玩":
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("43");
                                case 1:
                                    return ProductsFragment.newInstance("44");
                                case 2:
                                    return ProductsFragment.newInstance("45");
                                case 3:
                                    return ProductsFragment.newInstance("46");
                                case 4:
                                    return ProductsFragment.newInstance("47");
                            }
                        case "相機":
                            tabLayout.setTabMode(TabLayout.MODE_FIXED);
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("48");
                                case 1:
                                    return ProductsFragment.newInstance("49");
                                case 2:
                                    return ProductsFragment.newInstance("50");
                                case 3:
                                    return ProductsFragment.newInstance("51");
                            }
                        case "電腦零組件":
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("52");
                                case 1:
                                    return ProductsFragment.newInstance("53");
                                case 2:
                                    return ProductsFragment.newInstance("54");
                                case 3:
                                    return ProductsFragment.newInstance("55");
                                case 4:
                                    return ProductsFragment.newInstance("56");
                            }
                        case "手機周邊":
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("57");
                                case 1:
                                    return ProductsFragment.newInstance("58");
                                case 2:
                                    return ProductsFragment.newInstance("59");
                                case 3:
                                    return ProductsFragment.newInstance("60");
                            }
                        case "資訊周邊":
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("61");
                                case 1:
                                    return ProductsFragment.newInstance("62");
                                case 2:
                                    return ProductsFragment.newInstance("63");
                                case 3:
                                    return ProductsFragment.newInstance("64");
                                case 4:
                                    return ProductsFragment.newInstance("65");
                                case 5:
                                    return ProductsFragment.newInstance("66");
                                case 6:
                                    return ProductsFragment.newInstance("67");
                            }
                        case "智慧穿戴":
                            tabLayout.setTabMode(TabLayout.MODE_FIXED);
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("68");
                                case 1:
                                    return ProductsFragment.newInstance("70");
                                case 2:
                                    return ProductsFragment.newInstance("69");
                            }
                    }

                case "manclothes":
                    tabLayout.setTabMode(TabLayout.MODE_FIXED);
                    switch (MidddleCategoryName) {
                        case "男裝":
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("1");
                                case 1:
                                    return ProductsFragment.newInstance("2");
                                case 2:
                                    return ProductsFragment.newInstance("3");
                                case 3:
                                    return ProductsFragment.newInstance("4");
                            }
                        case "男仕配件":
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("5");
                                case 1:
                                    return ProductsFragment.newInstance("6");
                                case 2:
                                    return ProductsFragment.newInstance("7");
                            }
                        case "男鞋襪":
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("8");
                                case 1:
                                    return ProductsFragment.newInstance("9");
                                case 2:
                                    return ProductsFragment.newInstance("10");
                                case 3:
                                    return ProductsFragment.newInstance("11");
                            }
                    }
                case "sports":
                    tabLayout.setTabMode(TabLayout.MODE_FIXED);
                    switch (MidddleCategoryName) {
                        case "機能服飾":
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("71");
                                case 1:
                                    return ProductsFragment.newInstance("72");
                                case 2:
                                    return ProductsFragment.newInstance("73");
                                case 3:
                                    return ProductsFragment.newInstance("74");
                            }
                        case "運動健身":
                            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("75");
                                case 1:
                                    return ProductsFragment.newInstance("76");
                                case 2:
                                    return ProductsFragment.newInstance("77");
                                case 3:
                                    return ProductsFragment.newInstance("78");
                                case 4:
                                    return ProductsFragment.newInstance("79");
                            }
                        case "舒壓按摩":
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("80");
                                case 1:
                                    return ProductsFragment.newInstance("81");
                                case 2:
                                    return ProductsFragment.newInstance("82");
                            }
                        case "玩具":
                            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("83");
                                case 1:
                                    return ProductsFragment.newInstance("84");
                                case 2:
                                    return ProductsFragment.newInstance("85");
                                case 3:
                                    return ProductsFragment.newInstance("86");
                                case 4:
                                    return ProductsFragment.newInstance("87");
                            }
                    }

                case "books":
                    tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                    switch (MidddleCategoryName) {
                        case "文學休閒":
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("144");
                                case 1:
                                    return ProductsFragment.newInstance("145");
                                case 2:
                                    return ProductsFragment.newInstance("146");
                                case 3:
                                    return ProductsFragment.newInstance("147");
                                case 4:
                                    return ProductsFragment.newInstance("148");
                            }
                        case "生活家庭":
                            tabLayout.setTabMode(TabLayout.MODE_FIXED);
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("149");
                                case 1:
                                    return ProductsFragment.newInstance("150");
                                case 2:
                                    return ProductsFragment.newInstance("151");
                                case 3:
                                    return ProductsFragment.newInstance("152");
                            }
                        case "知識理財":
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("153");
                                case 1:
                                    return ProductsFragment.newInstance("154");
                                case 2:
                                    return ProductsFragment.newInstance("155");
                                case 3:
                                    return ProductsFragment.newInstance("156");
                                case 4:
                                    return ProductsFragment.newInstance("157");
                                case 5:
                                    return ProductsFragment.newInstance("158");
                                case 6:
                                    return ProductsFragment.newInstance("159");
                            }
                        case "文具用品":
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("160");
                                case 1:
                                    return ProductsFragment.newInstance("161");
                                case 2:
                                    return ProductsFragment.newInstance("162");
                                case 3:
                                    return ProductsFragment.newInstance("163");
                                case 4:
                                    return ProductsFragment.newInstance("164");
                            }
                    }

                case "appliance":
                    tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                    switch (MidddleCategoryName){
                        case "大家電":
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("88");
                                case 1:
                                    return ProductsFragment.newInstance("89");
                                case 2:
                                    return ProductsFragment.newInstance("90");
                                case 3:
                                    return ProductsFragment.newInstance("91");
                                case 4:
                                    return ProductsFragment.newInstance("92");
                                case 5:
                                    return ProductsFragment.newInstance("93");
                                case 6:
                                    return ProductsFragment.newInstance("94");
                            }
                        case "生活家電":
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("95");
                                case 1:
                                    return ProductsFragment.newInstance("96");
                                case 2:
                                    return ProductsFragment.newInstance("97");
                                case 3:
                                    return ProductsFragment.newInstance("98");
                                case 4:
                                    return ProductsFragment.newInstance("99");
                                case 5:
                                    return ProductsFragment.newInstance("100");
                                case 6:
                                    return ProductsFragment.newInstance("101");
                                case 7:
                                    return ProductsFragment.newInstance("102");
                                case 8:
                                    return ProductsFragment.newInstance("103");
                                case 9:
                                    return ProductsFragment.newInstance("104");
                            }
                        case "視聽娛樂":
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("105");
                                case 1:
                                    return ProductsFragment.newInstance("106");
                                case 2:
                                    return ProductsFragment.newInstance("107");
                                case 3:
                                    return ProductsFragment.newInstance("108");
                                case 4:
                                    return ProductsFragment.newInstance("109");
                                case 5:
                                    return ProductsFragment.newInstance("110");
                                case 6:
                                    return ProductsFragment.newInstance("112");
                                case 7:
                                    return ProductsFragment.newInstance("111");
                            }
                    }

                case "life":
                    tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                    switch (MidddleCategoryName){
                        case "餐廚用品":
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("113");
                                case 1:
                                    return ProductsFragment.newInstance("114");
                                case 2:
                                    return ProductsFragment.newInstance("115");
                                case 3:
                                    return ProductsFragment.newInstance("116");
                                case 4:
                                    return ProductsFragment.newInstance("117");
                                case 5:
                                    return ProductsFragment.newInstance("118");
                                case 6:
                                    return ProductsFragment.newInstance("119");
                            }
                        case "居家裝修":
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("120");
                                case 1:
                                    return ProductsFragment.newInstance("121");
                                case 2:
                                    return ProductsFragment.newInstance("122");
                                case 3:
                                    return ProductsFragment.newInstance("123");
                                case 4:
                                    return ProductsFragment.newInstance("124");
                            }
                        case "生活百貨":
                            switch (position) {
                                case 0:
                                    return ProductsFragment.newInstance("125");
                                case 1:
                                    return ProductsFragment.newInstance("126");
                                case 2:
                                    return ProductsFragment.newInstance("127");
                                case 3:
                                    return ProductsFragment.newInstance("128");
                                case 4:
                                    return ProductsFragment.newInstance("129");
                            }
                    }

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            switch (LargeCategoryName) {
                case "womenclothes":
                    switch (MidddleCategoryName) {
                        case "女裝":
                            return 5;
                        case "女仕配件":
                            return 3;
                        case "女鞋襪":
                            return 4;
                    }
                case "cosmetics":
                    switch (MidddleCategoryName) {
                        case "保養":
                            return 5;
                        case "沐浴清潔":
                            return 4;
                        case "彩妝":
                            return 5;
                    }
                case "computer":
                    switch (MidddleCategoryName) {
                        case "手機平板":
                            return 7;
                        case "桌上電腦":
                            return 6;
                        case "筆記型電腦":
                            return 6;
                        case "電玩":
                            return 5;
                        case "相機":
                            return 4;
                        case "電腦零組件":
                            return 5;
                        case "手機周邊":
                            return 4;
                        case "資訊周邊":
                            return 7;
                        case "智慧穿戴":
                            return 3;
                    }
                case "manclothes":
                    switch (MidddleCategoryName) {
                        case "男裝":
                            return 4;
                        case "男仕配件":
                            return 3;
                        case "男鞋襪":
                            return 4;
                    }
                case "sports":
                    switch (MidddleCategoryName) {
                        case "機能服飾":
                            return 4;
                        case "運動健身":
                            return 5;
                        case "舒壓按摩":
                            return 3;
                        case "玩具":
                            return 5;
                    }
                case "books":
                    switch (MidddleCategoryName) {
                        case "文學休閒":
                            return 5;
                        case "生活家庭":
                            return 4;
                        case "知識理財":
                            return 7;
                        case "文具用品":
                            return 5;
                    }
                case "appliance":
                    switch (MidddleCategoryName) {
                        case "大家電":
                            return 7;
                        case "生活家電":
                            return 4;
                        case "視聽娛樂":
                            return 8;
                    }
                case "life":
                    switch (MidddleCategoryName) {
                        case "餐廚用品":
                            return 7;
                        case "居家裝修":
                            return 5;
                        case "生活百貨":
                            return 5;
                    }

                default:
                    return 0;
            }
        }

        public CharSequence getPageTitle(int position) {
            switch (LargeCategoryName) {
                case "womenclothes":
                    switch (MidddleCategoryName) {
                        case "女裝":
                            switch (position) {
                                case 0:
                                    return "女上衣";
                                case 1:
                                    return "褲/裙";
                                case 2:
                                    return "大衣外套";
                                case 3:
                                    return "洋裝禮服";
                                case 4:
                                    return "貼身衣物";
                            }
                        case "女仕配件":
                            switch (position) {
                                case 0:
                                    return "女皮飾";
                                case 1:
                                    return "女包";
                                case 2:
                                    return "女配件";
                            }
                        case "女鞋襪":
                            switch (position) {
                                case 0:
                                    return "女運動鞋";
                                case 1:
                                    return "女休閒鞋";
                                case 2:
                                    return "高跟鞋";
                                case 3:
                                    return "女襪子";
                            }

                    }

                case "cosmetics":
                    switch (MidddleCategoryName) {
                        case "保養":
                            switch (position) {
                                case 0:
                                    return "臉部保養";
                                case 1:
                                    return "眼部保養";
                                case 2:
                                    return "唇部保養";
                                case 3:
                                    return "手足保養";
                                case 4:
                                    return "身體保養";
                            }
                        case "沐浴清潔":
                            switch (position) {
                                case 0:
                                    return "洗/護/潤髮";
                                case 1:
                                    return "肌膚清潔";
                                case 2:
                                    return "臉部清潔";
                                case 3:
                                    return "口腔清潔";
                            }
                        case "彩妝":
                            switch (position) {
                                case 0:
                                    return "臉部彩妝";
                                case 1:
                                    return "眼眉彩妝";
                                case 2:
                                    return "唇部彩妝";
                                case 3:
                                    return "指甲彩繪";
                                case 4:
                                    return "美妝用品";
                            }
                    }

                case "computer":
                    switch (MidddleCategoryName) {
                        case "手機平板":
                            switch (position) {
                                case 0:
                                    return "APPLE";
                                case 1:
                                    return "SAMSUNG";
                                case 2:
                                    return "SONY";
                                case 3:
                                    return "OPPO";
                                case 4:
                                    return "ASUS";
                                case 5:
                                    return "HTC";
                                case 6:
                                    return "其他";
                            }
                        case "桌上電腦":
                            switch (position) {
                                case 0:
                                    return "Acer";
                                case 1:
                                    return "ASUS";
                                case 2:
                                    return "MSI";
                                case 3:
                                    return "Lenovo";
                                case 4:
                                    return "HP";
                                case 5:
                                    return "其他";
                            }
                        case "筆記型電腦":
                            switch (position) {
                                case 0:
                                    return "Acer";
                                case 1:
                                    return "ASUS";
                                case 2:
                                    return "MSI";
                                case 3:
                                    return "Lenovo";
                                case 4:
                                    return "HP";
                                case 5:
                                    return "其他";
                            }
                        case "電玩":
                            switch (position) {
                                case 0:
                                    return "任天堂";
                                case 1:
                                    return "PlayStation";
                                case 2:
                                    return "XBOX";
                                case 3:
                                    return "電腦遊戲";
                                case 4:
                                    return "其他電玩";
                            }
                        case "相機":
                            switch (position) {
                                case 0:
                                    return "單眼相機";
                                case 1:
                                    return "數位相機";
                                case 2:
                                    return "攝影機";
                                case 3:
                                    return "相機周邊";
                            }
                        case "電腦零組件":
                            switch (position) {
                                case 0:
                                    return "顯示卡";
                                case 1:
                                    return "記憶體";
                                case 2:
                                    return "電源供應器";
                                case 3:
                                    return "硬碟/隨身碟";
                                case 4:
                                    return "電腦螢幕";
                            }
                        case "手機周邊":
                            switch (position) {
                                case 0:
                                    return "手機殼/套";
                                case 1:
                                    return "保護貼";
                                case 2:
                                    return "充電周邊";
                                case 3:
                                    return "Apple周邊";
                            }
                        case "資訊周邊":
                            switch (position) {
                                case 0:
                                    return "耳機/喇叭";
                                case 1:
                                    return "滑鼠/鍵盤";
                                case 2:
                                    return "電腦軟體";
                                case 3:
                                    return "繪圖/手寫板";
                                case 4:
                                    return "網路設備";
                                case 5:
                                    return "印表機";
                                case 6:
                                    return "其他周邊";
                            }
                        case "智慧穿戴":
                            switch (position) {
                                case 0:
                                    return "手環/手錶";
                                case 1:
                                    return "穿戴周邊";
                                case 2:
                                    return "其他周邊";
                            }
                    }
                case "manclothes":
                    switch (MidddleCategoryName) {
                        case "男裝":
                            switch (position) {
                                case 0:
                                    return "上衣";
                                case 1:
                                    return "褲子";
                                case 2:
                                    return "大衣外套";
                                case 3:
                                    return "辦公服飾";
                            }
                        case "男仕配件":
                            switch (position) {
                                case 0:
                                    return "男皮飾";
                                case 1:
                                    return "男包";
                                case 2:
                                    return "男配件";
                            }
                        case "男鞋襪":
                            switch (position) {
                                case 0:
                                    return "男運動鞋";
                                case 1:
                                    return "男休閒鞋";
                                case 2:
                                    return "男皮鞋";
                                case 3:
                                    return "男襪子";
                            }
                    }

                case "sports":
                    switch (MidddleCategoryName) {
                        case "機能服飾":
                            switch (position) {
                                case 0:
                                    return "機能男裝";
                                case 1:
                                    return "機能女裝";
                                case 2:
                                    return "機能外套";
                                case 3:
                                    return "機能配件";
                            }
                        case "運動健身":
                            switch (position) {
                                case 0:
                                    return "健身器材";
                                case 1:
                                    return "瑜珈用品";
                                case 2:
                                    return "球類運動";
                                case 3:
                                    return "水上運動";
                                case 4:
                                    return "戶外用品";
                            }
                        case "舒壓按摩":
                            switch (position) {
                                case 0:
                                    return "紓壓小物";
                                case 1:
                                    return "按摩器材";
                                case 2:
                                    return "按摩椅";
                            }
                        case "玩具":
                            switch (position) {
                                case 0:
                                    return "積木/拼圖";
                                case 1:
                                    return "桌遊";
                                case 2:
                                    return "玩偶";
                                case 3:
                                    return "其他玩具";
                                case 4:
                                    return "模型/公仔/動漫";
                            }

                    }

                case "books":
                    switch (MidddleCategoryName){
                        case "文學休閒":
                            switch (position) {
                                case 0:
                                    return "文學小說";
                                case 1:
                                    return "雜誌";
                                case 2:
                                    return "動漫/輕小說";
                                case 3:
                                    return "童書/繪本";
                                case 4:
                                    return "旅遊";
                            }
                        case "生活家庭":
                            switch (position) {
                                case 0:
                                    return "飲食保健";
                                case 1:
                                    return "料理烹調";
                                case 2:
                                    return "親子家庭";
                                case 3:
                                    return "生活風格";
                            }
                        case "知識理財":
                            switch (position) {
                                case 0:
                                    return "人文歷史";
                                case 1:
                                    return "商業理財";
                                case 2:
                                    return "自然科普";
                                case 3:
                                    return "語言學習";
                                case 4:
                                    return "心理勵志";
                                case 5:
                                    return "電腦資訊";
                                case 6:
                                    return "考試用書";
                            }
                        case "文具用品":
                            switch (position) {
                                case 0:
                                    return "書寫用具";
                                case 1:
                                    return "紙製品";
                                case 2:
                                    return "辦公用品";
                                case 3:
                                    return "修正系列";
                                case 4:
                                    return "美術/書法";
                            }
                    }

                case "appliance":
                    switch (MidddleCategoryName){
                        case "大家電":
                            switch (position) {
                                case 0:
                                    return "冰箱/冰櫃";
                                case 1:
                                    return "洗/乾衣機";
                                case 2:
                                    return "冷/暖氣";
                                case 3:
                                    return "清淨機";
                                case 4:
                                    return "除濕機";
                                case 5:
                                    return "吸塵器";
                                case 6:
                                    return "風扇";
                            }
                        case "生活家電":
                            switch (position) {
                                case 0:
                                    return "電(子)鍋";
                                case 1:
                                    return "咖啡機";
                                case 2:
                                    return "烤箱/微波爐";
                                case 3:
                                    return "吹風機";
                                case 4:
                                    return "刮鬍刀";
                                case 5:
                                    return "烘/洗碗機";
                                case 6:
                                    return "果汁/豆漿機";
                                case 7:
                                    return "電磁爐";
                                case 8:
                                    return "飲/淨水機";
                                case 9:
                                    return "其他";
                            }
                        case "視聽娛樂":
                            switch (position) {
                                case 0:
                                    return "電視";
                                case 1:
                                    return "投影機";
                                case 2:
                                    return "音響";
                                case 3:
                                    return "DVD/BD撥放器";
                                case 4:
                                    return "電視盒/棒";
                                case 5:
                                    return "卡拉OK設備";
                                case 6:
                                    return "影片/多媒體";
                                case 7:
                                    return "周邊";
                            }
                    }

                case "life":
                    switch (MidddleCategoryName){
                        case "餐廚用品":
                            switch (position) {
                                case 0:
                                    return "鍋具";
                                case 1:
                                    return "刀具";
                                case 2:
                                    return "烘培用品";
                                case 3:
                                    return "碗盤筷匙";
                                case 4:
                                    return "杯瓶壺";
                                case 5:
                                    return "保鮮器材";
                                case 6:
                                    return "咖啡用品";
                            }
                        case "居家裝修":
                            switch (position) {
                                case 0:
                                    return "燈具";
                                case 1:
                                    return "電動工具";
                                case 2:
                                    return "五金器材";
                                case 3:
                                    return "園藝";
                                case 4:
                                    return "裝潢耗材";
                            }
                        case "生活百貨":
                            switch (position) {
                                case 0:
                                    return "衛浴用品";
                                case 1:
                                    return "收納相關";
                                case 2:
                                    return "雨具";
                                case 3:
                                    return "清潔用品";
                                case 4:
                                    return "其他居家生活";
                            }
                    }
                default:
                    return null;
            }
        }
    }
}
