package com.jhcms.waimaiV3.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jhcms.common.model.Data_WaiMai_PayOrder;
import com.jhcms.common.utils.ActivityCollector;
import com.jhcms.common.utils.LogUtil;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.WaiMaiPay;

import static com.jhcms.common.utils.Utils.saveCrashInfo2File;

public abstract class PayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        /*竖屏*/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActivityCollector.addActivity(this);
        initView();
        onCrash();
        LogUtil.d("SwipeBaseActivity" + getClass().getSimpleName());
    }

    public void onCrash() {
        try {
            initData();
        } catch (Exception e) {
            ToastUtil.show("服务器出现异常");
            saveCrashInfo2File(e);
        }
    }

    /*处理数据*/
    protected abstract void initData();

    protected abstract void initView();

    private WaiMaiPay pay;

    public void DealWithPayOrder(String code, Data_WaiMai_PayOrder data, WaiMaiPay.OnPayListener onPayListener) {
        if (pay == null) {
            pay = new WaiMaiPay(this, onPayListener);
        }
        pay.pay(code, data);
    }


}
