package com.jhcms.tuangou.activity;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jhcms.common.stickylistheaders.StickyListHeadersListView;
import com.jhcms.common.widget.BaseActivity;
import com.jhcms.tuangou.adapter.MainAdapter;
import com.jhcms.tuangou.adapter.SortLeftAdapter;
import com.jhcms.tuangou.model.listmode;
import com.jhcms.waimaiV3.R;

import java.util.ArrayList;


public class TuanSortAll extends BaseActivity {
    private ArrayList<String> mSortData = new ArrayList<>();
    private ListView mLvLeftlist;
    private SortLeftAdapter mSortleftAdapter;
    private int checkId;
    ArrayList<listmode> bodyList = new ArrayList<>();
    private MainAdapter mRightAdapter;
    private StickyListHeadersListView rightlv;
    private MyOnGoodsScrollListener myOnGoodsScrollListener;
    private View view;
    private ImageView mIvBack;
    private TextView mTvTitle;
    @Override
    protected void initView() {
        setContentView(R.layout.tuan_activity_sort_all);
    }

    @Override
    protected void initData() {
        inintLeftdata();
        inintrightAdpter();
    }

    private void inintrightAdpter() {
        for (int i = 0; i < mSortData.size(); i++) {
            ArrayList<String> list = new ArrayList<>();
            listmode listmode = new listmode();
            for (int j = 0; j < 4; j++) {
                list.add("item" + j);
            }
            listmode.setList(list);
            bodyList.add(listmode);
        }
        mRightAdapter = new MainAdapter(TuanSortAll.this);
        mRightAdapter.setHeadList(mSortData);
        mRightAdapter.setBodyList(bodyList);
        rightlv.setAdapter(mRightAdapter);
        mLvLeftlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rightlv.setSelection(position);
            }
        });
    }

    private void inintLeftdata() {
        for (int i = 0; i < 10; i++) {
            mSortData.add("item" + i);
        }
        mSortleftAdapter = new SortLeftAdapter(TuanSortAll.this, mSortData);
        mSortleftAdapter.setCheckId(0);
        mLvLeftlist.setAdapter(mSortleftAdapter);
        mLvLeftlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSortleftAdapter.setCheckId(position);
                mSortleftAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initActionBar() {
        mIvBack = (ImageView) view.findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TuanSortAll.this.finish();
            }
        });
        mTvTitle= (TextView) view.findViewById(R.id.title_content);
        mTvTitle.setText("分类");

    }

    @Override
    protected void initFindViewById() {
        mLvLeftlist = (ListView) this.findViewById(R.id.goodsCateGoryList);
        rightlv = (StickyListHeadersListView) this.findViewById(R.id.goodsList);
        myOnGoodsScrollListener = new MyOnGoodsScrollListener();
        rightlv.setOnScrollListener(myOnGoodsScrollListener);
        view = this.findViewById(R.id.title_layout);


    }

    @Override
    protected void initEvent() {

    }

    /**
     * 处理滑动 是两个ListView联动
     */
    private class MyOnGoodsScrollListener implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            mSortleftAdapter.setCheckId(firstVisibleItem);
            mSortleftAdapter.notifyDataSetChanged();

        }
    }

}
