package com.jhcms.waimaiV3.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.google.gson.Gson;
import com.jhcms.common.model.CollectModel;
import com.jhcms.common.model.Response_CollectModel;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.DividerListItemDecoration;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.MineCollectAdapter;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Wyj on 2017/5/9
 * TODO:
 */
public class MineCollectActivity extends SwipeBaseActivity implements OnRequestSuccessCallback {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collect_recycleView)
    LRecyclerView recycleView;
    private LRecyclerViewAdapter adapter;
    private int page = 1;
    private ArrayList<CollectModel> mDataList = new ArrayList<>();
    private MineCollectAdapter mMineCollEctAdapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                BindViewData((ArrayList<CollectModel>) msg.obj);
            }
        }
    };

    /**
     * 绑定数据
     *
     * @param DataList
     */
    private void BindViewData(ArrayList<CollectModel> DataList) {

        if (DataList != null && DataList.size() > 0 && mDataList.size() % 10 == 0 && page != 1) {
            mDataList.addAll(DataList);
        }
        if (page == 1) {
            mDataList.clear();
            mDataList.addAll(DataList);
            mMineCollEctAdapter.setmDataList(mDataList);
            recycleView.refreshComplete(mDataList.size());
        }else {
            mMineCollEctAdapter.setmDataList(DataList);
            recycleView.refreshComplete(mDataList.size());
        }
        if(mDataList.size()%10!=0){
            recycleView.setLoadMoreEnabled(false);
        }

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_mine_collect);
        ButterKnife.bind(this);
        inintRefrsh();
    }

    private void inintRefrsh() {
        //设置头部加载颜色
        recycleView.setHeaderViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载颜色
        recycleView.setFooterViewColor(R.color.themColor, R.color.themColor, R.color.background);
        //设置底部加载文字提示
        recycleView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");


        recycleView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
//        shopList.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);  //设置下拉刷新箭头
        recycleView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);//设置加载更多Progress的样式


        /**
         * 下拉刷新
         * //禁用下拉刷新功能
         mRecyclerView.setPullRefreshEnabled(false);
         */
        recycleView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
//                mMineCollEctAdapter.clear();
//                adapter.notifyDataSetChanged();
                page = 1;
                RequestData(page);
            }
        });
        /**
         * 加载更多
         * //禁用自动加载更多功能
         mRecyclerView.setLoadMoreEnabled(false);
         */
        recycleView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mDataList.size() % 10 == 0) {
                    page++;
                    RequestData(page);
                } else {
                    ToastUtil.show("没有更多数据！");
                }
            }
        });
        recycleView.refresh();
    }

    private void RequestData(int page) {
        try {
            JSONObject js = new JSONObject();
            js.put("page", page);
            String str = js.toString();
            HttpUtils.postUrl(this,Api.WAIMAI_COLLECT,str,true,this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void initData() {
        tvTitle.setText("我的收藏");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mMineCollEctAdapter = new MineCollectAdapter(this);
        mMineCollEctAdapter.setUnfriended(new MineCollectAdapter.Unfriended() {
            @Override
            public void cancel() {
                mMineCollEctAdapter.clear();
                adapter.notifyDataSetChanged();
                RequestData(page);
            }
        });
        adapter = new LRecyclerViewAdapter(mMineCollEctAdapter);
//        mMineCollEctAdapter.setmDataList(mDataList);
        recycleView.setAdapter(adapter);
        recycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DividerListItemDecoration divider = new DividerListItemDecoration.Builder(this)
                .setHeight(R.dimen.dp_10)
                .setColorResource(R.color.background)
                .build();
        recycleView.addItemDecoration(divider);
    }




    @Override
    public void onSuccess(String url, String Json) {
        Gson gson=new Gson();
        switch (url){
            case Api.WAIMAI_COLLECT:
                Response_CollectModel  mdata=gson.fromJson(Json,Response_CollectModel.class);
                if(mdata.error.equals("0")){
                    Message message=Message.obtain();
                    message.what=0;
                    message.obj=mdata.data.collect;
                    mHandler.sendMessage(message);
                }else {
                    ToastUtil.show(mdata.message);
                }
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
