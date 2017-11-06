package com.jhcms.mall.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.widget.TextView;

import com.classic.common.MultipleStatusView;
import com.google.gson.Gson;
import com.jhcms.common.model.Data_Mall_Addr;
import com.jhcms.common.model.Response;
import com.jhcms.common.model.Response_Mall_Addr;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.widget.ListViewForScrollView;
import com.jhcms.mall.adapter.MallShippingAddressAdapter;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;
import com.jhcms.waimaiV3.dialog.TipDialog;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的收货地址
 */
public class MallShippingAddressActivty extends SwipeBaseActivity implements OnRequestSuccessCallback {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.listview)
    ListViewForScrollView listview;
    @Bind(R.id.statusview)
    MultipleStatusView statusview;
    @Bind(R.id.refreshlayout)
    SpringView refreshlayout;
    @Bind(R.id.addnewaddress)
    TextView addnewaddress;
    private MallShippingAddressAdapter mAdapter;
    private int page = 1;
    private List<Data_Mall_Addr.ItemsBean> addItems;
    private String addr_id;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_mall_shipping_address_activty);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        addr_id = getIntent().getStringExtra("addr_id");
        tvTitle.setText("我的收货地址");
        toolbar.setNavigationOnClickListener(v -> finish());
        setToolBar(toolbar);
        initRefreshlayout();
        mAdapter = new MallShippingAddressAdapter(MallShippingAddressActivty.this);
        listview.setAdapter(mAdapter);
        /*设为默认地址*/
        mAdapter.setOnDefaultListener(position -> {
            if (null != addItems && addItems.size() > 0) {
                updateAdd(Api.MALL_ADDR_SETDEFAULT, addItems.get(position).addr_id);
            }
        });
        /*删除地址*/
        mAdapter.setOnDeteleListener(position -> {
            /*client/v3/mall/addr/delete*/
            TipDialog dialog = new TipDialog(MallShippingAddressActivty.this, new TipDialog.setTipDialogCilck() {
                @Override
                public void setPositiveListener() {
                    if (null != addItems && addItems.size() > 0) {
                        updateAdd(Api.MALL_ADDR_DELETE, addItems.get(position).addr_id);
                    }
                }

                @Override
                public void setNegativeListener() {

                }
            });
            dialog.setMessage("确定要删除该地址吗?");
            dialog.show();
        });

        /*修改地址*/
        mAdapter.setOnModifyListener(position -> {
            Intent intent = new Intent(MallShippingAddressActivty.this, MallAddNewAddressActivity.class);
            intent.putExtra(MallAddNewAddressActivity.FLAG, MallAddNewAddressActivity.MODIFY);
            intent.putExtra(MallAddNewAddressActivity.ADDR_ID, addItems.get(position).addr_id);
            startActivity(intent);
        });
        /*下单界面更换地址*/
        mAdapter.setOnChangeListener((position, isChecked) -> {
            if (isChecked) {
                Intent intent = new Intent();
                intent.putExtra("addr_id", addItems.get(position).addr_id);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        page = 1;
        requestAdd(Api.MALL_ADDR_INDEX, 1);
    }

    private void updateAdd(String api, String addr_id) {
        if (TextUtils.isEmpty(addr_id)) {
            return;
        }
        JSONObject object = new JSONObject();
        try {
            object.put("addr_id", addr_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String str = object.toString();
        HttpUtils.postUrl(this, api, str, true, this);
    }


    private void requestAdd(String api, int page) {
        try {
            JSONObject object = new JSONObject();
            object.put("page", page);
            String str = object.toString();
            HttpUtils.postUrl(this, api, str, true, this);

        } catch (Exception e) {
            ToastUtil.show("网络出现了小问题！");
        }
    }


    private void initRefreshlayout() {
        refreshlayout.setGive(SpringView.Give.BOTH);
        refreshlayout.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                requestAdd(Api.MALL_ADDR_INDEX, page);
            }

            @Override
            public void onLoadmore() {
                page++;
                requestAdd(Api.MALL_ADDR_INDEX, page);
            }
        });
        refreshlayout.setHeader(new DefaultHeader(MallShippingAddressActivty.this));
        refreshlayout.setFooter(new DefaultFooter(MallShippingAddressActivty.this));
        refreshlayout.setType(SpringView.Type.FOLLOW);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        listview.dispatchTouchEvent(event);
        return super.onTouchEvent(event);
    }


    @OnClick(R.id.addnewaddress)
    public void onViewClicked() {
        Intent intent = new Intent(MallShippingAddressActivty.this, MallAddNewAddressActivity.class);
        intent.putExtra(MallAddNewAddressActivity.FLAG, MallAddNewAddressActivity.ADD);
        startActivity(intent);
    }

    @Override
    public void onSuccess(String url, String Json) {
        Gson gson = new Gson();
        switch (url) {
            case Api.MALL_ADDR_INDEX:
                Response_Mall_Addr response = gson.fromJson(Json, Response_Mall_Addr.class);
                if (response.error.equals("0")) {
                    addItems = response.data.items;
                    if (page == 1 && addItems.size() == 0) {
                        statusview.showEmpty();
                    } else {
                        statusview.showContent();
                    }
                    mAdapter.setData(addItems,addr_id);
                } else {
                    ToastUtil.show(response.message);
                }
                refreshlayout.onFinishFreshAndLoad();
                break;
            case Api.MALL_ADDR_SETDEFAULT:
            case Api.MALL_ADDR_DELETE:
                Response response1 = gson.fromJson(Json, Response.class);
                if ("0".equals(response1.error)) {
                    onResume();
                }
                ToastUtil.show(response1.message);
                break;
        }

    }

    @Override
    public void onBeforeAnimate() {

    }

    @Override
    public void onErrorAnimate() {
        refreshlayout.onFinishFreshAndLoad();
    }
}
