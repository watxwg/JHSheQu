package com.jhcms.shequ.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jhcms.common.model.BankModel;
import com.jhcms.common.model.Response_Bank_model;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.shequ.adapter.BankListAdapter;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BankListActivity extends SwipeBaseActivity {

    @Bind(R.id.Rv_BankList)
    RecyclerView RvBankList;
    @Bind(R.id.spring)
    SpringView mSpring;
    @Bind(R.id.ll_AddBank)
    LinearLayout llAddBank;
    @Bind(R.id.iv_close)
    ImageView ivClose;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.seach_iv)
    ImageView seachIv;
    @Bind(R.id.tv_right_title)
    TextView tvRightTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.empty_Imageview)
    FrameLayout emptyImageview;
    private BankListAdapter MbanklistAdapter;
    private int pagerIndex;
    private ArrayList<BankModel> mdatalist=new ArrayList<>();
    //信用卡详情参数
    private String CardModel="CardModel";
    private  String Type="Type";

    @Override
    protected void initData() {
        BindInitViewData();
        inintSpringView();
        InintRvBankList();
        requestBankData();
    }

    private void requestBankData() {
        HttpUtils.postUrl(BankListActivity.this, Api.Card_List, null, false, new OnRequestSuccessCallback() {
            @Override
            public void onSuccess(String url, String Json) {
                dismiss(BankListActivity.this);
                switch (url) {
                    case Api.Card_List:
                        Gson gson = new Gson();
                        Response_Bank_model response_bank_model = gson.fromJson(Json, Response_Bank_model.class);
                        if (response_bank_model.error.equals("0")) {
                            mdatalist.clear();
                            if (response_bank_model.getData().size() > 0) {
                                emptyImageview.setVisibility(View.GONE);
                                RvBankList.setVisibility(View.VISIBLE);
                                mdatalist.addAll(response_bank_model.getData());
                                MbanklistAdapter.notifyDataSetChanged();
                            } else {
                                emptyImageview.setVisibility(View.VISIBLE);
                                RvBankList.setVisibility(View.GONE);
                            }
                        } else {
                            ToastUtil.show(response_bank_model.message);
                        }
                        break;

                }
            }

            @Override
            public void onBeforeAnimate() {
                showProgressDialog(BankListActivity.this);
            }

            @Override
            public void onErrorAnimate() {
                dismiss(BankListActivity.this);
            }
        });
    }

    private void InintRvBankList() {
        MbanklistAdapter = new BankListAdapter(BankListActivity.this,mdatalist);
        MbanklistAdapter.setmOnItemClickListent(new BankListAdapter.onItemClickListent() {
            @Override
            public void onitemClickListent(int postion) {
                Intent intent=new Intent(BankListActivity.this,AddBankActivity.class);
                intent.putExtra(Type,"Update");
                intent.putExtra("CardModel",mdatalist.get(postion));
                startActivity(intent);
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(BankListActivity.this);
        RvBankList.setLayoutManager(manager);
        RvBankList.setAdapter(MbanklistAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
       mSpring.callFresh();
    }

    /**
     * 下拉刷新
     */
    private void inintSpringView() {
        mSpring.setGive(SpringView.Give.BOTH);
        mSpring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                requestBankData();
                 mSpring.onFinishFreshAndLoad();

            }

            @Override
            public void onLoadmore() {


            }
        });
        mSpring.setHeader(new DefaultHeader(BankListActivity.this));
        mSpring.setType(SpringView.Type.FOLLOW);
    }

    private void BindInitViewData() {
        tvTitle.setText("我的信用卡");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_bank_list);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_close, R.id.ll_AddBank})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_AddBank:
                Intent intent = new Intent(BankListActivity.this, AddBankActivity.class);
                intent.putExtra(Type,"Add");
                startActivity(intent);
                break;
        }
    }



}
