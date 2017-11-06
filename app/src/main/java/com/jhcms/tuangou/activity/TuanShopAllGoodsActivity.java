package com.jhcms.tuangou.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.classic.common.MultipleStatusView;
import com.google.gson.Gson;
import com.jhcms.common.model.Data_Group_Shop_Detail;
import com.jhcms.common.model.Response_Group_All_Good;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.tuangou.adapter.SonRvAdapter;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.jhcms.common.utils.Utils.saveCrashInfo2File;


public class TuanShopAllGoodsActivity extends SwipeBaseActivity implements OnRequestSuccessCallback {

    public static String SHOP_ID = "TYPE";
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.content_view)
    RecyclerView rvAllgoods;
    @Bind(R.id.refreshlayout)
    SpringView mRefreshLayout;
    @Bind(R.id.statusview)
    MultipleStatusView statusview;
    private SonRvAdapter sonAdapter;
    private String shopId;
    private int pageNum = 1;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_tuan_shop_all_goods);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        setToolBar(toolbar);
        shopId = getIntent().getStringExtra(SHOP_ID);
        tvTitle.setText("该商家全部商品");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        requestAllGoods(Api.WAIMAI_TUAN_ALL_GOODS, shopId, pageNum);

        inintIntent();
        inintlistview();
    }

    private void requestAllGoods(String api, String shopId, int pageNum) {
        JSONObject object = new JSONObject();
        try {
            object.put("shop_id", shopId);
            object.put("page", pageNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String str = object.toString();
        HttpUtils.postUrl(this, api, str, true, this);
    }

    private void inintlistview() {
        /*item_tuan_frgment_listview*/
        sonAdapter = new SonRvAdapter(this);
        rvAllgoods.setAdapter(sonAdapter);
        rvAllgoods.setLayoutManager(new LinearLayoutManager(this));
        rvAllgoods.addItemDecoration(Utils.setDivider(this, R.dimen.dp_050, R.color.qing));

    }

    private void inintIntent() {
        mRefreshLayout.setGive(SpringView.Give.BOTH);

        mRefreshLayout.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                requestAllGoods(Api.WAIMAI_TUAN_ALL_GOODS, shopId, pageNum);
            }

            @Override
            public void onLoadmore() {
                pageNum++;
                requestAllGoods(Api.WAIMAI_TUAN_ALL_GOODS, shopId, pageNum);
            }
        });

        mRefreshLayout.setHeader(new DefaultHeader(this));
        mRefreshLayout.setFooter(new DefaultHeader(this));
        mRefreshLayout.setType(SpringView.Type.FOLLOW);
    }

    @Override
    public void onSuccess(String url, String Json) {
        try {
            Gson gson = new Gson();
            Response_Group_All_Good groupAllGood = gson.fromJson(Json, Response_Group_All_Good.class);
            if (groupAllGood.error.equals("0")) {
                List<Data_Group_Shop_Detail.DetailBean.TuanBean.ProductBean> items = groupAllGood.data.items;
                statusview.showContent();
                if (pageNum == 1) {
                    if (items.size() == 0) {
                        statusview.showEmpty();
                    } else {
                        sonAdapter.setData(items);
                    }
                } else {
                    if (items.size() == 0) {
                        ToastUtil.show("没有更多数据了");
                    } else {
                        sonAdapter.addAll(items);
                    }
                }
                mRefreshLayout.onFinishFreshAndLoad();
            } else {
                ToastUtil.show(getString(R.string.接口异常));
                statusview.showError();
            }
        } catch (Exception e) {
            statusview.showError();
            ToastUtil.show(getString(R.string.接口异常));
            saveCrashInfo2File(e);
        }

    }

    @Override
    public void onBeforeAnimate() {

    }

    @Override
    public void onErrorAnimate() {

    }
}
