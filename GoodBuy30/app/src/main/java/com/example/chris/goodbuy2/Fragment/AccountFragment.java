package com.example.chris.goodbuy2.Fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.chris.goodbuy2.Activity.HomeActivity;
import com.example.chris.goodbuy2.Activity.LoginActivity;
import com.example.chris.goodbuy2.Activity.ShoppingCartActivity;
import com.example.chris.goodbuy2.R;
import com.facebook.login.LoginManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    BuyerFragment buyerFragment;
    SellerFragment sellerFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        try {
            //----------------------- Tabs and Viewpager 設定 -----------------------//
            buyerFragment = new BuyerFragment();
            sellerFragment = new SellerFragment();

            ViewPager pager = (ViewPager)view.findViewById(R.id.account_pager);
            TabLayout tabLayout = (TabLayout) view.findViewById(R.id.account_tabs);
            SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getChildFragmentManager()); //注意: 因為在fragment裡面使用childFragment做替換,所以必須用getChildFragment();
            pager.setAdapter(pagerAdapter);
            tabLayout.setupWithViewPager(pager);


            //----------------------- ActionBar 設定 -----------------------//
            setHasOptionsMenu(true); // ActionBar 設定 !!!!!重要!!!!!!
            Toolbar toolbar = view.findViewById(R.id.toolbar);
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("debug00",e.toString());
        }
    }

    //----------------------- ActionBar 設定 -----------------------//
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.account_toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences login_check=getActivity().getSharedPreferences("login",Context.MODE_PRIVATE);
        switch (item.getItemId()){
            case R.id.shopping_cart:
                //startActivity(intent_ShoppingCart);
                //Toast.makeText(getActivity(), "Click Shopping cart", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ShoppingCartActivity.class);
                startActivity(intent);
                return true;
            case R.id.speach:
                // dialog
                final Dialog dialog = new Dialog(getActivity());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false); //無法利用回上一頁按鈕離開對話框
                dialog.setContentView(R.layout.logout_layout);
                Button logout_btn = dialog.findViewById(R.id.logout_btn);
                Button cancel_btn = dialog.findViewById(R.id.cancel_btn);
                dialog.show();
                logout_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //修改及確認登入狀態
                        SharedPreferences login_check=getActivity().getSharedPreferences("login",Context.MODE_PRIVATE);
                        SharedPreferences.Editor login_save=login_check.edit();
                        login_save.remove("member_id");
                        login_save.remove("member_email");
                        login_save.remove("member_name");
                        login_save.remove("member_token");
                        login_save.remove("member_status");
                        login_save.apply();
                        Toast.makeText(getActivity(), "帳號已登出", Toast.LENGTH_SHORT).show();
                        LoginManager.getInstance().logOut(); // FB登出
                        dialog.cancel();
                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        getActivity().finish();
                        startActivity(intent);
                    }
                });

                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity() ,"取消登出", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //----------------------- Tabs and Viewpager 設定 -----------------------//
    private class SectionsPagerAdapter extends FragmentPagerAdapter{
        public SectionsPagerAdapter(FragmentManager fm){super(fm);}

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return buyerFragment;
                case 1:
                    return sellerFragment;
                default:
                    return null;
            }
        }

        public  int getCount(){return 2;}

        public CharSequence getPageTitle(int position){
            switch (position){
                case 0:
                    return getResources().getText(R.string.tab_buyer);
                case 1:
                    return getResources().getText(R.string.tab_seller);
                default:
                    return null;
            }
        }
    }
}
