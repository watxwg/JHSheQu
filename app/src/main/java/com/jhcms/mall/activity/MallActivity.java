package com.jhcms.mall.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.NoSlideViewPager;
import com.jhcms.mall.adapter.MainPagerAdapter;
import com.jhcms.mall.model.EventBusModel;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


/**
 * Created by Administrator on 2017/4/11.
 */
public class MallActivity extends SwipeBaseActivity implements View.OnClickListener {
    ImageView mHomeIv, mMineIv, mTypeIv, mShopCarIv;
    TextView mHomeTv, mMineTv, mTypeTv, mShopCarTv;
    LinearLayout mHomeLl, mMineLl, mTypeLl, mShopCarLl;
    NoSlideViewPager viewPager;
    private LinearLayout mTabLayoutview;
    private int mBackShopCartFragmetFlag = -1;

    private int CUR = 0;
    private MainPagerAdapter adapter;


    protected void initView() {
        setContentView(R.layout.activity_mall);
        EventBus.getDefault().register(this);

        mHomeIv = (ImageView) findViewById(R.id.img_home);
        mHomeTv = (TextView) findViewById(R.id.txt_home);
        mHomeLl = (LinearLayout) findViewById(R.id.home_page);

        mTypeIv = (ImageView) findViewById(R.id.img_type);
        mTypeTv = (TextView) findViewById(R.id.txt_type);
        mTypeLl = (LinearLayout) findViewById(R.id.type_page);

        mShopCarIv = (ImageView) findViewById(R.id.img_shopcar);
        mShopCarTv = (TextView) findViewById(R.id.txt_shopcar);
        mShopCarLl = (LinearLayout) findViewById(R.id.shopcar_page);

        mMineIv = (ImageView) findViewById(R.id.img_self);
        mMineTv = (TextView) findViewById(R.id.txt_self);
        mMineLl = (LinearLayout) findViewById(R.id.self_page);
        viewPager = (NoSlideViewPager) findViewById(R.id.viewpager);

        mHomeLl.setOnClickListener(this);
        mMineLl.setOnClickListener(this);
        mTypeLl.setOnClickListener(this);
        mShopCarLl.setOnClickListener(this);
        mTabLayoutview = (LinearLayout) this.findViewById(R.id.tabLayoutview);

    }

    protected void initData() {

        adapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            public void onPageSelected(int num) {
                show(num);
            }
        });
        viewPager.setCurrentItem(CUR);
        show(CUR);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_page:
                mBackShopCartFragmetFlag = CUR;
                this.CUR = 0;
                show(CUR);
                this.viewPager.setCurrentItem(0);
                break;
            case R.id.type_page:
                this.CUR = 1;
                mBackShopCartFragmetFlag = CUR;
                show(CUR);
                this.viewPager.setCurrentItem(1);
                break;
            case R.id.shopcar_page:
                if (TextUtils.isEmpty(Api.TOKEN)) {
                    Utils.GoLogin(this);
                } else {
                    this.CUR = 2;
                    show(CUR);
                    this.viewPager.setCurrentItem(2);
                }
                break;
            case R.id.self_page:
                this.CUR = 3;
                mBackShopCartFragmetFlag = CUR;
                show(CUR);
                this.viewPager.setCurrentItem(3);
                break;
        }

    }


    private void show(int position) {
        mHomeIv.setImageResource(position == 0 ? R.mipmap.icon_home_press
                : R.mipmap.icon_home);
        mHomeTv.setTextColor(getResources().getColor(
                position == 0 ? R.color.theme_color : R.color.second_txt_color));

        mTypeIv.setImageResource(position == 1 ? R.mipmap.icon_type_press
                : R.mipmap.icon_type);
        mTypeTv.setTextColor(getResources().getColor(
                position == 1 ? R.color.theme_color : R.color.second_txt_color));

        mShopCarIv.setImageResource(position == 2 ? R.mipmap.icon_shopcar_press
                : R.mipmap.icon_shopcar);
        mShopCarTv.setTextColor(getResources().getColor(
                position == 2 ? R.color.theme_color : R.color.second_txt_color));


        mMineIv.setImageResource(position == 3 ? R.mipmap.icon_mine_press
                : R.mipmap.icon_mine);
        mMineTv.setTextColor(getResources().getColor(
                position == 3 ? R.color.theme_color : R.color.second_txt_color));
    }

    @Subscribe
    public void onEventMainThread(EventBusModel event) {
        if (event.getType() == 0) {
            show(mBackShopCartFragmetFlag);
            viewPager.setCurrentItem(mBackShopCartFragmetFlag);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(MallActivity.this);
    }
    private long time = 0;
    @Override
    public void onBackPressed() {
        long temp = System.currentTimeMillis();
        if (time == 0 || temp - time > 2000) {
            time = temp;
            ToastUtil.show(getString(R.string.再按一次退出程序));
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            finish();
        }
    }
}
