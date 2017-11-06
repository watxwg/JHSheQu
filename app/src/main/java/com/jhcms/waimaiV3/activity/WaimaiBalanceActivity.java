package com.jhcms.waimaiV3.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.google.gson.Gson;
import com.jhcms.common.model.Balancemodel;
import com.jhcms.common.model.Response_Balance;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.WaimaiBalanceActivityAdapter;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WaimaiBalanceActivity extends SwipeBaseActivity implements OnRequestSuccessCallback {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.adress_recycleView)
    LRecyclerView adressRecycleView;
    private WaimaiBalanceActivityAdapter mAdapter;
    LRecyclerViewAdapter mLRecyclerViewAdapter;
    private ArrayList<Balancemodel> mdataList = new ArrayList<>();
    private int page = 1;
    private TextView mTvmoney;
    private TextView mtvchongzhi;
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                adressRecycleView.refresh();
            }
        }
    };

    @Override
    protected void initData() {
        setToolBar(toolbar);
        tvTitle.setText(getResources().getString(R.string.我的余额));
        toolbar.setNavigationOnClickListener(v -> finish());
        View view = LayoutInflater.from(WaimaiBalanceActivity.this).inflate(R.layout.waimai_balance_headview, (ViewGroup) this.findViewById(R.id.content), false);
        mTvmoney = (TextView) view.findViewById(R.id.money_tv);
        mtvchongzhi = (TextView) view.findViewById(R.id.chongzhi);
        mAdapter = new WaimaiBalanceActivityAdapter(WaimaiBalanceActivity.this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        adressRecycleView.setAdapter(mLRecyclerViewAdapter);
        DividerDecoration divider = new DividerDecoration.Builder(this)
                .setHeight(R.dimen.dp_2)
                .setColorResource(R.color.background)
                .build();
        adressRecycleView.addItemDecoration(divider);
//        adressRecycleView
        mLRecyclerViewAdapter.addHeaderView(view);
        adressRecycleView.setLayoutManager(new LinearLayoutManager(this));
        //设置头部加载颜色
        adressRecycleView.setHeaderViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载颜色
        adressRecycleView.setFooterViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载文字提示
        adressRecycleView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");


        adressRecycleView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
//        adressRecycleView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);  //设置下拉刷新箭头
        adressRecycleView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);//设置加载更多Progress的样式
        /**
         * 下拉刷新
         * //禁用下拉刷新功能
         mRecyclerView.setPullRefreshEnabled(false);
         */
        adressRecycleView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.clear();
                mLRecyclerViewAdapter.notifyDataSetChanged();
                page = 1;
                RequestAddressListData(page);

            }
        });
        /**
         * 加载更多
         * //禁用自动加载更多功能
         mRecyclerView.setLoadMoreEnabled(false);
         */
        adressRecycleView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                RequestAddressListData(page);
            }
        });
        /* * 充值跳转*/
        mtvchongzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaimaiBalanceActivity.this, RechargeActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Message message = Message.obtain();
        message.what = 0;
        mhandler.sendMessage(message);
    }

    private void RequestAddressListData(int page) {
        try {
            JSONObject js = new JSONObject();
            js.put("page", page);
            String str = js.toString();
            HttpUtils.postUrl(this, Api.WAIMAI_MONEY, str, true, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_waimai_balance);
        ButterKnife.bind(this);
    }

    private void BindViewData(Response_Balance mModel) {
        mTvmoney.setText(mModel.getData().getMoney());
        if (page == 1) {
            mdataList.clear();
            mdataList.addAll(mModel.getData().getItems());
            mAdapter.setDataList(mdataList);
            adressRecycleView.refreshComplete(mdataList.size());
        } else {
            if (mModel.getData().getItems().size() > 0) {
                mdataList.addAll(mModel.getData().getItems());
                mAdapter.setDataList(mdataList);
                adressRecycleView.refreshComplete(mdataList.size());
            }
        }

    }

    @Override
    public void onSuccess(String url, String Json) {
        Gson gson = new Gson();
        switch (url) {
            case Api.WAIMAI_MONEY:
                Response_Balance mModel = gson.fromJson(Json, Response_Balance.class);
                BindViewData(mModel);
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
