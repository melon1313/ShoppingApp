package com.example.chris.goodbuy2.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.chris.goodbuy2.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    //int[] images;
    ArrayList<String> images;
    LayoutInflater inflater;
    Context context;

    public ViewPagerAdapter(Activity mainActivity, ArrayList<String> images){
        this.context = mainActivity;
        this.images = images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
        //return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView;
        View itemview = inflater.inflate(R.layout.item, container, false);
        imageView = (ImageView)itemview.findViewById(R.id.ad_container);
        //imageView.setImageResource(images[position % images.length]);

        // 載入圖片錯誤、加載程序
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_cloud_24dp);
        requestOptions.error(R.drawable.ic_error_black_24dp);

        Glide.with(itemview.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(images.get(position % images.size())).into(imageView);

        // add item.xml to viewpager
        container.addView(itemview);

        return itemview;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}

