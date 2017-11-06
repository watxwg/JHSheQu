package com.jhcms.tuangou.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.classic.common.MultipleStatusView;
import com.google.gson.Gson;
import com.jhcms.common.model.Group_OfferToPay_model;
import com.jhcms.common.model.Response_Group_OfferToPay;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.widget.ListViewForScrollView;
import com.jhcms.tuangou.activity.LookTuanOrderMerchantEvaluationActivity;
import com.jhcms.tuangou.activity.TuanOrderEvaluateActivity;
import com.jhcms.tuangou.activity.TuanToPayActivity;
import com.jhcms.tuangou.adapter.GroupOfferToPayAdapter;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.fragment.WaiMai_BaseFragment;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangyujie
 * Date 2017/7/14.
 * TODO 优惠买单fragment
 */

public class Group_OfferToPayFragment extends WaiMai_BaseFragment {
    @Bind(R.id.lsitview)
    ListViewForScrollView listview;
    @Bind(R.id.content_view)
    SpringView layout;
    @Bind(R.id.statusview)
    MultipleStatusView statusview;
    private int Pager = 1;
    Response_Group_OfferToPay mdatamodel;
    private GroupOfferToPayAdapter madapter;
    private ArrayList<Group_OfferToPay_model> mDataList = new ArrayList<>();

    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_group_offertopay, null);
        ButterKnife.bind(this, view);
        InintData();
        InintListview();
        InintRefreshLayout();
        return view;
    }


    private void InintListview() {
        madapter = new GroupOfferToPayAdapter(getContext(), mDataList);
        listview.setAdapter(madapter);
        madapter.setBtnOnclick(new GroupOfferToPayAdapter.BtnOnclick() {
            @Override
            public void BtnClick(String type, int postion) {
                Intent i = new Intent();
                switch (type) {
                    case "toplay":
                        i.setClass(getContext(), TuanToPayActivity.class);
                        i.putExtra("OrderId", mDataList.get(postion).getOrder_id());
                        i.putExtra("Money", mDataList.get(postion).getAmount());
                        i.putExtra("FromWhere", "Group_OfferToPayFragment");
                        startActivity(i);
                        break;
                    case "ToComment":
                        i.setClass(getContext(), TuanOrderEvaluateActivity.class);
                        i.putExtra("order_id", mDataList.get(postion).getOrder_id());
                        i.putExtra("type", "maidan");
                        i.putExtra("shop_logo", mDataList.get(postion).getShop_logo());
                        i.putExtra("shop_title", mDataList.get(postion).getShop_title());
                        startActivity(i);
                        break;
                    case "LookComment":
                        i.setClass(getContext(), LookTuanOrderMerchantEvaluationActivity.class);
                        i.putExtra("comment_id", mDataList.get(postion).getComment_id());
                        startActivity(i);
                        break;
                }
            }
        });
        //设置重试视图点击事件
        statusview.setOnRetryClickListener(v -> {
            Pager = 1;
            RequestData();
        });
    }

    private void InintRefreshLayout() {
        layout.setGive(SpringView.Give.BOTH);//摄制刷新方式
        layout.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                Pager = 1;
                RequestData();
            }

            @Override
            public void onLoadmore() {
                Pager++;
                RequestData();
            }
        });
        layout.setHeader(new DefaultHeader(getContext()));
        layout.setFooter(new DefaultFooter(getContext()));
        layout.setType(SpringView.Type.FOLLOW);
    }

    private void InintData() {
        RequestData();
    }

    @Override
    public void onResume() {
        super.onResume();
        Pager = 1;
        RequestData();
    }

    private void RequestData() {
        if (TextUtils.isEmpty(Api.TOKEN)) {
            return;
        }
        try {
            JSONObject js = new JSONObject();
            js.put("page", Pager);
            HttpUtils.postUrl(getContext(), Api.TUAN_ORDER_MAIDAN_ITEMS, js.toString(), false, new OnRequestSuccessCallback() {
                @Override
                public void onSuccess(String url, String Json) {
                    DismissDialog();
                    layout.onFinishFreshAndLoad();
                    Gson gson = new Gson();
                    try {
                        mdatamodel = gson.fromJson(Json, Response_Group_OfferToPay.class);
                        BindviewData(mdatamodel);
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtil.show("网络出现问题！");
                    }

                }

                @Override
                public void onBeforeAnimate() {
                    ShowLoadingDialog();
                    layout.callFresh();
                }

                @Override
                public void onErrorAnimate() {
                    DismissDialog();
                    layout.onFinishFreshAndLoad();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void BindviewData(Response_Group_OfferToPay mdatamodel) {
        statusview.showContent();
        if (Pager == 1) {
            mDataList.clear();
            if (mdatamodel.getData().getItems().size() == 0) {
                statusview.showEmpty();
            }
        }
        mDataList.addAll(mdatamodel.getData().getItems());
        madapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
