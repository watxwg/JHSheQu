package com.jhcms.tuangou.activity;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jhcms.common.model.Data_WaiMai_SearchHot;
import com.jhcms.common.model.Response_WaiMai_SearchHot;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.DividerListItemDecoration;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ProgressDialogUtil;
import com.jhcms.common.utils.SearDataBaseUtils;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.ClearEditText;
import com.jhcms.shequ.adapter.SearchHistoryAdapter;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jhcms.common.utils.Utils.saveCrashInfo2File;

public class TuanSearchActivity extends SwipeBaseActivity implements OnRequestSuccessCallback {


    @Bind(R.id.et_content)
    ClearEditText etContent;
    @Bind(R.id.ll_search)
    LinearLayout llSearch;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appBar)
    AppBarLayout appBar;
    @Bind(R.id.flowlayout)
    TagFlowLayout flowlayout;
    @Bind(R.id.tv_delete)
    TextView tvDelete;
    @Bind(R.id.rv_history)
    RecyclerView rvHistory;
    private SearchHistoryAdapter searchHistoryAdapter;
    private LinearLayoutManager searchHistoryManager;
    private String afterText;
    private String[] mVals;
    private TagAdapter<String> mAdapter;
    private LayoutInflater mInflater;
    private List<String> searchHistoryData;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_tuan_search);
        ButterKnife.bind(this);
        mInflater = LayoutInflater.from(this);
    }

    @Override
    protected void initData() {
        setToolBar(appBar);
        toolbar.setNavigationOnClickListener(v -> finish());
        /*监听EditView上删除图片按钮*/
        etContent.setClearListener(() -> {
            /*刷新搜索历史*/
            if (SearDataBaseUtils.search().size() > 0) {
                searchHistoryAdapter.setData(SearDataBaseUtils.search());
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
                if (TextUtils.isEmpty(afterText)) {
                    ToastUtil.show("请输入搜索内容");
                } else {
                    initRequest(afterText);
                }
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

            }

            @Override
            public void afterTextChanged(Editable s) {
                afterText = s.toString();
            }
        });
        requestHotSearch(Api.TUAN_SHOP_HOTSEARCH);
        /*分割线*/
        DividerListItemDecoration divider = Utils.setDivider(this, R.dimen.dp_1, R.color.list_item);


        /**
         * 搜索历史
         * */
        searchHistoryManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        searchHistoryAdapter = new SearchHistoryAdapter(this);

        rvHistory.setLayoutManager(searchHistoryManager);
        rvHistory.setAdapter(searchHistoryAdapter);
        rvHistory.addItemDecoration(divider);
        searchHistoryAdapter.setOnClickListener((view, position) -> {
            String strHistory = SearDataBaseUtils.search().get(position);
            etContent.setText(strHistory);
            etContent.setSelection(strHistory.length());
                /*请求输入框里的内容*/
            initRequest(strHistory);
        });


        /**
         * 清除历史记录
         * */
        tvDelete.setOnClickListener(v -> {
            SearDataBaseUtils.deleteAll();
            searchHistoryAdapter.setData(null);
            ToastUtil.show("清除成功");
        });
    }

    private void requestHotSearch(String api) {
        HttpUtils.postUrl(this, api, null, true, this);
    }

    private void initRequest(String str) {
        /*搜索信息保存在数据库中*/
        SearDataBaseUtils.insert(str);
        Intent intent = new Intent(this, TuanSearchGoodsActivity.class);
        intent.putExtra(TuanSearchGoodsActivity.CONTENT, str);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchHistoryData = SearDataBaseUtils.search();
        if (searchHistoryData.size() > 0) {
            searchHistoryAdapter.setData(searchHistoryData);
            tvDelete.setVisibility(View.VISIBLE);
        } else {
            tvDelete.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.ll_search, R.id.tv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_search:
                if (TextUtils.isEmpty(etContent.getText().toString())) {
                    ToastUtil.show("请输入搜索内容");
                } else {
                    initRequest(afterText);
                }
                break;
            case R.id.tv_delete:
                SearDataBaseUtils.deleteAll();
                searchHistoryAdapter.setData(null);
                ToastUtil.show("清除成功");
                break;
        }
    }

    @Override
    public void onSuccess(String url, String Json) {
        try {
            Gson gson = new Gson();
            Response_WaiMai_SearchHot searchHot = gson.fromJson(Json, Response_WaiMai_SearchHot.class);
            if (searchHot.error.equals("0")) {
                Data_WaiMai_SearchHot data = searchHot.data;

                dealwithHot(data.hots);
            } else {
                ToastUtil.show(searchHot.message);
            }
        } catch (Exception e) {
            ToastUtil.show(getString(R.string.数据解析异常));
            saveCrashInfo2File(e);
        } finally {
            ProgressDialogUtil.dismiss(TuanSearchActivity.this);
        }
    }

    private void dealwithHot(List<String> hots) {
        if (null != hots && hots.size() > 0) {
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

    }

    @Override
    public void onBeforeAnimate() {

    }

    @Override
    public void onErrorAnimate() {

    }
}
