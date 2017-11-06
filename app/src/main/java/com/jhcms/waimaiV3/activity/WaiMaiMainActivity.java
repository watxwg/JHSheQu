package com.jhcms.waimaiV3.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.jhcms.common.model.Response_Get_Adv;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.DownUtils;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.shequ.activity.LoginActivity;
import com.jhcms.shequ.model.EventBusEventModel;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.dialog.CallDialog;
import com.jhcms.waimaiV3.fragment.WaiMai_BaseFragment;
import com.jhcms.waimaiV3.fragment.WaiMai_HomeFragment;
import com.jhcms.waimaiV3.fragment.WaiMai_MineFragment;
import com.jhcms.waimaiV3.fragment.WaiMai_OrderFragment;
import com.jhcms.waimaiV3.fragment.WaiMai_ShopCarFragment;
import com.orhanobut.hawk.Hawk;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jhcms.common.utils.Api.CLIENT_ADV_START;
import static com.jhcms.common.utils.Utils.saveCrashInfo2File;

/**
 * Created by Wyj on 2017/5/9
 * TODO:外卖模块 WaiMaiMainActivity
 */
public class WaiMaiMainActivity extends SwipeBaseActivity {

    public static String TYPE = "TYPE";
    @Bind(R.id.contentLayout)
    FrameLayout contentLayout;
    @Bind(R.id.ll_home)
    LinearLayout llHome;
    @Bind(R.id.ll_shopcar)
    LinearLayout llShopcar;
    @Bind(R.id.ll_order)
    LinearLayout llOrder;
    @Bind(R.id.ll_mine)
    LinearLayout llMine;

    private List<WaiMai_BaseFragment> mBaseFragment;
    /**
     * 选中的Fragment的对应的位置
     */
    public static WaiMaiMainActivity instance;

    private int mCurrentIndex = -1;
    private final int GET_AD = 1;
    /**
     * 上次切换的Fragment
     */
    private Fragment mFragment;
    private WaiMai_BaseFragment fragment;
    private WaiMai_HomeFragment mHomeFragment;
    private WaiMai_OrderFragment mOrderFragment;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_AD:
                    getAd();
                    break;
            }
        }
    };

    @Override
    protected void initData() {
        instance = this;
        initFragment();
    }


    @Override
    protected void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_waimai_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mHandler.sendEmptyMessage(GET_AD);
    }

    private void initFragment() {
        mHomeFragment = new WaiMai_HomeFragment();
        mOrderFragment = new WaiMai_OrderFragment();
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(mHomeFragment);
        mBaseFragment.add(new WaiMai_ShopCarFragment());
        mBaseFragment.add(mOrderFragment);
        mBaseFragment.add(new WaiMai_MineFragment());
        checked(0);
    }

    /**
     * 获取通知栏权限
     * 如果没有打开通知权限则跳到设置界面
     */
    public void initNotification() {
        boolean enabled = Utils.isNotificationEnabled(this);
        if (!enabled) {
            new CallDialog(this)
                    .setMessage("开启通知栏权限能够及时收到订单信息")
                    .setTipTitle("获取通知的权限")
                    .setPositiveButton("确定", v -> Utils.toSetting(WaiMaiMainActivity.this))
                    .setNegativeButton("取消", null)
                    .show();
        }
    }


    public void checked(int p) {
        setSelected(p);
        mCurrentIndex = p;
        WaiMai_BaseFragment to = getFragment(p);
        switchFragment(mFragment, to);
    }

    /**
     * @param from 刚显示的Fragment,马上就要被隐藏了
     * @param to   马上要切换到的Fragment，一会要显示
     */
    private void switchFragment(Fragment from, Fragment to) {
        if (from != to) {//才切换
            mFragment = to;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            if (!to.isAdded()) {//to没被添加
                //隐藏from
                if (from != null) {
                    transaction.hide(from);
                }
                //添加to
                if (to != null) {
                    transaction.add(R.id.contentLayout, to).commit();
                }
            } else {
                //隐藏from
                if (from != null) {
                    transaction.hide(from);
                }
                //显示to
                if (to != null) {
                    transaction.show(to).commit();
                }
            }
        }
    }

    public void setSelected(int selected) {
        if (mCurrentIndex == selected) {
            alreadyAtFragment(mCurrentIndex);
        }
        llHome.setSelected(selected == 0 ? true : false);
        llShopcar.setSelected(selected == 1 ? true : false);
        llOrder.setSelected(selected == 2 ? true : false);
        llMine.setSelected(selected == 3 ? true : false);
    }

    public WaiMai_BaseFragment getFragment(int position) {
        fragment = mBaseFragment.get(position);
        return fragment;
    }

    @OnClick({R.id.ll_home, R.id.ll_shopcar, R.id.ll_order, R.id.ll_mine})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_home:
                checked(0);
                break;
            case R.id.ll_shopcar:
                checked(1);
                break;
            case R.id.ll_order:
                if (!TextUtils.isEmpty(Api.TOKEN)) {
                    checked(2);
                } else {
                    Utils.GoLogin(this);
                }
                break;
            case R.id.ll_mine:
                checked(3);
                break;
        }
    }

    private void alreadyAtFragment(int currentIndex) {
        //如果在当前页
        switch (currentIndex) {
            case 0:
                mHomeFragment.scrollToTop();
                break;
            case 1:
//                Toast.makeText(WaiMaiMainActivity.this, "购物车", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                mOrderFragment.scrollToTop();
//                Toast.makeText(WaiMaiMainActivity.this, "订单", Toast.LENGTH_SHORT).show();
                break;
            case 3:
//                Toast.makeText(WaiMaiMainActivity.this, "我的", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        DownUtils.cancleOkGo(this);
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (null != intent) {
            if (!TextUtils.isEmpty(intent.getStringExtra(TYPE)) && intent.getStringExtra(TYPE).equals("GO_ORDER")) {
                checked(2);
            }
        }

    }

    private long time = 0;

    @Override
    public void onBackPressed() {
        long temp = System.currentTimeMillis();
        if (mCurrentIndex == 0) {
            /*if (time == 0 || temp - time > 2000) {
                time = temp;
                ToastUtil.show(getString(R.string.再按一次退出程序));
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                finish();
            }*/
            finish();
        } else {
            checked(0);
        }

    }

    @Subscribe
    public void onEventMainThread(EventBusEventModel event) {
        if (event.getType() == 1) {
            Intent intent = new Intent(WaiMaiMainActivity.this, LoginActivity.class);
            startActivity(intent);
            ((WaiMai_MineFragment) mBaseFragment.get(3)).updaLoginStatu();
        }
    }

    /**
     * 获取App启动页广告信息
     */
    private void getAd() {
        HttpUtils.postUrl(this, CLIENT_ADV_START, null, false, new OnRequestSuccessCallback() {
            @Override
            public void onSuccess(String url, String Json) {
                try {
                    Gson gson = new Gson();
                    Response_Get_Adv getAdv = gson.fromJson(Json, Response_Get_Adv.class);
                    if (getAdv.error.equals("0")) {
                        if (null != getAdv.data.items && getAdv.data.items.size() > 0) {
                            Hawk.put("advkey", getAdv.data.items);
                        }
                    } else {
                        ToastUtil.show(getAdv.message);
                    }


                } catch (Exception e) {
                    ToastUtil.show(getString(R.string.数据解析异常));
                    saveCrashInfo2File(e);
                }

            }

            @Override
            public void onBeforeAnimate() {

            }

            @Override
            public void onErrorAnimate() {

            }
        });
    }
}
