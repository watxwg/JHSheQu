package com.jhcms.waimaiV3.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.classic.common.MultipleStatusView;
import com.coorchice.library.SuperTextView;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jhcms.common.dialog.actionsheet.BaseBubblePopup;
import com.jhcms.common.model.Response_WaiMai_Home;
import com.jhcms.common.model.SharedResponse;
import com.jhcms.common.model.ShopDetail;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.DesignViewUtils;
import com.jhcms.common.utils.DividerListItemDecoration;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.StatusBarUtil;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.shequ.model.EventBusEventModel;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.CouponsViewPagerAdapter;
import com.jhcms.waimaiV3.adapter.MinatoAdapter;
import com.jhcms.waimaiV3.adapter.SelectAdapter;
import com.jhcms.waimaiV3.dialog.CallDialog;
import com.jhcms.waimaiV3.dialog.DaYangDialog;
import com.jhcms.waimaiV3.dialog.ShareDialog;
import com.jhcms.waimaiV3.fragment.WaiMaiShopDetailsFragment;
import com.jhcms.waimaiV3.fragment.WaiMaiShopEvaluationFragment;
import com.jhcms.waimaiV3.fragment.WaiMaiShopGoodsFragment;
import com.jhcms.waimaiV3.fragment.WaiMai_HomeFragment;
import com.jhcms.waimaiV3.litepal.Product;
import com.jhcms.waimaiV3.litepal.Shop;
import com.jhcms.waimaiV3.model.Goods;
import com.jhcms.waimaiV3.model.MessageEvent;
import com.jhcms.waimaiV3.model.ShareItem;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jhcms.common.utils.Utils.saveCrashInfo2File;

/**
 * Created by Wyj on 2017/5/9
 * TODO:商家界面
 */
public class WaiMaiShopActivity extends BaseActivity implements ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener, OnRequestSuccessCallback {
    /**
     * 上个界面传shopId的标志
     */
    public static String SHOP_ID = "TYPE";
    /**
     * 再来一单标记 商品json字符串
     */
    public static String PRODUCT_INFO = "PRODUCT_INFO";
    /**
     * GuiGeDialog 通知ShopActivity刷新商品数据的标志
     */
    public static String REFRESH_GOODS = "REFRESH THE GOODS";
    /**
     * 首页热销传过来的商品product_id
     */
    public static String PRODUCT_ID = "PRODUCT_ID";
    @Bind(R.id.iv_shop_logo)
    ImageView ivShopLogo;
    @Bind(R.id.tv_shop_name)
    TextView tvShopName;
    @Bind(R.id.tv_shop_status)
    SuperTextView tvShopStatus;
    @Bind(R.id.ll_shop_info)
    LinearLayout llShopInfo;
    @Bind(R.id.tv_shop_delivery)
    TextView tvShopDelivery;
    @Bind(R.id.tv_notice)
    TextView tvNotice;
    @Bind(R.id.ll_notice)
    LinearLayout llNotice;
    @Bind(R.id.head_layout)
    LinearLayout headLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.toolbar_tab)
    TabLayout toolbarTab;
    @Bind(R.id.appBar)
    AppBarLayout appBar;
    @Bind(R.id.main_vp_container)
    ViewPager mainVpContainer;
    @Bind(R.id.root_layout)
    CoordinatorLayout rootLayout;
    @Bind(R.id.imgCart)
    ImageView imgCart;
    @Bind(R.id.tvCount)
    TextView tvCount;
    @Bind(R.id.rl_shopCart)
    RelativeLayout rlShopCart;
    @Bind(R.id.tvCost)
    TextView tvCost;
    @Bind(R.id.tvTips)
    TextView tvTips;
    @Bind(R.id.tvSubmit)
    TextView tvSubmit;
    @Bind(R.id.iv_coucou)
    ImageView ivCoucou;
    @Bind(R.id.ll_bottom)
    LinearLayout llBottom;
    @Bind(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @Bind(R.id.content_view)
    ViewGroup anim_mask_layout;
    @Bind(R.id.bottomShopCartSheetLayout)
    BottomSheetLayout shopCartLayout;
    @Bind(R.id.minatoLayout)
    BottomSheetLayout minatoLayout;
    @Bind(R.id.bottomFormatSheetLayout)
    BottomSheetLayout formatLayout;
    @Bind(R.id.contentLayout)
    FrameLayout contentLayout;
    @Bind(R.id.tv_huodong_num)
    TextView tvHuodongNum;
    @Bind(R.id.main_multiplestatusview)
    MultipleStatusView multipleStatusView;
    @Bind(R.id.ll_huodong)
    LinearLayout llHuodong;
    @Bind(R.id.iv_more)
    ImageView ivMore;
    @Bind(R.id.marquee_view)
    ViewFlipper marqueeView;

    private ArrayList<Fragment> fragments;
    private String[] tabTitles = new String[]{"商品", "评价", "详情"};
    public static WaiMaiShopActivity instance = null;
    private static SparseIntArray groupSelect;
    private ShareItem shareItems;
    private boolean isAppBarLayoutOpen;
    private boolean isAppBarLayoutClose;
    private String shop_id;
    private ShopDetail shopDetail;
    private List<ShopDetail.ItemsEntity> items;
    private Response_WaiMai_Home shopData;
    private String pei_type;
    private int status;
    private String product_info;
    private TextView tvPagePrice;
    private ShareDialog dialog;
    private int type_Id;
    /**
     * 判断是否让凑凑列表显示
     */
//    private boolean needShowCou = false;
    /**
     * 判断凑一凑 是否正在显示
     */
    private boolean isShowCou = false;

    /**
     * 打包费
     */
    private double pagePrice;
    private MinatoAdapter minatoAdapter;
    private boolean isHindeCou = false;
    /**
     * 首页热销传过来的product_id
     */
    private String product_id;
    private static SparseArray<Goods> specList;
    /*保存商品从第start个分类开始保存*/
    private int start = 1;


    @Override
    protected void onResume() {
        super.onResume();
        /**
         * 从商品详情界面返回过来后要刷新
         *
         * */
        if (Api.isUpDate) {
            update(true);
            Api.isUpDate = false;
        }


    }

    public static SparseArray<Goods> getSpecList() {
        return specList;
    }

    public static SparseArray<Goods> getSelectedList() {
        return selectedList;
    }

    /**
     * 获取分类数据
     *
     * @return
     */
    public static SparseIntArray getGroupSelect() {
        return groupSelect;
    }

    private static SparseArray<Goods> selectedList;
    private Handler mHanlder;
    private NumberFormat nf;
    private RecyclerView rvSelected;

    /**
     * 购物车View
     */
    private View shopCartSheet;
    /**
     * 凑一凑View
     */
    private View minatoSheet;
    private SelectAdapter selectAdapter;
    private static boolean appBarLayoutClose;
    /**
     * 选中的Fragment的对应的位置
     */
    private int mCurrentIndex = -1;
    private Fragment fragment;
    /**
     * 上次切换的Fragment
     */
    private Fragment mFragment;
    private WaiMaiShopGoodsFragment shopGoodsFragment;
    private WaiMaiShopEvaluationFragment shopEvaluationFragment;
    private WaiMaiShopDetailsFragment shopDetailsFragment;
    private double totalAmount = 0;
    private DividerListItemDecoration divider;
    /**
     * 判断购物车是否处于显示状态
     */
    private boolean isShowShopCart = false;
    /*购物车中的凑凑按钮*/
    private ImageView coucou;
    /**
     * 收藏店铺字段
     * 1 收藏
     * 0 未收藏
     */
    private String collect;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_waimai_shop);
        ButterKnife.bind(this);
        instance = this;
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData() {
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, toolbar);
        /*获取shopID*/
        shop_id = getIntent().getStringExtra(SHOP_ID);
        product_info = getIntent().getStringExtra(PRODUCT_INFO);
        product_id = getIntent().getStringExtra(PRODUCT_ID);
        mHanlder = new Handler(getMainLooper());
        selectedList = new SparseArray<>();
        groupSelect = new SparseIntArray();
        specList = new SparseArray();

        /*getCurrencyInstance 返回当前缺省语言环境的通用格式*/
        nf = NumberFormat.getCurrencyInstance();
        /*设置数值的小数部分允许的最大位数为2*/
        nf.setMaximumFractionDigits(2);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity();
            }
        });
        shopGoodsFragment = new WaiMaiShopGoodsFragment();

        shopEvaluationFragment = new WaiMaiShopEvaluationFragment();
        shopDetailsFragment = new WaiMaiShopDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SHOP_ID, shop_id);
        shopEvaluationFragment.setArguments(bundle);
        shopDetailsFragment.setArguments(bundle);
        fragments = new ArrayList<>();
        fragments.add(shopGoodsFragment);
        fragments.add(shopEvaluationFragment);
        fragments.add(shopDetailsFragment);

        mainVpContainer.setAdapter(new CouponsViewPagerAdapter(getSupportFragmentManager(), fragments, tabTitles));
        toolbarTab.setupWithViewPager(mainVpContainer);
        mainVpContainer.addOnPageChangeListener(this);

        toolbarTab.addOnTabSelectedListener(this);


        toolbar.getBackground().mutate().setAlpha(0);
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int distance = Math.abs(verticalOffset);
                int height = headLayout.getHeight() - toolbar.getHeight();

                if (distance <= height && distance >= 0) {
                    float scale = (float) distance / height;
                    float alpha = (255 * scale);
                    toolbar.getBackground().mutate().setAlpha((int) alpha);
                } else if (distance >= height) {
                    toolbar.getBackground().mutate().setAlpha(255);
                }
                appBarLayoutClose = DesignViewUtils.isAppBarLayoutClose(appBarLayout, verticalOffset);
            }
        });
        divider = new DividerListItemDecoration.Builder(this)
                .setHeight(R.dimen.dp_1)
                .setColorResource(R.color.background)
                .build();
        checked(0);
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                isAppBarLayoutOpen = DesignViewUtils.isAppBarLayoutOpen(verticalOffset);
                isAppBarLayoutClose = DesignViewUtils.isAppBarLayoutClose(appBarLayout, verticalOffset);
                invalidateOptionsMenu();
                if (isAppBarLayoutClose()) {
                    ivMore.setVisibility(View.VISIBLE);
                } else {
                    ivMore.setVisibility(View.GONE);
                }
            }
        });
        RequestShopDetail(Api.WAIMAI_SHOP_DETAIL, shop_id);
    }


    public static boolean isAppBarLayoutClose() {
        return appBarLayoutClose;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_collected_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        /*关闭状态*/
        if (isAppBarLayoutClose) {
            menu.findItem(R.id.action_share).setVisible(false);
            menu.findItem(R.id.action_collect).setVisible(false);
            menu.findItem(R.id.action_collected).setVisible(false);
            menu.findItem(R.id.action_search).setVisible(false);
        } else {/*展开状态*/
            if ("1".equals(collect)) {
                /*已收藏*/
                menu.findItem(R.id.action_collected).setVisible(true);
                menu.findItem(R.id.action_collect).setVisible(false);
            } else {
                /*未收藏*/
                menu.findItem(R.id.action_collected).setVisible(false);
                menu.findItem(R.id.action_collect).setVisible(true);
            }
            menu.findItem(R.id.action_share).setVisible(true);
            menu.findItem(R.id.action_search).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemId = item.getItemId();
        switch (menuItemId) {
            case R.id.action_share:
                initShareShop();
                break;
            case R.id.action_collect:
            case R.id.action_collected:
                initCollectShop();
                break;
            case R.id.action_search:
                startInStoreSearch();
                break;
        }
        return true;
    }

    /**
     * 处理收藏店铺
     */
    private void initCollectShop() {
        if (!TextUtils.isEmpty(Api.TOKEN)) {
            if (!TextUtils.isEmpty(collect) && collect.equals("1")) {
                status = 0;
            } else {
                status = 1;
            }
            requestCollect(Api.WAIMAI_COLLECTION_MERCHANT, status, 1, shop_id);
        } else {
            Utils.GoLogin(this);
        }
    }

    /**
     * 处理分享店铺
     */
    private void initShareShop() {
        dialog = new ShareDialog(this);
        dialog.setItem(shareItems);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }


    public class CustomBubblePopup extends BaseBubblePopup<CustomBubblePopup> {
        private String shopCollect;
        @Bind(R.id.rl_search)
        RelativeLayout rlSearch;
        @Bind(R.id.rl_share)
        RelativeLayout rlShare;
        @Bind(R.id.iv_collect)
        ImageView ivCollect;
        @Bind(R.id.tv_collect)
        TextView tvCollect;
        @Bind(R.id.rl_collect)
        RelativeLayout rlCollect;

        public CustomBubblePopup(Context context, String collect) {
            super(context);
            this.shopCollect = collect;
        }

        @Override
        public View onCreateBubbleView() {
            View inflate = View.inflate(mContext, R.layout.popup_menu_more, null);
            ButterKnife.bind(this, inflate);
            return inflate;
        }

        @Override
        public void setUiBeforShow() {
            super.setUiBeforShow();
            if (shopCollect.equals("1")) {
                ivCollect.setImageResource(R.mipmap.navbar_btn_collect_yes);
            } else {
                ivCollect.setImageResource(R.mipmap.navbar_btn_collect_no);
            }
            rlSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startInStoreSearch();
                    dismiss();
                }
            });
            rlShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    initShareShop();
                }
            });
            rlCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    initCollectShop();
                }
            });
        }
    }

    private void startInStoreSearch() {
        Intent intent = new Intent(WaiMaiShopActivity.this, WaiMaiInStoreSearchActivity.class);
        intent.putExtra(WaiMaiInStoreSearchActivity.SHOP_DETAIL, shopDetail);
        intent.putExtra(WaiMaiInStoreSearchActivity.SHOP_MINATO, couGoodsList);
        startActivity(intent);
    }


    /**
     * status	否	int	状态
     * type	是	int	收藏类型,1:店铺,2:人员3:商品
     * can_id	是	int	shop_id或staff_id
     * <p>
     * 0804  把type字段修改为wiamai
     *
     * @param api
     * @param status
     * @param type
     * @param can_id
     */
    private void requestCollect(String api, final int status, int type, String can_id) {
        JSONObject object = new JSONObject();
        try {
            object.put("status", status);
            object.put("type", "waimai");
            object.put("can_id", can_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String str = object.toString();
        HttpUtils.postUrl(this, api, str, true, new OnRequestSuccessCallback() {
            @Override
            public void onSuccess(String url, String Json) {
                Gson gson = new Gson();
                SharedResponse response = gson.fromJson(Json, SharedResponse.class);
                if (response.error.equals("0")) {
                    if (status == 0) {
                        collect = "0";
                        invalidateOptionsMenu();
                    } else {
                        collect = "1";
                        invalidateOptionsMenu();
                    }
                }
                ToastUtil.show(response.message);
                try {
                } catch (Exception e) {
                    ToastUtil.show(getString(R.string.接口异常));
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


    @OnClick({R.id.tvSubmit, R.id.iv_coucou, R.id.ll_bottom, R.id.ll_notice, R.id.ll_huodong, R.id.iv_more})
    public void onBindClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_huodong:
            case R.id.ll_notice:
                intent.setClass(WaiMaiShopActivity.this, WaiMaiShopAnnouncementActivity.class);
                intent.putExtra(WaiMaiShopAnnouncementActivity.SHOP_DETAIL, shopDetail);
                startActivity(intent);
                break;
            case R.id.tvSubmit:
                if (!TextUtils.isEmpty(Api.TOKEN)) {
                    List<Goods> arrayList = new ArrayList<>();
                    for (int i = 0; i < selectedList.size(); i++) {
                        arrayList.add(selectedList.valueAt(i));
                    }
                    intent.setClass(this, ConfirmOrderActivity.class);
                    intent.putExtra(ConfirmOrderActivity.SHOP_CART, (Serializable) arrayList);
                    intent.putExtra(ConfirmOrderActivity.SHOP_ID, shop_id);
                    intent.putExtra(ConfirmOrderActivity.PEI_TYPE, pei_type);
                    startActivity(intent);
                } else {
                    Utils.GoLogin(this);
                }
                break;
            case R.id.iv_coucou:
                showMinato();
                break;
            case R.id.ll_bottom:
                /*购物车*/
                if (null != minatoLayout && minatoLayout.isSheetShowing()) {
                    minatoLayout.dismissSheet();
                    isHindeCou = true;
                }
                showShopCart();
                break;
            case R.id.iv_more:
                new CustomBubblePopup(this, collect)
                        .gravity(Gravity.BOTTOM)
                        .anchorView(ivMore)
                        .triangleWidth(20)
                        .triangleHeight(10)
                        .showAnim(null)
                        .dismissAnim(null)
                        .show();
                break;
        }
    }

    /*ViewPager滑动监听*/
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /*TabLayout监听*/
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        checked(tab.getPosition());
        if (tab.getPosition() == 0) {
            rlShopCart.setVisibility(View.VISIBLE);
            if (isShowCouCou()) {
                ivCoucou.setVisibility(View.GONE);
            } else {
                ivCoucou.setVisibility(View.VISIBLE);
            }
            rlBottom.setVisibility(View.VISIBLE);
        } else {
            ivCoucou.setVisibility(View.GONE);
            rlShopCart.setVisibility(View.GONE);
            rlBottom.setVisibility(View.GONE);
        }
    }

    private void checked(int position) {
        mCurrentIndex = position;
        Fragment to = getFragment(position);
        switchFragment(mFragment, to);
    }

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

    public Fragment getFragment(int position) {
        fragment = fragments.get(position);
        return fragment;
    }


    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        if (tab.getPosition() == 1) {
            shopEvaluationFragment.setScrollTop();
        }
    }


    /*----------------------------------------------------------*/

    public void playAnimation(int[] start_location) {
        ImageView img = new ImageView(this);
        img.setImageResource(R.mipmap.icon_shop_add);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        params.width = Utils.dip2px(this, 20);
        params.height = Utils.dip2px(this, 20);
        img.setLayoutParams(params);
        setAnim(img, start_location);
    }

    private void setAnim(final View v, int[] start_location) {

        addViewToAnimLayout(multipleStatusView, v, start_location);
        Animation set = createAnim(start_location[0], start_location[1]);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                mHanlder.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        multipleStatusView.removeView(v);
                    }
                }, 100);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(set);
    }

    private void addViewToAnimLayout(final ViewGroup vg, final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        int[] loc = new int[2];
        vg.getLocationInWindow(loc);
        view.setX(x);
        view.setY(y - loc[1]);
        vg.addView(view);
    }

    private Animation createAnim(int startX, int startY) {
        int[] des = new int[2];
        imgCart.getLocationInWindow(des);

        AnimationSet set = new AnimationSet(false);

        Animation translationX = new TranslateAnimation(0, des[0] - startX, 0, 0);
        translationX.setInterpolator(new LinearInterpolator());
        Animation translationY = new TranslateAnimation(0, 0, 0, des[1] - startY);
        translationY.setInterpolator(new AccelerateInterpolator());
        Animation alpha = new AlphaAnimation(1, 0.5f);
        set.addAnimation(translationX);
        set.addAnimation(translationY);
        set.addAnimation(alpha);
        set.setDuration(300);

        return set;
    }

    //根据类别Id获取属于当前类别的数量
    public int getSelectedGroupCountByTypeId(int typeId) {
        return groupSelect.get(typeId);

    }


    //根据商品id获取当前商品的采购数量
    public int getSelectedItemCountById(int id) {
        Goods temp = selectedList.get(id);
        if (temp == null) {
            return 0;
        }
        return temp.count;
    }

    //根据商品id获取当前规格商品的采购数量
    public static int getSpecItemCountById(int id) {
        Goods temp = specList.get(id);
        if (temp == null) {
            return 0;
        }
        return temp.count;
    }


    public void add(Goods item, boolean refreshGoodList) {
        /**
         * 判断是不是热销
         * 处理左侧热销不显示数量
         * */
        if (shopDetail.items != null && shopDetail.items.size() > 0 && shopDetail.items.get(0).cate_id.equals("hot")) {
            if (item.typeId == 0) {
                for (int i = 1; i < shopDetail.items.size(); i++) {
                    for (int j = 0; j < shopDetail.items.get(i).products.size(); j++) {
                        if (item.productId.equals(shopDetail.items.get(i).products.get(j).product_id)) {
                            type_Id = i;
                        }
                    }
                }
            } else {
                type_Id = item.typeId;
            }
        } else {
            for (int i = 0; i < shopDetail.items.size(); i++) {
                for (int j = 0; j < shopDetail.items.get(i).products.size(); j++) {
                    if (item.productId.equals(shopDetail.items.get(i).products.get(j).product_id)) {
                        type_Id = i;
                    }
                }
            }

        }
        /*处理左侧商品分类的数量--start*/
        int groupCount = groupSelect.get(type_Id);
        if (groupCount == 0) {
            groupSelect.append(type_Id, 1);
        } else {
            groupSelect.append(type_Id, ++groupCount);
        }
        /*处理左侧商品分类的数量--end*/

        /*处理进入店铺后规格商品的添加--start*/
        if (item.is_spec.equals("1")) {
            Goods specTemp = specList.get(Integer.parseInt(item.productId));
            if (specTemp == null) {
                for (int i = 0; i < goodsList.size(); i++) {
                    if (goodsList.get(i).product_id.equals(item.productId)) {
                        goodsList.get(i).count = 1;
                        specList.append(Integer.parseInt(item.productId), goodsList.get(i));
                    }
                }
            } else {
                specTemp.count++;
            }
        }
        /*处理进入店铺后规格商品的添加--end*/
        /*处理添加商品的操作--start*/
        Goods temp = selectedList.get(Integer.parseInt(item.product_id));
        if (temp == null) {
            item.count = 1;
            selectedList.append(Integer.parseInt(item.product_id), item);
        } else {
            temp.count++;
        }
        /*处理添加商品的操作--end*/
        update(refreshGoodList);
    }

    //移除商品
    public void remove(Goods item, boolean refreshGoodList) {
        if (shopDetail.items != null && shopDetail.items.size() > 0 && shopDetail.items.get(0).cate_id.equals("hot")) {
            if (item.typeId == 0) {
                for (int i = 1; i < shopDetail.items.size(); i++) {
                    for (int j = 0; j < shopDetail.items.get(i).products.size(); j++) {
                        if (item.productId.equals(shopDetail.items.get(i).products.get(j).product_id)) {
                            type_Id = i;
                        }
                    }
                }
            } else {
                type_Id = item.typeId;
            }
        } else {
            type_Id = item.typeId;
        }


        int groupCount = groupSelect.get(type_Id);
        if (groupCount == 1) {
            groupSelect.delete(type_Id);
        } else if (groupCount > 1) {
            groupSelect.append(type_Id, --groupCount);
        }

        Goods temp = selectedList.get(Integer.parseInt(item.product_id));
        if (temp != null) {
            if (temp.count < 2) {
                temp.count--;
                selectedList.remove(Integer.parseInt(item.product_id));
            } else {
                temp.count--;
            }
        }

        /*处理进入店铺后规格商品的添加--start*/
        if (item.is_spec.equals("1")) {
            Goods specTemp = specList.get(Integer.parseInt(item.productId));
            if (specTemp != null) {
                specTemp.count--;
            }
        }
        /*处理进入店铺后规格商品的添加--end*/


        update(refreshGoodList);
    }

    //刷新布局 总价、购买数量等
    private void update(boolean refreshGoodList) {
        int size = selectedList.size();
        int count = 0;
        totalAmount = 0;
        for (int i = 0; i < size; i++) {
            Goods item = selectedList.valueAt(i);
            count += item.count;
            totalAmount += item.count * Double.parseDouble(item.price);
        }
        if (count < 1) {
            tvCount.setVisibility(View.GONE);
        } else {
            tvCount.setVisibility(View.VISIBLE);
        }

        tvCount.setText(String.valueOf(count));
        dealWithPagePrice();

        /**
         * 判断是否让凑凑按钮 显示
         *
         * 当购物车显示的时候 让 ivCoucou 隐藏 并判断 购物车上的coucou 凑一凑按钮是否显示
         * 当凑一凑显示的时候 让 ivCoucou 隐藏
         * 当购物车 凑一凑 都不显示的时候 在判断是否让 ivCoucou 显示
         *
         * */
        if (isShowShopCart) {
            ivCoucou.setVisibility(View.GONE);
            if (coucou != null) {
                if (isShowCouCou()) {
                    coucou.setVisibility(View.GONE);
                } else {
                    coucou.setVisibility(View.VISIBLE);
                }
            }
        } else if (isShowCou) {
            ivCoucou.setVisibility(View.GONE);
        } else {
            if (isShowCouCou()) {
                ivCoucou.setVisibility(View.GONE);
            } else {
                ivCoucou.setVisibility(View.VISIBLE);
            }
        }


        /*通知ShopGoodsFragment刷新myAdapter*/
        if (refreshGoodList) {
            shopGoodsFragment.setMyAdapterDataChanged();
        }
        /*通知ShopGoodsFragment刷新typeAdapter*/
        shopGoodsFragment.setTypeAdapterDataChanged();


        if (selectAdapter != null) {
            selectAdapter.notifyDataSetChanged();
        }
        if (minatoAdapter != null) {
            minatoAdapter.notifyDataSetChanged();
        }

        if (shopCartLayout.isSheetShowing() && selectedList.size() < 1) {
            shopCartLayout.dismissSheet();
        }
    }

    //清空购物车
    public void clearCart() {
        selectedList.clear();
        groupSelect.clear();
        specList.clear();
        update(true);
    }


    /**
     * 凑一凑视图
     *
     * @return
     */
    private RecyclerView rvMinato;

    private View creatMinatoView() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_minato_sheet, (ViewGroup) getWindow().getDecorView(), false);
        rvMinato = (RecyclerView) view.findViewById(R.id.rv_minato);
        rvMinato.setLayoutManager(new LinearLayoutManager(this));
        rvMinato.addItemDecoration(divider);
        minatoAdapter = new MinatoAdapter(this, couGoodsList);
        rvMinato.setAdapter(minatoAdapter);
        return view;
    }

    /**
     * 购物车视图
     *
     * @return
     */
    private View createShopCartView() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_shopcart_sheet, (ViewGroup) getWindow().getDecorView(), false);
        rvSelected = (RecyclerView) view.findViewById(R.id.selectRecyclerView);
        rvSelected.setLayoutManager(new LinearLayoutManager(this));
        rvSelected.addItemDecoration(divider);
        TextView clear = (TextView) view.findViewById(R.id.clear);
        tvPagePrice = (TextView) view.findViewById(R.id.tv_pageprice);
        coucou = (ImageView) view.findViewById(R.id.iv_coucou);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CallDialog(WaiMaiShopActivity.this)
                        .setMessage("是否清空已选商品")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("清空", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                clearCart();
                            }
                        })
                        .show();
            }
        });
        coucou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopCartLayout.dismissSheet();
                isHindeCou = true;
                showMinato();
            }
        });
        selectAdapter = new SelectAdapter(this, selectedList);
        rvSelected.setAdapter(selectAdapter);
        return view;
    }

    /**
     * 凑一凑 的显示状态 isShowCou
     * <p>
     * 消失的时候 判断是否显示 ivCoucou凑一凑
     * 显示的时候 让ivCoucou凑一凑 隐藏
     */
    private void showMinato() {
        if (minatoSheet == null) {
            minatoSheet = creatMinatoView();
        }
        if (minatoLayout.isSheetShowing()) {
            minatoLayout.dismissSheet();
            isShowCou = false;
        } else {
            minatoLayout.showWithSheetView(minatoSheet);
            isShowCou = true;
            minatoLayout.addOnSheetStateChangeListener(new BottomSheetLayout.OnSheetStateChangeListener() {
                @Override
                public void onSheetStateChanged(BottomSheetLayout.State state) {
                        /*formatLayout 消失*/
                    if (state.toString().equals("HIDDEN")) {
                        isShowCou = false;
                        if (isHindeCou) {
                            ivCoucou.setVisibility(View.GONE);
                            isHindeCou = false;
                        } else {
                            if (!isShowCouCou()) {
                                ivCoucou.setVisibility(View.VISIBLE);
                            } else {
                                ivCoucou.setVisibility(View.GONE);
                            }
                        }
                    } else if (state.toString().equals("PEEKED")) {
                        /*formatLayout 展开*/
                        ivCoucou.setVisibility(View.GONE);
                    }
                }
            });
        }
    }


    /**
     * 购物车显示的时候 判断是否显示凑一凑
     * 购物车展开状态 ivCoucou 隐藏
     * 判断coucou 是否显示
     * 购物车关闭状态 判断 ivCoucou 是否显示
     */
    private void showShopCart() {
        if (shopCartSheet == null) {
            shopCartSheet = createShopCartView();
        }
        if (shopCartLayout.isSheetShowing()) {
            shopCartLayout.dismissSheet();

        } else {
            if (selectedList.size() != 0) {
                /*弹出购物车*/
                shopCartLayout.showWithSheetView(shopCartSheet);

                shopCartLayout.addOnSheetStateChangeListener(new BottomSheetLayout.OnSheetStateChangeListener() {
                    @Override
                    public void onSheetStateChanged(BottomSheetLayout.State state) {
                        /*formatLayout 消失*/
                        if (state.toString().equals("HIDDEN")) {
                            isShowShopCart = false;
                            if (!isShowCouCou()) {
                                ivCoucou.setVisibility(View.VISIBLE);
                            } else {
                                ivCoucou.setVisibility(View.GONE);
                            }
                            if (isHindeCou) {
                                ivCoucou.setVisibility(View.GONE);
                                isHindeCou = false;
                            }
                        } else if (state.toString().equals("PEEKED")) {
                            /*formatLayout 展开*/
                            isShowShopCart = true;
                             /*处理购物车打包费问题*/
                            dealWithPagePrice();
                            /*处理购物车凑一凑问题*/
                            if (isShowCouCou()) {
                                coucou.setVisibility(View.GONE);
                            } else {
                                coucou.setVisibility(View.VISIBLE);
                            }
                            ivCoucou.setVisibility(View.GONE);
                        }
                    }
                });
            }
        }
    }


    private void dealWithPagePrice() {
        double v = 0;
        for (int i = 0; i < selectedList.size(); i++) {
            Goods item = selectedList.valueAt(i);
            v += item.count * Double.parseDouble(item.pagePrice);
        }
//        if (v != 0) {
        if (tvPagePrice != null) {
            tvPagePrice.setText("￥" + v);
        }
//        }
        pagePrice = v;
        tvCost.setText(nf.format(totalAmount + v));

        /**
         * 商家未打烊状态
         * */
        if ("1".equals(shopDetail.yysj_status) && "1".equals(shopDetail.yy_status)) {
            if (totalAmount > 0 && totalAmount + v >= Double.parseDouble(shopDetail.min_amount)) {
                tvTips.setVisibility(View.GONE);
                tvSubmit.setVisibility(View.VISIBLE);
            } else {
                tvSubmit.setVisibility(View.GONE);
                tvTips.setVisibility(View.VISIBLE);
                /**
                 * 处理还差多少钱起送
                 * */
                if (totalAmount != 0 && Double.parseDouble(shopDetail.min_amount) > totalAmount + v) {
                    tvTips.setText("差" + nf.format(Double.parseDouble(shopDetail.min_amount) - totalAmount - v) + "起送");
                } else {
                    tvTips.setText("￥" + shopDetail.min_amount + "元起送");
                }
            }
        }
    }


    /**
     * 购物车金额是否大于起送金额
     * 大于？凑凑按钮隐藏
     * 小于？凑凑按钮显示
     * true 隐藏
     *
     * @return
     */
    public boolean isShowCouCou() {
        if (shopDetail.min_amount.equals("0")) {
            return true;
        } else if ((totalAmount + pagePrice) < Double.parseDouble(shopDetail.min_amount) && (totalAmount + pagePrice) >= Double.parseDouble(shopDetail.min_amount) / 2) {
            return false;
        } else {
            return true;
        }
    }

    private void RequestShopDetail(String api, String shop_id) {
        JSONObject object = new JSONObject();
        try {
            object.put("shop_id", shop_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String str = object.toString();
        HttpUtils.postUrl(this, api, str, false, this);
    }

    private SuperTextView tvYouhuiWord;
    private TextView tvYouhuiTitle;

    @Override
    public void onSuccess(String url, String Json) {
        switch (url) {
            case Api.WAIMAI_SHOP_DETAIL:
                try {
                    Gson gson = new Gson();
                    shopData = gson.fromJson(Json, Response_WaiMai_Home.class);
                    if (shopData.error.equals("0")) {
                        shopDetail = shopData.data.detail;
                        collapsingToolbar.setTitle(shopDetail.title);
                        /*配送方式*/
                        pei_type = shopDetail.pei_type;
                        /*店铺名称*/
                        tvShopName.setText(shopDetail.title);
                        /*店铺logo*/
                        Utils.LoadStrPicture(this, Api.IMAGE_URL + shopDetail.logo, ivShopLogo);
                            /*显示内容视图*/
                        multipleStatusView.showContent();
                        /*营业状态*/
                        if ("1".equals(shopDetail.yysj_status) && "1".equals(shopDetail.yy_status)) {
                            tvShopStatus.setText(getString(R.string.营业中));
                            tvShopStatus.setSolid(getResources().getColor(R.color.themColor));
                            tvTips.setText("￥" + shopDetail.min_amount + "元起送");
                        } else {
                            tvShopStatus.setText(getString(R.string.打烊));
                            tvTips.setText("已打烊");
                            DaYangDialog dialog = new DaYangDialog(WaiMaiShopActivity.this);
                            dialog.show();
                            tvShopStatus.setSolid(getResources().getColor(R.color.third_txt_color));
                        }
                        if (!TextUtils.isEmpty(shopDetail.freight) && !shopDetail.freight.equals("0")) {
                            tvShopDelivery.setText(shopDetail.min_amount + "元起送 /" + shopDetail.pei_time + "分钟送达 / 配送费" + shopDetail.freight + "元");
                        } else {
                            tvShopDelivery.setText(shopDetail.min_amount + "元起送 /" + shopDetail.pei_time + "分钟送达 / 免配送费");
                        }
                        /*收藏店铺*/
                        collect = shopDetail.collect;
                        invalidateOptionsMenu();
                        /*活动*/
                        List<ShopDetail.HuodongEntity> huodong = shopDetail.huodong;
                        if (null != huodong) {
                            if (huodong.size() == 0) {
                                llHuodong.setVisibility(View.GONE);
                            } else {
                                llHuodong.setVisibility(View.VISIBLE);
                                tvHuodongNum.setText(huodong.size() + "个活动");
                                for (int i = 0; i < huodong.size(); i++) {
                                    View view = LayoutInflater.from(this).inflate(R.layout.youhuiquan, null);
                                    tvYouhuiWord = (SuperTextView) view.findViewById(R.id.tv_youhui_word);
                                    tvYouhuiTitle = (TextView) view.findViewById(R.id.tv_youhui_title);
                                    tvYouhuiWord.setText(huodong.get(i).word);
                                    tvYouhuiWord.setSolid(Color.parseColor("#" + huodong.get(i).color));
                                    tvYouhuiTitle.setText(huodong.get(i).title);
                                    marqueeView.addView(view);
                                }
                            }
                        }
                        /*分享*/
                        shareItems = new ShareItem();
                        shareItems.setTitle(shopDetail.title);
                        shareItems.setLogo(Api.IMAGE_URL + shopDetail.logo);
                        shareItems.setUrl(shopDetail.share_url);
                        shareItems.setDescription(shopDetail.delcare);
                        tvNotice.setText(TextUtils.isEmpty(shopDetail.delcare) ? "暂无公告" : shopDetail.delcare);
                        items = shopDetail.items;
                        Product product = null;
                        DataSupport.deleteAll(Product.class, "shopId = ? ", shopDetail.shop_id);
                        /**
                         * 从1开始循环是因为把热销中的商品排除掉
                         * */
                        if (items != null && items.size() > 0) {
                            if (items.get(0).cate_id.equals("hot")) {
                                start = 1;
                            } else {
                                start = 0;
                            }
                            Api.GOOD_LIST = goodsList = new ArrayList<>();
                            couGoodsList = new ArrayList<>();
                            for (int i = start; i < items.size(); i++) {
                                for (int j = 0; j < items.get(i).products.size(); j++) {
                                    Goods goods = new Goods(items.get(i).products.get(j), items.get(i).products.get(j).product_id, items.get(i).title, i);
                                    goods.setBad(items.get(i).products.get(j).bad);
                                    goods.setGood(items.get(i).products.get(j).good);
                                    goods.setIs_spec(items.get(i).products.get(j).is_spec);
                                    goods.setPrice(items.get(i).products.get(j).price);
                                    goods.setProduct_id(items.get(i).products.get(j).product_id);
                                    goods.setProductId(items.get(i).products.get(j).product_id);
                                    goods.setSale_sku(items.get(i).products.get(j).sale_sku);
                                    goods.setShop_id(items.get(i).products.get(j).shop_id);
                                    goods.setSpec_id("0");
                                    goods.setPagePrice(items.get(i).products.get(j).package_price);
                                    goods.setName(items.get(i).products.get(j).title);
                                    goods.setTitle(items.get(i).title);
                                    goods.setLogo(items.get(i).products.get(j).photo);
                                    goods.setTypeId(i);
                                    goodsList.add(goods);
                                    /*把每个商品Goods对象转为json对象字符串*/
                                    String toJson = gson.toJson(goods);
                                    /*把每个商品都存到数据库中*/
                                    product = new Product();
                                    product.setTitle(items.get(i).products.get(j).title);
                                    product.setShopId(shopDetail.shop_id);
                                    product.setGoodInfo(toJson);
                                    product.save();
                                }
                            }

                        /*筛选符合凑一凑商品*/
                            for (int i = 0; i < goodsList.size(); i++) {
                                /**
                                 * 筛选不是规格的商品
                                 * */
                                if (goodsList.get(i).is_spec.equals("0")) {
                                    /**
                                     * 商品价格小于等于起送价的一半
                                     * */
                                    if (Double.parseDouble(goodsList.get(i).price) <= Double.parseDouble(shopDetail.min_amount) / 2) {
                                        couGoodsList.add(goodsList.get(i));
                                    }
                                }
                            }
                        }
                        if (!TextUtils.isEmpty(product_id)) {
                            appBar.setExpanded(false, true);
                        }
                        shopGoodsFragment.setData(shopDetail, couGoodsList, product_id);
                        dealWith();
                    } else {
                        //显示错误视图
                        multipleStatusView.showError();
                        ToastUtil.show(shopData.message);
                    }

                } catch (Exception e) {
                    ToastUtil.show(getString(R.string.接口异常));
                    multipleStatusView.showError();
                    saveCrashInfo2File(e);
                }
                break;
        }
    }

    private ArrayList<Goods> goodsList;
    /**
     * 凑一凑商品集合
     */
    private ArrayList<Goods> couGoodsList;

    private void dealWith() {

        List<Shop> mDestList = DataSupport.where("shop_id = ?", shop_id).find(Shop.class);
        /**
         * 处理购物车、再来一单
         * */
        if (!TextUtils.isEmpty(product_info)) {
            appBar.setExpanded(false, true);
            dealWithProduct(product_info, true);
        } else if (null != mDestList && mDestList.size() > 0) {
            /**
             * 处理 首页进来
             * */
            dealWithProduct(mDestList.get(0).getProduct_info(), false);
        }
    }

    private void dealWithProduct(String productInfo, boolean isShowShopCart) {
        int sale_sku = 0;
        Gson gson = new Gson();
        StringBuilder sb = new StringBuilder();
        final List<Goods> goodses = gson.fromJson(productInfo, new TypeToken<List<Goods>>() {
        }.getType());
        for (int i = 0; i < goodses.size(); i++) {
            int count = goodses.get(i).count;
            for (int j = 0; j < goodsList.size(); j++) {
                if (goodses.get(i).productId.equals(goodsList.get(j).productId)) {
                    sale_sku = goodsList.get(j).productsEntity.sale_sku;
                }
            }
            if (sale_sku == 0) {
                sb.append(goodses.get(i).name + ",");
            } else if (count > sale_sku) {
                /*for (int j = 0; j < sale_sku; j++) {
                    add(goodses.get(i), true);
                }*/
                sb.append(goodses.get(i).name + ",");
            } else if (count <= sale_sku) {
                for (int j = 0; j < count; j++) {
                    add(goodses.get(i), true);
                }
            }
        }
        if (isShowShopCart) {
            showShopCart();
        }
        if (!TextUtils.isEmpty(sb.toString())) {
            ToastUtil.show("购物车中的\n" + sb.substring(0, sb.length() - 1) + "\n数据发生变化");

        }
    }

    @Override
    public void onBeforeAnimate() {
        //显示加载中视图
        multipleStatusView.showLoading();
    }


    @Override
    public void onErrorAnimate() {
        //显示错误视图
        multipleStatusView.showError();
    }

    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        if (event.message.equals(REFRESH_GOODS)) {
            update(true);
        }
    }

    @Subscribe
    public void onEventMainThread(EventBusEventModel event) {
        if (event.getType() == 3) {
            WaiMaiShopActivity.this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        closeActivity();
    }

    private void closeActivity() {
        if (null != shopCartLayout && shopCartLayout.isSheetShowing()) {
            shopCartLayout.dismissSheet();
        } else if (null != minatoLayout && minatoLayout.isSheetShowing()) {
            minatoLayout.dismissSheet();
        } else {
            /*删除保存的所有商品*/
            DataSupport.deleteAll(Product.class);
            /*删除店内购物车所有商品*/
            DataSupport.deleteAll(Shop.class, "shop_id = ? ", shop_id);
            if (selectedList.size() > 0) {
                Shop shop = new Shop();
                shop.setShop_id(shop_id);
                shop.setShop_name(shopDetail.title);
                List<Goods> saveList = new ArrayList<>();
                for (int i = 0; i < selectedList.size(); i++) {
                    Goods item = selectedList.valueAt(i);
                    saveList.add(item);
                }
                Gson gson = new Gson();
                String json = gson.toJson(saveList);
                shop.setProduct_info(json);
                shop.save();
            }
            EventBus.getDefault().post(new MessageEvent(WaiMai_HomeFragment.REFRESH_SHOPITEM));
            EventBus.getDefault().post(new MessageEvent(WaiMaiBusinessListActivity.REFRESH_SHOPITEM));
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


}
