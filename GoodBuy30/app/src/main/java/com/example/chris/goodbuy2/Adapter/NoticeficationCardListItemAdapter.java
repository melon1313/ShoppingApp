package com.example.chris.goodbuy2.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.chris.goodbuy2.Model.Notice_item;
import com.example.chris.goodbuy2.R;

import java.util.ArrayList;
import java.util.List;

public class NoticeficationCardListItemAdapter extends RecyclerView.Adapter<NoticeficationCardListItemAdapter.MyViewHolder> {

    private Context context;
    private List<Notice_item> list;

    public NoticeficationCardListItemAdapter(Context context, List<Notice_item> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.noticefication_card_list_item_layout,
                viewGroup, false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.name.setText(list.get(position).getTitle());
        myViewHolder.description.setText(list.get(position).getInformation());
        myViewHolder.price.setText(list.get(position).getNotice_time());

        // 載入圖片錯誤、加載程序
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_cloud_24dp);
        requestOptions.error(R.drawable.ic_error_black_24dp);

        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(list.get(position).getImages()).into(myViewHolder.thumbnail);

//        Glide.with(context).load(images[position]).error(R.drawable.ic_cloud_24dp).placeholder(R.drawable.ic_backup_black_24dp)
//                .centerCrop().fitCenter().into(myViewHolder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Notice_item item, int position) {
        list.add(position, item);
        notifyItemInserted(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, description, price;
        public ImageView thumbnail;
        public RelativeLayout viewBackground, viewForeground;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }
    }
}

