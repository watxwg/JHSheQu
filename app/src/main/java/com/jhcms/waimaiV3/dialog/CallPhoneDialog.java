package com.jhcms.waimaiV3.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.MyApplication;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.OrderDetailsActivity;
import com.jhcms.waimaiV3.activity.OrderDetailsGMSActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangyujie
 * Date 2017/5/26.
 * TODO
 */

public class CallPhoneDialog extends Dialog {
    private final Context context;
    @Bind(R.id.tv_call_shop)
    TextView tvCallShop;
    @Bind(R.id.tv_call_staff)
    TextView tvCallStaff;
    @Bind(R.id.tv_call_csd)
    TextView tvCallCsd;
    @Bind(R.id.line_staff)
    View lineStaff;
    @Bind(R.id.line_shop)
    View lineShop;
    private boolean isShowStaffPhone;
    private OnSelectListener listener;


    /**
     * @param context
     * @param isShowStaffPhone true 显示联系骑手 false 隐藏联系骑手
     */
    public CallPhoneDialog(@NonNull Context context, boolean isShowStaffPhone) {
        super(context, R.style.Dialog);
        this.context = context;
        this.isShowStaffPhone = isShowStaffPhone;
    }

    /**
     * @param hindShop true 隐藏联系商家
     */
    public void setHindShop(boolean hindShop) {
        if (hindShop) {
            tvCallShop.setVisibility(View.GONE);
            lineShop.setVisibility(View.GONE);
        } else {
            tvCallShop.setVisibility(View.VISIBLE);
            lineShop.setVisibility(View.VISIBLE);
        }
    }

    public interface OnSelectListener {
        void selectListener(String type);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_callphone_layout);
        ButterKnife.bind(this);
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = Utils.getScreenW(context) * 4 / 5;
        window.setAttributes(lp);

        if (isShowStaffPhone) {
            tvCallStaff.setVisibility(View.VISIBLE);
            lineStaff.setVisibility(View.VISIBLE);
        } else {
            tvCallStaff.setVisibility(View.GONE);
            lineStaff.setVisibility(View.GONE);
        }
    }


    @OnClick({R.id.tv_call_shop, R.id.tv_call_staff, R.id.tv_call_csd})
    public void onClick(View view) {
        if (listener != null) {
            switch (view.getId()) {
                case R.id.tv_call_shop:
                    if (MyApplication.MAP.equals(Api.GAODE)) {
                        listener.selectListener(OrderDetailsActivity.CALL_SHOP);
                    } else if (MyApplication.MAP.equals(Api.GOOGLE)) {
                        listener.selectListener(OrderDetailsGMSActivity.CALL_SHOP);
                    }
                    break;
                case R.id.tv_call_staff:
                    if (MyApplication.MAP.equals(Api.GAODE)) {
                        listener.selectListener(OrderDetailsActivity.CALL_STAFF);
                    } else if (MyApplication.MAP.equals(Api.GOOGLE)) {
                        listener.selectListener(OrderDetailsGMSActivity.CALL_STAFF);
                    }
                    break;
                case R.id.tv_call_csd:
                    if (MyApplication.MAP.equals(Api.GAODE)) {
                        listener.selectListener(OrderDetailsActivity.CALL_CSD);
                    } else if (MyApplication.MAP.equals(Api.GOOGLE)) {
                        listener.selectListener(OrderDetailsGMSActivity.CALL_CSD);
                    }
                    break;
            }
        }
        dismiss();
    }

    public void setOnCallPhoneListener(OnSelectListener listener) {
        this.listener = listener;
    }
}
