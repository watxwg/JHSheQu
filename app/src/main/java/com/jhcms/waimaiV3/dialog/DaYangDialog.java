package com.jhcms.waimaiV3.dialog;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.jhcms.common.dialog.actionsheet.BaseAnimatorSet;
import com.jhcms.common.dialog.actionsheet.ZoomOutBottomExit;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangyujie
 * Date 2017/5/26.
 * TODO
 */

public class DaYangDialog extends Dialog {
    private final Context context;
    @Bind(R.id.iv_dayang)
    ImageView ivDayang;

    private int DISMISS = 0x111;
    private int SHOW = 0x222;


    /**
     * @param context
     */
    public DaYangDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
        this.context = context;
    }

    @OnClick(R.id.iv_dayang)
    public void onViewClicked() {
        dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_dayang_layout);
        ButterKnife.bind(this);
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = Utils.getScreenW(context) * 4 / 5;
        window.setAttributes(lp);

    }

    @Override
    public void show() {
        super.show();
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
            }).playOn(ivDayang);
        } else {
            superDismiss();
        }
    }
    public void superDismiss() {
        super.dismiss();
    }

    private void animation( int mDuration) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(ivDayang, "alpha", 0, 1, 1, 1).setDuration(mDuration),
                ObjectAnimator.ofFloat(ivDayang, "scaleX", 0.5f, 1.05f, 0.95f, 1).setDuration(mDuration),
                ObjectAnimator.ofFloat(ivDayang, "scaleY", 0.5f, 1.05f, 0.95f, 1).setDuration(mDuration)
        );
        animatorSet.start();

    }

}
