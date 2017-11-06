package com.jhcms.mall.dialog;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.IntegerRes;
import android.util.SparseArray;
import android.view.View;

/**
 * 作者：WangWei
 * 日期：2017/8/25 16:11
 * 描述：
 */

public class ViewHolder {
    private final SparseArray sparseArray;
    private final View contentView;

    private ViewHolder(View view) {
        sparseArray = new SparseArray();
        contentView = view;
    }

    public static ViewHolder newInstance(View view) {
        return new ViewHolder(view);
    }

    /**
     * 获取控件
     *
     * @param resId 控件id
     * @param <T>   控件类名
     * @return
     */
    public <T extends View> T getView(int resId) {
        View view = (View) sparseArray.get(resId);
        if (view == null) {
            view = contentView.findViewById(resId);
            sparseArray.append(resId, view);
        }
        return (T) view;
    }

    /**
     * 设置点击监听
     * @param resId 控件ID
     * @param onClickListener listener
     */
    public void setOnClickListener(@IdRes int resId, View.OnClickListener onClickListener){
        getView(resId).setOnClickListener(onClickListener);
    }
}
