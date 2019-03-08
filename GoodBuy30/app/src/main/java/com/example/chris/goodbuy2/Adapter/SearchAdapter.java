package com.example.chris.goodbuy2.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chris.goodbuy2.Model.Product_item;
import com.example.chris.goodbuy2.R;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private Context context;
    private List<Product_item> list;
    private LayoutInflater layoutInflater;

    public SearchAdapter(Context context, List<Product_item> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.search_layout, null);

        return new MyViewHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        LinearLayout linearLayout = myViewHolder.linearLayout;
        TextView textView = linearLayout.findViewById(R.id.search_text);
        textView.setText(list.get(position).getProduct_name());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(ArrayList<Product_item> filterList) {
         list = filterList;
         notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = (LinearLayout)itemView;
        }
    }
}
