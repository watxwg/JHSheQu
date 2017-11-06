package com.jhcms.waimaiV3.dialog;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhcms.common.dialog.actionsheet.BaseAnimatorSet;
import com.jhcms.common.dialog.actionsheet.SlideBottomExit;
import com.jhcms.common.model.ShopHongBao;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.HongBaoAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangyujie
 * Date 2017/5/26.
 * TODO 天降红包Dialog
 */

public class HongBaoDialog extends Dialog {
    private final Context context;
    private final List<ShopHongBao.ItemsEntity> items;
    @Bind(R.id.rv_hongbao)
    RecyclerView rvHongbao;
    @Bind(R.id.tv_use)
    TextView tvUse;
    @Bind(R.id.ll_root)
    LinearLayout llRoot;


    /**
     * @param context
     * @param items
     */
    public HongBaoDialog(@NonNull Context context, List<ShopHongBao.ItemsEntity> items) {
        super(context, R.style.Dialog);
        this.context = context;
        this.items = items;
    }

    @OnClick({R.id.iv_close, R.id.tv_use})
    public void onViewClicked() {
        dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_hongbao_layout);
        ButterKnife.bind(this);
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = Utils.getScreenW(context) * 4 / 5;
        window.setAttributes(lp);
        initData();

    }

    private void initData() {
        HongBaoAdapter hongBaoAdapter = new HongBaoAdapter(context, items);
        if (items.size() > 2) {
            ViewGroup.LayoutParams layoutParams = rvHongbao.getLayoutParams();
            layoutParams.height = 240;
        }
        rvHongbao.setAdapter(hongBaoAdapter);
        rvHongbao.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void show() {
        super.show();
        animation(1000);
    }

    @Override
    public void dismiss() {
        SlideBottomExit exit = new SlideBottomExit();
        if (exit != null) {
            exit.listener(new BaseAnimatorSet.AnimatorListener() {
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
}
