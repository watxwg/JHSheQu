package com.jhcms.shequ.activity;

import android.content.res.Resources;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jhcms.common.model.Data_WaiMai_SearchHot;
import com.jhcms.common.model.Response_WaiMai_SearchHot;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.DatabaseUtil;
import com.jhcms.common.utils.DividerListItemDecoration;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ProgressDialogUtil;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.widget.ClearEditText;
import com.jhcms.shequ.adapter.SearchHistoryAdapter;
import com.jhcms.shequ.adapter.SearchHotAdapter;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;
import com.jhcms.waimaiV3.adapter.CouponsViewPagerAdapter;
import com.jhcms.waimaiV3.fragment.SearchForBusinessFragment;
import com.jhcms.waimaiV3.fragment.SearchForGoodsFragment;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jhcms.common.utils.Utils.saveCrashInfo2File;


public class WaiMaiSearchGoodsActivity extends SwipeBaseActivity implements OnRequestSuccessCallback {

    @Bind(R.id.et_content)
    ClearEditText etContent;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appBar)
    AppBarLayout appBar;
    @Bind(R.id.gv_hot)
    GridView gvHot;
    @Bind(R.id.tv_delete)
    TextView tvDelete;
    @Bind(R.id.rv_history)
    RecyclerView searchHistoryListView;
    @Bind(R.id.ll_all)
    LinearLayout llAll;
    @Bind(R.id.iv_search)
    ImageView ivSearch;
    @Bind(R.id.ll_search)
    LinearLayout llSearch;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.flowlayout)
    TagFlowLayout flowlayout;
    @Bind(R.id.vp_search)
    ViewPager vpSearch;

    private SearchHistoryAdapter searchHistoryAdapter;
    private SearchHotAdapter searchHotAdapter;


    private LinearLayoutManager searchHistoryManager;
    private List<String> searchHistoryData = new ArrayList<>();
    private String afterText;


    List<Fragment> fragmentList;
    String[] titles = {"商品", "商家"};
    private SearchForGoodsFragment goodsFragment;
    private SearchForBusinessFragment businessFragment;
    private List<String> hots;
    private LayoutInflater mInflater;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_waimai_search_goods);
        ButterKnife.bind(this);
        mInflater = LayoutInflater.from(this);

    }


    @Override
    protected void initData() {
        setToolBar(appBar);
        toolbar.setNavigationOnClickListener(v -> closeActivity());
        initTabLayout();

        /*监听EditView上删除图片按钮*/
        etContent.setClearListener(() -> {
            /*刷新搜索历史*/
            if (DatabaseUtil.search().size() > 0) {
                searchHistoryAdapter.setData(DatabaseUtil.search());
                tvDelete.setVisibility(View.VISIBLE);
            } else {
                tvDelete.setVisibility(View.GONE);
            }
        });

        /**
         *监听键盘回车键
         */
        etContent.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                initRequest(afterText);
                return true;
            }
            return false;
        });
        /*监听EditView字符串变化*/
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence str, int start, int before, int count) {
                /*当输入框里是空字符串时*/
                if (str.toString().isEmpty()) {
                    /*隐藏搜索列表*/
                    tabLayout.setVisibility(View.GONE);
                    vpSearch.setVisibility(View.GONE);
                    /*显示热搜和搜索历史*/
                    llAll.setVisibility(View.VISIBLE);

                } else {
                    /*请求输入框里的内容*/
//                    initRequest(str.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                afterText = s.toString();
            }
        });

       /*分割线*/
        DividerListItemDecoration divider = new DividerListItemDecoration.Builder(this)
                .setHeight(R.dimen.dp_1)
                .setColorResource(R.color.list_item)
                .build();


        requestHotSearch(Api.WAIMAI_SHOP_HOTSEARCH);

        /**
         * 热搜结果
         * */
        searchHotAdapter = new SearchHotAdapter(this);
        gvHot.setAdapter(searchHotAdapter);
        gvHot.setOnItemClickListener((parent, view, position, id) -> {
            String strHot = hots.get(position);
            etContent.setText(strHot);
            etContent.setSelection(strHot.length());
                /*请求输入框里的内容*/
            initRequest(strHot);
        });

        /**
         * 搜索历史
         * */
        searchHistoryManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        searchHistoryAdapter = new SearchHistoryAdapter(this);


        searchHistoryListView.setLayoutManager(searchHistoryManager);
        searchHistoryListView.setAdapter(searchHistoryAdapter);
        searchHistoryListView.addItemDecoration(divider);
        searchHistoryAdapter.setOnClickListener((view, position) -> {
            String strHistory = DatabaseUtil.search().get(position);
            etContent.setText(strHistory);
            etContent.setSelection(strHistory.length());
                /*请求输入框里的内容*/
            initRequest(strHistory);
        });


        /**
         * 清除历史记录
         * */
        tvDelete.setOnClickListener(v -> {
            DatabaseUtil.deleteAll();
            searchHistoryAdapter.setData(null);
            ToastUtil.show("清除成功");
        });
    }

    private void requestHotSearch(String api) {
        HttpUtils.postUrl(this, api, null, true, this);
    }


    private void initTabLayout() {
        goodsFragment = new SearchForGoodsFragment();
        businessFragment = new SearchForBusinessFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(goodsFragment);
        fragmentList.add(businessFragment);

        vpSearch.setAdapter(new CouponsViewPagerAdapter(getSupportFragmentManager(), fragmentList, titles));
        tabLayout.setupWithViewPager(vpSearch);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction()) {
            //处理事件
            String str = etContent.getText().toString();
            if (!TextUtils.isEmpty(str)) {
                /*请求输入框里的内容*/
                initRequest(str.toString());
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }


    @Override
    protected void onResume() {
        super.onResume();
        searchHistoryData = DatabaseUtil.search();
        if (searchHistoryData.size() > 0) {
            searchHistoryAdapter.setData(searchHistoryData);
            tvDelete.setVisibility(View.VISIBLE);
        } else {
            tvDelete.setVisibility(View.GONE);
        }
    }

    private void initRequest(String str) {
        /*显示搜索列表*/
        tabLayout.setVisibility(View.VISIBLE);
        vpSearch.setVisibility(View.VISIBLE);
        /*隐藏热搜和搜索历史*/
        llAll.setVisibility(View.GONE);
        /*把搜索信息传到fragment*/
        goodsFragment.setData(str);
        businessFragment.setData(str);
        /*搜索信息保存在数据库中*/
        DatabaseUtil.insert(str);
    }


    private void closeActivity() {
        if (etContent.getText().toString().isEmpty()) {
            finish();
        } else {
            etContent.setText("");
            llAll.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.GONE);
            vpSearch.setVisibility(View.GONE);

            /*刷新搜索历史*/
            if (DatabaseUtil.search().size() > 0) {
                searchHistoryAdapter.setData(DatabaseUtil.search());
                tvDelete.setVisibility(View.VISIBLE);
            } else {
                tvDelete.setVisibility(View.GONE);
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        tabLayout.post(() -> setIndicator(tabLayout, 30, 30));
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        closeActivity();
    }


    @OnClick(R.id.ll_search)
    public void onClick() {
        if (TextUtils.isEmpty(etContent.getText().toString())) {
            ToastUtil.show("请输入搜索内容");
        } else {
            initRequest(afterText);
        }
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

    private TagAdapter<String> mAdapter;
    private String[] mVals;

    @Override
    public void onSuccess(String url, String Json) {
        try {
            Gson gson = new Gson();
            Response_WaiMai_SearchHot searchHot = gson.fromJson(Json, Response_WaiMai_SearchHot.class);
            if (searchHot.error.equals("0")) {
                Data_WaiMai_SearchHot data = searchHot.data;
                hots = data.hots;
                searchHotAdapter.setmListdata(hots);
                dealwithHot();
            } else {
                ToastUtil.show(searchHot.message);
            }
        } catch (Exception e) {
            ToastUtil.show(getString(R.string.数据解析异常));
            saveCrashInfo2File(e);
        } finally {
            ProgressDialogUtil.dismiss(WaiMaiSearchGoodsActivity.this);
        }

    }

    private void dealwithHot() {
        mVals = hots.toArray(new String[hots.size()]);
        flowlayout.setAdapter(mAdapter = new TagAdapter<String>(mVals) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.adapter_search_hot_item,
                        flowlayout, false);
                tv.setText(s);
                return tv;
            }
        });


        flowlayout.setOnTagClickListener((view, position, parent) -> {
            String strHot = hots.get(position);
            etContent.setText(strHot);
            etContent.setSelection(strHot.length());
            /*请求输入框里的内容*/
            initRequest(strHot);
            return true;
        });
    }

    @Override
    public void onBeforeAnimate() {

    }

    @Override
    public void onErrorAnimate() {

    }
}
