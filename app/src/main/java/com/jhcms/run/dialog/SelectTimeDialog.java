package com.jhcms.run.dialog;

import android.media.MediaDataSource;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import com.jhcms.mall.dialog.BaseDialog;
import com.jhcms.mall.dialog.ViewHolder;
import com.jhcms.run.adapter.LeftTimeAdapter;
import com.jhcms.run.adapter.RightTimeAdapter;
import com.jhcms.run.mode.DayConfigInfoModel;
import com.jhcms.waimaiV3.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/10/10 17:29
 * 描述：
 */

public class SelectTimeDialog extends BaseDialog {
    //当天可选择的时间
    private List<String> todayTime;
    //全天可选择的时间段
    private List<String> normalTiem;
    private List<DayConfigInfoModel> dayConfigInfoModels;
    private LeftTimeAdapter leftTimeAdapter;
    private List<String> rightData;
    private RightTimeAdapter rightTimeAdapter;
    private DayConfigInfoModel selectDay;
    private OnTimeSlectListener mListener;

    public SelectTimeDialog(@NonNull List<String> todayTime, @NonNull List<String> normalTiem,@NonNull List<DayConfigInfoModel> dayConfigInfoModels){
        this.todayTime = todayTime;
        this.normalTiem =  normalTiem;
        this.dayConfigInfoModels=dayConfigInfoModels;
        rightData = new ArrayList<>();
    }
    @Override
    public int provideLauoutId() {
        return R.layout.dialog_run_pick_time;
    }

    @Override
    public void initDialog(ViewHolder viewHolder, BaseDialog dialog) {
        viewHolder.getView(R.id.iv_close).setOnClickListener(v -> dismiss());
        RecyclerView rvLeft = viewHolder.<RecyclerView>getView(R.id.rv_left);
        RecyclerView rvRight = viewHolder.<RecyclerView>getView(R.id.rv_right);
        rightData.clear();
        rightData.addAll(todayTime);
        rightTimeAdapter = new RightTimeAdapter(getContext(),rightData);
        rightTimeAdapter.setOnItemSelectListener((time,position)->{
            if (mListener != null) {
                mListener.onTimeSelected(selectDay,time);
            }
            dismiss();
        });
        rvRight.setAdapter(rightTimeAdapter);
        rvRight.setLayoutManager(new LinearLayoutManager(getContext()));

        leftTimeAdapter = new LeftTimeAdapter(getContext(),dayConfigInfoModels);
        leftTimeAdapter.setOnItemSelectListener((dayConfigInfoModels,position)->{
            List<String> temp = position==0?todayTime:normalTiem;
            rightTimeAdapter.setData(temp);
        });
        rvLeft.setAdapter(leftTimeAdapter);
        rvLeft.setLayoutManager(new LinearLayoutManager(getContext()));

        selectDay = dayConfigInfoModels.get(0);
    }
    public void setHeight(@FloatRange(from = 0.0f,to = 1.0f) float height, DisplayMetrics displayMetrics){
        int height2= (int) (displayMetrics.heightPixels*height);
        setHeight(height2);
    }
    public void setOnTimeSelectListener(OnTimeSlectListener listener){
        mListener = listener;
    }
    public interface OnTimeSlectListener{
        void onTimeSelected(DayConfigInfoModel dayConfigInfoModel,String time);
    }



}
