package com.example.chris.goodbuy2.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chris.goodbuy2.R;

import java.util.ArrayList;
import java.util.List;

public class SpecificationAdapter extends RecyclerView.Adapter<SpecificationAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> list;
    private LayoutInflater layoutInflater;

    private int nowclicked = -1;

    private TextView textViewQuantity, product_edit_count;
    private ArrayList<Integer> product_quantities;
    private ImageView remove_btn, add_btn;

    public SpecificationAdapter(Context context, ArrayList<String> list, ArrayList<Integer>product_quantities,
                                TextView textViewQuantity, TextView product_edit_count,
                                ImageView remove_btn, ImageView add_btn) {
        this.context = context;
        this.list = list;
        this.textViewQuantity = textViewQuantity;
        this.product_quantities = product_quantities;
        this.product_edit_count = product_edit_count;
        this.remove_btn = remove_btn;
        this.add_btn = add_btn;
        layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        TextView textView = (TextView) layoutInflater.inflate(R.layout.specification_layout, null);

        return new MyViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {

        final TextView textView = myViewHolder.textView;
        if(product_quantities.get(position) > 0){
            textView.setText(list.get(position));
        }


        final int sdk = android.os.Build.VERSION.SDK_INT;
        int quantities = product_quantities.get(position);

        if (nowclicked == position) {
            textViewQuantity.setText(String.valueOf(quantities));
            remove_btn.setVisibility(View.INVISIBLE);
            if(quantities == 1){
                product_edit_count.setText("1");
                add_btn.setVisibility(View.INVISIBLE);
                remove_btn.setVisibility(View.INVISIBLE);
            }else if(Integer.valueOf(product_edit_count.getText().toString()) > quantities){
                product_edit_count.setText("1");
                remove_btn.setVisibility(View.VISIBLE);
                add_btn.setVisibility(View.INVISIBLE);
            }else if(Integer.valueOf(product_edit_count.getText().toString()) == quantities) {
                add_btn.setVisibility(View.INVISIBLE);
                remove_btn.setVisibility(View.VISIBLE);
            }else {
                add_btn.setVisibility(View.VISIBLE);
            }

            textView.setTextColor(context.getResources().getColor(R.color.white));
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                textView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.specification_clicked_layout));
            } else {
                textView.setBackground(ContextCompat.getDrawable(context, R.drawable.specification_clicked_layout));
            }
        } else {
            textView.setTextColor(context.getResources().getColor(R.color.green));
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                textView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.specification_layout));
            } else {
                textView.setBackground(ContextCompat.getDrawable(context, R.drawable.specification_layout));
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    nowclicked = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }
    }

    public int getSelectedPosition(){
        return nowclicked;
    }
}
