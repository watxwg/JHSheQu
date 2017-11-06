package com.jhcms.waimaiV3.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.google.gson.Gson;
import com.jhcms.common.model.Data_WaiMai_Comment;
import com.jhcms.common.model.Response_WaiMai_Commons;
import com.jhcms.common.model.ShopComment;
import com.jhcms.common.model.ShopCommentDetail;
import com.jhcms.common.model.ShopCommentSwitch;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.widget.GridViewForScrollView;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.WaiMaiShopActivity;
import com.jhcms.waimaiV3.adapter.CommentAdapter;
import com.jhcms.waimaiV3.adapter.CommentTypeAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.jhcms.common.utils.Utils.saveCrashInfo2File;

/**
 * Created by Wyj on 2017/5/6
 * TODO: 商家评价
 * 评价中照片显示几行
 */
public class WaiMaiShopEvaluationFragment extends CustomerBaseFragment implements OnRequestSuccessCallback {
    @Bind(R.id.rv_comment)
    LRecyclerView rvComment;
    private TextView tvScore;
    private TextView tvZonghe;
    private TextView tvFavorableRate;
    private RatingBar rbService;
    private TextView tvService;
    private RatingBar rbDistribution;
    private TextView tvDistribution;
    private GridViewForScrollView gvAllType;
    private LinearLayout llContent;
    private ImageView ivContent;


    private CommentTypeAdapter typeAdapter;
    private boolean isSelectLook = true;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private CommentAdapter commentAdapter;
    private LinearLayout headerView;
    private LinearLayoutManager layoutManager;
    private boolean isPrepared = false;
    private String shop_id;
    private int pageNum = 1;
    private Response_WaiMai_Commons waiMaiCommons;
    private Data_WaiMai_Comment data;
    private List<ShopComment> commentItems;
    private ShopCommentDetail commentDetail;
    private List<ShopCommentSwitch> commentSwitches;
    /*评价类型默认全部*/
    private int commType = 0;
    private String is_null = null;


    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_waimai_evaluation, null);
        ButterKnife.bind(this, view);
        return view;

    }


    @Override
    protected void initData() {

        /*初始化RecycleView*/
        commentAdapter = new CommentAdapter(getActivity());
        lRecyclerViewAdapter = new LRecyclerViewAdapter(commentAdapter);
        rvComment.setAdapter(lRecyclerViewAdapter);
        DividerDecoration divider = new DividerDecoration.Builder(getActivity())
                .setHeight(R.dimen.dp_1)
                .setColorResource(R.color.shopcar_line)
                .build();
        rvComment.addItemDecoration(divider);
        layoutManager = new LinearLayoutManager(getActivity());
        rvComment.setLayoutManager(layoutManager);


        /*添加头部布局*/
        headerView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.layout_evaluation_header, (ViewGroup) getActivity().findViewById(android.R.id.content), false);


        tvScore = (TextView) headerView.findViewById(R.id.tv_score);
        tvZonghe = (TextView) headerView.findViewById(R.id.tv_zonghe);
        tvFavorableRate = (TextView) headerView.findViewById(R.id.tv_favorable_rate);
        rbService = (RatingBar) headerView.findViewById(R.id.rb_service);
        tvService = (TextView) headerView.findViewById(R.id.tv_service);
        rbDistribution = (RatingBar) headerView.findViewById(R.id.rb_distribution);
        tvDistribution = (TextView) headerView.findViewById(R.id.tv_distribution);
        gvAllType = (GridViewForScrollView) headerView.findViewById(R.id.gv_allType);
        llContent = (LinearLayout) headerView.findViewById(R.id.ll_content);
        ivContent = (ImageView) headerView.findViewById(R.id.iv_content);
        ivContent.setSelected(true);
        is_null = "1";
        llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSelectLook) {
                    ivContent.setSelected(false);
                    isSelectLook = false;
                    is_null = null;
                    pageNum = 1;
                    requestComments(Api.WAIMAI_SHOP_COMMENTS, shop_id, pageNum, commType, is_null);
                } else {
                    ivContent.setSelected(true);
                    isSelectLook = true;
                    is_null = "1";
                    pageNum = 1;
                    requestComments(Api.WAIMAI_SHOP_COMMENTS, shop_id, pageNum, commType, is_null);
                }
            }
        });

        /**
         * 初始化GridView
         * */
        gvAllType.setFocusable(false);
        typeAdapter = new CommentTypeAdapter(getActivity());
        gvAllType.setAdapter(typeAdapter);

        gvAllType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pisition, long l) {
                typeAdapter.setPisition(pisition);
                commType = pisition;
                pageNum = 1;
                requestComments(Api.WAIMAI_SHOP_COMMENTS, shop_id, pageNum, commType, is_null);
            }
        });


        lRecyclerViewAdapter.addHeaderView(headerView);
        rvComment.setPullRefreshEnabled(true);
        rvComment.setLoadMoreEnabled(true);
        rvComment.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        rvComment.setHeaderViewColor(R.color.themColor, R.color.themColor, R.color.shopcar_line);
        //设置底部加载颜色
        rvComment.setFooterViewColor(R.color.themColor, R.color.themColor, R.color.white);
        //设置底部加载文字提示
        rvComment.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
        rvComment.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);//设置加载更多Progress的样式

        rvComment.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                requestComments(Api.WAIMAI_SHOP_COMMENTS, shop_id, pageNum, commType, is_null);
            }
        });

        rvComment.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                requestComments(Api.WAIMAI_SHOP_COMMENTS, shop_id, pageNum, commType, is_null);
            }
        });
        rvComment.refresh();
    }

    /**
     * shop_id	是	int	商家ID
     * page	是	int	页数
     * type	否	int	评论类型：1满意，2一般，3不满意，4有图，全部0或空
     * is_null	否	int	只看有内容的评论，有值即可
     *
     * @param api
     */
    private void requestComments(String api, String shopId, int page, int type, String is_null) {
        JSONObject object = new JSONObject();
        try {
            object.put("shop_id", shopId);
            object.put("page", page);
            object.put("type", type);
            object.put("is_null", is_null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String str = object.toString();


        HttpUtils.postUrl(getActivity(), api, str, true, this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    public void setScrollTop() {
        if (WaiMaiShopActivity.isAppBarLayoutClose()) {
            rvComment.smoothScrollToPosition(0);
            rvComment.refresh();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void setArguments(Bundle bundle) {
        super.setArguments(bundle);
        shop_id = bundle.getString(WaiMaiShopActivity.SHOP_ID);
    }

    @Override
    public void onSuccess(String url, String Json) {
        try {
            Gson gson = new Gson();
            waiMaiCommons = gson.fromJson(Json, Response_WaiMai_Commons.class);
            if (waiMaiCommons.error.equals("0")) {
                data = waiMaiCommons.data;
                commentItems = data.items;
                commentDetail = data.detail;
                commentSwitches = data.switchnav;
                if (null != commentItems) {
                    if (pageNum == 1) {
                        commentAdapter.setDataList(commentItems);
                    } else {
                        if (commentItems.size() < 10) {
                            rvComment.setNoMore(true);
                        } else {
                            commentAdapter.addAll(commentItems);
                        }
                    }
                    rvComment.refreshComplete(commentItems.size());


                    lRecyclerViewAdapter.notifyDataSetChanged();
                }

                /*好评率*/
                tvFavorableRate.setText(String.format(getString(R.string.商家好评率), commentDetail.avg_good) + "%");
                /*配送评分*/
                tvDistribution.setText(commentDetail.avg_peisong + "分");
                rbDistribution.setProgress(((int) (10 * Double.parseDouble(commentDetail.avg_peisong))));
                /*服务态度*/
                tvScore.setText(commentDetail.avg_score);
                tvService.setText(commentDetail.avg_score + "分");

                rbService.setProgress(((int) (10 * Double.parseDouble(commentDetail.avg_score))));


                /*评价类型*/
                typeAdapter.setData(commentSwitches);
            } else {
                rvComment.refreshComplete(0);
                ToastUtil.show(waiMaiCommons.message);
            }
        } catch (Exception e) {
            ToastUtil.show(getString(R.string.接口异常));
            saveCrashInfo2File(e);
        }
    }

    @Override
    public void onBeforeAnimate() {
    }

    @Override
    public void onErrorAnimate() {
        rvComment.refreshComplete(0);
    }
}
