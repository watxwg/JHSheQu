package com.jhcms.waimaiV3.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.jhcms.common.model.TimeModel;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.PickerView;
import com.jhcms.waimaiV3.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangyujie
 * Date 2017/5/25.
 * TODO 送达时间
 */
public class PickerViewDialog extends Dialog {
    private final Context context;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.pickerview)
    PickerView pickerView;
    @Bind(R.id.tv_determine)
    TextView tvDetermine;
    private List<TimeModel> mDatas;
    private String selectText;
    private int selectId = 0;
    private OnSelectTimeListener timeListener;
    private  ArrayList<String>mtimedata=new ArrayList<>();

    public PickerViewDialog(Context context) {
        super(context, R.style.Dialog);
        this.context = context;
    }

    public void setmDatas(List<TimeModel> mDatas) {
        this.mDatas = mDatas;
    }

    public void setHideTime(boolean hideTime) {
        if (hideTime) {
            tvTime.setVisibility(View.GONE);
        } else {
            tvTime.setVisibility(View.VISIBLE);
        }

    }

    public interface OnSelectTimeListener {
        void selectTimeListener(String time,int selectId);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pickview_layout);
        ButterKnife.bind(this);
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = Utils.getScreenW(context) * 4 / 5;
        window.setAttributes(lp);
        tvTime.setText(mDatas.get(0).getDate()+"分钟");
        selectText = mDatas.get(0).getMinute()+"分钟";
        final ArrayList<String> mdata=new ArrayList<>();
        for (int i=0;i<mDatas.size();i++){
            mdata.add(mDatas.get(i).getMinute()+"分钟");
            mtimedata.add(mDatas.get(i).getMinute()+"分钟");
        }
        pickerView.setData(mdata);
        pickerView.setSelected(0);
        pickerView.setOnSelectListener(new PickerView.onSelectListener() {

            @Override
            public void onSelect(String text, int mCurrentSelected) {
                tvTime.setText(mDatas.get(mtimedata.indexOf(text)).getDate()+"分钟");
                selectId = mtimedata.indexOf(text);
                selectText = tvTime.getText().toString();
            }
        });
    }

    public void setOnSelectTimeListener(OnSelectTimeListener timeListener) {
        this.timeListener = timeListener;
    }

    @OnClick(R.id.tv_determine)
    public void onClick() {
        if (timeListener != null) {
            timeListener.selectTimeListener(selectText,selectId);
        }
        dismiss();
    }
}
