package com.example.chris.goodbuy2.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.chris.goodbuy2.Adapter.RatingDetailAdapter;
import com.example.chris.goodbuy2.Model.Product_item_detail;
import com.example.chris.goodbuy2.R;

public class RatingActivity extends AppCompatActivity {

    private RecyclerView rating_recycleview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        //----------------------- ActionBar 設定 -----------------------//
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("商品評價");

        Product_item_detail product = (Product_item_detail)getIntent().getSerializableExtra("rating_product");


        RatingDetailAdapter ratingDetailAdapter = new RatingDetailAdapter(this, product);
        rating_recycleview = findViewById(R.id.rating_recycleview_id);
        rating_recycleview.setHasFixedSize(true);
        rating_recycleview.setLayoutManager(new LinearLayoutManager(this));
        rating_recycleview.setAdapter(ratingDetailAdapter);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
