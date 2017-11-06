package com.jhcms.mall.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者：WangWei
 * 日期：2017/8/25 10:03
 * 描述：商品和商家列表界面 商品双列显示的ItemDecoration
 */

public class ProductDoubleListDecoration extends RecyclerView.ItemDecoration{
    private int space;
    private int colum;
    public ProductDoubleListDecoration(int space,int colum){
        this.space=space;
        this.colum = colum;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        int viewLayoutPosition = layoutParams.getViewLayoutPosition();
        if(viewLayoutPosition==-1){
            return;
        }
        if(viewLayoutPosition%colum==0){
            outRect.set(0,space,space/2,0);
        }else if(viewLayoutPosition%colum==colum-1){
            outRect.set(space/2,space,0,0);
        }else{
            outRect.set(space/2,space,space/2,0);
        }

    }
}
