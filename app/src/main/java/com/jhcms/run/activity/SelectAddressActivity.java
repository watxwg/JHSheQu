package com.jhcms.run.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccess;
import com.jhcms.mall.adapter.AdAdapter;
import com.jhcms.mall.model.BaseResponse;
import com.jhcms.run.adapter.AddressSelectAdapter;
import com.jhcms.run.mode.AddressInfoModel;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectAddressActivity extends SwipeBaseActivity {
    public static final String RESUTL = "result";
    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.rv_list)
    RecyclerView rvList;
    @Bind(R.id.fm_add)
    FrameLayout fmAdd;
    private ArrayList<AddressInfoModel> addressData;
    private AddressSelectAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        requestAddressData();
    }

    private void requestAddressData() {
        HttpUtils.postUrlWithBaseResponse(this, Api.PAOTUI_GET_ADDRESS_LIST,null,true,new OnRequestSuccess<BaseResponse<List<AddressInfoModel>>>(){
            @Override
            public void onSuccess(String url, BaseResponse<List<AddressInfoModel>> data) {
                super.onSuccess(url, data);
                List<AddressInfoModel> data1 = data.getData();
                if(data1!=null&&data1.size()>0){
                    addressData.clear();
                    addressData.addAll(data1);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_select_address2);
        ButterKnife.bind(this);
        addressData = new ArrayList<AddressInfoModel>();
        adapter = new AddressSelectAdapter(this,addressData);
        rvList.setAdapter(adapter);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnItemClickListener(((addressInfoModel, position) -> {
            Intent result = new Intent();
            result.putExtra(RESUTL,addressInfoModel);
            setResult(RESULT_OK,result);
            finish();
        }));
        titleTv.setText(R.string.选择售货地址);
    }

    @OnClick({R.id.back_iv, R.id.fm_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                onBackPressed();
                break;
            case R.id.fm_add:
                break;
        }
    }
}
