package com.jhcms.tuangou.dialog;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhcms.common.dialog.actionsheet.BaseAnimatorSet;
import com.jhcms.common.dialog.actionsheet.ZoomOutBottomExit;
import com.jhcms.common.model.Data_Group_Order_Details;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin
 * on 2017/8/4.
 * TODO
 */

public class GroupCoucherDialog extends Dialog {

    private final Context context;
    @Bind(R.id.ll_root)
    LinearLayout llRoot;
    @Bind(R.id.tv_voucher_code)
    TextView tvVoucherCode;
    @Bind(R.id.tv_voucher_num)
    TextView tvVoucherNum;
    @Bind(R.id.iv_voucher_code)
    ImageView ivVoucherCode;
    @Bind(R.id.tv_voucher_status)
    TextView tvVoucherStatus;
    private Data_Group_Order_Details.OrderBean data;

    public GroupCoucherDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_group_coucher_layout);
        ButterKnife.bind(this);
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = Utils.getScreenW(context) * 4 / 5;
        window.setAttributes(lp);
    }

    private void initData() {
        tvVoucherCode.setText("券码:" + data.ticket_number);
        tvVoucherNum.setText("X" + data.tuan_number);
        Bitmap bitmap = CodeUtils.createImage(data.ticket_number, Utils.getScreenW(context) * 3 / 5, Utils.getScreenW(context) * 3 / 5, null);
        ivVoucherCode.setImageBitmap(bitmap);

        //                    ticket_status 团购券0 未使用 1 已核销 -1 已退款
        if (!TextUtils.isEmpty(data.ticket_status)) {
            if (data.ticket_status.equals("0")) {
                tvVoucherStatus.setText("待使用");
            } else if (data.ticket_status.equals("1")) {
                tvVoucherStatus.setText("已使用");
            } else if (data.ticket_status.equals("-1")) {
                tvVoucherStatus.setText("已退款");
            }
        }

    }

    @Override
    public void show() {
        super.show();
        initData();
        animation(1000);
    }

    @Override
    public void dismiss() {
        ZoomOutBottomExit outExit = new ZoomOutBottomExit();
        if (outExit != null) {
            outExit.listener(new BaseAnimatorSet.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    superDismiss();
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                    superDismiss();
                }
            }).playOn(llRoot);
        } else {
            superDismiss();
        }
    }

    public void superDismiss() {
        super.dismiss();
    }

    private void animation(int mDuration) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(llRoot, "alpha", 0, 1, 1, 1).setDuration(mDuration),
                ObjectAnimator.ofFloat(llRoot, "scaleX", 0.5f, 1.05f, 0.95f, 1).setDuration(mDuration),
                ObjectAnimator.ofFloat(llRoot, "scaleY", 0.5f, 1.05f, 0.95f, 1).setDuration(mDuration)
        );
        animatorSet.start();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus)
            super.onWindowFocusChanged(hasFocus);
    }

    public void setData(Data_Group_Order_Details.OrderBean data) {
        this.data = data;
    }
}
