package com.example.chris.goodbuy2.Task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;

public class item_Product_Image_Load_Task extends AsyncTask<Integer, Void, Integer> {

    private Context context;
    private int res;
    private ImageView imageView;

    public item_Product_Image_Load_Task(Context context, ImageView imageView) {
        this.context = context;
        this.imageView = imageView;
    }

    @Override
    protected Integer doInBackground(Integer... integers) {
        res = integers[0];
        return res;
    }

    @Override
    protected void onPostExecute(Integer integer) {

        imageView.setImageResource(integer);
    }
}
