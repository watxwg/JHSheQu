package com.jhcms.run.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccess;
import com.jhcms.mall.adapter.CommonAdapter;
import com.jhcms.mall.adapter.viewholder.CommonViewHolder;
import com.jhcms.mall.model.BaseResponse;
import com.jhcms.run.mode.ChargeExplainModel;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChargeExplainActivity extends SwipeBaseActivity {
    private static final String RUN_PRICE_PARAM = "runPrice";
    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.tv_run_price)
    TextView tvRunPrice;
    @Bind(R.id.rv_licheng)
    RecyclerView rvLicheng;
    @Bind(R.id.rv_night)
    RecyclerView rvNight;
    private List<String> lichengData;
    private List<String> nightData;
    private ChargeExplainAdapter lichengAdapter;
    private ChargeExplainAdapter nightAdapter;

    /**
     *
     * @param context
     * @param runPrice 跑腿费
     * @return
     */
    public static Intent generateIntent(Context context, String runPrice){
        Intent intent = new Intent(context,ChargeExplainActivity.class);
        intent.putExtra(RUN_PRICE_PARAM,runPrice);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        requestData();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_charge_explain);
        ButterKnife.bind(this);
        String s = getIntent().getStringExtra(RUN_PRICE_PARAM);
        tvRunPrice.setText(getString(R.string.mall_¥f,s));
        titleTv.setText(R.string.费用说明);
        lichengData=new ArrayList<>();
        nightData=new ArrayList<>();
        lichengAdapter = new ChargeExplainAdapter(this,lichengData);
        nightAdapter = new ChargeExplainAdapter(this,nightData);
        rvLicheng.setAdapter(lichengAdapter);
        rvLicheng.setLayoutManager(new LinearLayoutManager(this));
        rvNight.setAdapter(nightAdapter);
        rvNight.setLayoutManager(new LinearLayoutManager(this));
    }

    private void requestData() {
        HttpUtils.postUrlWithBaseResponse(this, Api.PAOTUI_CHARGE_EXPLAIN,null,true,new OnRequestSuccess<BaseResponse<ChargeExplainModel>>(){
            @Override
            public void onSuccess(String url, BaseResponse<ChargeExplainModel> data) {
                super.onSuccess(url, data);
                ChargeExplainModel explainModel = data.getData();
                if(explainModel.getDistance()!=null&&explainModel.getDistance().size()>0){
                    lichengData.clear();
                    lichengData.addAll(explainModel.getDistance());
                    lichengAdapter.notifyDataSetChanged();
                }
                if(explainModel.getTime()!=null&&explainModel.getTime().size()>0){
                    nightData.clear();
                    nightData.addAll(explainModel.getTime());
                    nightAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        onBackPressed();
    }

    private static class ChargeExplainAdapter extends CommonAdapter<String>{

        public ChargeExplainAdapter(Context context, List<String> data) {
            super(context, data, R.layout.adapter_charge_explain_item);
        }

        @Override
        public void convertViewData(CommonViewHolder holder, String bean) {
            holder.setTextViewText(R.id.tv_name,bean);
        }
    }
}
