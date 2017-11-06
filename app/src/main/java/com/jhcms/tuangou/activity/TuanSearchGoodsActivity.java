package com.jhcms.tuangou.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jhcms.common.model.Data_BusinessListSortList;
import com.jhcms.common.model.Response_BusinessList_SortList;
import com.jhcms.common.model.SortModel;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.LogUtil;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.MyPopupWindow;
import com.jhcms.shequ.fragment.BusinessFragment;
import com.jhcms.shequ.fragment.CommodityFragment;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;
import com.jhcms.waimaiV3.adapter.ClassifyPopLeftAdapter;
import com.jhcms.waimaiV3.adapter.ClassifyPopRightAdapter;
import com.jhcms.waimaiV3.adapter.CouponsViewPagerAdapter;
import com.jhcms.waimaiV3.adapter.GroupClassifyPopLeftAdapter;
import com.jhcms.waimaiV3.adapter.GroupClassifyPopRightAdapter;
import com.jhcms.waimaiV3.adapter.OrderByPopAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 团购商家分类
 */
public class TuanSearchGoodsActivity extends SwipeBaseActivity implements OnRequestSuccessCallback {

    @Bind(R.id.tv_search)
    TextView tvSearch;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.tv_classification)
    TextView tvClassification;
    @Bind(R.id.iv_classification)
    ImageView ivClassification;
    @Bind(R.id.ll_classification)
    LinearLayout llClassification;
    @Bind(R.id.tv_region)
    TextView tvRegion;
    @Bind(R.id.iv_region)
    ImageView ivRegion;
    @Bind(R.id.ll_region)
    LinearLayout llRegion;
    @Bind(R.id.tv_sort)
    TextView tvSort;
    @Bind(R.id.iv_sort)
    ImageView ivSort;
    @Bind(R.id.ll_sort)
    LinearLayout llSort;
    @Bind(R.id.ll_search)
    LinearLayout llSearch;
    @Bind(R.id.line_staff)
    View line;
    @Bind(R.id.search_viewPager)
    ViewPager searchViewPager;
    @Bind(R.id.iv_switch)
    ImageView ivSwitch;
    @Bind(R.id.appBarLayout)
    AppBarLayout appBarLayout;


    List<Fragment> fragmentList;
    String[] titles = {"商品", "商家"};
    @Bind(R.id.ll_switch)
    LinearLayout llSwitch;
    private CommodityFragment commodityFragment;
    private BusinessFragment businessFragment;
    /**
     * 是否切换列表显示类型
     */
    private boolean isSwitch = false;
    /*ListView类型*/
    public static int SWITCH_LISTVIEW = 1000;
    /*GridView类型*/
    public static int SWITCH_GRIDVIEW = 1001;

    public static String CATE_ID = "CATE_ID";
    public static String CONTENT = "CONTENT";

    private MyPopupWindow classifyPop;            //弹出选择分类框
    private View classifyView;                  //选择框的View
    private ListView classifyLeftList;               //左侧ListView
    private ListView classifyRightList;              //右侧ListView
    private ClassifyPopLeftAdapter classifyLeftAdapter;
    private ClassifyPopRightAdapter classifyRightAdapter;
    /*分类选项中左侧选中的位置*/
    private int classifyLeftPosition = 0;


    private MyPopupWindow sortPop;                //排序Popup
    private View orderByView;                   //排序View
    private ListView orderbyList;               //排序ListView
    private OrderByPopAdapter orderByAdapter;
    private List<String> orderbyData = new ArrayList<>();


    private MyPopupWindow regionPop;
    private View regionView;
    private ListView regionLeftList;
    private ListView regionRightList;
    private GroupClassifyPopLeftAdapter regionLeftAdapter;
    private GroupClassifyPopRightAdapter regionRightAdapter;
    /*区域选项中左侧选中的位置*/
    private int regionLeftPosition = 0;
    private String cate_id;
    private int area_id;
    private int business_id;
    /*排序 price：人均消费 default：智能 juli：距离 score：评分*/
    private String[] busOrders = {"default", "juli", "score", "price"};
    private int[] busInfo = {R.string.智能排序, R.string.距离最近, R.string.评分最高, R.string.人均消费最低};
    private String busOrder = busOrders[0];
    /*团购商品(client/v3/tuan/shop/goods)*/
    /*排序 price：价格 default：默认 sales：销量*/
    private String[] commOrders = {"default", "score", "price"};
    private int[] commInfo = {R.string.智能排序, R.string.销量最高, R.string.团购价最低};
    private String commOrder = commOrders[0];

    private String title = "";
    /**
     * tablayout 选中的是商家还是商品
     */
    private int tabPosition = 1;
    private LinearLayout classifyPopList, regionPopList;
    /**
     *
     * */
    private int busPosition;
    private int commPosition;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_search_merchant_goods);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        setToolBar(appBarLayout);
        cate_id = getIntent().getStringExtra(CATE_ID);
        title = getIntent().getStringExtra(CONTENT);
        if (!TextUtils.isEmpty(title)) {
            tvSearch.setText(title);
        }
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
        /**
         * 当appBarLayout滑动时 隐藏PopuWindow
         * */
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            if (Math.abs(verticalOffset) > 0) {
                hidePop(0);
                hidePop(1);
                hidePop(2);
            }
        });

        /*TabLayout*/
        commodityFragment = new CommodityFragment();/*商品Fragment*/
        businessFragment = new BusinessFragment();/*商家Fragment*/
        fragmentList = new ArrayList<>();
        fragmentList.add(commodityFragment);
        fragmentList.add(businessFragment);
        searchViewPager.setAdapter(new CouponsViewPagerAdapter(getSupportFragmentManager(), fragmentList, titles));

        tabLayout.setupWithViewPager(searchViewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPosition = tab.getPosition();
                orderbyData.clear();
                if (tab.getPosition() == 0) {
                    ivSwitch.setImageResource(R.drawable.select_switch_btn);
                    llSwitch.setClickable(true);
                    for (int i = 0; i < commInfo.length; i++) {
                        orderbyData.add(getString(commInfo[i]));
                    }
                    tvSort.setText(orderbyData.get(commPosition));
                    if (orderByAdapter != null)
                        orderByAdapter.setPosition(commPosition);
                } else {
                    ivSwitch.setImageResource(R.mipmap.tuan_btn_switch_two_no);
                    llSwitch.setClickable(false);
                    for (int i = 0; i < busInfo.length; i++) {
                        orderbyData.add(getString(busInfo[i]));
                    }
                    tvSort.setText(orderbyData.get(busPosition));
                    if (orderByAdapter != null)
                        orderByAdapter.setPosition(busPosition);
                }
                if (null != orderByAdapter) {
                    orderByAdapter.setData(orderbyData);
                }
                hidePop(0);
                hidePop(1);
                hidePop(2);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //TODO 默认选中第二个标签
        searchViewPager.setCurrentItem(1);
        tabLayout.getTabAt(1).select();
        //TODO 排序
        initSequence();
        requestCates(Api.WAIMAI_TUAN_CATES_AREAS, Api.CITY_CODE);
    }

    private void requestItems() {
        commodityFragment.setItems(title, cate_id, area_id, business_id, commOrder);
        businessFragment.setItems(title, cate_id, area_id, business_id, busOrder);
    }


    private void requestCates(String api, String cityCode) {
        JSONObject object = new JSONObject();
        try {
            object.put("city_code", cityCode);
            HttpUtils.postUrl(this, api, object.toString(), false, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 分类
     *
     * @param mSortData
     */
    private void initClassify(final ArrayList<SortModel> mSortData) {
        classifyView = LayoutInflater.from(this).inflate(R.layout.popup_choose_classify, null);
        classifyPop = MyPop(classifyView);
        classifyPopList = (LinearLayout) classifyView.findViewById(R.id.ll_list);
        classifyLeftList = (ListView) classifyView.findViewById(R.id.left_list);
        classifyRightList = (ListView) classifyView.findViewById(R.id.right_list);
        classifyView.findViewById(R.id.ll_touch).setOnClickListener(v -> hidePop(0));
        int pHeight = mSortData.size() * Utils.dip2px(this, 40);
        if (pHeight >= Utils.getScreenH(this) / 2) {
            pHeight = Utils.getScreenH(this) / 2;
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height = pHeight;
        classifyPopList.setLayoutParams(params);

        classifyLeftAdapter = new ClassifyPopLeftAdapter(this);
        classifyRightAdapter = new ClassifyPopRightAdapter(this);
        classifyLeftList.setAdapter(classifyLeftAdapter);
        classifyLeftAdapter.setData(mSortData);
        if (!TextUtils.isEmpty(cate_id)) {
            for (int i = 0; i < mSortData.size(); i++) {
                if (mSortData.get(i).cate_id.equals(cate_id)) {
                    classifyLeftPosition = i;
                    tvClassification.setText(mSortData.get(i).title);
                }
            }
        } else {
            cate_id = mSortData.get(0).cate_id;
        }
        requestItems();
        /*默认选中第一项*/
        classifyLeftAdapter.setPosition(classifyLeftPosition);
        classifyRightAdapter.setData(mSortData.get(classifyLeftPosition).childrens);
        classifyRightAdapter.setId(0);

        /*左边listview点击事件*/
        classifyLeftList.setOnItemClickListener((parent, view, position, id) -> {
            classifyLeftPosition = position;
            classifyLeftAdapter.setPosition(position);
            classifyRightAdapter.setId(0);
            classifyRightAdapter.setData(mSortData.get(classifyLeftPosition).childrens);
            if (null != mSortData.get(classifyLeftPosition).childrens && mSortData.get(classifyLeftPosition).childrens.size() == 0) {
                tvClassification.setText(mSortData.get(classifyLeftPosition).title);
                cate_id = mSortData.get(classifyLeftPosition).cate_id;
                hidePop(0);//隐藏分类Pop
                requestItems();
            }
        });

        classifyRightList.setAdapter(classifyRightAdapter);
        /*右边listview点击事件*/
        classifyRightList.setOnItemClickListener((parent, view, position, id) -> {
            classifyRightAdapter.setId(position);
            tvClassification.setText(mSortData.get(classifyLeftPosition).childrens.get(position).title);
            cate_id = mSortData.get(classifyLeftPosition).childrens.get(position).cate_id;
            hidePop(0);//隐藏分类Pop
            requestItems();
        });
    }

    /**
     * 区域
     *
     * @param area_items
     */
    private void initRegion(final List<Data_BusinessListSortList.AreaItemsBean> area_items) {
        regionView = LayoutInflater.from(this).inflate(R.layout.popup_choose_classify, null);
        regionPop = MyPop(regionView);
        regionPopList = (LinearLayout) regionView.findViewById(R.id.ll_list);
        regionLeftList = (ListView) regionView.findViewById(R.id.left_list);
        regionRightList = (ListView) regionView.findViewById(R.id.right_list);

        int pHeight = area_items.size() * Utils.dip2px(this, 40);
        if (pHeight >= Utils.getScreenH(this) / 2) {
            pHeight = Utils.getScreenH(this) / 2;
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height = pHeight;
        regionPopList.setLayoutParams(params);


        regionView.findViewById(R.id.ll_touch).setOnClickListener(v -> hidePop(1));

        regionLeftAdapter = new GroupClassifyPopLeftAdapter(this);
        regionRightAdapter = new GroupClassifyPopRightAdapter(this);
        regionLeftList.setAdapter(regionLeftAdapter);
        regionLeftAdapter.setData(area_items);
        /*默认选中第一项*/
        regionLeftAdapter.setPosition(regionLeftPosition);
        regionRightAdapter.setData(area_items.get(regionLeftPosition).business);
        regionRightAdapter.setPosition(0);
        /*左边listview点击事件*/
        regionLeftList.setOnItemClickListener((parent, view, position, id) -> {
            regionLeftPosition = position;
            regionLeftAdapter.setPosition(position);
            regionRightAdapter.setPosition(0);
            regionRightAdapter.setData(area_items.get(regionLeftPosition).business);
            if (null != area_items.get(regionLeftPosition).business && area_items.get(regionLeftPosition).business.size() == 0) {
                tvRegion.setText(area_items.get(regionLeftPosition).area_name);
                area_id = area_items.get(regionLeftPosition).area_id;
                business_id = 0;
                hidePop(1);//隐藏分类Pop
                requestItems();
            }
        });

        regionRightList.setAdapter(regionRightAdapter);
        /*右边listview点击事件*/
        regionRightList.setOnItemClickListener((parent, view, position, id) -> {
            regionRightAdapter.setPosition(position);
            tvRegion.setText(area_items.get(regionLeftPosition).business.get(position).business_name);
            area_id = area_items.get(regionLeftPosition).area_id;
            business_id = area_items.get(regionLeftPosition).business.get(position).business_id;
            hidePop(1);//隐藏分类Pop
            requestItems();
        });
    }

    /**
     * 排序
     */
    private void initSequence() {
        orderbyData.clear();
        for (int i = 0; i < busInfo.length; i++) {
            orderbyData.add(getString(busInfo[i]));
        }
        orderByView = LayoutInflater.from(this).inflate(R.layout.popup_choose_orderby, null);
        orderbyList = (ListView) orderByView.findViewById(R.id.orderby_list);
        orderByView.findViewById(R.id.ll_touch).setOnClickListener(v -> hidePop(2));
        sortPop = MyPop(orderByView);
        orderByAdapter = new OrderByPopAdapter(this);
        orderByAdapter.setData(orderbyData);
        orderbyList.setAdapter(orderByAdapter);
        orderbyList.setOnItemClickListener((parent, view, position, id) -> {
            tvSort.setText(orderbyData.get(position));
            hidePop(2);//隐藏排序pop
            orderByAdapter.setPosition(position);
            if (tabPosition == 1) {
                busPosition = position;
                busOrder = busOrders[busPosition];
            } else {
                commPosition = position;
                commOrder = commOrders[position];
            }
            LogUtil.i("busOrder---" + busOrder);
            LogUtil.i("commOrder--" + commOrder);
            requestItems();
        });
    }

    private MyPopupWindow MyPop(View view) {
        MyPopupWindow popWindow = new MyPopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return popWindow;
    }

    private void hidePop(int position) {
        switch (position) {
            case 0:
                if (classifyPop != null && classifyPop.isShowing()) {
                    classifyPop.dismiss();
                    llClassification.setSelected(false);
                }
                break;
            case 1:
                if (regionPop != null && regionPop.isShowing()) {
                    regionPop.dismiss();
                    llRegion.setSelected(false);
                }
                break;
            case 2:
                if (sortPop != null && sortPop.isShowing()) {
                    sortPop.dismiss();
                    llSort.setSelected(false);
                }
                break;

            default:
                break;
        }

    }


    @OnClick({R.id.ll_classification, R.id.ll_region, R.id.ll_sort, R.id.ll_switch, R.id.ll_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_classification:
                hidePop(1);
                hidePop(2);
                if (classifyPop != null) {
                    if (classifyPop.isShowing()) {
                        hidePop(0);
                    } else {
                        llClassification.setSelected(true);
                        classifyPop.showAsDropDown(line, 0, 0);
                    }
                }
                break;
            case R.id.ll_region:
                hidePop(0);
                hidePop(2);
                if (regionPop != null) {
                    if (regionPop.isShowing()) {
                        hidePop(1);
                    } else {
                        llRegion.setSelected(true);
                        regionPop.showAsDropDown(line, 0, 0);
                    }
                }
                break;
            case R.id.ll_sort:
                hidePop(0);
                hidePop(1);
                if (sortPop != null) {
                    if (sortPop.isShowing()) {
                        hidePop(2);
                    } else {
                        llSort.setSelected(true);
                        sortPop.showAsDropDown(line, 0, 0);
                    }
                }
                break;
            case R.id.ll_switch:
                hidePop(0);
                hidePop(1);
                hidePop(2);
                if (isSwitch) {
                    ivSwitch.setSelected(false);
                    isSwitch = false;
                    commodityFragment.setSwitch(SWITCH_LISTVIEW);
                } else {
                    isSwitch = true;
                    ivSwitch.setSelected(true);
                    commodityFragment.setSwitch(SWITCH_GRIDVIEW);
                }

                break;
            case R.id.ll_search:
                if (TextUtils.isEmpty(title)) {
                    startActivity(new Intent(this, TuanSearchActivity.class));
                } else {
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        tabLayout.post(() -> setIndicator(tabLayout, 50, 50));
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

    private Data_BusinessListSortList mData;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0://TODO 绑定全部分类数据
                    //TODO 全部分类
                    mData = (Data_BusinessListSortList) msg.obj;
                    //TODO 分类
                    ArrayList<SortModel> mSortData = mData.items;
                    initClassify(mSortData);
                    //TODO 区域
                    List<Data_BusinessListSortList.AreaItemsBean> area_items = mData.area_items;
                    initRegion(area_items);
                    break;
            }
        }
    };

    @Override
    public void onSuccess(String url, String Json) {
        Gson gson = new Gson();
        switch (url) {
            case Api.WAIMAI_TUAN_CATES_AREAS:
                Response_BusinessList_SortList response = gson.fromJson(Json, Response_BusinessList_SortList.class);
                if (response.error.equals("0")) {
                    Data_BusinessListSortList data = response.data;
                    Message message = Message.obtain();
                    message.what = 0;
                    message.obj = data;
                    mHandler.sendMessage(message);
                } else {
                    ToastUtil.show(response.message);
                }
                break;
        }
    }

    @Override
    public void onBeforeAnimate() {

    }

    @Override
    public void onErrorAnimate() {

    }
}
