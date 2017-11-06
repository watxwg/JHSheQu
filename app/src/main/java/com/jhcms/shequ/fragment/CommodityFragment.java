package com.jhcms.shequ.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.classic.common.MultipleStatusView;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.google.gson.Gson;
import com.jhcms.common.model.Data_Group_Shop_Goods;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.widget.RecycleViewBaseAdapter;
import com.jhcms.shequ.activity.SearchMerchantGoodsActivity;
import com.jhcms.shequ.adapter.CommodityGridViewAdapter;
import com.jhcms.shequ.adapter.CommodityListViewAdapter;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.fragment.CustomerBaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.jhcms.common.utils.Utils.saveCrashInfo2File;

/**
 * Created by Wyj on 2017/5/4.
 */
public class CommodityFragment extends CustomerBaseFragment implements OnRequestSuccessCallback {
    @Bind(R.id.content_view)
    LRecyclerView commLrecycle;
    @Bind(R.id.statusview)
    MultipleStatusView statusview;
    /*判断Activity是否创建完成*/
    private boolean isPrepared = false;
    private LRecyclerViewAdapter listViewLAdapter, gridViewLAdapter;
    private CommodityListViewAdapter listViewAdapter;
    private CommodityGridViewAdapter gridViewAdapter;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private String title, cate_id, order;
    private int area_id, business_id;
    private int pageNum = 1;


    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_commodity, null);
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

        /*初始化数据*/
        /*ListView 数据*/
        linearLayoutManager = new LinearLayoutManager(getActivity());
        listViewAdapter = new CommodityListViewAdapter(getActivity());
        listViewLAdapter = new LRecyclerViewAdapter(listViewAdapter);
        /*GridView 数据*/
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridViewAdapter = new CommodityGridViewAdapter(getActivity());
        gridViewLAdapter = new LRecyclerViewAdapter(gridViewAdapter);
        /*处理listView布局*/
        initListView();
        isPrepared = true;
        lazyLoad();
    }

    private void initListView() {
        commLrecycle.setLayoutManager(linearLayoutManager);
        commLrecycle.setAdapter(listViewLAdapter);
        /*处理刷新*/
        initRefresh(listViewLAdapter, listViewAdapter);
    }

    private void initGridView() {
        /**
         * 先设置LayoutManager-->在设置LRecyclerViewAdapter
         * */
        commLrecycle.setLayoutManager(gridLayoutManager);
        commLrecycle.setAdapter(gridViewLAdapter);
        /*处理刷新*/
        initRefresh(gridViewLAdapter, gridViewAdapter);
    }

    private void initRefresh(final LRecyclerViewAdapter mLRecyclerViewAdapter, final RecycleViewBaseAdapter baseAdapter) {
        //设置头部加载颜色
        commLrecycle.setHeaderViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载颜色
        commLrecycle.setFooterViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载文字提示
        commLrecycle.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
        commLrecycle.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        commLrecycle.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);//设置加载更多Progress的样式

        commLrecycle.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                baseAdapter.clear();
                mLRecyclerViewAdapter.notifyDataSetChanged();
                pageNum = 1;
                request(pageNum);
            }
        });
        /**
         * 加载更多
         * //禁用自动加载更多功能
         mRecyclerView.setLoadMoreEnabled(false);
         */
        commLrecycle.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                request(pageNum);
            }
        });
    }


    @Override
    protected void lazyLoad() {
        if (isVisible && isPrepared) {
            if (commLrecycle != null) {

            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void setSwitch(int aSwitch) {
        if (aSwitch == SearchMerchantGoodsActivity.SWITCH_LISTVIEW) {
            /*处理listView布局*/
            initListView();
        } else if (aSwitch == SearchMerchantGoodsActivity.SWITCH_GRIDVIEW) {
            /*处理GridView*/
            initGridView();
        }
    }


    /**
     * @param title
     * @param cate_id
     * @param area_id
     * @param business_id
     * @param order
     */
    public void setItems(String title, String cate_id, int area_id, int business_id, String order) {
        this.title = title;
        this.cate_id = cate_id;
        this.area_id = area_id;
        this.business_id = business_id;
        this.order = order;
        pageNum = 1;
        request(pageNum);
    }

    private void request(int pageNum) {
        JSONObject object = new JSONObject();
        try {
            object.put("title", title);
            object.put("cate_id", cate_id);
            object.put("area_id", area_id);
            object.put("biz_id", business_id);
            object.put("order", order);
            object.put("page", pageNum);
            HttpUtils.postUrl(getActivity(), Api.WAIMAI_TUAN_SHOP_GOODS, object.toString(), false, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(String url, String Json) {
        try {
            Gson gson = new Gson();
            Data_Group_Shop_Goods response = gson.fromJson(Json, Data_Group_Shop_Goods.class);
            if (response.error.equals("0")) {
                statusview.showContent();
                List<Data_Group_Shop_Goods.DataBean.ItemsBean> items = response.data.items;
                if (pageNum == 1) {
                    if (null != items && items.size() == 0) {
                        statusview.showEmpty();
                    } else {
                        listViewAdapter.setDataList(items);
                        gridViewAdapter.setDataList(items);
                    }
                } else {
                    if (null != items && items.size() == 0) {
                        commLrecycle.setNoMore(true);
                    } else {
                        listViewAdapter.addAll(items);
                        gridViewAdapter.addAll(items);
                    }
                }
                commLrecycle.refreshComplete(items.size());
            } else {
                commLrecycle.refreshComplete(0);
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
        if (pageNum > 1) {
            commLrecycle.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                @Override
                public void reload() {
                    commLrecycle.setNoMore(true);
                }
            });
        } else {
            commLrecycle.refreshComplete(0);
        }
    }
}
