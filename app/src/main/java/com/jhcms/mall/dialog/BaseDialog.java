package com.jhcms.mall.dialog;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.jhcms.waimaiV3.R;

/**
 * 作者：WangWei
 * 日期：2017/8/25 16:08
 * 描述：基础对话框
 */

public abstract class BaseDialog extends DialogFragment {
    private static final String MARGIN = "margin";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String DIM = "dim_amount";
    private static final String BOTTOM = "show_bottom";
    private static final String CANCEL = "out_cancel";
    private static final String ANIM = "anim_style";
    private static final String LAYOUT = "layout_id";

    private int margin;//左右边距
    private int width;//宽度
    private int height;//高度
    private float dimAmount = 0.5f;//灰度深浅
    private boolean showBottom;//是否底部显示
    private boolean outCancel = true;//是否点击外部取消
    private int animStyle=R.style.DefaultAnimation;
    @LayoutRes
    protected int layoutId;

    public abstract int provideLauoutId();

    public abstract void initDialog(ViewHolder viewHolder, BaseDialog dialog);

    @Override
    public void onStart() {
        super.onStart();
        initParams();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.mall_baseDialog);
        layoutId=provideLauoutId();
        //恢复保存的数据
        if (savedInstanceState != null) {
            margin = savedInstanceState.getInt(MARGIN);
            width = savedInstanceState.getInt(WIDTH);
            height = savedInstanceState.getInt(HEIGHT);
            dimAmount = savedInstanceState.getFloat(DIM);
            showBottom = savedInstanceState.getBoolean(BOTTOM);
            outCancel = savedInstanceState.getBoolean(CANCEL);
            animStyle = savedInstanceState.getInt(ANIM);
            layoutId = savedInstanceState.getInt(LAYOUT);

        }
    }
    /**
     * 屏幕旋转等导致DialogFragment销毁后重建时保存数据
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MARGIN, margin);
        outState.putInt(WIDTH, width);
        outState.putInt(HEIGHT, height);
        outState.putFloat(DIM, dimAmount);
        outState.putBoolean(BOTTOM, showBottom);
        outState.putBoolean(CANCEL, outCancel);
        outState.putInt(ANIM, animStyle);
        outState.putInt(LAYOUT, layoutId);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutId, container, false);
        initDialog(ViewHolder.newInstance(view), this);
        return view;
    }

    private void initParams() {
        Window window = getDialog().getWindow();
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = this.dimAmount;
        if (width == 0) {
            DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
            int widthPixels = displayMetrics.widthPixels;
            params.width = widthPixels - 2 * margin;
        } else {
            params.width = this.width;
        }
        if (height == 0) {

            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        } else {
            params.height = height;
        }
        if (showBottom) {
            params.gravity = Gravity.BOTTOM;
        }
        window.setWindowAnimations(animStyle);
        window.setAttributes(params);
        setCancelable(outCancel);
    }

    public BaseDialog setAnimSytle(@StyleRes int animStyle) {
        this.animStyle = animStyle;
        return this;
    }

    /**
     * 设置左右两边的边距，单位是dp
     *
     * @param margin
     * @return
     */
    public BaseDialog setMargin(int margin) {
        this.margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, margin, getContext().getResources().getDisplayMetrics());
        return this;
    }
    public BaseDialog setWidth(int width){
        this.width = width;
        return this;
    }
    public BaseDialog setHeight(int height){
        this.height=height;
        return this;
    }
    public BaseDialog setShowBoottom(boolean b){
        this.showBottom=b;
        return this;
    }
    public BaseDialog setOutCanclable(boolean canclable){
        outCancel=canclable;
        return this;
    }


}
