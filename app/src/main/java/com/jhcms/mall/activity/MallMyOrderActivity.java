package com.jhcms.mall.activity;

import android.graphics.Matrix;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhcms.mall.adapter.ViewPagerAdapter;
import com.jhcms.mall.fragment.MallMyOrderFragment;
import com.jhcms.mall.model.MallOrderItemMode;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MallMyOrderActivity extends SwipeBaseActivity {
    public static String TYPE = "MALLMYORDERACTIVITY_TYPE";
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_all)
    TextView tvAll;
    @Bind(R.id.rl_all)
    RelativeLayout rlAll;
    @Bind(R.id.tv_payment)
    TextView tvPayment;
    @Bind(R.id.tv_payment_num)
    TextView tvPaymentNum;
    @Bind(R.id.rl_payment)
    RelativeLayout rlPayment;
    @Bind(R.id.tv_delivered)
    TextView tvDelivered;
    @Bind(R.id.tv_delivered_num)
    TextView tvDeliveredNum;
    @Bind(R.id.rl_delivered)
    RelativeLayout rlDelivered;
    @Bind(R.id.tv_paid)
    TextView tvPaid;
    @Bind(R.id.tv_paid_num)
    TextView tvPaidNum;
    @Bind(R.id.rl_paid)
    RelativeLayout rlPaid;
    @Bind(R.id.tv_evaluated)
    TextView tvEvaluated;
    @Bind(R.id.tv_evaluated_num)
    TextView tvEvaluatedNum;
    @Bind(R.id.rl_evaluated)
    RelativeLayout rlEvaluated;
    @Bind(R.id.tv_refunds)
    TextView tvRefunds;
    @Bind(R.id.tv_refunds_num)
    TextView tvRefundsNum;
    @Bind(R.id.rl_refunds)
    RelativeLayout rlRefunds;
    @Bind(R.id.img_bottom_line)
    ImageView imgBottomLine;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private int currImagex;// 线条距离y主坐标
    private ViewPagerAdapter mAdapter;

    private int endx = 0;//图片最终滑动的距离x轴距离
    float offset;// 动画图片偏移量
    private int screenW = 0;
    private int selectPosition = 0;
    /**
     * 0:全部
     * 1：待付款
     * 2：待发货
     * 3：待收货
     * 4：待评价
     * 5：退款/售后
     */
    private int type;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_mall_my_order);
        ButterKnife.bind(this);
    }


    @Override
    protected void initData() {
        setToolBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        tvTitle.setText("我的订单");
        type = getIntent().getIntExtra(TYPE, 0);
        inintTabLayoutAndViewPager();
        initImageView();
        select(type);
    }


    private void inintTabLayoutAndViewPager() {
        for (int i = 0; i < 6; i++) {
            mFragments.add(new MallMyOrderFragment(i));
        }
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragments);
        viewpager.setAdapter(mAdapter);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                select(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    /**
     * 选项卡的偏移量
     */
    public void ss(int currIndex, int endX) {
        Animation animation = new TranslateAnimation(currIndex, endX, 0, 0);
        currImagex = endX;
        animation.setFillAfter(true);// True:图片停在动画结束位置
        animation.setDuration(300);
        imgBottomLine.startAnimation(animation);
    }

    //初始化线条滑动最终点
    public void imageLineslide(int currradioButton) {
        switch (currradioButton) {
            case 0:
                endx = (int) offset;
                break;
            case 1:
                endx = (int) (screenW / 6 + offset);
                break;
            case 2:
                endx = (int) offset + screenW / 6 * 2;
                break;
            case 3:
                endx = (int) offset + screenW / 6 * 3;
                break;
            case 4:
                endx = (int) offset + screenW / 6 * 4;
                break;
            case 5:
                endx = (int) offset + screenW / 6 * 5;
                break;
        }
    }

    public void changeTabs(int postion) {
        switch (postion) {
            case 0:
                tvAll.setSelected(true);
                tvPayment.setSelected(false);
                tvDelivered.setSelected(false);
                tvPaid.setSelected(false);
                tvEvaluated.setSelected(false);
                tvRefunds.setSelected(false);
                break;
            case 1:
                tvAll.setSelected(false);
                tvPayment.setSelected(true);
                tvDelivered.setSelected(false);
                tvPaid.setSelected(false);
                tvEvaluated.setSelected(false);
                tvRefunds.setSelected(false);
                break;
            case 2:
                tvAll.setSelected(false);
                tvPayment.setSelected(false);
                tvDelivered.setSelected(true);
                tvPaid.setSelected(false);
                tvEvaluated.setSelected(false);
                tvRefunds.setSelected(false);
                break;
            case 3:
                tvAll.setSelected(false);
                tvPayment.setSelected(false);
                tvDelivered.setSelected(false);
                tvPaid.setSelected(true);
                tvEvaluated.setSelected(false);
                tvRefunds.setSelected(false);
                break;
            case 4:
                tvAll.setSelected(false);
                tvPayment.setSelected(false);
                tvDelivered.setSelected(false);
                tvPaid.setSelected(false);
                tvEvaluated.setSelected(true);
                tvRefunds.setSelected(false);
                break;
            case 5:
                tvAll.setSelected(false);
                tvPayment.setSelected(false);
                tvDelivered.setSelected(false);
                tvPaid.setSelected(false);
                tvEvaluated.setSelected(false);
                tvRefunds.setSelected(true);
                break;
        }
    }

    public void initImageView() {
        DisplayMetrics dm = new DisplayMetrics();
        MallMyOrderActivity.this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenW = dm.widthPixels;
        currImagex = (int) (offset = 0);
        Matrix matrix = new Matrix();
        matrix.postTranslate(0, 0);
        imgBottomLine.setImageMatrix(matrix);// 设置动画初始位置
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (selectPosition != 0) {
            select(selectPosition);
        }
    }

    @OnClick({R.id.rl_all, R.id.rl_payment, R.id.rl_delivered, R.id.rl_paid, R.id.rl_evaluated, R.id.rl_refunds})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_all:
                selectPosition = 0;
                break;
            case R.id.rl_payment:
                selectPosition = 1;
                break;
            case R.id.rl_delivered:
                selectPosition = 2;
                break;
            case R.id.rl_paid:
                selectPosition = 3;
                break;
            case R.id.rl_evaluated:
                selectPosition = 4;
                break;
            case R.id.rl_refunds:
                selectPosition = 5;
                break;
        }
        select(selectPosition);
    }

    private void select(int position) {
        viewpager.setCurrentItem(position);
        imageLineslide(position);
        changeTabs(position);
        ss(currImagex, endx);
    }

    public void setOrderNum(MallOrderItemMode.CountBean count) {
        /*待付款*/
        if (!TextUtils.isEmpty(count.need_pay) && !"0".equals(count.need_pay)) {
            tvPaymentNum.setText(count.need_pay);
            tvPaymentNum.setVisibility(View.VISIBLE);
        } else {
            tvPaymentNum.setVisibility(View.GONE);
        }

        /*待发货*/
        if (!TextUtils.isEmpty(count.need_fahuo) && !"0".equals(count.need_fahuo)) {
            tvDeliveredNum.setText(count.need_fahuo);
            tvDeliveredNum.setVisibility(View.VISIBLE);
        } else {
            tvDeliveredNum.setVisibility(View.GONE);
        }

        /*代收款*/
        if (!TextUtils.isEmpty(count.need_shouhuo) && !"0".equals(count.need_shouhuo)) {
            tvPaidNum.setText(count.need_shouhuo);
            tvPaidNum.setVisibility(View.VISIBLE);
        } else {
            tvPaidNum.setVisibility(View.GONE);
        }

        /*待评价*/
        if (!TextUtils.isEmpty(count.need_comment) && !"0".equals(count.need_comment)) {
            tvEvaluatedNum.setText(count.need_comment);
            tvEvaluatedNum.setVisibility(View.VISIBLE);
        } else {
            tvEvaluatedNum.setVisibility(View.GONE);
        }
        /*退款售后*/
        if (!TextUtils.isEmpty(count.refund) && !"0".equals(count.refund)) {
            tvRefundsNum.setText(count.refund);
            tvRefundsNum.setVisibility(View.VISIBLE);
        } else {
            tvRefundsNum.setVisibility(View.GONE);
        }
    }
}
