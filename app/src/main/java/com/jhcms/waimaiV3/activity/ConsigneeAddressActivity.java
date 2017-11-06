package com.jhcms.waimaiV3.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.classic.common.MultipleStatusView;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.google.gson.Gson;
import com.jhcms.common.model.Data_AddAddress;
import com.jhcms.common.model.MyAddress;
import com.jhcms.common.model.Response_ConsigneeAddressActivity;
import com.jhcms.common.model.SharedResponse;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.AddressAdapter;
import com.jhcms.waimaiV3.dialog.TipDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Wyj on 2017/5/16
 * TODO:地址管理界面
 */
public class ConsigneeAddressActivity extends SwipeBaseActivity implements OnRequestSuccessCallback {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.content_view)
    LRecyclerView adressRecycleView;
    private AddressAdapter adapter;
    LRecyclerViewAdapter mLRecyclerViewAdapter;
    @Bind(R.id.multiplestatusview)
    MultipleStatusView statusview;

    private ArrayList<MyAddress> mDataList = new ArrayList<>();
    private Data_AddAddress mdata;
    private int page;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                /*会员地址列表*/
                case 0:
                    mdata = (Data_AddAddress) msg.obj;
                    bindDataView(mdata.items);
                    break;
                /*删除地址*/
                case 1:
                    RequestAddressListData(page);
                    break;
                /*下单时选择地址*/
                case 2:
                    mdata = (Data_AddAddress) msg.obj;
                    bindDataView(mdata.items);
                    break;
            }
        }
    };
    private String addr_id;
    private String shop_id;

    private void bindDataView(List<MyAddress> items) {
        statusview.showContent();

        if (page == 1) {
            if (items != null && items.size() == 0) {
                statusview.showEmpty();
            } else {
                mDataList.clear();
                mDataList.addAll(items);
                adapter.setDataList(mDataList);
            }

        } else {
            if (items != null && items.size() == 0) {
                adressRecycleView.setNoMore(true);
            } else {
                mDataList.clear();
                mDataList.addAll(items);
                adapter.setDataList(mDataList);
            }
        }
        adressRecycleView.refreshComplete(mDataList.size());
    }


    @Override
    protected void initView() {
        setContentView(R.layout.activity_consignee_address);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        setToolBar(toolbar);
        tvTitle.setText(getResources().getString(R.string.我的收货地址));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addr_id = getIntent().getStringExtra("addr_id");
        shop_id = getIntent().getStringExtra("shop_id");
        adapter = new AddressAdapter(this);
        if (null != addr_id) {
            adapter.setType(addr_id);
        }
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        adressRecycleView.setAdapter(mLRecyclerViewAdapter);
        DividerDecoration divider = new DividerDecoration.Builder(this)
                .setHeight(R.dimen.dp_10)
                .setColorResource(R.color.background)
                .build();
        adressRecycleView.addItemDecoration(divider);


        adressRecycleView.setLayoutManager(new LinearLayoutManager(this));
        //设置头部加载颜色
        adressRecycleView.setHeaderViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载颜色
        adressRecycleView.setFooterViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载文字提示
        adressRecycleView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");

        adressRecycleView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        adressRecycleView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);//设置加载更多Progress的样式


        adapter.setOnClickListener(new AddressAdapter.OnClickListener() {
            @Override
            public void adressClick(String type, final int position) {
                switch (type) {
                    case "modify":
                        Intent intent = new Intent(ConsigneeAddressActivity.this, AddAddressActivity.class);
                        intent.putExtra("type", AddAddressActivity.MODIFY);
                        intent.putExtra("modle", mDataList.get(position));
                        startActivity(intent);
                        break;
                    case "delete":
                        TipDialog dialog = new TipDialog(ConsigneeAddressActivity.this, new TipDialog.setTipDialogCilck() {
                            @Override
                            public void setPositiveListener() {
                                DeleteADdress(position);
                            }

                            @Override
                            public void setNegativeListener() {

                            }
                        });
                        dialog.setMessage("确定要删除该地址吗?");
                        dialog.show();
                        break;
                    case "confirm":
                        Intent confirm = new Intent();
                        if (null != shop_id) {
                            confirm.putExtra("address", mDataList.get(position));
                            setResult(RESULT_OK, confirm);
                            finish();
                        }/* else {
                            confirm.setClass(ConsigneeAddressActivity.this, AddAddressActivity.class);
                            confirm.putExtra("type", AddAddressActivity.MODIFY);
                            confirm.putExtra("modle", mDataList.get(position));
                            startActivity(confirm);
                        }*/
                        break;
                }
            }
        });

        /**
         * 下拉刷新
         * //禁用下拉刷新功能
         * mRecyclerView.setPullRefreshEnabled(false);
         */
        adressRecycleView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                mLRecyclerViewAdapter.notifyDataSetChanged();
                page = 1;
                if (null != shop_id) {
                    RequestOrderAddressList(page);
                } else {
                    RequestAddressListData(page);
                }

            }
        });
        adressRecycleView.setLoadMoreEnabled(false);
    }


    private void DeleteADdress(int postion) {//删除地址
        try {
            JSONObject js = new JSONObject();
            js.put("addr_id", mDataList.get(postion).addr_id);
            String str = js.toString();
            HttpUtils.postUrl(this,Api.WAIMAI_ADDRESS_DELETE, str, true, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        adressRecycleView.refresh();
    }


    /**
     * 请求会员地址
     *
     * @param page
     */
    private void RequestAddressListData(int page) {
        try {
            JSONObject js = new JSONObject();
            js.put("page", page);
            String str = js.toString();
            HttpUtils.postUrl(this,Api.WAIMAI_ADDRESS_LIST, str, true, this);

        } catch (Exception e) {
            ToastUtil.show("网络出现了小问题！");
        }
    }

    private void RequestOrderAddressList(int page) {

        try {
            JSONObject js = new JSONObject();
            js.put("page", page);
            js.put("shop_id", shop_id);
            String str = js.toString();
            HttpUtils.postUrl(this,Api.WAIMAI_ORDER_ADDRESS_LIST, str, true, this);

        } catch (Exception e) {
            ToastUtil.show("网络出现了小问题！");
        }
    }

    @OnClick(R.id.tv_add_adress)
    public void onClick() {
        Intent intent = new Intent(ConsigneeAddressActivity.this, AddAddressActivity.class);
        intent.putExtra("type", AddAddressActivity.ADD);
        startActivity(intent);
    }

    @Override
    public void onSuccess(String url, String Json) {
        Gson gson = new Gson();
        switch (url) {
            case Api.WAIMAI_ADDRESS_LIST:
                Response_ConsigneeAddressActivity ResponseData = gson.fromJson(Json, Response_ConsigneeAddressActivity.class);
                Message message = Message.obtain();
                message.what = 0;
                message.obj = ResponseData.data;
                mHandler.sendMessage(message);
                break;
            case Api.WAIMAI_ADDRESS_DELETE:
                SharedResponse shareResPonse = gson.fromJson(Json, SharedResponse.class);
                if (shareResPonse.error.equals("0")) {
                    Toast.makeText(ConsigneeAddressActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    Message message1 = Message.obtain();
                    message1.what = 1;
                    mHandler.sendMessage(message1);
                }
                break;
            case Api.WAIMAI_ORDER_ADDRESS_LIST:
                Response_ConsigneeAddressActivity orderAddData = gson.fromJson(Json, Response_ConsigneeAddressActivity.class);
                Message orderList = Message.obtain();
                orderList.what = 2;
                orderList.obj = orderAddData.data;
                mHandler.sendMessage(orderList);
                break;
        }
    }

    @Override
    public void onBeforeAnimate() {

    }

    @Override
    public void onErrorAnimate() {
        adressRecycleView.refreshComplete(0);
    }
}
