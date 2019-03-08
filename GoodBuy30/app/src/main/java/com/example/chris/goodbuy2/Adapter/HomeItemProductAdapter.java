package com.example.chris.goodbuy2.Adapter;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.chris.goodbuy2.R;

public class HomeItemProductAdapter extends RecyclerView.Adapter<HomeItemProductAdapter.MyViewHolder> {

    private String[] title;
    //    private int[] images;
    private String[] images;
    private int[] productId;
    private Context context;

    public HomeItemProductAdapter(Context context, String[] title, String[] images, int[] productId) {
        this.title = title;
        this.images = images;
        this.productId = productId;
        this.context = context;
    }

//    public HomeItemProductAdapter(String[] title, int[] images, String[] productId) {
//        this.title = title;
//        this.images = images;
//        this.productId = productId;
//    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardView cardview = (CardView) LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.home_item_product_layout, viewGroup, false);

        return new MyViewHolder(cardview);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {
        CardView cardView = myViewHolder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.info_image);
        TextView textView = (TextView) cardView.findViewById(R.id.info_text);
//        Drawable drawable = ContextCompat.getDrawable(cardView.getContext(), images[position]);
//        imageView.setImageDrawable(drawable);
        textView.setText(title[position]);

        // 載入圖片錯誤、加載程序
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_cloud_24dp);
        requestOptions.error(R.drawable.ic_error_black_24dp);

        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(images[position]).into(imageView);

        cardView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.v("vvv", " " + position + " " + productId[position]);
            }
        });

    }

    @Override
    public int getItemCount() {
        return title.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public MyViewHolder(@NonNull CardView itemView) {
            super(itemView);
            cardView = itemView;
        }
    }
}

