package com.jhcms.waimaiV3.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.classic.common.MultipleStatusView;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.google.gson.Gson;
import com.jhcms.common.model.Data_WaiMai_SearchGood;
import com.jhcms.common.model.Response_WaiMai_SearchGood;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.WaiMaiShopActivity;
import com.jhcms.waimaiV3.adapter.SearchGoodsGridAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.jhcms.common.utils.Utils.saveCrashInfo2File;

/**
 * Created by wangyujie
 * Date 2017/5/31.
 * TODO 搜索商品
 */

public class SearchForGoodsFragment extends CustomerBaseFragment implements OnRequestSuccessCallback {
    @Bind(R.id.rv_goods)
    LRecyclerView rvGoods;
    @Bind(R.id.statusview)
    MultipleStatusView statusview;

    private boolean isPrepared = false;
    LRecyclerViewAdapter viewAdapter;
    private SearchGoodsGridAdapter adapter;
    private String result;
    private int pageNum = 1;
    private List<Data_WaiMai_SearchGood.ItemsEntity> items;


    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_search_goods, null);
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
        adapter = new SearchGoodsGridAdapter(getActivity());
        viewAdapter = new LRecyclerViewAdapter(adapter);
        rvGoods.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvGoods.setAdapter(viewAdapter);
        rvGoods.setHeaderViewColor(R.color.themColor, R.color.themColor, R.color.background);
        rvGoods.setFooterViewColor(R.color.themColor, R.color.themColor, R.color.background);
        rvGoods.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
        rvGoods.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        rvGoods.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);//设置加载更多Progress的样式
        rvGoods.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                pageNum = 1;
                requestSearch(Api.WAIMAI_SHOP_SEARCH, result);
            }
        });
        rvGoods.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                requestSearch(Api.WAIMAI_SHOP_SEARCH, result);
            }
        });
        rvGoods.refresh();
        adapter.setOnShopItemClickListener(new SearchGoodsGridAdapter.OnGoodItemClickListener() {
            @Override
            public void onGoodItem(String shop_id) {
                if (!Utils.isFastDoubleClick()) {
                    if (!Utils.isFastDoubleClick()) {
                        Intent intent = new Intent(getActivity(), WaiMaiShopActivity.class);
                        intent.putExtra(WaiMaiShopActivity.SHOP_ID, shop_id);
                        getActivity().startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if (isVisible && isPrepared) {

        }
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
            object.put("type", "product");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.postUrl(getActivity(),api, object.toString(), true, this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onSuccess(String url, String Json) {
        try {
            Gson gson = new Gson();
            Response_WaiMai_SearchGood searchGood = gson.fromJson(Json, Response_WaiMai_SearchGood.class);
            if (searchGood.error.equals("0")) {
                Data_WaiMai_SearchGood data = searchGood.data;
                items = data.items;
                statusview.showContent();
                if (pageNum == 1) {
                    if (items.size() == 0) {
                        rvGoods.setNoMore(true);
                        statusview.showEmpty();
                    } else {
                        adapter.setData(items);
                    }
                } else {
                    if (items.size() == 0) {
                        rvGoods.setNoMore(true);
                    } else {
                        adapter.addAll(items);
                    }
                }

                rvGoods.refreshComplete(items.size());
            } else {
                ToastUtil.show(searchGood.message);
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

    public void setData(String result) {
        this.result = result;
        if (isPrepared) {
            rvGoods.refresh();
        }
    }
}
