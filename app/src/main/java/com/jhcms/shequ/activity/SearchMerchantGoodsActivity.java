package com.jhcms.shequ.activity;

import android.content.res.Resources;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.shequ.fragment.BusinessFragment;
import com.jhcms.shequ.fragment.CommodityFragment;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;
import com.jhcms.waimaiV3.adapter.ClassifyPopLeftAdapter;
import com.jhcms.waimaiV3.adapter.ClassifyPopRightAdapter;
import com.jhcms.waimaiV3.adapter.CouponsViewPagerAdapter;
import com.jhcms.waimaiV3.adapter.OrderByPopAdapter;
import com.jhcms.waimaiV3.model.Model;
import com.jhcms.waimaiV3.model.ShopCateChildrens;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jhcms.common.utils.Api.WAIMAI_TUAN_CATES_AREAS;

/**
 * 社区首页分类
 */
public class SearchMerchantGoodsActivity extends SwipeBaseActivity implements OnRequestSuccessCallback {


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
    /*是否切换列表显示类型*/
    private boolean isSwitch = false;
    /*ListView类型*/
    public static int SWITCH_LISTVIEW = 1000;
    /*GridView类型*/
    public static int SWITCH_GRIDVIEW = 1001;


    private PopupWindow classifyPop;            //弹出选择分类框
    private View classifyView;                  //选择框的View
    private ListView classifyLeftList;               //左侧ListView
    private ListView classifyRightList;              //右侧ListView
    private ClassifyPopLeftAdapter classifyLeftAdapter;
    private ClassifyPopRightAdapter classifyRightAdapter;
    private List<ShopCateChildrens> leftData;
    private List<ShopCateChildrens> rightData;
    /*分类选项中左侧选中的位置*/
    private int classifyLeftPosition = 0;


    private PopupWindow sortPop;                //排序Popup
    private View orderByView;                   //排序View
    private ListView orderbyList;               //排序ListView
    private OrderByPopAdapter orderByAdapter;
    private List<String> orderbyData;


    private PopupWindow regionPop;
    private View regionView;
    private ListView regionLeftList;
    private ListView regionRightList;
    private ClassifyPopLeftAdapter regionLeftAdapter;
    private ClassifyPopRightAdapter regionRightAdapter;
    /*区域选项中左侧选中的位置*/
    private int regionLeftPosition = 0;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_search_merchant_goods);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /**
         * 当appBarLayout滑动时 隐藏PopuWindow
         * */
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) > 0) {
                    hidePop(0);
                    hidePop(1);
                    hidePop(2);
                }
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
                if (tab.getPosition() == 0) {
                    ivSwitch.setImageResource(R.drawable.select_switch_btn);
                    llSwitch.setClickable(true);
                } else {
                    ivSwitch.setImageResource(R.mipmap.tuan_btn_switch_two_no);
                    llSwitch.setClickable(false);
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

        //TODO 分类
        initClassify();
        //TODO 区域
        initRegion();
        // TODO 排序
        initSequence();
        requestCates(WAIMAI_TUAN_CATES_AREAS, Api.CITY_CODE);


    }

    private void requestCates(String api, String cityCode) {
        JSONObject object = new JSONObject();
        try {
            object.put("city_code", cityCode);
            HttpUtils.postUrl(this, api, null, true, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 分类
     */
    private void initClassify() {
        classifyView = LayoutInflater.from(this).inflate(R.layout.popup_choose_classify, null);
        classifyPop = MyPop(classifyView);
        classifyLeftList = (ListView) classifyView.findViewById(R.id.left_list);
        classifyRightList = (ListView) classifyView.findViewById(R.id.right_list);
        classifyLeftAdapter = new ClassifyPopLeftAdapter(this);
        classifyRightAdapter = new ClassifyPopRightAdapter(this);
        classifyLeftList.setAdapter(classifyLeftAdapter);
        // classifyLeftAdapter.setMoneyIsEmpty(getLeftData());
        /*默认选中第一项*/
        classifyLeftAdapter.setPosition(0);
//        classifyRightAdapter.setMoneyIsEmpty(getRightData(0));
        classifyRightAdapter.setId(0);
        /*左边listview点击事件*/
        classifyLeftList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                classifyLeftPosition = i;
                classifyRightAdapter.setId(0);
//                classifyRightAdapter.setMoneyIsEmpty(getRightData(i));
                classifyLeftAdapter.setPosition(i);
            }
        });

        classifyRightList.setAdapter(classifyRightAdapter);
        /*右边listview点击事件*/
        classifyRightList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                classifyRightAdapter.setId(i);
                hidePop(0);//隐藏分类Pop
                tvClassification.setText(getRightData(classifyLeftPosition).get(i).getTitle());

            }
        });
    }

    /**
     * 区域
     */
    private void initRegion() {
        regionView = LayoutInflater.from(this).inflate(R.layout.popup_choose_classify, null);
        regionPop = MyPop(regionView);
        regionLeftList = (ListView) regionView.findViewById(R.id.left_list);
        regionRightList = (ListView) regionView.findViewById(R.id.right_list);
        regionLeftAdapter = new ClassifyPopLeftAdapter(this);
        regionRightAdapter = new ClassifyPopRightAdapter(this);
        regionLeftList.setAdapter(regionLeftAdapter);
//        regionLeftAdapter.setMoneyIsEmpty(getLeftData());
        /*默认选中第一项*/
        regionLeftAdapter.setPosition(0);
//        regionRightAdapter.setMoneyIsEmpty(getRightData(0));
        regionRightAdapter.setId(0);
        /*左边listview点击事件*/
        regionLeftList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                regionLeftPosition = i;
                regionRightAdapter.setId(0);
//                regionRightAdapter.setMoneyIsEmpty(getRightData(i));
                regionLeftAdapter.setPosition(i);
            }
        });

        regionRightList.setAdapter(regionRightAdapter);
        /*右边listview点击事件*/
        regionRightList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                regionRightAdapter.setId(i);
                hidePop(1);//隐藏分类Pop
                tvRegion.setText(getRightData(regionLeftPosition).get(i).getTitle());

            }
        });
    }

    /**
     * 排序
     */
    private void initSequence() {
        orderbyData = new ArrayList<>();
        orderbyData.add(getString(R.string.智能排序));
        orderbyData.add(getString(R.string.距离最近));
        orderbyData.add(getString(R.string.评分最高));
        orderbyData.add(getString(R.string.销量最高));
        orderbyData.add(getString(R.string.起送价最低));
        orderbyData.add(getString(R.string.最早送达));
        orderByView = LayoutInflater.from(this).inflate(R.layout.popup_choose_orderby, null);
        orderbyList = (ListView) orderByView.findViewById(R.id.orderby_list);

        sortPop = MyPop(orderByView);
        orderByAdapter = new OrderByPopAdapter(this);
        orderByAdapter.setData(orderbyData);
        orderbyList.setAdapter(orderByAdapter);
        orderbyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                orderByAdapter.setPosition(position);
                hidePop(2);//隐藏排序pop
                tvSort.setText(orderbyData.get(position));
                ToastUtil.show(orderbyData.get(position));
            }
        });
    }

    private PopupWindow MyPop(View view) {
        PopupWindow popWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
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

    public List<ShopCateChildrens> getLeftData() {
        leftData = new ArrayList<>();
        for (int i = 0; i < Model.LEFTLISTVIEWTXT.length; i++) {
            ShopCateChildrens leftChildren = new ShopCateChildrens();
            leftChildren.setTitle(Model.LEFTLISTVIEWTXT[i]);
            leftChildren.setNum(Model.LISTVIEWNUM[i]);
            leftData.add(leftChildren);
        }
        return leftData;
    }

    public List<ShopCateChildrens> getRightData(int position) {
        rightData = new ArrayList<>();
        for (int i = 0; i < Model.RIGHTLISTTXT[position].length; i++) {
            ShopCateChildrens rightChildren = new ShopCateChildrens();
            rightChildren.setTitle(Model.RIGHTLISTTXT[position][i]);
            rightData.add(rightChildren);
        }
        return rightData;
    }

    @OnClick({R.id.ll_classification, R.id.ll_region, R.id.ll_sort, R.id.ll_switch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_classification:
                ToastUtil.show("分类");
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
                ToastUtil.show("区域");
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
                ToastUtil.show("排序");
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
                ToastUtil.show("切换");
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
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabLayout, 50, 50);
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

    @Override
    public void onSuccess(String url, String Json) {
        switch (url) {
            case Api.WAIMAI_TUAN_CATES_AREAS:


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
