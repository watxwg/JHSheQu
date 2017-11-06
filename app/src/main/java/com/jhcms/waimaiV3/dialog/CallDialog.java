package com.jhcms.waimaiV3.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/7.
 */

public class CallDialog extends Dialog {
    private final Context context;
    @Bind(R.id.tip_title)
    TextView tipTitle;
    @Bind(R.id.ll_tip_title)
    LinearLayout llTipTitle;
    @Bind(R.id.tip_message)
    TextView tipMessage;
    @Bind(R.id.ll_tip_message)
    LinearLayout tipTipMessage;
    @Bind(R.id.tip_negative)
    TextView tipNegative;
    @Bind(R.id.tip_positive)
    TextView tipPositive;

    private String title;
    private String message;
    private View.OnClickListener positiveListener, negativeListener;
    private CharSequence positiveStr;
    private CharSequence negativeStr;


    public CallDialog(Context context) {
        super(context, R.style.Dialog);
        this.context = context;
    }

    /**
     * @param title 对话框标题内容
     */
    public CallDialog setTipTitle(String title) {
        this.title = title;
        return this;
    }

    public CallDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    public CallDialog setPositiveButton(CharSequence text,
                                        View.OnClickListener l) {
        positiveStr = text;
        positiveListener = l;
        return this;
    }

    public CallDialog setNegativeButton(CharSequence text,
                                        View.OnClickListener l) {
        negativeStr = text;
        negativeListener = l;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tip_layout);
        ButterKnife.bind(this);
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = Utils.getScreenW(context) * 4 / 5;
        window.setAttributes(lp);
        /*设置对话框标题*/
        if (TextUtils.isEmpty(title)) {
            llTipTitle.setVisibility(View.GONE);
        } else {
            llTipTitle.setVisibility(View.VISIBLE);
            tipTitle.setText(title);
        }
        /*设置对话框信息*/
        if (!TextUtils.isEmpty(message)) {
            tipMessage.setText(message);
        }
        tipPositive.setText(positiveStr == null ? "" : positiveStr);
        tipNegative.setText(negativeStr == null ? "" : negativeStr);
    }

    @OnClick({R.id.tip_positive, R.id.tip_negative})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tip_positive:
                if (positiveListener != null) {
                    positiveListener.onClick(view);
                }
                dismiss();
                break;
            case R.id.tip_negative:
                if (negativeListener != null) {
                    negativeListener.onClick(view);
                }
                dismiss();
                break;
        }
    }
}
