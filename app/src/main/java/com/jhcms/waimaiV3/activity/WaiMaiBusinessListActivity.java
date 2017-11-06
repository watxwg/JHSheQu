package com.jhcms.waimaiV3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.classic.common.MultipleStatusView;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.google.gson.Gson;
import com.jhcms.common.model.Response_BusinessList_SortList;
import com.jhcms.common.model.Response_WaiMai_Home;
import com.jhcms.common.model.ShopItems;
import com.jhcms.common.model.SortModel;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.LogUtil;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.MyPopupWindow;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.ClassifyPopLeftAdapter;
import com.jhcms.waimaiV3.adapter.ClassifyPopRightAdapter;
import com.jhcms.waimaiV3.adapter.FilterAdapter;
import com.jhcms.waimaiV3.adapter.OrderByPopAdapter;
import com.jhcms.waimaiV3.adapter.ShopItemAdapter;
import com.jhcms.waimaiV3.model.MessageEvent;
import com.jhcms.waimaiV3.model.Model;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jhcms.common.utils.Utils.saveCrashInfo2File;

/**
 * Created by Wyj on 2017/4/20
 * TODO:商家列表
 */
public class WaiMaiBusinessListActivity extends SwipeBaseActivity implements View.OnClickListener, OnRequestSuccessCallback {
    public static String CAT_ID = "CAT_ID";
    public static String REFRESH_SHOPITEM = "REFRESH_SHOPITEM";
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.business_toolbar)
    Toolbar businessToolbar;
    @Bind(R.id.tv_allSort)
    TextView tvAllSort;
    @Bind(R.id.iv_allSort)
    ImageView ivAllSort;
    @Bind(R.id.ll_allSort)
    LinearLayout llAllSort;
    @Bind(R.id.tv_sequence)
    TextView tvSequence;
    @Bind(R.id.iv_sequence)
    ImageView ivSequence;
    @Bind(R.id.ll_sequence)
    LinearLayout llSequence;
    @Bind(R.id.tv_filter)
    TextView tvFilter;
    @Bind(R.id.iv_filter)
    ImageView ivFilter;
    @Bind(R.id.ll_filter)
    LinearLayout llFilter;
    @Bind(R.id.ll_buss)
    LinearLayout llBuss;
    @Bind(R.id.content_view)
    LRecyclerView businessList;
    @Bind(R.id.statusview)
    MultipleStatusView statusview;
    @Bind(R.id.line_staff)
    View line;

    private ShopItemAdapter businessAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;

    private MyPopupWindow classifyPop;            //弹出选择分类框
    private View classifyView;                  //选择框的View
    private ListView popLeftList;               //左侧ListView
    private ListView popRightList;              //右侧ListView
    private LinearLayout popList;
    private ClassifyPopLeftAdapter leftAdapter;
    private ClassifyPopRightAdapter rightAdapter;
    int leftId;
    private MyPopupWindow orderByPop;             //排序Popup
    private View orderByView;                   //排序View
    private ListView orderbyList;               //排序ListView
    private OrderByPopAdapter orderByAdapter;
    private List<String> orderbyData;
    private MyPopupWindow filterPop;              //筛选Popup
    private View filterView;
    private List<String> pei, youhui, tese;
    private FilterAdapter peiTypeAdapter, youHuiAdapter, teSeAdapter;
    private int peiTypeId = -1;
    private int youhuiId = -1;
    private int teSeId = -1;//筛选中选中每级的id
    private String peiType;//配送方式选中的条目
    private String youHui;//优惠信息选中条目
    private String teSe;//商家特色选中条目
    private ArrayList<SortModel> mSortData;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0://TODO 绑定全部分类数据
                    //TODO 全部分类
                    mSortData = (ArrayList<SortModel>) msg.obj;
                    initClassify(mSortData);
                    break;
            }
        }
    };
    private int rightID;
    /**
     * 排序传值
     * 默认：default 距离：juli 评分：score 销量：sales 起送价：price 配送时间：ptime
     */
    private String order = "default";
    String[] orders = {"default", "juli", "score", "sales", "price", "ptime"};
    /**
     * 配送方式
     * shop_pei商家配送，roof_pei平台配送
     */
    private String pei_filter;
    String[] peiFilter = {"", "roof_pei", "shop_pei"};

    /**
     * 优惠方式
     * youhui_first首单优惠，manjian满减，manfan满返 ,coupon 进店领券
     */
    private String youhui_filter;
    String[] youhuiFilter = {"youhui_first", "manjian", "manfan", "coupon"};

    /**
     * 特色
     * is_new是否是新店，online_pay是否支持在线支付，free_pei免配送费，zero_amount0元起送
     * "免配送费", "新店开业", "0元起送", "在线支付"
     */
    private String feature_filter;
    String[] featureFilter = {"free_pei", "is_new", "zero_amount", "online_pay"};
    /**
     * cate_id	分类ID	分类ID
     */
    private String cate_id;
    private List<ShopItems> items;
    /**
     * 商品一级分类id
     */
    private String cat_id;
    private int pageNum = 1;
    private int height;
    private ArrayList<SortModel> sortItems;


    @Override
    protected void initData() {
        setToolBar(businessToolbar);
        cate_id = cat_id = getIntent().getStringExtra(CAT_ID);
        //TODO 请求分类数据
        initBusiness();
        RequestSortData();
        if (businessToolbar != null) {
            businessToolbar.setNavigationOnClickListener(v -> finish());
        }
        // TODO 排序
        initSequence();
        // TODO 筛选
        initFilter();
        height = businessToolbar.getHeight() + Utils.getStatusBarHeight(this) + llBuss.getHeight();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_waimai_business_list);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

    }

    private void RequestSortData() {
        HttpUtils.postUrl(this, Api.WAIMAI_SORT, null, true, this);
    }

    private void initRefresh() {
        //设置头部加载颜色
        businessList.setHeaderViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载颜色
        businessList.setFooterViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载文字提示
        businessList.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
        businessList.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        businessList.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);//设置加载更多Progress的样式
        businessList.setOnRefreshListener(() -> {
            if (null == sortItems) {
                RequestSortData();
            }
            businessAdapter.clear();
            lRecyclerViewAdapter.notifyDataSetChanged();
            pageNum = 1;
            requestShopList();
        });
        businessList.setNestedScrollingEnabled(true);
        /**
         * 加载更多
         * //禁用自动加载更多功能
         */
//        businessList.setLoadMoreEnabled(false);
        businessList.setOnLoadMoreListener(() -> {
            pageNum++;
            requestShopList();
        });
    }

    /**
     * 全部分类
     */
    private void initClassify(final ArrayList<SortModel> mData) {
        classifyView = LayoutInflater.from(this).inflate(R.layout.popup_choose_classify, null);
        classifyPop = MyPop(classifyView);
        popList = (LinearLayout) classifyView.findViewById(R.id.ll_list);
        popLeftList = (ListView) classifyView.findViewById(R.id.left_list);
        popRightList = (ListView) classifyView.findViewById(R.id.right_list);
        int pHeight = mData.size() * Utils.dip2px(this, 40);
        if (pHeight >= Utils.getScreenH(this) / 2) {
            pHeight = Utils.getScreenH(this) / 2;
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height = pHeight;
        popList.setLayoutParams(params);

        classifyView.findViewById(R.id.ll_touch).setOnClickListener(v -> hidePop(0));
        leftAdapter = new ClassifyPopLeftAdapter(this);
        rightAdapter = new ClassifyPopRightAdapter(this);
        popLeftList.setAdapter(leftAdapter);
        leftAdapter.setData(mData);

        /**
         * 当没有其他页面传cat_id进来时
         * 默认请求分类中的第一个cate_id
         * */
        if (!TextUtils.isEmpty(cat_id)) {
            for (int i = 0; i < mData.size(); i++) {
                if (mData.get(i).cate_id.equals(cat_id)) {
                    leftId = i;
                    tvAllSort.setText(mData.get(i).title);
                }
            }
        } else {
            cate_id = mData.get(0).cate_id;
        }
        /*默认选中第一项*/
        leftAdapter.setPosition(leftId);
        LogUtil.e("mData.get(leftId).childrens: " + mData.get(leftId).childrens);
        rightAdapter.setData(mData.get(leftId).childrens);

        rightAdapter.setId(0);

        businessList.refresh();
        /*左边listview点击事件*/
        popLeftList.setOnItemClickListener((parent, view, position, id) -> {
            leftId = position;
            leftAdapter.setPosition(leftId);
            if (null != mData.get(position).childrens && mData.get(position).childrens.size() > 0) {
                rightAdapter.setId(0);
                rightAdapter.setData(mData.get(position).childrens);
            } else {
                    /*TODO 请求商户列表接口*/
                tvAllSort.setText(mData.get(position).title);
                hidePop(0);//隐藏全部分类pop
                cate_id = mData.get(0).cate_id;
                rightAdapter.setData(mData.get(position).childrens);
                businessList.refresh();
            }
        });
        popRightList.setAdapter(rightAdapter);
        /*右边listview点击事件*/
        popRightList.setOnItemClickListener((parent, view, position, id) -> {
            rightAdapter.setId(position);
            hidePop(0);//隐藏全部分类pop
            tvAllSort.setText(mData.get(leftId).childrens.get(position).title);
                /*TODO 请求商户列表接口*/
            cate_id = mData.get(leftId).childrens.get(position).cate_id;
            businessList.refresh();
        });
    }

    public void requestShopList() {
        JSONObject object = new JSONObject();
        try {
            object.put("page", pageNum);
            object.put("cate_id", cate_id);
            object.put("order", order);
            object.put("pei_filter", pei_filter);
            object.put("youhui_filter", youhui_filter);
            object.put("feature_filter", feature_filter);
            String string = object.toString();
            HttpUtils.postUrl(this, Api.WAIMAI_SHOP_LIST, string, true, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
        orderByView.findViewById(R.id.ll_touch).setOnClickListener(v -> hidePop(1));
        orderByPop = MyPop(orderByView);
        orderByAdapter = new OrderByPopAdapter(this);
        orderByAdapter.setData(orderbyData);
        orderbyList.setAdapter(orderByAdapter);
        orderbyList.setOnItemClickListener((parent, view, position, id) -> {
            orderByAdapter.setPosition(position);
            hidePop(1);//隐藏排序pop
            tvSequence.setText(orderbyData.get(position));
            order = orders[position];
            businessList.refresh();
        });
    }

    /**
     * 筛选
     */
    private void initFilter() {
        filterView = LayoutInflater.from(this).inflate(R.layout.popup_choose_filter, null);
        filterPop = MyPop(filterView);
        filterView.findViewById(R.id.ll_touch).setOnClickListener(v -> hidePop(2));
        GridView gridViewPeitype = (GridView) filterView.findViewById(R.id.gridView_peitype);
        GridView gridViewYouhui = (GridView) filterView.findViewById(R.id.gridView_youhui);
        GridView gridViewTese = (GridView) filterView.findViewById(R.id.gridView_tese);
        TextView tvDetermine = (TextView) filterView.findViewById(R.id.tv_determine);
        TextView tvEmptied = (TextView) filterView.findViewById(R.id.tv_emptied);
        peiTypeAdapter = new FilterAdapter(this);
        youHuiAdapter = new FilterAdapter(this);
        teSeAdapter = new FilterAdapter(this);
        //TODO 配送方式
        gridViewPeitype.setAdapter(peiTypeAdapter);
        peiTypeAdapter.setData(getPei());
        gridViewPeitype.setOnItemClickListener((parent, view, position, id) -> {
            peiTypeId = position;
            peiTypeAdapter.setPosition(position);
        });
        //TODO 优惠信息
        gridViewYouhui.setAdapter(youHuiAdapter);
        youHuiAdapter.setData(getYouHui());
        gridViewYouhui.setOnItemClickListener((parent, view, position, id) -> {
            youhuiId = position;
            youHuiAdapter.setPosition(position);
        });
        //TODO 商家特色
        gridViewTese.setAdapter(teSeAdapter);
        teSeAdapter.setData(getTeSe());
        gridViewTese.setOnItemClickListener((parent, view, position, id) -> {
            teSeId = position;
            teSeAdapter.setPosition(position);
        });
        //TODO 清空
        tvEmptied.setOnClickListener(v -> {
            peiTypeId = -1;
            youhuiId = -1;
            teSeId = -1;
            peiTypeAdapter.setPosition(peiTypeId);
            youHuiAdapter.setPosition(youhuiId);
            teSeAdapter.setPosition(teSeId);
        });
        //TODO 确定
        tvDetermine.setOnClickListener(v -> {
            if (peiTypeId != -1) {
                peiType = getPei().get(peiTypeId);
                pei_filter = peiFilter[peiTypeId];
            } else {
                peiType = null;
                pei_filter = "";
            }
            if (youhuiId != -1) {
                youHui = getYouHui().get(youhuiId);
                youhui_filter = youhuiFilter[youhuiId];
            } else {
                youHui = null;
                youhui_filter = "";
            }
            if (teSeId != -1) {
                teSe = getTeSe().get(teSeId);
                feature_filter = featureFilter[teSeId];
            } else {
                teSe = null;
                feature_filter = "";
            }
            businessList.refresh();
            hidePop(2);
        });
    }

    private MyPopupWindow MyPop(View view) {
        int screenH = Utils.getScreenH(this);
        int height = screenH - businessToolbar.getHeight() - Utils.getStatusBarHeight(this) - llBuss.getHeight();
        MyPopupWindow popWindow = new MyPopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return popWindow;
    }


    private void initBusiness() {
        businessAdapter = new ShopItemAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(businessAdapter);
        businessList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        businessList.setAdapter(lRecyclerViewAdapter);
        initRefresh();
        businessAdapter.setOnClickListener(shopId -> {
            if (!Utils.isFastDoubleClick()) {
                Intent intent = new Intent(WaiMaiBusinessListActivity.this, WaiMaiShopActivity.class);
                intent.putExtra(WaiMaiShopActivity.SHOP_ID, shopId);
                startActivity(intent);
            }
        });
    }


    @OnClick({R.id.ll_allSort, R.id.ll_sequence, R.id.ll_filter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_allSort://TODO 全部分类
                hidePop(1);
                hidePop(2);
                if (classifyPop != null) {
                    if (classifyPop.isShowing()) {
                        hidePop(0);
                    } else {
                        llAllSort.setSelected(true);
                        classifyPop.showAsDropDown(line);
                    }
                }
                break;
            case R.id.ll_sequence://TODO 排序
                hidePop(0);
                hidePop(2);
                if (orderByPop != null) {
                    if (orderByPop.isShowing()) {
                        hidePop(1);
                    } else {
                        llSequence.setSelected(true);
                        orderByPop.showAsDropDown(line);
                    }
                }
                break;
            case R.id.ll_filter://TODO 筛选
                hidePop(0);
                hidePop(1);
                if (filterPop != null) {
                    if (filterPop.isShowing()) {
                        hidePop(2);
                    } else {
                        llFilter.setSelected(true);
                        filterPop.showAsDropDown(line);
                    }
                }
                break;
        }
    }

    private void hidePop(int position) {
        switch (position) {
            case 0:
                if (classifyPop != null && classifyPop.isShowing()) {
                    classifyPop.dismiss();
                    llAllSort.setSelected(false);
                }
                break;
            case 1:
                if (orderByPop != null && orderByPop.isShowing()) {
                    orderByPop.dismiss();
                    llSequence.setSelected(false);
                }
                break;
            case 2:
                if (filterPop != null && filterPop.isShowing()) {
                    filterPop.dismiss();
                    llFilter.setSelected(false);
                }
                break;
            default:
                break;
        }
    }

    public List<String> getPei() {
        pei = new ArrayList<>();
        for (int i = 0; i < Model.FILTERPEI.length; i++) {
            pei.add(Model.FILTERPEI[i]);
        }
        return pei;
    }

    public List<String> getYouHui() {
        youhui = new ArrayList<>();
        for (int i = 0; i < Model.FILTERYOUHUI.length; i++) {
            youhui.add(Model.FILTERYOUHUI[i]);
        }
        return youhui;
    }

    public List<String> getTeSe() {
        tese = new ArrayList<>();
        for (int i = 0; i < Model.FILTERTESR.length; i++) {
            tese.add(Model.FILTERTESR[i]);
        }
        return tese;
    }

    @Override
    public void onSuccess(String url, String Json) {
        try {
            Gson gson = new Gson();
            switch (url) {
                case Api.WAIMAI_SORT:
                    Response_BusinessList_SortList response_businessList_sortList = gson.fromJson(Json, Response_BusinessList_SortList.class);
                    if (response_businessList_sortList.error.equals("0")) {
                        sortItems = response_businessList_sortList.data.items;
                        Message message = Message.obtain();
                        message.what = 0;
                        message.obj = sortItems;
                        mHandler.sendMessage(message);
                    } else {
                        ToastUtil.show(response_businessList_sortList.message);
                    }
                    break;
                case Api.WAIMAI_SHOP_LIST:
                    Response_WaiMai_Home response = gson.fromJson(Json, Response_WaiMai_Home.class);
                    if (response.error.equals("0")) {
                        statusview.showContent();
                        items = response.data.items;
                        if (pageNum == 1) {
                            if (null != items && items.size() == 0) {
                                statusview.showEmpty();
                            } else {
                                businessAdapter.setDataList(items);
                            }
                        } else {
                            if (null != items && items.size() == 0) {
                                businessList.setNoMore(true);
                            } else {
                                businessAdapter.addAll(items);
                            }
                        }
                        businessList.refreshComplete(items.size());
                    } else {
                        businessList.refreshComplete(0);
                        ToastUtil.show(response.message);
                    }
                    break;
            }
        } catch (Exception e) {
            businessList.refreshComplete(0);
            ToastUtil.show("数据解析异常");
            saveCrashInfo2File(e);
        }
    }

    @Override
    public void onBeforeAnimate() {

    }


    @Override
    public void onErrorAnimate() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        if (event.message.equals(REFRESH_SHOPITEM)) {
            if (businessAdapter != null) {
                businessAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}