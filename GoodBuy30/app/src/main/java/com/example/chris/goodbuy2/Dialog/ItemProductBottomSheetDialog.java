package com.example.chris.goodbuy2.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.chris.goodbuy2.Activity.ItemProductActivity;
import com.example.chris.goodbuy2.Activity.ShoppingCartActivity;
import com.example.chris.goodbuy2.Adapter.SpecificationAdapter;
import com.example.chris.goodbuy2.R;
import com.example.chris.goodbuy2.Service.QueryTask;
import com.example.chris.goodbuy2.SetView.GridSpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class ItemProductBottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener {
    private BottomSheetListener mlistener;

    private int product_count;
    private TextView product_edit_count;
    private TextView product_quantity_textview;
    private Button shopping_button;
    private ImageView add_btn, remove_btn;

    private ArrayList<Integer> product_quantity;
    private ArrayList<String> specification;

    private String image,shoppingCartURL;

    View view;
    private BottomSheetBehavior behavior;
    private boolean isBuyDirect;
    private SpecificationAdapter specificationAdapter;
    private SharedPreferences login_check;

    private String price;
    public static ItemProductBottomSheetDialog newInstance(ArrayList<Integer> product_quantity,
                                                           ArrayList<String> specification, String image,
                                                           boolean isBuyDirect, String price) {
        ItemProductBottomSheetDialog itemProductBottomSheetDialog = new ItemProductBottomSheetDialog();
        itemProductBottomSheetDialog.product_quantity = product_quantity;
        itemProductBottomSheetDialog.specification = specification;
        itemProductBottomSheetDialog.image = image;
        itemProductBottomSheetDialog.isBuyDirect = isBuyDirect;
        itemProductBottomSheetDialog.price = price;
        return itemProductBottomSheetDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog)super.onCreateDialog(savedInstanceState);
        view = View.inflate(getContext(), R.layout.specification_bottom_sheet_layout, null);
        dialog.setContentView(view);

        behavior = BottomSheetBehavior.from((View)view.getParent());

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //cjView view = inflater.inflate(R.layout.specification_bottom_sheet_layout, container, false);
//
//        Button button01 = view.findViewById(R.id.button01);
//        Button button02 = view.findViewById(R.id.button02);
//        button01.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mlistener.onButtonClicked("Button1 clicked");
//                dismiss();
//            }
//        });
//
//        button02.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mlistener.onButtonClicked("Button2 clicked");
//                dismiss();
//            }
//        });
        //------ 導覽列
        //加減按鈕
        add_btn = view.findViewById(R.id.add_imageview_id);
        remove_btn = view.findViewById(R.id.remove_imageview_id);

        product_edit_count = view.findViewById(R.id.product_edit_count);
        add_btn.setOnClickListener(this);
        remove_btn.setOnClickListener(this);
        product_count = 1;

        //商品價格
        TextView price111 = view.findViewById(R.id.price111);
        price111.setText("$" + price);

        //商品圖片
        ImageView imageView = view.findViewById(R.id.bottomsheet_imageview);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_cloud_24dp);
        requestOptions.error(R.drawable.ic_error_black_24dp);
        Glide.with(getActivity())
                .setDefaultRequestOptions(requestOptions)
                .load(image).into(imageView);

        //商品數量
        product_quantity_textview = view.findViewById(R.id.product_quantity);
        product_quantity_textview.setText( String.valueOf(product_quantity.get(0)));


        //商品規格
        RecyclerView recyclerView = view.findViewById(R.id.specification_recycleview);
        specificationAdapter = new SpecificationAdapter(
                getActivity(), specification, product_quantity,
                product_quantity_textview, product_edit_count,
                remove_btn, add_btn);
        recyclerView.setAdapter(specificationAdapter);

        int spanCount = 3;
        int spacing = 10;
        boolean includeEdge = false;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        //購物按鈕
        shopping_button = view.findViewById(R.id.shopping_button);
        if (isBuyDirect){
            shopping_button.setText("直接購買");
        }else {
            shopping_button.setText("加入購物車");
        }
        shopping_button.setOnClickListener(this);
        //購物車相關資料
        shoppingCartURL =getResources().getString(R.string.localDataBase)+"update_shoppingCart.php";
        login_check =getActivity().getSharedPreferences("login",Context.MODE_PRIVATE);

        return null;
    }

    @Override
    public void onClick(View v) {
        product_count = Integer.valueOf(product_edit_count.getText().toString());
        switch (v.getId()) {
            // 增加按鈕
            case R.id.add_imageview_id:
                if (product_count < Integer.valueOf(product_quantity_textview.getText().toString())){
                    product_count++;
                    product_edit_count.setText(String.valueOf(product_count));
                    remove_btn.setVisibility(View.VISIBLE);
                    if(product_count == Integer.valueOf(product_quantity_textview.getText().toString())){
                        add_btn.setVisibility(View.INVISIBLE);
                    }
                }else {
                    add_btn.setVisibility(View.INVISIBLE);
                }
                Log.v("bbbbb", "product_count: "+ product_count);
                break;
                // 減少按鈕
            case R.id.remove_imageview_id:
                if (product_count > 1) {
                    product_count--;
                    product_edit_count.setText(String.valueOf(product_count));
                    add_btn.setVisibility(View.VISIBLE);
                    if (product_count == 1){
                        remove_btn.setVisibility(View.INVISIBLE);
                    }
                }else{
                    remove_btn.setVisibility(View.INVISIBLE);
                }
                break;
                // 購物按鈕
            case R.id.shopping_button:
                if(product_count == 0){
                    Toast.makeText(getActivity(), "已加入購物車", Toast.LENGTH_SHORT).show();
                }else{
                    product_count--;
                    sendDataToDataBase(isBuyDirect);
                }
                break;
        }

    }

    private void sendDataToDataBase(boolean isBuyDirect) {
        int selectedPosition = specificationAdapter.getSelectedPosition();
        if(selectedPosition == -1){
            Toast.makeText(getContext(), "請選擇商品規格", Toast.LENGTH_SHORT).show();
        }else {
            String selectedQuantity = product_edit_count.getText().toString();
            if(isBuyDirect){
                Add_shoppingCart(selectedQuantity);
//                Toast.makeText(getContext(), "這是\"直接購買\"的位置 : " + selectedPosition +
//                                "\n這是商品購買數量 :" + selectedQuantity, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ShoppingCartActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(getContext(), "已加入購物車", Toast.LENGTH_SHORT).show();
                Add_shoppingCart(selectedQuantity);

            }
            dismiss();
        }
    }

    public interface BottomSheetListener {
        void onButtonClicked(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mlistener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            Log.v("zzz", String.valueOf(e));
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }
// 新增至購物車//
    private void Add_shoppingCart(String Quantity)
    {
        JSONObject obj =new JSONObject();
        try {
            obj.put("member_id",login_check.getString("member_id","Null"));
            obj.put("token_check_user",login_check.getString("member_token","null"));
            obj.put("quantity",Quantity);
            obj.put("product_id",login_check.getString("ID","null"));
            obj.put("action",3);
        String strobj   =  obj.toString();
        String result = new QueryTask().execute(shoppingCartURL,strobj).get();
        //若正確跑完result會寫修改成功
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
