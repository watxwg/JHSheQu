package com.jhcms.common.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Button;


/**
 * Created by Administrator on 2015/11/30.
 */
public class TimeCount extends CountDownTimer {

    Button getValidate;
    Context context;

    public TimeCount(long millisInFuture, long countDownInterval,
                     Button button, Context context) {
        // 参数依次为总时长,和计时的时间间隔
        super(millisInFuture, countDownInterval);
        this.getValidate = button;
        this.context = context;

    }

    @SuppressWarnings("deprecation")
    @Override
    public void onFinish() {// 计时完毕时触发
        getValidate.setText("重新获取");
//        getValidate.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_verificationbtn));
//        getValidate.setPadding(40, 23, 40, 23);
        getValidate.setEnabled(true);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        // 计时过程显示
        getValidate.setText(millisUntilFinished / 1000 + "秒");
  //      getValidate.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_verificationbtn_press));
   //     getValidate.setPadding(70, 23, 70, 23);
        getValidate.setEnabled(false);
    }
}
