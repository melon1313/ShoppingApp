package com.example.chris.goodbuy2.Helper;

import android.support.v7.widget.RecyclerView;

public interface INoticeRecyclerItemTouchHelperListener {
    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
}
