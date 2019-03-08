package com.example.chris.goodbuy2.Fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chris.goodbuy2.Adapter.NoticeficationCardListItemAdapter;
import com.example.chris.goodbuy2.Helper.INoticeRecyclerItemTouchHelperListener;
import com.example.chris.goodbuy2.Helper.NoticeRecyclerItemTouchHelper;
import com.example.chris.goodbuy2.Model.Notice_item;
import com.example.chris.goodbuy2.R;
import com.example.chris.goodbuy2.Service.QueryTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoticficationFragment extends Fragment  implements INoticeRecyclerItemTouchHelperListener {

    String strobj;
    List<Notice_item> list;
    String fcm_title;
    String[] Ntitles;//標題動態陣列
    String fcm_msg;
    String[] Nmsgs;//訊息內文動態陣列
    String fcm_time;
    String[] Ntimes; //時間動態陣列
    String fcm_pic;
    String[] Npics;//圖片動態陣列
    String ID;
    String[] IDs;//通知ID動態陣列
    RecyclerView recyclerView;
    NoticeficationCardListItemAdapter noticeficationCardListItemAdapter;
    LinearLayout rootLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_noticfication, container, false);
        rootLayout = view.findViewById(R.id.root_layout);
        getData();
        try {
            setRecyclerViewWidgets(view);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(),"目前沒有資料!!!!!!!!!!!!!!",Toast.LENGTH_LONG).show();
        }

//        //----------------------- ActionBar 設定 -----------------------//
//        setHasOptionsMenu(true); // ActionBar 設定 !!!!!重要!!!!!!
//        Toolbar toolbar = view.findViewById(R.id.basic_tool_barn);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setCustomView(R.layout.abs_layout);
        return view;
    }



    private void setRecyclerViewWidgets(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //設定動畫
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
//        defaultItemAnimator.setAddDuration(1000);
//        defaultItemAnimator.setMoveDuration(1000);
        recyclerView.setItemAnimator(defaultItemAnimator);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        addListItemData(); // 把資料放進 Notice_item 清單並做swipe delete 設定
    }

    private void addListItemData(){
        list = new ArrayList<>();
        if (list != null){list.clear();}
        for(int i = 0; i < Npics.length; i++){
            Notice_item notice_item = new Notice_item(Ntitles[i], Nmsgs[i], Ntimes[i], Npics[i], IDs[i]);
            list.add(notice_item);
        }

        noticeficationCardListItemAdapter = new NoticeficationCardListItemAdapter(getContext(),list);
        recyclerView.setAdapter(noticeficationCardListItemAdapter); // 將資料適配
        // swipe delete
        ItemTouchHelper.SimpleCallback itemTouchHelperCallBack
                = new NoticeRecyclerItemTouchHelper(0, ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(recyclerView);
    }

    private void getData() {
        String msg_importURL =getResources().getString(R.string.localDataBase)+"notification.php";
        //        SharedPreferences fcm_sh = getActivity().getSharedPreferences("fcm_msg",Context.MODE_PRIVATE);
//        String fcm_title=fcm_sh.getString("fcm_title","title");
//        String fcm_content=fcm_sh.getString("fcm_content","message");
//        String fcm_data=fcm_sh.getString("data","Data");
//        Log.d("debug0099","通知標題為:  "+fcm_title+"  , 訊息內容為:"+fcm_content);
//        txt.setText(fcm_data);
        JSONObject obj = new JSONObject();
        SharedPreferences login_check=getActivity().getSharedPreferences("login",Context.MODE_PRIVATE);
        try {
            obj.put("receiver_id",login_check.getString("member_id","null") );
            obj.put("status","1");//(輸入"1",表示要取出資料 )
            obj.put("token_check_user",login_check.getString("member_token","null"));
        } catch (JSONException e) {
            Log.d("debug0001",e.toString());
        }



        try {
            strobj = obj.toString();
            Log.d("debug000000000","001");
            String a=new QueryTask().execute(msg_importURL , strobj).get();
            Log.d("debug000000000","002");
            Log.d("debug000000000222",a);
            JSONArray jarr = new JSONArray(a);


            Ntitles=new String[jarr.length()]; //標題動態陣列
            Nmsgs=new String[jarr.length()];//訊息內文動態陣列
            Ntimes=new String[jarr.length()];//時間動態陣列
            Npics=new String[jarr.length()];//圖片動態陣列
            IDs =new String[jarr.length()];//ID動態陣列

            Log.d("debug000000000jarr",jarr.toString());
            for (int i = 0; i < jarr.length(); i++) {

                fcm_title = jarr.getJSONObject(i).getString("title");
                Ntitles[i] = fcm_title;
                Log.d("debug00",Ntitles[i]);
                fcm_msg = jarr.getJSONObject(i).getString("content");
                Nmsgs[i] = fcm_msg;
                fcm_time = jarr.getJSONObject(i).getString("send_time");
                Ntimes[i] = fcm_time;
                fcm_pic =getResources().getString(R.string.localDataBaseImg)+jarr.getJSONObject(i).getString("product_image");
                Npics[i] = fcm_pic;
                ID=jarr.getJSONObject(i).getString("id");
                IDs[i]=ID;

                //顯示狀況<陣列狀況>
                Log.d("debug001000","Title: $$"+Ntitles[i]+",Msg: $$"+Nmsgs[i]+",Times: $$"+Ntimes[i]+",Pics: $$"+Npics[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void deleData(String ID,String action){
        //String ID 通知ID
        //String action   刪除為0,還原為1。
        String msg_importURL =getResources().getString(R.string.localDataBase)+"notificationdele.php";
        JSONObject obj = new JSONObject();
        SharedPreferences login_check=getActivity().getSharedPreferences("login",Context.MODE_PRIVATE);
        try {
            obj.put("receiver_id",login_check.getString("member_id","null") );
            obj.put("id",ID);
            obj.put("action",action);
            obj.put("status","1");//(輸入"1",表示要取出資料 )
            String strobj=obj.toString();
            String result = new QueryTask().execute(msg_importURL,strobj).get();
            Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
            Log.d("debug00",result);
        } catch (Exception e) {
            Log.d("debug0001",e.toString());
        }}

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if(viewHolder instanceof NoticeficationCardListItemAdapter.MyViewHolder){
            final Notice_item deleteItem = list.get(viewHolder.getAdapterPosition());
            final  int deleteIndex = viewHolder.getAdapterPosition();
            final String id = list.get(viewHolder.getAdapterPosition()).getID();

            noticeficationCardListItemAdapter.removeItem(deleteIndex);
            deleData(id, "0"); // 刪除訊息

            Snackbar snackbar = Snackbar.make(rootLayout, "已移除訊息", Snackbar.LENGTH_LONG);
            snackbar.setAction("復原刪除", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    noticeficationCardListItemAdapter.restoreItem(deleteItem, deleteIndex);
                    deleData(id, "1"); // 恢復訊息
                }
            });

            snackbar.setActionTextColor(getResources().getColor(R.color.baby_green));
            snackbar.show();
        }
    }
}

