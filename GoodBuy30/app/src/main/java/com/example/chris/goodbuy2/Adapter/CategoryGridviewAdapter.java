package com.example.chris.goodbuy2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chris.goodbuy2.R;

import org.w3c.dom.Text;

public class CategoryGridviewAdapter extends BaseAdapter {

    private String[] categorydata;
    private Context context;
    private LayoutInflater layoutInflater;
    private View view;

    public CategoryGridviewAdapter(Context context, String[] categorydata) {
        this.context = context;
        this.categorydata = categorydata;
    }

    @Override
    public int getCount() {
        return categorydata.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = new View(context);
        if(convertView == null){
            view = layoutInflater.inflate(R.layout.category_single_item, null);
            TextView textView = view.findViewById(R.id.category_textview);
            textView.setText(categorydata[position]);
        }else {
            view = convertView;
        }

        return view;
    }
}
