package com.jhcms.shequ.activity;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.widget.NoSlideViewPager;
import com.jhcms.shequ.adapter.MainPagerAdapter;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;

public class MainActivity extends SwipeBaseActivity implements View.OnClickListener {

    ImageView mHomeIv, mMineIv, mTypeIv, mShopCarIv, mOrderIv;
    TextView mHomeTv, mMineTv, mTypeTv, mShopCarTv, mOrderTv;
    LinearLayout mHomeLl, mMineLl, mTypeLl, mShopCarLl, mOrderLl;
    NoSlideViewPager viewPager;

    private int CUR = 0;
    private MainPagerAdapter adapter;
    @Override
    protected void initData() {
        initViewPager();
    }
    protected void initView() {
        setContentView(R.layout.activity_main);
        mHomeIv = (ImageView) findViewById(R.id.img_home);
        mHomeTv = (TextView) findViewById(R.id.txt_home);
        mHomeLl = (LinearLayout) findViewById(R.id.home_page);

        mTypeIv = (ImageView) findViewById(R.id.img_type);
        mTypeTv = (TextView) findViewById(R.id.txt_type);
        mTypeLl = (LinearLayout) findViewById(R.id.type_page);

        mShopCarIv = (ImageView) findViewById(R.id.img_shopcar);
        mShopCarTv = (TextView) findViewById(R.id.txt_shopcar);
        mShopCarLl = (LinearLayout) findViewById(R.id.shopcar_page);

        mOrderIv = (ImageView) findViewById(R.id.img_order);
        mOrderTv = (TextView) findViewById(R.id.txt_order);
        mOrderLl = (LinearLayout) findViewById(R.id.order_page);

        mMineIv = (ImageView) findViewById(R.id.img_self);
        mMineTv = (TextView) findViewById(R.id.txt_self);
        mMineLl = (LinearLayout) findViewById(R.id.self_page);
        viewPager = (NoSlideViewPager) findViewById(R.id.viewpager);

        mHomeLl.setOnClickListener(this);
        mMineLl.setOnClickListener(this);
        mOrderLl.setOnClickListener(this);
        mTypeLl.setOnClickListener(this);
        mShopCarLl.setOnClickListener(this);
    }


    private void initViewPager() {
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
                this.CUR = 0;
                show(CUR);
                this.viewPager.setCurrentItem(0);
                break;
            case R.id.type_page:
                //     if(!Utils.isEmpty(Global.token)){
                this.CUR = 1;
                show(CUR);
                this.viewPager.setCurrentItem(1);
//                }else {
//                    Intent intent = new Intent();
//                    intent.setClass(this,LoginActivity.class);
//                    startActivity(intent);
//                }
                break;
            case R.id.shopcar_page:
                //     if(!Utils.isEmpty(Global.token)){
                this.CUR = 2;
                show(CUR);
                this.viewPager.setCurrentItem(2);
//                }else {
//                    Intent intent = new Intent();
//                    intent.setClass(this,LoginActivity.class);
//                    startActivity(intent);
//                }
                break;
            case R.id.order_page:
                //     if(!Utils.isEmpty(Global.token)){
                this.CUR = 3;
                show(CUR);
                this.viewPager.setCurrentItem(3);
//                }else {
//                    Intent intent = new Intent();
//                    intent.setClass(this,LoginActivity.class);
//                    startActivity(intent);
//                }
                break;
            case R.id.self_page:
                //     if(!Utils.isEmpty(Global.token)){
                this.CUR = 4;
                show(CUR);
                this.viewPager.setCurrentItem(4);
//                }else {
//                    Intent intent = new Intent();
//                    intent.setClass(this,LoginActivity.class);
//                    startActivity(intent);
//                }
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

        mOrderIv.setImageResource(position == 3 ? R.mipmap.icon_order_press
                : R.mipmap.icon_order);
        mOrderTv.setTextColor(getResources().getColor(
                position == 3 ? R.color.theme_color : R.color.second_txt_color));

        mMineIv.setImageResource(position == 4 ? R.mipmap.icon_mine_press
                : R.mipmap.icon_mine);
        mMineTv.setTextColor(getResources().getColor(
                position == 4 ? R.color.theme_color : R.color.second_txt_color));
    }

    private long time = 0;

    @Override
    public void onBackPressed() {
        long temp = System.currentTimeMillis();
        if (time == 0 || temp - time > 2000) {
            time = temp;
            ToastUtil.show(getString(R.string.再按一次退出程序));
        } else {
            finish();
        }
    }
}
