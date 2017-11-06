package com.jhcms.waimaiV3.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.classic.common.MultipleStatusView;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.google.gson.Gson;
import com.jhcms.common.model.Data_WaiMai_Home;
import com.jhcms.common.model.Response_WaiMai_Home;
import com.jhcms.common.model.ShopItems;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.WaiMaiShopActivity;
import com.jhcms.waimaiV3.adapter.ShopItemAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.jhcms.common.utils.Utils.saveCrashInfo2File;

/**
 * Created by wangyujie
 * Date 2017/5/31.
 * TODO 搜索商家
 */

public class SearchForBusinessFragment extends CustomerBaseFragment implements OnRequestSuccessCallback {
    @Bind(R.id.rv_business)
    LRecyclerView rvBusiness;
    @Bind(R.id.statusview)
    MultipleStatusView statusview;
    private ShopItemAdapter shopItemAdapter;
    private LRecyclerViewAdapter ViewAdapter;
    private List<ShopItems> data;
    private int pageNum = 1;
    private String result;

    private boolean isPrepared = false;
    private List<ShopItems> items;
    private DividerDecoration divider;


    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_search_business, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    protected void initData() {
        shopItemAdapter = new ShopItemAdapter(getActivity());
        ViewAdapter = new LRecyclerViewAdapter(shopItemAdapter);
        divider = new DividerDecoration.Builder(getActivity())
                .setHeight(R.dimen.dp_1)
                .setColorResource(R.color.background)
                .build();
        rvBusiness.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvBusiness.setAdapter(ViewAdapter);
        rvBusiness.addItemDecoration(divider);


        rvBusiness.setHeaderViewColor(R.color.themColor, R.color.themColor, R.color.background);
        rvBusiness.setFooterViewColor(R.color.themColor, R.color.themColor, R.color.background);
        rvBusiness.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
        rvBusiness.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        rvBusiness.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);//设置加载更多Progress的样式
        rvBusiness.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                shopItemAdapter.clear();
                pageNum = 1;
                requestSearch(Api.WAIMAI_SHOP_SEARCH, result);
            }
        });
        rvBusiness.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                requestSearch(Api.WAIMAI_SHOP_SEARCH, result);
            }
        });
        rvBusiness.refresh();


        //TODO 店铺点击事件
        shopItemAdapter.setOnClickListener(new ShopItemAdapter.OnClickListener() {
            @Override
            public void onClick(String shopId) {
                if (!Utils.isFastDoubleClick()) {
                    Intent intent = new Intent(getActivity(), WaiMaiShopActivity.class);
                    intent.putExtra(WaiMaiShopActivity.SHOP_ID, shopId);
                    startActivity(intent);
                }
            }
        });

    }

    /**
     * page	    否	int	    分页码
     * title	否	string	关键字
     * type	    是	string	类型 shop商家 product商品
     *
     * @param result
     * @param api
     */
    public void requestSearch(String api, String result) {
        JSONObject object = new JSONObject();
        try {
            object.put("title", result);
            object.put("page", pageNum);
            object.put("type", "shop");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.postUrl(getActivity(), api, object.toString(), false, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        isPrepared = true;
    }

    @Override
    protected void lazyLoad() {
        if (isVisible && isPrepared) {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    public void setData(String result) {
        this.result = result;
        if (isPrepared) {
            rvBusiness.refresh();
        }
    }

    @Override
    public void onSuccess(String url, String Json) {

        try {
            Gson gson = new Gson();
            Response_WaiMai_Home response = gson.fromJson(Json, Response_WaiMai_Home.class);
            if (response.error.equals("0")) {
                Data_WaiMai_Home data = response.data;
                items = data.items;
                statusview.showContent();
                if (pageNum == 1) {
                    if (items.size() == 0) {
                        rvBusiness.setNoMore(true);
                        statusview.showEmpty();
                    } else {
                        shopItemAdapter.setDataList(items);
                    }
                } else {
                    if (items.size() == 0) {
                        rvBusiness.setNoMore(true);
                    } else {
                        shopItemAdapter.addAll(items);
                    }
                }
                rvBusiness.refreshComplete(items.size());


            } else {
                ToastUtil.show(response.message);
            }
        } catch (Exception e) {
            ToastUtil.show(getString(R.string.数据解析异常));
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
