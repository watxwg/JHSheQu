package com.jhcms.waimaiV3.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.jhcms.common.utils.StatusBarUtil;
import com.jhcms.waimaiV3.R;


/**
 * Created by admin on 2016/4/18.
 */
public abstract class CustomerBaseFragment extends WaiMai_BaseFragment {

    private boolean isCheckPermission = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }

    }

    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }

    /**
     * 不可见
     */
    protected void onInvisible() {

    }

    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract void lazyLoad();

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setTitle(R.string.notifyTitle)
                .setMessage(R.string.notifyMsg)
                // 拒绝, 退出应用
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                })
                .setPositiveButton(R.string.setting, (dialog, which) -> startAppSettings())
                .setCancelable(false);
        builder.show();
    }

    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    private void startAppSettings() {
        isCheckPermission = true;
        Uri packageURI = Uri.parse("package:" + getActivity().getPackageName());
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
        startActivity(intent);
    }

    public void setToolBar(View view) {
        StatusBarUtil.immersive(getActivity(), R.color.theme_color, 0f);
        StatusBarUtil.setPaddingSmart(getActivity(), view);
    }
}
