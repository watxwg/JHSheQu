package com.jhcms.mall.activity;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccess;
import com.jhcms.common.widget.BaseActivity;
import com.jhcms.common.widget.DynamicTagFlowLayout;
import com.jhcms.common.widget.ListViewForListView;
import com.jhcms.mall.adapter.MallSearchAdpter;
import com.jhcms.mall.adapter.MallSearchListAdapter;
import com.jhcms.mall.litepal.MallSearchHistory;
import com.jhcms.mall.model.BaseResponse;
import com.jhcms.mall.model.HotSearchModel;
import com.jhcms.waimaiV3.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.FindMultiCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MallSearchActivty extends BaseActivity {
    private static final String TAG = "MallSearchActivty";

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_sort)
    TextView tvSort;
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.iv_delete)
    ImageView ivDelete;
    @Bind(R.id.fm_search)
    FrameLayout fmSearch;
    @Bind(R.id.rv_search_list)
    RecyclerView rvSearchList;
    private List<String> listData;
    private List<String> hotProduct;
    private List<String> hotShop;
    private List<String> hotData;
    private List<String> searchHistory;
    private View headerView;
    private PopupWindow searchpopwin;
    private LayoutInflater layoutInflater;
    /**
     * 标记当前的搜索种类，true是商品，false是商家
     */
    private boolean currentCatagory = true;
    /*---------------------headerView-------------------------*/
    private TagFlowLayout tagFlowLayout;
    private TextView tvDelete;
    private MallSearchListAdapter listAdapter;
    private TagAdapter<String> tagAdapter;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_mall_search_activty);
        ButterKnife.bind(this);
        inintsearchpopwi();
        rvSearchList.setLayoutManager(new LinearLayoutManager(this));
        headerView = LayoutInflater.from(this).inflate(R.layout.mall_search_list_item_header, rvSearchList, false);
        initHeaderView(headerView);
        listData = new ArrayList<>();
        listAdapter = new MallSearchListAdapter(this, listData, true, headerView);
        rvSearchList.setAdapter(listAdapter);
    }

    /**
     * 初始化headerView
     *
     * @param headerView
     */
    private void initHeaderView(View headerView) {
        tagFlowLayout = (TagFlowLayout) headerView.findViewById(R.id.tag_flow);
        tvDelete = (TextView) headerView.findViewById(R.id.tv_delete);
        tvDelete.setOnClickListener(v -> {
            searchHistory.clear();
            listData.clear();
            listAdapter.notifyDataSetChanged();
            DataSupport.deleteAll(MallSearchHistory.class);
        });
        hotData = new ArrayList<>();
        layoutInflater = LayoutInflater.from(this);
        tagAdapter = new TagAdapter<String>(hotData) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) layoutInflater.inflate(R.layout.mall_tag_layout, parent, false);
                tv.setText(s);
                return tv;
            }
        };
        tagFlowLayout.setAdapter(tagAdapter);
        tagFlowLayout.setOnTagClickListener((view, position, parent) -> {
            etSearch.setText(((TextView) view).getText().toString());
            etSearch.setSelection(((TextView) view).getText().toString().length());
            return true;
        });
    }

    @Override
    protected void initData() {
        requestHotData(Api.MALL_PRODUCT_SEARCH_HOT, true);
        requestHotData(Api.MALL_SHOP_SEARCH_HOT, false);
        DataSupport.order("id").findAsync(MallSearchHistory.class).listen(new FindMultiCallback() {
            @Override
            public <T> void onFinish(List<T> t) {
                List<MallSearchHistory> data = (List<MallSearchHistory>) t;
                searchHistory = new ArrayList<String>();
                for (MallSearchHistory mallSearchHistory : data) {
                    searchHistory.add(mallSearchHistory.getSearchContent());
                }
                listData.addAll(searchHistory);
                listAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 请求热门搜索数据
     *
     * @param api
     * @param isShowDialog 是否显示加载对话框
     */
    private void requestHotData(String api, boolean isShowDialog) {
        HttpUtils.postUrlWithBaseResponse(this, api, null, isShowDialog
                , new OnRequestSuccess<BaseResponse<HotSearchModel>>() {

                    @Override
                    public void onSuccess(String url, BaseResponse<HotSearchModel> data) {
                        if (Api.MALL_PRODUCT_SEARCH_HOT.equals(url)) {
                            hotProduct = data.getData().getHots();
                            initHotData(hotProduct);
                        } else if (Api.MALL_SHOP_SEARCH_HOT.equals(url)) {
                            hotShop = data.getData().getHots();
                        }
                        Log.e("++++++++++", "onSuccess: " + data.getData().getHots().size());
                    }

                    @Override
                    public void onBeforeAnimate() {

                    }

                    @Override
                    public void onErrorAnimate() {

                    }
                });

    }

    /**
     * 初始化热搜数据
     *
     * @param hotData
     */
    private void initHotData(List<String> hotData) {
        this.hotData.clear();
        this.hotData.addAll(hotData);
        tagAdapter.notifyDataChanged();
    }

    private void    inintsearchpopwi() {
        View view = LayoutInflater.from(MallSearchActivty.this).inflate(R.layout.item_mallsearchactivity_mall_select, null);
        searchpopwin = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        searchpopwin.setContentView(view);
        searchpopwin.setOutsideTouchable(true);
        searchpopwin.setBackgroundDrawable(new BitmapDrawable());
        searchpopwin.setFocusable(false);// 获取焦点
        final TextView mTvGood = (TextView) view.findViewById(R.id.Good_tv);
        final TextView mTvshop = (TextView) view.findViewById(R.id.shop_tv);

        mTvGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSort.setText(getString(R.string.mall_商品));
                if (searchpopwin.isShowing()) {
                    searchpopwin.dismiss();
                }
                if (currentCatagory) {
                    return;
                } else {
                    currentCatagory = true;
                    hotData.clear();
                    hotData.addAll(hotProduct);
                    tagAdapter.notifyDataChanged();
                }
            }
        });
        mTvshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSort.setText(getString(R.string.mall_商家));
                if (searchpopwin.isShowing()) {
                    searchpopwin.dismiss();
                }
                if (currentCatagory) {
                    currentCatagory = false;
                    hotData.clear();
                    hotData.addAll(hotShop);
                    tagAdapter.notifyDataChanged();
                } else {
                    return;
                }
            }
        });
    }


    @Override
    protected void initActionBar() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    fmSearch.setVisibility(View.VISIBLE);
                    ivDelete.setVisibility(View.VISIBLE);
                } else {
                    fmSearch.setVisibility(View.GONE);
                    ivDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected void initFindViewById() {
    }

    @Override
    protected void initEvent() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @OnClick({R.id.iv_back, R.id.tv_sort, R.id.iv_delete, R.id.fm_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                //隐藏软键盘
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if(im!=null){
                    im.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),0);
                }
                onBackPressed();
                break;
            case R.id.tv_sort:
                if (searchpopwin != null && !searchpopwin.isShowing())
                    searchpopwin.showAsDropDown(tvSort);
                break;
            case R.id.iv_delete:
                etSearch.setText("");

                break;
            case R.id.fm_search:
                String content = etSearch.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(this, getString(R.string.mall_请输入), Toast.LENGTH_SHORT).show();
                    return;
                }
                for (String s : searchHistory) {
                    if(content.equals(s)){
                        return;
                    }
                }
                MallSearchHistory history = new MallSearchHistory();
                history.setSearchContent(content);
                history.save();
                searchHistory.add(content);
                break;
        }
    }
}
