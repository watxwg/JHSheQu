package com.jhcms.mall.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.view.View;

/**
 * 作者：WangWei
 * 日期：2017/8/22 11:02
 * 描述：
 */

public class HomeAdDection extends ItemDecoration{
    private int mSpace ;


    public HomeAdDection(int itemSpace){
        mSpace = itemSpace;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        int position = layoutParams.getViewLayoutPosition();
        if(position>=1){
            if(position%2==1){
                outRect.set(0,mSpace,mSpace,0);
            }else{
                outRect.set(0,mSpace,0,0);
            }
        }
    }
}
