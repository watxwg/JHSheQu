package com.jhcms.mall.activity;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.jhcms.common.widget.BaseActivity;
import com.jhcms.common.widget.CustomViewpager;
import com.jhcms.common.widget.GridViewForScrollView;
import com.jhcms.mall.adapter.MallShopDivisionAdapter;
import com.jhcms.mall.adapter.ViewPagerAdapter;
import com.jhcms.mall.fragment.MallShopDivisionFragment;
import com.jhcms.waimaiV3.R;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;

/**
 * 活动专区
 */
public class MallShopDivisionActivity extends BaseActivity {
    private View titleview;
    private ImageView mIvBack;
    private TextView mTvtitle;
    private ArrayList<Integer>images=new ArrayList<>();
    private ConvenientBanner mBanner;
    private SpringView mRefreshLayout;
    private CustomViewpager mViewpager;
    private ViewPagerAdapter mViewpagerAdapter;
    private  ImageView mIcon1,mIcon2,mIcon3,mIcon4;
    private MallShopDivisionAdapter mGrildAdapter;
    private GridViewForScrollView mDataGridView;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_mall_shop_division);
    }

    @Override
    protected void initData() {
        inintBanner();
        inintRefreshlayout();
        inintViewpager();
        inintMdataGrildView();

    }

    private void inintMdataGrildView() {
        mGrildAdapter=new MallShopDivisionAdapter(MallShopDivisionActivity.this);
        mDataGridView.setAdapter(mGrildAdapter);
    }

    private void inintViewpager() {
        MallShopDivisionFragment mFragment1=new MallShopDivisionFragment(mViewpager,0);
        MallShopDivisionFragment mFragment2=new MallShopDivisionFragment(mViewpager,1);
        final ArrayList<Fragment> mFragments=new ArrayList<>();
        mFragments.add(mFragment1);
        mFragments.add(mFragment2);
        mViewpagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),mFragments);
        mViewpager.setAdapter(mViewpagerAdapter);
        // final ImageView[] dots=new ImageView[mFragments.size()];
        final ImageView[] dots= upadtedotsVistable(mFragments.size());
        mViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < mFragments.size(); i++) {
                    if (position == i) {
                        dots[i].setImageResource(R.mipmap.mall_activity_slider_oval_c);
                    } else {
                        dots[i].setImageResource(R.mipmap.mall_activity_slider_oval_d);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private ImageView[] upadtedotsVistable(int size) {
        ImageView[] dots=new ImageView[size];
        switch (size){
            case 1:

                mIcon1.setVisibility(View.VISIBLE);
                dots[0]=mIcon1;

            break;
            case 2:
                mIcon1.setVisibility(View.VISIBLE);
                mIcon2.setVisibility(View.VISIBLE);
                dots[0]=mIcon1;
                dots[1]=mIcon2;
             break;
            case 3:
                mIcon2.setVisibility(View.VISIBLE);
                mIcon1.setVisibility(View.VISIBLE);
                mIcon3.setVisibility(View.VISIBLE);
                dots[0]=mIcon1;
                dots[1]=mIcon2;
                dots[2]=mIcon3;
                break;
            case 4:
                mIcon2.setVisibility(View.VISIBLE);
                mIcon1.setImageResource(View.VISIBLE);
                mIcon3.setImageResource(View.VISIBLE);
                mIcon4.setImageResource(View.VISIBLE);
                dots[0]=mIcon1;
                dots[1]=mIcon2;
                dots[2]=mIcon3;
                dots[3]=mIcon4;
                break;
        }
        return  dots;
    }

    private void inintRefreshlayout() {
        mRefreshLayout.setGive(SpringView.Give.BOTH);
        mRefreshLayout.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MallShopDivisionActivity.this,"onRefresh",Toast.LENGTH_LONG).show();
                        mRefreshLayout.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MallShopDivisionActivity.this,"onRefresh",Toast.LENGTH_LONG).show();
                        mRefreshLayout.onFinishFreshAndLoad();
                    }
                }, 1000);

            }
        });
        mRefreshLayout.setHeader(new DefaultHeader(MallShopDivisionActivity.this));
        mRefreshLayout.setFooter(new DefaultFooter(MallShopDivisionActivity.this));
        mRefreshLayout.setType(SpringView.Type.FOLLOW);
    }
    private void inintBanner() {
        for (int i=0;i<4;i++){
            images.add(R.mipmap.activity_banner);
        }
        mBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, images);    //设置需要切换的View
        mBanner.setPointViewVisible(true)  ;  //设置指示器是否可见
        mBanner.setPageIndicator(new int[]{R.mipmap.mall_activity_slider_c, R.mipmap.mall_activity_slider_d})  ; //设置指示器圆点
        mBanner.startTurning(2000);     //设置自动切换（同时设置了切换时间间隔）
        mBanner .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL) ;//设置指示器位置（左、中、右）
        mBanner .setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MallShopDivisionActivity.this,"ok", Toast.LENGTH_LONG).show();
            }
        }); //设置点击监听事件


    }
    class NetworkImageHolderView implements Holder<Integer> {
        private ImageView imageView;
        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context,int position, Integer data) {
            Glide.with(context).load(data).into(imageView);

        }
    }
    @Override
    protected void initActionBar() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MallShopDivisionActivity.this.finish();
            }
        });
        mTvtitle.setText("活动专区");
    }

    @Override
    protected void initFindViewById() {
        titleview=this.findViewById(R.id.title);
        mIvBack= (ImageView) titleview.findViewById(R.id.back_iv);
        mTvtitle= (TextView) titleview.findViewById(R.id.title_tv);
        mBanner= (ConvenientBanner) this.findViewById(R.id.convenientBanner);
        mRefreshLayout= (SpringView) findViewById(R.id.refreshlayout);
        mViewpager= (CustomViewpager) this.findViewById(R.id.viewpager);
        mIcon1= (ImageView) this.findViewById(R.id.icon1);
        mIcon2= (ImageView) this.findViewById(R.id.icon2);
        mIcon3= (ImageView) this.findViewById(R.id.icon3);
        mIcon4= (ImageView) this.findViewById(R.id.icon4);
        mDataGridView= (GridViewForScrollView) this.findViewById(R.id.hotshop_gv);
    }

    @Override
    protected void initEvent() {

    }
}
