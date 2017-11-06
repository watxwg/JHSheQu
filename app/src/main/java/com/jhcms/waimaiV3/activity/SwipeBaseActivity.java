package com.jhcms.waimaiV3.activity;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.aitangba.swipeback.SwipeBackActivity;
import com.jhcms.common.listener.PermissionListener;
import com.jhcms.common.utils.ActivityCollector;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.StatusBarUtil;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.waimaiV3.R;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

import static com.jhcms.common.utils.Utils.saveCrashInfo2File;


public abstract class SwipeBaseActivity extends SwipeBackActivity {
    public static String ORDER_ID = "ORDER_ID";

    private KProgressHUD dialog;
    private static PermissionListener mListener;
    public String TAG = "wangyujie";

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    private boolean isAndroidTV() {
        final UiModeManager uiModeManager = (UiModeManager) getSystemService(Activity.UI_MODE_SERVICE);
        return uiModeManager.getCurrentModeType() == Configuration.UI_MODE_TYPE_TELEVISION;
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (isAndroidTV()) {
            requestWindowFeature(Window.FEATURE_OPTIONS_PANEL);
        }
        super.onCreate(savedInstanceState);
        /*竖屏*/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent.setCatchUncaughtExceptions(true);
        ActivityCollector.addActivity(this);
        initView();
        onCrash();
    }

    public void onCrash() {
        Api.REGISTRATION_ID = JPushInterface.getRegistrationID(this);
        try {
            initData();
        } catch (Exception e) {
            ToastUtil.show("出现异常");
            saveCrashInfo2File(e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    /*处理数据*/
    protected abstract void initData();

    protected abstract void initView();

    public void showProgressDialog(Context context) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
        dialog = KProgressHUD.create(context).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setDimAmount(0.3f);

        dialog.show();
    }

    public void dismiss(Context context) {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToastUtil.hideToast();
        ActivityCollector.removeActivity(this);
    }

    public static void requestRuntimePermission(String[] permissions, PermissionListener listener) {
        Activity topActivity = ActivityCollector.getTopActivity();
        if (topActivity == null) {
            return;
        }
        mListener = listener;
        //拒绝权限的集合
        ArrayList<String> permissionList = new ArrayList<String>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(topActivity, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }
        if (!permissionList.isEmpty()) {
            for (String permission : permissionList) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(topActivity, permission)) {

                }
            }
            ActivityCompat.requestPermissions(topActivity, permissionList.toArray(new String[permissionList.size()]), 1);
        } else {
            mListener.onGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    List<String> deniedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            deniedPermissions.add(permission);
                        }
                    }
                    if (deniedPermissions.isEmpty()) {
                        mListener.onGranted();
                    } else {
                        mListener.onDenied(deniedPermissions);
                    }
                }
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            View v = getCurrentFocus();
            if (HideKeyboard(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean HideKeyboard(View view, MotionEvent event) {
        if (view != null && (view instanceof EditText)) {

            int[] location = {0, 0};
            view.getLocationInWindow(location);
            int left = location[0], top = location[1], bottom = top + view.getHeight(), right = left + view.getWidth();
            boolean isInEt = (event.getX() > left && event.getX() < right && event.getY() > top
                    && event.getY() < bottom);
            return !isInEt;
        }
        return false;
    }

    public void setToolBar(View view) {
        StatusBarUtil.immersive(this, R.color.theme_color, 0.6f);
        StatusBarUtil.setPaddingSmart(this, view);
    }
}
