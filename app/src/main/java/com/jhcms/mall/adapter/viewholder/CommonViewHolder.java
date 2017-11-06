package com.jhcms.mall.adapter.viewholder;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 作者：WangWei
 * 日期：2017/8/28 11:09
 * 描述：通用的RecyclerViewHolder，配合CommonAdpater使用
 */

public class CommonViewHolder extends RecyclerView.ViewHolder{
    private Context mContext;
    private SparseArray<View> mViews;
    public CommonViewHolder(Context context, View itemView) {
        super(itemView);
        mViews=new SparseArray<>();
    }
    public static CommonViewHolder get(int layoutId,Context context,ViewGroup parent){
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new CommonViewHolder(context,view);
    }
    public <T extends View> T getView(@IdRes int viewId){
        View view = mViews.get(viewId);
        if(view==null){
            view=itemView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T) view;
    }
    public void setOnClickListener(@IdRes int viewId, View.OnClickListener listener){
        getView(viewId).setOnClickListener(listener);
    }
    public void setTextViewText(@IdRes int viewId, String text){
        this.<TextView>getView(viewId).setText(text);
    }
    public void setVisiability(@IdRes int viewId, int visibility){
        this.getView(viewId).setVisibility(visibility);
    }
}
