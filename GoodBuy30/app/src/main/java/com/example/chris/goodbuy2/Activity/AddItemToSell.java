package com.example.chris.goodbuy2.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chris.goodbuy2.Data.Category_data;
import com.example.chris.goodbuy2.R;
import com.example.chris.goodbuy2.Service.QueryTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class AddItemToSell extends AppCompatActivity implements View.OnClickListener {

    private ImageView uploadPic1;
    private Spinner large,middle,small;
    private ArrayAdapter<String> large_adapter,middle_adapter,small_adapter;
    private Context context;
    private String[][] middle_select;
    private String[][][] small_select;
    private ImageButton add_btn;
    private ArrayList<EditText> edittxts_specification = new ArrayList<>(),
            edittxts_unitPrices=new ArrayList<>(),
            edittxts_quantities=new ArrayList<>();
    private ArrayList<TextView>  textViews=new ArrayList<>();
    private LinearLayout parentLinearLayout;
    private EditText ProductName_editText,description_editText,specification_editText,unitprice_editText,quantities_editText;

    //相簿/相機上傳頁面
    private Dialog Upload_choose_dialog;
    private Button upload_Camera_btn,upload_Gallery_btn,upload_CancelDailog_btn,Upload_toPHP_btn;
    private final int SelectPic_REQUST = 1,Camera_REQUST=0;
    private Bitmap bitmap1,bitmap2,bitmap3;
    private int PicNum;
    private boolean Pic2_Bool =false,Pic3_Bool=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_to_sell);
        specification_editText=findViewById(R.id.specification_editText);
        edittxts_specification.add(specification_editText);
        unitprice_editText=findViewById(R.id.price_editText);
        edittxts_unitPrices.add(unitprice_editText);
        quantities_editText=findViewById(R.id.quantity_editText);
        edittxts_quantities.add(quantities_editText);
        context =this;
        Pic2_Bool =false;
        Pic3_Bool=false;
        PicNum=0;
        initializeWidgets();
        uploadDialogWidgetsInitialize();
        //下拉式清單
        large_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Category_data.large_category);
        large_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        large=(Spinner) findViewById(R.id.large_categories);
        large.setAdapter(large_adapter);
        large.setOnItemSelectedListener(selectListener);

        middle_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Category_data.category_womenclothes);
        middle_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        middle=(Spinner) findViewById(R.id.middle_categories);
        middle.setAdapter(middle_adapter);
        middle_select=new String[][]{Category_data.category_womenclothes,Category_data.category_cosmetic,Category_data.category_3c,
                Category_data.category_menclothes,Category_data.category_sports,Category_data.category_books,
                Category_data.category_appliance,Category_data.category_life};
        middle.setOnItemSelectedListener(selectListener2);

        small_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Category_data.small_category_femaleCloth);
        small_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        small=(Spinner) findViewById(R.id.small_categories);
        small.setAdapter(small_adapter);
        small_select=new String[][][]{{Category_data.small_category_femaleCloth,Category_data.small_category_femaleAccessories,Category_data.small_category_femaleShoes},
                {Category_data.small_category_Cosmetic,Category_data.small_category_bodycare,Category_data.small_category_bodyclean},
                {Category_data.small_category_mobile,Category_data.small_category_desktop,Category_data.small_category_laptop,
                        Category_data.small_category_videogame,Category_data.small_category_camera,Category_data.small_category_computerAccessories,
                        Category_data.small_category_mobileAcessories,Category_data.small_category_techAccessories,Category_data.small_category_smartWear},
                {Category_data.small_category_maleCloth,Category_data.small_category_maleAccessories,Category_data.small_category_maleShoes},
                {Category_data.small_category_sportsCloth,Category_data.small_category_sportsAccessories,Category_data.small_category_massage,
                        Category_data.small_category_toy},{Category_data.small_category_literature,Category_data.small_category_life,
                Category_data.small_category_finace,Category_data.small_category_stationery},{Category_data.small_category_bigAppliance,
                Category_data.small_category_lifeAppliance,Category_data.small_category_entertainment},{Category_data.small_category_kitchenware,
                Category_data.small_category_homeFixtool,Category_data.small_category_dailyNecessities}};
        large.getOnItemClickListener();
        middle.getOnItemSelectedListener();
    }


    private void initializeWidgets()
    {
        //Toolbar含返回的按鈕
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Toolbar含返回的按鈕
        ProductName_editText = findViewById(R.id.ProductName_editText);
        description_editText = findViewById(R.id.description_editText);

        uploadPic1=findViewById(R.id.UploadimageView1);
        uploadPic1.setOnClickListener(this);
//        uploadPic2=findViewById(R.id.UploadimageView2);
//        uploadPic3=findViewById(R.id.UploadimageView3);

        //spinner裡的原件
        parentLinearLayout=findViewById(R.id.parentlinearLayout);
        add_btn=findViewById(R.id.add_Button);
        add_btn.setOnClickListener(this);
//        dele_btn=findViewById(R.id.dele_Button);
        //spinner裡的原件

        Upload_toPHP_btn =findViewById(R.id.upload_toPHP_btn);
        Upload_toPHP_btn.setOnClickListener(this);
        Upload_choose_dialog = new Dialog(this);
        Upload_choose_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Upload_choose_dialog.setCancelable(false); //無法利用回上一頁按鈕離開對話框
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.UploadimageView1:
                PicNum = 0;
                Upload_choose_dialog.show();
                Toast.makeText(this, "首頁圖片", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.UploadimageView2:
//                if(Pic2_Bool){
//                    PicNum=1;
//                    Upload_choose_dialog.show();}
//                else{
//                    Toast.makeText(this, "請先上傳封面圖片", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case R.id.UploadimageView3:
//                if(Pic3_Bool){
//                    PicNum=2;
//                    Upload_choose_dialog.show();}
//                else {
//                    Toast.makeText(this, "請先上傳第二張圖片", Toast.LENGTH_SHORT).show();
//                }
//                break;
            case R.id.add_Button:
                LayoutInflater inflater  = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View rowView = inflater.inflate(R.layout.textbox,null);
                EditText add_edittxt1 = rowView.findViewById(R.id.editText1);
                EditText add_edittxt2 = rowView.findViewById(R.id.editText2);
                EditText add_edittxt3 = rowView.findViewById(R.id.editText3);
                TextView add_textview1 =rowView.findViewById(R.id.textView1);
                TextView add_textview2 =rowView.findViewById(R.id.textView2);
                TextView add_textview3 =rowView.findViewById(R.id.textView3);
//                new_dele_btn =rowView.findViewById(R.id.dele_Button1);
//                new_dele_btn.setOnClickListener(this);
                parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount()-1);
                edittxts_specification.add(add_edittxt1);
                edittxts_unitPrices.add(add_edittxt2);
                edittxts_quantities.add(add_edittxt3);
                textViews.add(add_textview1);
                textViews.add(add_textview2);
                textViews.add(add_textview3);
                break;
//            case R.id.dele_Button:
//                parentLinearLayout.removeView((View) v.getParent());
//                edittxts_specification.remove(specification_editText);
//                edittxts_unitPrices.remove(unitprice_editText);
//                edittxts_quantities.remove(quantities_editText);
////                Toast.makeText(AddItemToSell.this, "Test", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.dele_Button1:
//                onDelete(new_dele_btn);
            case R.id.upload_toPHP_btn:
                boolean null_check = check_input_is_null();// 2019 02 11 鵬
                if(!null_check) // 2019 02 11 鵬
                    return;  // 2019 02 11 鵬
                upload_data_forAddProduct();
                Toast.makeText(context, "資料上傳", Toast.LENGTH_SHORT).show();
                break;
        }
    }
//---------2019 02 11 鵬 ------------------------------------------
    public boolean check_input_is_null()
    {
        if( ProductName_editText.getText().toString().equals("")){
            Toast.makeText(context, "產品名稱一定要寫喔!!!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(description_editText.getText().toString().equals("")){
            Toast.makeText(context, "產品描述一定要寫喔!!!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(specification_editText.getText().toString().equals("")){
            Toast.makeText(context, "規格一定要寫喔!!!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(unitprice_editText.getText().toString().equals("")){
            Toast.makeText(context, "價錢一定要寫喔!!!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(quantities_editText.getText().toString().equals("")){
            Toast.makeText(context, "數量一定要寫喔!!!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
//---------------------------------------------------------------------------------------

    public void onDelete(View v){
        parentLinearLayout.removeView((View) v.getParent());
        edittxts_specification.remove(specification_editText);
        edittxts_unitPrices.remove(unitprice_editText);
        edittxts_quantities.remove(quantities_editText);
//        Toast.makeText(AddItemToSell.this, "Test00", Toast.LENGTH_SHORT).show();
    }

    //上傳資料
    private void upload_data_forAddProduct()
    {
        SharedPreferences login_check=getSharedPreferences("login", Context.MODE_PRIVATE);
        String uploadURL = getResources().getString(R.string.localDataBase) + "sell_product_upload.php";


        ArrayList<String> Pics = new ArrayList<>();
        String[][]specifications_all =new String[edittxts_specification.size()][3];
        String[] specifications = new String[edittxts_specification.size()];
        String[] unitPrices  = new String[edittxts_specification.size()];
        String[] quantities  = new String[edittxts_specification.size()];
        if(bitmap1 != null){
            Pics.add(imageToString(bitmap1));
            Log.d("debug00_addProduct","01");
            //Toast.makeText(context, "0000", Toast.LENGTH_SHORT).show();
            Log.d("debug00_addProduct","02");
        }
        if(bitmap2 != null){
            Pics.add(imageToString(bitmap2));
//            Toast.makeText(context, "0000", Toast.LENGTH_SHORT).show();
        }
        if(bitmap3 != null){
            Pics.add(imageToString(bitmap3));
//            Toast.makeText(context, "0000", Toast.LENGTH_SHORT).show();
        }
        //Toast.makeText(context, edittxts_specification.size(), Toast.LENGTH_SHORT).show();
        for (int i=0; i<edittxts_specification.size();i++)
        {
            specifications[i]=edittxts_specification.get(i).getText().toString();
            unitPrices[i]=edittxts_unitPrices.get(i).getText().toString();
            quantities[i]=edittxts_quantities.get(i).getText().toString();

            specifications_all[i][0]=specifications[i];
            specifications_all[i][1]=unitPrices[i];
            specifications_all[i][2]=quantities[i];
        }
        String result = "";
        JSONObject obj =new JSONObject();
        try {
            obj.put("uploader",login_check.getString("member_id",null));
            obj.put("product_name",ProductName_editText.getText());
            obj.put("large_categories_str",large.getSelectedItem().toString());
            obj.put("middle_categories_str",middle.getSelectedItem().toString());
            obj.put("small_categories_str",small.getSelectedItem().toString());
            obj.put("description",description_editText.getText());
            obj.put("product_image",new JSONArray(Pics));
            obj.put("product_specification",new JSONArray(specifications_all));
//            obj.put("unit_prices",new JSONArray(unitPrices));
//            obj.put("quantities",new JSONArray(quantities));
            String strobj=obj.toString();

            Log.d("debug00_add01",strobj);
            Log.d("debug00_add02",Pics.toString());

             result =new QueryTask().execute(uploadURL,strobj).get();
            Log.d("debug00_add03",result);
            //--- 2019 02 11 鵬 ------------------------------------------------------
            if(result.equals("上傳失敗，請檢查網路是否異常"))
                Toast.makeText(context, "上傳失敗，請檢查網路是否異常",Toast.LENGTH_SHORT).show();
            else if(result.equals("上傳成功")) {
                Toast.makeText(context, "上傳成功", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, HomeActivity.class));
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("debug00_addProduct",e.toString());
        }

        //Toast.makeText(context, edittxts_unitPrices.get(0).getText().toString(), Toast.LENGTH_SHORT).show();


    }


    //設定取得圖片的dialog
    private void uploadDialogWidgetsInitialize() {
        // 上傳選擇路徑 Dialog
        try {
            Upload_choose_dialog.setContentView(R.layout.choose_upload_pic);
            upload_Camera_btn = (Button) Upload_choose_dialog.findViewById(R.id.upload_Camera_btn);
            upload_Gallery_btn = (Button) Upload_choose_dialog.findViewById(R.id.upload_Gallery_btn);
            upload_CancelDailog_btn = (Button) Upload_choose_dialog.findViewById(R.id.upload_CancelDailog_btn);
            upload_CancelDailog_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Upload_choose_dialog.cancel();
                }
            });
            upload_Camera_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //測試頁面路徑
                    takePicture();
                    Toast.makeText(getApplication(), "這是拍照上傳", Toast.LENGTH_SHORT).show();
                    Upload_choose_dialog.cancel();
                }
            });
            upload_Gallery_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectImage();
                    Toast.makeText(getApplication(), "這是相簿上傳", Toast.LENGTH_SHORT).show();
                    Upload_choose_dialog.cancel();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
            Log.d("debug00",e.toString());
        }
    }
    //設定取得圖片的dialog

    //選擇照片從圖片庫
    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, SelectPic_REQUST);
    }
    //選擇照片從圖片庫

    //從拍照取的圖片
    private void takePicture()
    {
        Intent intent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,Camera_REQUST);
    }
    //從拍照取的圖片

    //在dialog取得照片或圖片執行的行為
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            //從相簿...選擇照片...!!
            if (requestCode == SelectPic_REQUST  && data != null)
            {
                Uri path = data.getData();
                try {

                    switch (PicNum){
                        case 0:
                            bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                            uploadPic1.setImageBitmap(bitmap1);
                            Pic2_Bool =true;
                            break;
                        case 1:
                            bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                            Pic3_Bool =true;
                            break;
                        case 2:
                            bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                            break;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //從相機...拍攝照片...!!
            if(requestCode == Camera_REQUST && data != null)
            {
                Bundle bundle = data.getExtras();
                switch (PicNum){
                    case 0:
                        bitmap1 =(Bitmap)bundle.get("data");
                        uploadPic1.setImageBitmap(bitmap1);
                        Pic2_Bool =true;
                        break;
                    case 1:
                        bitmap2 =(Bitmap)bundle.get("data");
                        Pic3_Bool =true;
                        break;
                    case 2:
                        bitmap3 =(Bitmap)bundle.get("data");
                        break;
                }
            }
        }
    }
    //在dialog取得照片或圖片執行的行為

    //將圖片轉為字串方便上傳
    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }
    //將圖片轉為字串方便上傳

    //第一個下拉類別的監看式
    OnItemSelectedListener selectListener = new OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            //讀取第一個下拉選單是選擇第幾個
            int pos = large.getSelectedItemPosition();
            //重新產生新的Adapter，用的是二維陣列type2[pos]
            middle_adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, middle_select[pos]);
            //載入第二個下拉選單Spinner
            middle.setAdapter(middle_adapter);
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    };
    //第二個下拉類別的監看式
    OnItemSelectedListener selectListener2 = new OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            int pos =large.getSelectedItemPosition();
            int pos2 = middle.getSelectedItemPosition();
            //重新產生新的Adapter，用的是二維陣列type2[pos]
            small_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, small_select[pos][pos2]);
            //載入第二個下拉選單Spinner
            small.setAdapter(small_adapter);
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    };
}
