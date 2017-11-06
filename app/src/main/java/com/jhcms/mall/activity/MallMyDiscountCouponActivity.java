package com.jhcms.mall.activity;

import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhcms.common.widget.BaseActivity;
import com.jhcms.mall.adapter.MallProductDetailsViewPagerAdapter;
import com.jhcms.mall.fragment.MallMyDiscountCouponFragment;
import com.jhcms.waimaiV3.R;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * 我的优惠券页面
 */
public class MallMyDiscountCouponActivity extends BaseActivity {
    private View mHeadview;
    private ImageView mIvBack;
    private TextView mtitleContent;
    private TabLayout mtabLayout;
    private ViewPager mViewPager;
    private  TextView mGetCoupon;
    private MallProductDetailsViewPagerAdapter mAdapter;
    private ArrayList<Fragment> mFrragments=new ArrayList<>();
    private  ArrayList<String> mTitles=new ArrayList<>();
    @Override
    protected void initView() {
        setContentView(R.layout.activity_mall_my_discount_coupon);
    }

    @Override
    protected void initData() {
        inintTabLayoutAndViewpager();
    }

    private void inintTabLayoutAndViewpager() {
        MallMyDiscountCouponFragment fragment1=new MallMyDiscountCouponFragment(0);//可用
        MallMyDiscountCouponFragment fragment2=new MallMyDiscountCouponFragment(1);//不可用
        mFrragments.add(fragment1);
        mFrragments.add(fragment2);
        mTitles.add("可使用");
        mTitles.add("不可用");
        mAdapter=new MallProductDetailsViewPagerAdapter(getSupportFragmentManager(),mFrragments,mTitles);
        mViewPager.setAdapter(mAdapter);
        mtabLayout.setupWithViewPager(mViewPager);


    }

    @Override
    protected void initActionBar() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MallMyDiscountCouponActivity.this.finish();
            }
        });
        mtitleContent.setText("我的优惠券");
    }

    @Override
    protected void initFindViewById() {
        mHeadview=this.findViewById(R.id.title);
        mIvBack= (ImageView) this.findViewById(R.id.back_iv);
        mtitleContent= (TextView) this.findViewById(R.id.title_tv);
        mtabLayout= (TabLayout) this.findViewById(R.id.sliding_tabs);
        mViewPager= (ViewPager) this.findViewById(R.id.viewpager);
        mGetCoupon= (TextView) this.findViewById(R.id.GetCoupon);
    }

    @Override
    protected void initEvent() {

    }
    @Override
    public void onStart() {
        super.onStart();
        mtabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(mtabLayout, 30, 30);
            }
        });
    }






    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }


    }
}
