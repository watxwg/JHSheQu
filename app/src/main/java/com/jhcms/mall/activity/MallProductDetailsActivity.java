package com.jhcms.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.jhcms.common.widget.BaseActivity;
import com.jhcms.common.widget.CustomViewpager;
import com.jhcms.common.widget.ListenerScrollView;
import com.jhcms.mall.adapter.MallProductDetailsViewPagerAdapter;
import com.jhcms.mall.fragment.MallProuductDetailsFragment;
import com.jhcms.mall.fragment.MallProuductEvaluateFragment;
import com.jhcms.waimaiV3.R;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MallProductDetailsActivity extends BaseActivity {
    private View mTitleview;
    private ImageView mIvBack;
    private TextView mTvtitle;
    private ImageView mIvRight;
    private ArrayList<String> images=new ArrayList<>();
    private    ConvenientBanner mBanner;
    private CustomViewpager viewpager;
    private MallProductDetailsViewPagerAdapter mAdapter;
    private  ArrayList<Fragment> list=new ArrayList<>();
    private ArrayList<String>titles=new ArrayList<>();
    private TabLayout mtablayout;
    private LinearLayout ShopCall,Shop,ShopCollect;
    private  ImageView collectimaage;
    private SpringView mRefreshLayout;
    private LinearLayout mTitlelayout;
    private  ListenerScrollView  mSrollview;
    private TextView mGetCoupon;
    private PopupWindow mSelectpopuWindow;
    private TextView mSelectedStandard;
    private  ImageView mBackHome;
    private  ImageView mBackShopCart;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_mall_product_details);
    }

    @Override
    protected void initData() {
        inintBanner();
        inintTablayoutAndViewPager();
        inintRefreshlayout();
        inintmSelectpopuWindow();
    }

    /**
     * 初始化mSelectpopuWindow
     */
    private void inintmSelectpopuWindow() {
        View view= LayoutInflater.from(MallProductDetailsActivity.this).inflate(R.layout.mall_prouduct_details_pupw,null);
        ImageView mColseiv= (ImageView) view.findViewById(R.id.pupwclose);
        TagFlowLayout mFlowlayout= (TagFlowLayout) view.findViewById(R.id.mFlowLayout);
        TagFlowLayout mFlowlayout2= (TagFlowLayout) view.findViewById(R.id.mFlowLayout2);
        final ArrayList<String> mDataList=new ArrayList<>();
        mDataList.add("5元");
        mDataList.add("10元");
        mDataList.add("15元");
        mDataList.add("20元");
        final LayoutInflater mInflater=LayoutInflater.from(MallProductDetailsActivity.this);
        mFlowlayout.setAdapter(new TagAdapter<String>(mDataList)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {

                TextView tv = (TextView) mInflater.inflate(R.layout.mall_item_flowlayout_layout,
                        parent, false);
                tv.setText(s);
                return tv;
            }
        });

        mFlowlayout2.setAdapter(new TagAdapter<String>(mDataList)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {

                TextView tv = (TextView) mInflater.inflate(R.layout.mall_item_flowlayout_layout,
                        parent, false);
                tv.setText(s);
                return tv;
            }
        });



        mSelectpopuWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        mSelectpopuWindow.setContentView(view);
        mSelectpopuWindow.setOutsideTouchable(true);
        mSelectpopuWindow.setBackgroundDrawable(new BitmapDrawable());
        mSelectpopuWindow.setFocusable(true);// 获取焦点
        mSelectpopuWindow.setClippingEnabled(false);
        mColseiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSelectpopuWindow!=null&&mSelectpopuWindow.isShowing()){
                    mSelectpopuWindow.dismiss();
                }
            }
        });
        TextView mMinus= (TextView) view.findViewById(R.id.tvminus);
        TextView mAdd= (TextView) view.findViewById(R.id.tvadd);
        final TextView mNumber= (TextView) view.findViewById(R.id.number_tv);
        mMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mNumber.getText()!=null&&Integer.parseInt(mNumber.getText().toString())>0){
                    int mCount=Integer.parseInt(mNumber.getText().toString())-1;
                    mNumber.setText(String.valueOf(mCount));
                }
            }
        });
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mNumber.getText()!=null&&Integer.parseInt(mNumber.getText().toString())<10){//ToDO 10式库存量
                    int mCount=Integer.parseInt(mNumber.getText().toString())+1;
                    mNumber.setText(String.valueOf(mCount));
                }
            }
        });

    }

    private void inintRefreshlayout() {
        mRefreshLayout.setGive(SpringView.Give.BOTH);
        mRefreshLayout.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MallProductDetailsActivity.this,"onRefresh",Toast.LENGTH_LONG).show();
                        mRefreshLayout.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MallProductDetailsActivity.this,"onRefresh",Toast.LENGTH_LONG).show();
                        mRefreshLayout.onFinishFreshAndLoad();
                    }
                }, 1000);

            }
        });
        mRefreshLayout.setHeader(new DefaultHeader(MallProductDetailsActivity.this));
        mRefreshLayout.setFooter(new DefaultFooter(MallProductDetailsActivity.this));
        mRefreshLayout.setType(SpringView.Type.FOLLOW);
    }

    private void inintTablayoutAndViewPager() {
        LinearLayout linearLayout = (LinearLayout) mtablayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.mall_layout_divider_vertical));
        MallProuductDetailsFragment DetailsFragment=new MallProuductDetailsFragment(viewpager);
        MallProuductEvaluateFragment EvaluateFragment=new MallProuductEvaluateFragment(viewpager);
        list.add(DetailsFragment);
        list.add(EvaluateFragment);
        titles.add("商品详情");
        titles.add("商品评价");
        mAdapter=new MallProductDetailsViewPagerAdapter(getSupportFragmentManager(),list,titles);
        viewpager.setAdapter(mAdapter);
        mtablayout.setupWithViewPager(viewpager);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewpager.resetHeight(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mtablayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(mtablayout, 50, 50);
            }
        });
    }

    public void setIndicator (TabLayout tabs, int leftDip, int rightDip){
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


    /*
      初始化bunner
       */
    private void inintBanner() {
        for (int i=0;i<4;i++){
            images.add("http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg");
        }

        mBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, images);    //设置需要切换的View
        mBanner.setPointViewVisible(true)  ;  //设置指示器是否可见
        mBanner.setPageIndicator(new int[]{R.mipmap.icon_banner_normal, R.mipmap.icon_banner_choose})  ; //设置指示器圆点
        mBanner.startTurning(2000);     //设置自动切换（同时设置了切换时间间隔）
        mBanner.stopTurning()   ; //关闭自动切换
        mBanner .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL) ;//设置指示器位置（左、中、右）
        mBanner .setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MallProductDetailsActivity.this,"ok",Toast.LENGTH_LONG).show();
            }
        }); //设置点击监听事件
//        mSrollview.setOnChangeListener(new ListenerScrollView.onScrollChanged() {
//            @Override
//            public void onChanged(int l, int t, int oldl, int oldt) {
//                /**
//                 * 顶部透明度渐变
//                 */
//                if (mBanner != null && mBanner.getHeight() > 0) {
//                    int height = mBanner.getHeight();
//                    if (t < height) {
//                        int alpha = (int) (new Float(t) / new Float(height) * 255);
//                        if (alpha <= 0) {
//                            alpha = 0;
//                        }
//                        mTitlelayout.getBackground().setAlpha(alpha);
//                    } else {
//                        mTitlelayout.getBackground().setAlpha(255);
//                    }
//                }
//            }
//        });

    }
    class NetworkImageHolderView implements Holder<String> {
        private ImageView imageView;
        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context,int position, String data) {
            Glide.with(context).load(data).into(imageView);

        }
    }
    @Override
    protected void initActionBar() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MallProductDetailsActivity.this.finish();
            }
        });
        mTvtitle.setText("商品详情");
        mIvRight.setImageResource(R.mipmap.mall_navbar_btn_share);
        mIvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MallProductDetailsActivity.this,"开始分享",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void initFindViewById() {
        mTitleview=this.findViewById(R.id.title_layout);
        mIvBack= (ImageView) mTitleview.findViewById(R.id.iv_back);
        mTvtitle= (TextView) mTitleview.findViewById(R.id.title_content);
        mIvRight= (ImageView) mTitleview.findViewById(R.id.RightImage);
        mBanner= (ConvenientBanner) this.findViewById(R.id.convenientBanner);
        viewpager= (CustomViewpager) this.findViewById(R.id.viewpager);
        mtablayout= (TabLayout) this.findViewById(R.id.tab_FindFragment_title);
        ShopCall= (LinearLayout) this.findViewById(R.id.Shop_call);
        Shop= (LinearLayout) this.findViewById(R.id.Shop);
        ShopCollect= (LinearLayout) this.findViewById(R.id.collect);
        ShopCollect.setTag(false);
        collectimaage= (ImageView) this.findViewById(R.id.collectimaage);
        mRefreshLayout= (SpringView) findViewById(R.id.refreshLayout);
        mTitlelayout= (LinearLayout) mTitleview. findViewById(R.id.title_layout);
        mSrollview= (ListenerScrollView) findViewById(R.id.scroll_view);
        mGetCoupon= (TextView) this.findViewById(R.id.getCoupon);
        mSelectedStandard= (TextView) this.findViewById(R.id.selectedstandard);
       mBackHome= (ImageView) this.findViewById(R.id.backHome);
        mBackShopCart= (ImageView) this.findViewById(R.id.BackShopCart_Iv);
    }

    @Override
    protected void initEvent() {
        inintbottomtablebar();
        inintGetCoupon();
        inintSelectedStandard();
        inintBackHomeEvent();
        inintBackShopCartEvent();
    }

    private void inintBackShopCartEvent() {
        mBackShopCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MallProductDetailsActivity.this,"回到购物车",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void inintBackHomeEvent() {
        mBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MallProductDetailsActivity.this.finish();
            }
        });
    }

    private void inintSelectedStandard() {
        mSelectedStandard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSelectpopuWindow!=null&!mSelectpopuWindow.isShowing()){
                    mSelectpopuWindow.showAtLocation(MallProductDetailsActivity.this.findViewById(R.id.linearlayout), Gravity.BOTTOM,0,0);
                }
            }
        });
    }

    private void inintGetCoupon() {
        mGetCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MallProductDetailsActivity.this,MallCouponActivity.class);
                MallProductDetailsActivity.this.startActivity(intent);
            }
        });
    }

    private void inintbottomtablebar() {
        ShopCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + "123456");
                intent.setData(data);
                MallProductDetailsActivity.this.startActivity(intent);
            }
        });
        Shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(MallProductDetailsActivity.this,"店铺",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(MallProductDetailsActivity.this,MallShopDetailsActivity.class);
                intent.putExtra("Collect",(Boolean) ShopCollect.getTag());
                startActivity(intent);
            }
        });
        ShopCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag= (boolean) ShopCollect.getTag();
                if(flag){
                    Toast.makeText(MallProductDetailsActivity.this,"取消关注",Toast.LENGTH_LONG).show();
                    collectimaage.setImageResource(R.mipmap.mall_btn_collect);
                    ShopCollect.setTag(false);
                }else {
                    Toast.makeText(MallProductDetailsActivity.this,"关注",Toast.LENGTH_LONG).show();
                    collectimaage.setImageResource(R.mipmap.mall_btn_collect_yes);
                    ShopCollect.setTag(true);
                }
            }
        });

    }
}
