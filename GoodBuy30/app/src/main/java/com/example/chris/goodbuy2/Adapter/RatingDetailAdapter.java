package com.example.chris.goodbuy2.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chris.goodbuy2.Model.Product_item_detail;
import com.example.chris.goodbuy2.R;

public class RatingDetailAdapter extends RecyclerView.Adapter<RatingDetailAdapter.MyViewHolder> {

    private Context context;
    private Product_item_detail product;
    private LayoutInflater inflater;

    public RatingDetailAdapter(Context context, Product_item_detail product){
        this.context = context;
        this.product = product;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.rating_detail_layout, null);
        //CardView cardView = (CardView) inflater.inflate(R.layout.rating_detail_layout, null);
        return new MyViewHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
//        CardView cardView = myViewHolder.cardView;
//        TextView member_name_textview = cardView.findViewById(R.id.member_name_textview);
//        TextView rating_content_textview = cardView.findViewById(R.id.rating_content_textview);
//        TextView member_time_textview = cardView.findViewById(R.id.member_time_textview);
//        AppCompatRatingBar member_rating = cardView.findViewById(R.id.member_rating2);
//
//        member_name_textview.setText(product.getEvaluatiion_member_name().get(position));
//        rating_content_textview.setText(product.getComment_context().get(position));
//        member_time_textview.setText(product.getComment_datetime().get(position));
//        rating_content_textview.setText(product.getComment_context().get(position));
//        member_rating.setRating(Float.valueOf(String.valueOf(product.getScore().get(position))));
        LinearLayout linearLayout = myViewHolder.linearLayout;
        TextView member_name_textview = linearLayout.findViewById(R.id.member_name_textview);
        TextView rating_content_textview = linearLayout.findViewById(R.id.rating_content_textview);
        TextView member_time_textview = linearLayout.findViewById(R.id.member_time_textview);
        AppCompatRatingBar member_rating = linearLayout.findViewById(R.id.member_rating2);

        member_name_textview.setText(product.getEvaluatiion_member_name().get(position));
        rating_content_textview.setText(product.getComment_context().get(position));
        member_time_textview.setText(product.getComment_datetime().get(position));
        member_rating.setRating(Float.valueOf(String.format("%.1f", product.getScore().get(position))));
    }

    @Override
    public int getItemCount() {
        return product.getScore().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView;
        }
    }
}
