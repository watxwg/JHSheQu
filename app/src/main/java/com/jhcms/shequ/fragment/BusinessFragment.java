package com.jhcms.shequ.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.jhcms.common.model.Data_Group_Shop_Items;
import com.jhcms.common.model.Response_Group_Shop_Items;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.tuangou.adapter.GroupShopItemAdapter;
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
public class BusinessFragment extends CustomerBaseFragment implements OnRequestSuccessCallback {
    @Bind(R.id.content_view)
    LRecyclerView businessLrecycle;
    @Bind(R.id.statusview)
    MultipleStatusView statusview;
    /*判断Activity是否创建完成*/
    private boolean isPrepared = false;
    private LRecyclerViewAdapter listViewLAdapter;
    private GroupShopItemAdapter listViewAdapter;
    private LinearLayoutManager linearLayoutManager;

    private int pageNum = 1;
    private String title, cate_id, order;
    private int area_id, business_id;




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
        listViewAdapter = new GroupShopItemAdapter(getActivity());
        listViewLAdapter = new LRecyclerViewAdapter(listViewAdapter);

        /*处理listView布局*/
        initListView();
        isPrepared = true;
        lazyLoad();

    }

    private void initListView() {
        businessLrecycle.setLayoutManager(linearLayoutManager);
        businessLrecycle.setAdapter(listViewLAdapter);
        businessLrecycle.addItemDecoration(Utils.setDivider(getActivity(), R.dimen.dp_5, R.color.background));
        /*处理刷新*/
        initRefresh();
    }

    private void initRefresh() {
        //设置头部加载颜色
        businessLrecycle.setHeaderViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载颜色
        businessLrecycle.setFooterViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载文字提示
        businessLrecycle.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
        businessLrecycle.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        businessLrecycle.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);//设置加载更多Progress的样式
        businessLrecycle.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                request(pageNum);
            }
        });
        /**
         * 加载更多
         * //禁用自动加载更多功能
         mRecyclerView.setLoadMoreEnabled(false);
         */
        businessLrecycle.setOnLoadMoreListener(new OnLoadMoreListener() {
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
            if (businessLrecycle != null) {
            }
        }
    }

    public void setItems(String title, String cate_id, int area_id, int business_id, String order) {
        this.title = title;
        this.cate_id = cate_id;
        this.area_id = area_id;
        this.business_id = business_id;
        this.order = order;
        pageNum = 1;
        request(1);
    }

    private void request(int pageNum) {
        JSONObject object = new JSONObject();
        try {
            object.put("lat", Api.LAT);
            object.put("lng", Api.LON);
            object.put("title", title);
            object.put("cate_id", cate_id);
            object.put("area_id", area_id);
            object.put("biz_id", business_id);
            object.put("order", order);
            object.put("page", pageNum);
            HttpUtils.postUrl(getActivity(), Api.WAIMAI_TUAN_SHOP_ITEMS, object.toString(), true, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
            Response_Group_Shop_Items response = gson.fromJson(Json, Response_Group_Shop_Items.class);
            if (response.error.equals("0")) {
                statusview.showContent();
                List<Data_Group_Shop_Items.ItemsBean> items = response.data.items;
                if (pageNum == 1) {
                    if (null != items && items.size() == 0) {
                        statusview.showEmpty();
                    } else {
                        listViewAdapter.setDataList(items);
                    }
                } else {
                    if (null != items && items.size() == 0) {
                        businessLrecycle.setNoMore(true);
                    } else {
                        listViewAdapter.addAll(items);
                    }
                }
                businessLrecycle.refreshComplete(items.size());
            } else {
                businessLrecycle.refreshComplete(0);
                ToastUtil.show(response.message);
            }
        } catch (Exception e) {
            businessLrecycle.refreshComplete(0);
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
            businessLrecycle.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                @Override
                public void reload() {
                    businessLrecycle.setNoMore(true);
                }
            });
        } else {
            businessLrecycle.refreshComplete(0);
        }
    }
}
