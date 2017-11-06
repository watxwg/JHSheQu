package com.jhcms.tuangou.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.classic.common.MultipleStatusView;
import com.google.gson.Gson;
import com.jhcms.common.model.Data;
import com.jhcms.common.model.Data_Group_Good_Detail;
import com.jhcms.common.model.Response;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;
import com.jhcms.waimaiV3.activity.ToPayNewActivity;
import com.orhanobut.hawk.Hawk;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jhcms.common.utils.Utils.saveCrashInfo2File;
import static com.jhcms.tuangou.activity.GraphicDetailsActivity.DETAIL;

public class TuanConfirmOrderActivity extends SwipeBaseActivity implements OnRequestSuccessCallback {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_confirm_name)
    TextView tvConfirmName;
    @Bind(R.id.tv_confirm_price)
    TextView tvConfirmPrice;
    @Bind(R.id.iv_confirm_jian)
    ImageView ivConfirmJian;
    @Bind(R.id.tv_confirm_num)
    TextView tvConfirmNum;
    @Bind(R.id.iv_confirm_jia)
    ImageView ivConfirmJia;
    @Bind(R.id.tv_confirm_subtotal)
    TextView tvConfirmSubtotal;
    @Bind(R.id.tv_confirm_total_price)
    TextView tvConfirmTotalPrice;
    @Bind(R.id.tv_confirm_phone)
    TextView tvConfirmPhone;
    @Bind(R.id.statusview)
    MultipleStatusView statusview;
    private double price;
    private Data_Group_Good_Detail.DetailBean details;
    private int confirmNum = 1;
    private NumberFormat nf;

    @Override
    protected void initData() {
        setToolBar(toolbar);
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        details = (Data_Group_Good_Detail.DetailBean) getIntent().getSerializableExtra(DETAIL);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle.setText(R.string.确认订单);
        tvConfirmName.setText(details.title);
        tvConfirmPrice.setText(nf.format(Double.valueOf(details.price)));
        confirmNum = details.min_buy;
        dealWithPrices(confirmNum);


        Data user = Hawk.get("user");
        if (null != user) {
            tvConfirmPhone.setText(Utils.dealWithPhone(user.mobile));
        }
        statusview.showContent();
    }


    private void dealWithPrices(int num) {
        tvConfirmNum.setText(String.valueOf(num));

        price = Double.valueOf(details.price) * num;
        /*小计*/
        tvConfirmSubtotal.setText(nf.format(price));
        /*订单总价*/
        tvConfirmTotalPrice.setText(nf.format(price));
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_tuan_confirm_order);
        ButterKnife.bind(this);
        statusview.showLoading();
    }

    @OnClick({R.id.iv_confirm_jian, R.id.iv_confirm_jia, R.id.tv_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_confirm_jian:
                if (confirmNum <= details.min_buy) {
                    ToastUtil.show("不能低于最小购买数量");
                    return;
                }
                confirmNum--;
                dealWithPrices(confirmNum);
                break;
            case R.id.iv_confirm_jia:
                if (confirmNum >= details.max_buy) {
                    ToastUtil.show("不能超过最大购买数");
                    return;
                }
                confirmNum++;
                dealWithPrices(confirmNum);
                break;
            case R.id.tv_pay:
                if (!Utils.isFastDoubleClick()) {
                    submit();
                }
                break;

        }
    }

    /**
     * tuan_id	是	int	团购ID
     * nums	    是	int	数量
     */
    private void submit() {
        JSONObject object = new JSONObject();
        try {
            object.put("tuan_id", details.tuan_id);
            object.put("nums", confirmNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.postUrl(this, Api.WAIMAI_TUAN_SHOP_CREATE, object.toString(), true, this);

    }

    @Override
    public void onSuccess(String url, String Json) {
        try {
            Gson gson = new Gson();
            Response response = gson.fromJson(Json, Response.class);
            if (response.error.equals("0")) {
                Intent intent = new Intent(this, ToPayNewActivity.class);
                intent.putExtra(ToPayNewActivity.ORDER_ID, response.data.order_id);
                intent.putExtra(ToPayNewActivity.MONEY, price);
                intent.putExtra(ToPayNewActivity.FROM, ToPayNewActivity.TO_GROUP);
                startActivity(intent);
                finish();
            } else {
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

    }
}
