package com.example.chris.goodbuy2.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chris.goodbuy2.Model.Product_item_detail;
import com.example.chris.goodbuy2.Model.ShoppingCart_product_item;
import com.example.chris.goodbuy2.R;

import java.util.List;
import java.util.logging.Handler;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.MyViewHolder> {

    private Context context;
    private List<ShoppingCart_product_item> list;
    private LayoutInflater layoutInflater;
    private CheckBox seller_checkbox;
    private ShoppingCartProductsAdapter adapter;
    LinearLayout linearLayout;
    private String modifyShopppingCartURL;

    public ShoppingCartAdapter(Context context, List<ShoppingCart_product_item> list, String modifyShopppingCartURL) {
        this.context = context;
        this.list = list;
        this.modifyShopppingCartURL = modifyShopppingCartURL;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.shoppint_cart_layout, null);

        return new MyViewHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {
        linearLayout = myViewHolder.linearLayout;
        TextView seller_textview_id = linearLayout.findViewById(R.id.seller_textview_id);
        RecyclerView recyclerView = linearLayout.findViewById(R.id.seller_products_recyclerview_id);
        seller_textview_id.setText(list.get(position).getSeller_name());
        //seller_checkbox = linearLayout.findViewById(R.id.seller_checkbox);

        //seller_checkbox.setChecked(false);
        //seller_checkbox.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        adapter = new ShoppingCartProductsAdapter(context, list, position, modifyShopppingCartURL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        // Log.v("dddddddd", "我是adapter");

//        for (int i = 0; i < list.get(position).getIsCheckLists().size(); i++) {
//            if (Integer.valueOf(list.get(position).getIsCheckLists().get(i)) == 1) {
//                seller_checkbox.setChecked(true);
//            } else {
//                seller_checkbox.setChecked(false);
//            }
//        }


        //seller_checkbox.setChecked(list.get(position).isCheck());


        adapter.setListener(new ShoppingCartProductsAdapter.ShopCallBackListener() {
            @Override
            public void callBack() {
                //從商品適配裡調回來，回給 activity，activity 計算價格和數量
                if (mshopCallBackListener != null) {
                    mshopCallBackListener.callBack(list);
                }

//                //建立一個臨時的標誌位，用來記錄現在點選的狀態
//                boolean isAllChecked = true;
//                for (String string : list.get(position).getIsCheckLists()) {
//                    if (Integer.valueOf(string) == 0) {
//                        //只要有一個商品位選中，標誌位設定成false，並且跳出迴圈
//                        isAllChecked = false;
//                        break;
//                    }
//                }
//
//                //重新整理商家的狀態
//                seller_checkbox.setChecked(isAllChecked);
//                list.get(position).setIsCheck(isAllChecked);
//                for (int i = 0; i < list.get(position).getIsCheckLists().size(); i++) {
//                    if (isAllChecked) {
//                        list.get(position).setIsCheckLists(i, "1");
//                    } else {
//                        list.get(position).setIsCheckLists(i, "0");
//                    }
//                }
            }

            @Override
            public void refreshActivity() {
                if (mshopCallBackListener != null) {
                    mshopCallBackListener.refreshActivity();
                }
            }
        });
        //監聽checkBox的點選事件
        //目的是改變旗下所有商品的選中狀態
//        final boolean ischecked = false;
//        seller_checkbox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //首先改變自己的標誌位
//                //Toast.makeText(context, "拉~" + seller_checkbox.isChecked(), Toast.LENGTH_SHORT).show();
////                list.get(position).setIsCheck(seller_checkbox.isChecked());
////                //呼叫產品adapter的方法，用來全選或反選
////                adapter.selectOrRemoveAll(seller_checkbox.isChecked());
//                if (ischecked) {
//                    list.get(position).setIsCheck(true);
//                    //呼叫產品adapter的方法，用來全選或反選
//                    adapter.selectOrRemoveAll(true);
//                } else {
//                    list.get(position).setIsCheck(false);
//                    //呼叫產品adapter的方法，用來全選或反選
//                    adapter.selectOrRemoveAll(false);
//                }
//
//            }
//        });
//        seller_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                list.get(position).setIsCheck(isChecked);
//                adapter.selectOrRemoveAll(isChecked);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        if (list.size() == 0){
            return 0;
        }else {
            return list.size();
        }
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.seller_checkbox:
//                if (seller_checkbox.isChecked()){
//                    Toast.makeText(context, "我是seller_checkbox, 已按", Toast.LENGTH_SHORT).show();
//                    //adapter.refresh(seller_checkbox.isChecked());
//                }else {
//                    Toast.makeText(context, "我是seller_checkbox, 已取消", Toast.LENGTH_SHORT).show();
//                    //adapter.refresh(seller_checkbox.isChecked());
//
//                }
//
//                //adapter.refresh(seller_checkbox.isChecked());
//                break;
//        }
//    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView;
        }
    }
//    public LinearLayout getAdapterView(){
//        return linearLayout;
//    }

    private ShopCallBackListener mshopCallBackListener;

    public void setListener(ShopCallBackListener listener) {
        this.mshopCallBackListener = listener;
    }

    public interface ShopCallBackListener {
        void callBack(List<ShoppingCart_product_item> list);
        void refreshActivity();
    }
}
