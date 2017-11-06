package com.jhcms.waimaiV3.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.classic.common.MultipleStatusView;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.WaiMaiBusinessListActivity;
import com.jhcms.waimaiV3.activity.WaiMaiShopActivity;
import com.jhcms.waimaiV3.adapter.ShopCarAdapter;
import com.jhcms.waimaiV3.dialog.CallDialog;
import com.jhcms.waimaiV3.litepal.Shop;
import com.jhcms.waimaiV3.model.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Wyj on 2017/3/22.
 */
public class WaiMai_ShopCarFragment extends WaiMai_BaseFragment {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.content_view)
    LRecyclerView shopcarList;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.multiplestatusview)
    MultipleStatusView statusview;
    private ShopCarAdapter shopCarAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    public List<Shop> mDestList;

    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_waimai_shopcard, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        setToolBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> getActivity().finish());
        tvTitle.setText("购物车");
        /*初始化购物车列表*/
        initShopCartList();
        /*初始化刷新*/
        initRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        shopcarList.refresh();
    }

    private void initShopCartList() {
        shopCarAdapter = new ShopCarAdapter(getActivity());
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(shopCarAdapter);
        shopcarList.setNestedScrollingEnabled(false);
        shopcarList.setAdapter(mLRecyclerViewAdapter);
        shopcarList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        DividerDecoration divider = new DividerDecoration.Builder(getActivity())
                .setHeight(R.dimen.dp_10)
                .setColorResource(R.color.background)
                .build();
        shopcarList.addItemDecoration(divider);
        shopCarAdapter.setOnShopItemClickListener(new ShopCarAdapter.OnShopItemClickListener() {
            /**
             * 进入店铺
             * @param v
             * @param position
             */
            @Override
            public void goShop(View v, int position) {
                if (!Utils.isFastDoubleClick()) {
                    Intent intent = new Intent(mContext, WaiMaiShopActivity.class);
                    intent.putExtra(WaiMaiShopActivity.SHOP_ID, mDestList.get(position).shop_id);
                    intent.putExtra(WaiMaiShopActivity.PRODUCT_INFO, mDestList.get(position).getProduct_info());
                    mContext.startActivity(intent);
                }
            }

            /**
             * 删除购物车
             * @param v
             * @param position
             */
            @Override
            public void deteleComm(View v, final int position) {
                new CallDialog(getActivity())
                        .setMessage("是否删除该店铺商品")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("删除", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DataSupport.deleteAll(Shop.class, "shop_id = ? ", mDestList.get(position).getShop_id());
                                shopcarList.refresh();
                                EventBus.getDefault().post(new MessageEvent(WaiMai_HomeFragment.REFRESH_SHOPITEM));
                            }
                        })
                        .show();
            }
        });
        statusview.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 去逛逛跳到全部上商家分类
                 * */
                startActivity(new Intent(getActivity(), WaiMaiBusinessListActivity.class));
            }
        });
    }


    private void initRefresh() {
        //设置头部加载颜色
        shopcarList.setHeaderViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载颜色
        shopcarList.setFooterViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载文字提示
        shopcarList.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");

        shopcarList.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        shopcarList.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);//设置加载更多Progress的样式
        shopcarList.setOnRefreshListener(new OnRefreshListener() {


            @Override
            public void onRefresh() {
                mDestList = DataSupport.findAll(Shop.class);

                if (null != mDestList && mDestList.size() == 0) {
                    statusview.showEmpty();
                } else {
                    statusview.showContent();
                }
                shopCarAdapter.clear();
                mLRecyclerViewAdapter.notifyDataSetChanged();
                shopCarAdapter.setDataList(mDestList);
                shopcarList.refreshComplete(mDestList.size());
            }
        });
        /**
         * 加载更多
         * //禁用自动加载更多功能
         */
        shopcarList.setLoadMoreEnabled(false);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
