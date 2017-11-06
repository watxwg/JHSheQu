package com.jhcms.run.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.LogUtil;
import com.jhcms.run.fragment.Run_HomeFragment;
import com.jhcms.run.fragment.Run_MineFragment;
import com.jhcms.run.fragment.Run_OrderFragment;
import com.jhcms.shequ.activity.LoginActivity;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;
import com.jhcms.waimaiV3.fragment.WaiMai_BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RunMainActivity extends SwipeBaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.content)
    FrameLayout content;
    @Bind(R.id.navigation)
    BottomNavigationView navigation;
    private Run_HomeFragment homeFragment;
    private Run_OrderFragment orderFragment;
    private Run_MineFragment mineFragment;
    private List<WaiMai_BaseFragment> mBaseFragment;
    private WaiMai_BaseFragment fragment;
    private int postion = 0;
    /**
     * 上次切换的Fragment
     */
    private Fragment mFragment;
    private boolean isToLogin = false;
    @Override
    protected void initData() {
        initFragment();
        navigation.setOnNavigationItemSelectedListener(this);
    }
    private void initFragment() {
        homeFragment = new Run_HomeFragment();
        orderFragment = new Run_OrderFragment();
        mineFragment = new Run_MineFragment();
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(homeFragment);
        mBaseFragment.add(orderFragment);
        mBaseFragment.add(mineFragment);
        checked(0);
    }
    public void checked(int p) {
        WaiMai_BaseFragment to = getFragment(p);
        switchFragment(mFragment, to);
    }
    public WaiMai_BaseFragment getFragment(int position) {
        fragment = mBaseFragment.get(position);
        return fragment;
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
                    transaction.add(R.id.content, to).commit();
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
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tuan_home:
                postion = 0;
                checked(0);
                break;
            case R.id.tuan_order:
                if (TextUtils.isEmpty(Api.TOKEN)) {
                    isToLogin = true;
                    Intent intent = new Intent(RunMainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    postion = 1;
                    checked(1);
                }
                break;
            case R.id.tuan_mine:
                postion = 2;
                checked(2);
                break;
        }
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (isToLogin) {
            if (postion == 0) {
                navigation.setSelectedItemId(R.id.tuan_home);
            } else if (postion == 2) {
                navigation.setSelectedItemId(R.id.tuan_mine);
            }
            isToLogin = false;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String type = intent.getStringExtra("type");
        LogUtil.d("type" + type);
        /*订单详情返回时处理*/
        if (!TextUtils.isEmpty(type) && type.equals("ORDER")) {
            checked(1);
            navigation.setSelectedItemId(R.id.tuan_order);
        }

    }
    @Override
    protected void initView() {
        setContentView(R.layout.activity_run_main);
        ButterKnife.bind(this);

    }
}
