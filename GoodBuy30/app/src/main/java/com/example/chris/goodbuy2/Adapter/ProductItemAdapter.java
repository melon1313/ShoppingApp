package com.example.chris.goodbuy2.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.chris.goodbuy2.Activity.ItemProductActivity;
import com.example.chris.goodbuy2.Model.Product_item;
import com.example.chris.goodbuy2.R;

import java.io.Serializable;
import java.util.List;
import java.util.zip.Inflater;

public class ProductItemAdapter extends RecyclerView.Adapter<ProductItemAdapter.MyViewHolder> {


    private List<Product_item>list;
    private Context context;
    private int layoutId;
    private Intent intent;

    public ProductItemAdapter(Context context, List<Product_item> list, int layoutId) {
        this.context = context;
        this.list = list;
        this.layoutId = layoutId;
        intent = new Intent(context, ItemProductActivity.class);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardView cardView = (CardView) LayoutInflater.from(viewGroup.getContext()).
                inflate(layoutId, viewGroup, false);
        return new MyViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {
        CardView cardView = myViewHolder.cardView;
        ImageView imageView = cardView.findViewById(R.id.product_image);
        TextView title = cardView.findViewById(R.id.product_title);
        TextView price = cardView.findViewById(R.id.product_price);

        title.setText(list.get(position).getProduct_name());
        price.setText("$"+ String.valueOf(list.get(position).getProduct_price()));

        // 載入圖片錯誤、加載程序
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_cloud_24dp);
        requestOptions.error(R.drawable.ic_error_black_24dp);

        Glide.with(cardView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(list.get(position).getProduct_image()).into(imageView);

        cardView.setOnClickListener(new View.OnClickListener() {

            private Product_item product_item = list.get(position);
            @Override
            public void onClick(View v) {
                Log.v("vvv", " " + position);
                intent.putExtra("product_item", (Serializable)product_item);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
        }
    }
}
