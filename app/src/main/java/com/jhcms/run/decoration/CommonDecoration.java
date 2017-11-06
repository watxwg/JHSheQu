package com.jhcms.run.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 作者：WangWei
 * 日期：2017/10/10 14:37
 * 描述：
 */

public class CommonDecoration extends RecyclerView.ItemDecoration{
    private int space;
    private int colum;

    /**
     *
     * @param space 间距
     * @param colum 列数
     */
    public CommonDecoration(int space,int colum){
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
            outRect.set(space,space,space/2,0);
        }else if(viewLayoutPosition%colum==colum-1){
            outRect.set(space/2,space,space,0);
        }else{
            outRect.set(space/2,space,space/2,0);
        }

    }
}
