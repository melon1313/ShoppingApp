package com.example.chris.goodbuy2.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.chris.goodbuy2.R;
import com.example.chris.goodbuy2.Task.item_Product_Image_Load_Task;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class Item_Product_Image_Adapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<String> images;

    public Item_Product_Image_Adapter(Context context, ArrayList<String> images) {
        this.context = context;
        this.images = images;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if(images.size() > 1){
            return Integer.MAX_VALUE;
        }else {
            return images.size();
        }

    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    //渲染每一頁數據

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View layout = layoutInflater.inflate(R.layout.item_product_viewpager, null);
        ImageView imageView = (ImageView)layout.findViewById(R.id.item_iv);

        //設置顯示加載中的等待圖片
        imageView.setImageResource(R.drawable.ic_cloud_24dp);

//        //異步任務加載圖片
//        //TODO
//        item_Product_Image_Load_Task item_product_image_load_task = new item_Product_Image_Load_Task(context, imageView);
//        item_product_image_load_task.execute(images[position % images.length]);
        //imageView.setImageResource(images[position % images.length]);
        // 載入圖片錯誤、加載程序
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_cloud_24dp);
        requestOptions.error(R.drawable.ic_error_black_24dp);

        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(images.get(position % images.size())).into(imageView);

        //添加到viewpager裡面
        container.addView(layout);

        return layout;
    }
}
