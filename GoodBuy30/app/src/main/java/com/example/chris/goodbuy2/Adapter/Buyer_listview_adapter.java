package com.example.chris.goodbuy2.Adapter;

import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.chris.goodbuy2.R;

public class Buyer_listview_adapter extends ArrayAdapter<String> {

    private Integer[] itemname;
    private Integer[] imageId;
    private Activity context;

    public Buyer_listview_adapter(Activity context, Integer[]itemname, Integer[] imageId) {
        super(context, R.layout.buyer_listview);

        this.context = context;
        this.itemname = itemname;
        this.imageId = imageId;
    }
}
