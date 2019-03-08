package com.example.chris.goodbuy2.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.chris.goodbuy2.Activity.HomeActivity;
import com.example.chris.goodbuy2.Activity.ItemProductActivity;
import com.example.chris.goodbuy2.Model.Product_item;
import com.example.chris.goodbuy2.Model.ShoppingCart_product_item;
import com.example.chris.goodbuy2.R;
import com.example.chris.goodbuy2.Service.QueryTask;
import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class ShoppingCartProductsAdapter extends RecyclerView.Adapter<ShoppingCartProductsAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<ShoppingCart_product_item> list;
    private LayoutInflater layoutInflater;
    private int position01, currentposition;
    private Intent intent;
    private Product_item product_item;
    private CheckBox checkBox;
    private boolean isCheckedright;
    private TextView shopping_cart_product_quantities_textview;
    private Dialog dialog;
    private String modifyShopppingCartURL;
    private SharedPreferences login_check;

    public ShoppingCartProductsAdapter(Context context, List<ShoppingCart_product_item> list, int position, String modifyShopppingCartURL) {
        this.context = context;
        this.list = list;
        this.position01 = position;
        this.modifyShopppingCartURL = modifyShopppingCartURL;
        layoutInflater = LayoutInflater.from(context);
        intent = new Intent(context, ItemProductActivity.class);
        product_item = new Product_item();
        isCheckedright = false;
        login_check = context.getSharedPreferences("login", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.shopping_cart_products_layout, null);

        return new MyViewHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {
        Log.v("dddddddd", "我是adapter");
        LinearLayout linearLayout = myViewHolder.linearLayout;
        TextView shopping_cart_product_name = linearLayout.findViewById(R.id.shopping_cart_product_name);
        TextView shopping_cart_product_price = linearLayout.findViewById(R.id.shopping_cart_product_price);
        TextView shopping_cart_specification = linearLayout.findViewById(R.id.shopping_cart_specification);
        ImageView shopping_cart_product_image = linearLayout.findViewById(R.id.shopping_cart_product_image);
        ImageView trash_can_imageview = linearLayout.findViewById(R.id.trash_can_imageview);
        shopping_cart_product_quantities_textview = linearLayout.findViewById(R.id.shopping_cart_product_quantities);
        checkBox = linearLayout.findViewById(R.id.shopping_cart_products_checkbox);

        //設定購買數量
        shopping_cart_product_quantities_textview.setText("購買數量: " + list.get(position01).getShoppingCart_product_quantities().get(position));

        //垃圾桶
        trash_can_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "垃圾桶: " + list.get(position01).getShoppintCart_product_names().get(position), Toast.LENGTH_LONG).show();

                // dialog
                dialog = new Dialog(context);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false); //無法利用回上一頁按鈕離開對話框
                dialog.setContentView(R.layout.logout_layout);
                Button logout_btn = dialog.findViewById(R.id.logout_btn);
                Button cancel_btn = dialog.findViewById(R.id.cancel_btn);
                TextView shopping_cart_text1 = dialog.findViewById(R.id.shopping_cart_text1);
                TextView shopping_cart_text2 = dialog.findViewById(R.id.shopping_cart_text2);
                shopping_cart_text1.setText("您正在嘗試刪除此商品");
                shopping_cart_text2.setText("確定要刪除嗎?");
                logout_btn.setText("確定");
                dialog.show();
                logout_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "確定刪除", Toast.LENGTH_SHORT).show();
                        //id[0]=>product_id
                        //id[1]=>member_id
                        deleDataforShoppingCart(list.get(position01).getShoppintCart_product_ids().get(position),
                                list.get(position01).getMember_id());
                        notifyItemRemoved(position);
                        notifyDataSetChanged();
                        if (mshopCallBackListener != null) {
                            mshopCallBackListener.refreshActivity();
                        }
                        dialog.cancel();
                    }
                });

                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(context, "取消登出", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
            }
        });

        // 根據紀錄狀態，改變勾選
        if (Integer.valueOf(list.get(position01).getIsCheckLists().get(position)) == 1) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }

        //商品跟商家的有所不同，商品添加了選中改變的監聽
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //優先改變自己的狀態
                if (isChecked) {
                    list.get(position01).setIsCheckLists(position, "1");
                } else {
                    list.get(position01).setIsCheckLists(position, "0");
                }
                //回撥，目的是告訴 activity，有人選中狀態被改變
                if (mshopCallBackListener != null) {
                    mshopCallBackListener.callBack();
                }
            }
        });


        shopping_cart_product_name.setText(list.get(position01).getShoppintCart_product_names().get(position));
        shopping_cart_product_price.setText("$" + list.get(position01).getShoppingCart_product_unit_price().get(position));
        shopping_cart_specification.setText("選項: " + list.get(position01).getShoppingCart_product_speciations().get(position));

        //設置顯示加載中的等待圖片
        shopping_cart_product_image.setImageResource(R.drawable.ic_cloud_24dp);

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
                .load(list.get(position01).getShoppingCart_product_images().get(position)).into(shopping_cart_product_image);

        product_item.setProduct_id(Integer.valueOf(list.get(position01).getShoppintCart_product_ids().get(position)));
        product_item.setProduct_name(list.get(position01).getShoppintCart_product_names().get(position));

        //shopping_cart_product_image.setOnClickListener(this);

    }

    @Override
    public int getItemCount() {
        if (list.size() == 0){
            return 0;
        }else {
            return list.get(position01).getShoppintCart_product_ids().size();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shopping_cart_product_image:
                //Toast.makeText(context, "點擊圖片", Toast.LENGTH_LONG).show();
                intent.putExtra("product_item", (Serializable) product_item);
                context.startActivity(intent);
                break;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView;
        }
    }

//    public void refresh(boolean isCheckedright){
//        this.isCheckedright = isCheckedright;
//        if(list != null){
//            for(int i = 0; i < list.size(); i++){
//                notifyItemChanged(i);
//            }
//        }
//    }


//    public void selectOrRemoveAll(boolean isSelectAll){
////        for( String string : list.get(position01).getIsCheckLists()){
////            if(isSelectAll){
////                string = "1";
////            }else {
////                string = "0";
////            }
////        }
//        for (int i =0; i < list.get(position01).getIsCheckLists().size(); i++){
//            if(isSelectAll){
//                list.get(position01).setIsCheckLists(i, "1");
//            }else {
//                list.get(position01).setIsCheckLists(i, "0");
//            }
//        }
//        notifyDataSetChanged();
//    }

    private ShopCallBackListener mshopCallBackListener;

    public void setListener(ShopCallBackListener listener) {
        this.mshopCallBackListener = listener;
    }

    public interface ShopCallBackListener {
        void callBack();
        void refreshActivity();
    }

    ///////////////////刪除程式碼
    //刪除功能測試
    public void deleDataforShoppingCart(String... id) {
        //id[0]=>product_id
        //id[1]=>member_id
        JSONObject obj = new JSONObject();
        try {
            obj.put("product_id", id[0]);
            obj.put("member_id", id[1]);
            obj.put("action", "1");
            obj.put("token_check_user",login_check.getString("member_token","null"));
            String modifyIdInfo = obj.toString();
            String A = new QueryTask().execute(modifyShopppingCartURL, modifyIdInfo).get();
            Toast.makeText(context, "B000" + A, Toast.LENGTH_SHORT).show();
            Log.d("debug00_shoppingCart", "B000" + A);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("debug00_shoppingCart", e.toString());

        }
    }
}