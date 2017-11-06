package com.jhcms.waimaiV3.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.ProgressDialogUtil;
import com.jhcms.common.utils.StatusBarUtil;
import com.jhcms.waimaiV3.R;
import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.JPushInterface;
//import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2016/12/13.
 */
public abstract class WaiMai_BaseFragment extends Fragment {
    protected Context mContext;
    /**
     * 是否跳到设置页面
     */
    public boolean isCheckPermission = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        MobclickAgent.setScenarioType(getContext(), MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent.setCatchUncaughtExceptions(true);
        Api.REGISTRATION_ID = JPushInterface.getRegistrationID(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView();
    }

    /**
     * 强制子类重写，实现子类特有的ui
     */

    protected abstract View initView();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            initData();
        } catch (Exception e) {
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getContext());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getContext());
    }

    /*当子类需要初始化数据，或者联网请求绑定数据，展示数据的 */
    protected void initData() {
    }

    public void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.notifyTitle);
        builder.setMessage(R.string.notifyMsg);

        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        builder.setPositiveButton(R.string.setting,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                });

        builder.setCancelable(false);

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

    public void ShowLoadingDialog() {
        ProgressDialogUtil.showProgressDialog(getContext());
    }

    public void DismissDialog() {
        ProgressDialogUtil.dismiss(getContext());
    }

    public void setToolBar(View view) {
        StatusBarUtil.immersive(getActivity(), R.color.theme_color, 0f);
        StatusBarUtil.setPaddingSmart(getActivity(), view);
    }
}
