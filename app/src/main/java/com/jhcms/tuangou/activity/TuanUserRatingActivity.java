package com.jhcms.tuangou.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.GridViewForScrollView;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;
import com.jhcms.waimaiV3.adapter.CommentAdapter;
import com.jhcms.waimaiV3.adapter.CommentTypeAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TuanUserRatingActivity extends SwipeBaseActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.gv_allType)
    GridViewForScrollView gvAllType;
    @Bind(R.id.rv_comment)
    LRecyclerView rvComment;
    /**
     * 评价列表adapter
     */
    private CommentAdapter commentAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private LinearLayoutManager layoutManager;
    /**
     * 评价分类adapter
     */
    private CommentTypeAdapter typeAdapter;
    /**
     * 评价类型
     */
    private int commType;
    /**
     * 分页
     */
    private int pageNum;

    @Override
    protected void initView() {
        setToolBar(toolbar);
        setContentView(R.layout.activity_tuan_user_rating);
        ButterKnife.bind(this);
    }


    @Override
    protected void initData() {
        tvTitle.setText(R.string.用户评价);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /**
         * TODO 初始化评价类型
         * */
        initGridView();
        /**
         * TODO 初始化评价列表
         * */
        initRecycle();
        /**
         * TODO 初始化刷新控件
         * */
        initRefresh();

        rvComment.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
//                requestComments(Api.WAIMAI_SHOP_COMMENTS, shop_id, pageNum, commType, is_null);
            }
        });

        rvComment.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
//                requestComments(Api.WAIMAI_SHOP_COMMENTS, shop_id, pageNum, commType, is_null);
            }
        });
        rvComment.refresh();

    }

    private void initRefresh() {
        rvComment.setPullRefreshEnabled(true);
        rvComment.setLoadMoreEnabled(true);
        rvComment.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        rvComment.setHeaderViewColor(R.color.themColor, R.color.themColor, R.color.shopcar_line);
        //设置底部加载颜色
        rvComment.setFooterViewColor(R.color.themColor, R.color.themColor, R.color.white);
        //设置底部加载文字提示
        rvComment.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
        rvComment.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);//设置加载更多Progress的样式
    }

    private void initRecycle() {
        commentAdapter = new CommentAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(commentAdapter);
        rvComment.setAdapter(lRecyclerViewAdapter);
        rvComment.addItemDecoration(Utils.setDivider(this, R.dimen.dp_050, R.color.qing));
        layoutManager = new LinearLayoutManager(this);
        rvComment.setLayoutManager(layoutManager);
    }

    private void initGridView() {
        gvAllType.setFocusable(false);
        typeAdapter = new CommentTypeAdapter(this);
        gvAllType.setAdapter(typeAdapter);

        gvAllType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pisition, long l) {
                typeAdapter.setPisition(pisition);
                commType = pisition;
                pageNum = 1;
//                requestComments(Api.WAIMAI_SHOP_COMMENTS, shop_id, pageNum, commType, is_null);
            }
        });
    }

}
