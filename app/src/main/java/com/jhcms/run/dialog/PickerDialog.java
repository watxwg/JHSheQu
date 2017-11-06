package com.jhcms.run.dialog;

import android.support.annotation.FloatRange;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import com.jhcms.common.widget.PickerView;
import com.jhcms.mall.dialog.BaseDialog;
import com.jhcms.mall.dialog.ViewHolder;
import com.jhcms.waimaiV3.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/10/10 15:22
 * 描述：选择对话框
 */

public class PickerDialog extends BaseDialog {
    private List<String> mSelectData;
    private List<String> originData ;
    private String desc;
    private String selectedItem;
    private OnSelectListener mListener;

    @Override
    public int provideLauoutId() {
        return R.layout.dialog_pickview_layout;
    }

    /**
     * 根据修饰创建新的集合
     * @param selectData 需要显示的数据
     * @param modifier 修饰
     */
    public void setData(List<String> selectData,String modifier) {
        originData = selectData;
        mSelectData = new ArrayList<>();
        for (int i = 0; i < originData.size(); i++) {
            String s = originData.get(i);
            mSelectData.add(s+modifier);
        }
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public void initDialog(ViewHolder viewHolder, BaseDialog dialog) {
        viewHolder.<TextView>getView(R.id.tv_time).setText(desc);
        PickerView pickerView = viewHolder.<PickerView>getView(R.id.pickerview);
        pickerView.setData(mSelectData);
        pickerView.setSelected(0);
        selectedItem=originData.get(0);
        pickerView.setOnSelectListener((text, currentIndex) -> {

            int index = mSelectData.indexOf(text);
            selectedItem = originData.get(index);
        });

        viewHolder.getView(R.id.tv_determine).setOnClickListener(v -> {
            if (mListener != null) {

                mListener.onSelect(selectedItem,originData.indexOf(selectedItem));
            }
            dismiss();

        });


    }

    public void setOnSelectListener(OnSelectListener listener) {
        mListener = listener;
    }

    public interface OnSelectListener {
        void onSelect(String select,int position);
    }

    public void setWidth(@FloatRange(from = 0.0f, to = 1.0f) float width, DisplayMetrics displayMetrics) {
        int widthPixels = (int) (displayMetrics.widthPixels * width);
        setWidth(widthPixels);
    }
}
