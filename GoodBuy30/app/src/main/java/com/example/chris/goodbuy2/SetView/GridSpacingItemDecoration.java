package com.example.chris.goodbuy2.SetView;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount; //列數
    private int spacing; //間隔
    private  boolean includeEdge; //是否含邊緣

    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        //這裡是關鍵，需要根據你有幾列來判斷
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column

        if(includeEdge){
            outRect.left = spacing - column * spacing /spanCount;
            outRect.right = (column + 1) * spacing / spanCount;

            if(position < spanCount){ // top edge
                outRect.top = spacing;
            }
            outRect.bottom = spacing; //item bottom
        }else {
            outRect.left = column * spacing / spanCount;
            outRect.right = spacing - (column + 1) * spacing /spanCount;
            if(position >= spanCount){
                outRect.top = spacing; //item top
            }
        }
    }
}
