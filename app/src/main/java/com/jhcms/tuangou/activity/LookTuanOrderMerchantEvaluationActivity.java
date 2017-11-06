package com.jhcms.tuangou.activity;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jhcms.common.galleryfinal.GlideImageLoader;
import com.jhcms.common.model.Response_LookTuanOrderDetailsActivity;
import com.jhcms.common.model.photoModel;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.BaseActivity;
import com.jhcms.common.widget.GridViewForScrollView;
import com.jhcms.common.widget.RoundImageView;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.PicturePreviewActivity;
import com.jhcms.waimaiV3.adapter.PhontoGrildviewAdapter;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
 * Created by admin on 2017/7/24.
 */
public class LookTuanOrderMerchantEvaluationActivity extends BaseActivity implements OnRequestSuccessCallback {
    @Bind(R.id.ratingbarId)
    RatingBar ratingbarId;
    @Bind(R.id.iv_take_photo)
    ImageView ivTakePhoto;
    @Bind(R.id.ll_take_photo)
    LinearLayout llTakePhoto;
    @Bind(R.id.rv_photo)
    RecyclerView rvPhoto;
    @Bind(R.id.photoLayout)
    LinearLayout photoLayout;
    @Bind(R.id.ShopEvaluate)
    EditText ShopEvaluate;
    @Bind(R.id.ImmediateEvaluation_tv)
    TextView ImmediateEvaluationTv;
    @Bind(R.id.Replylayout)
    LinearLayout Replylayout;
    @Bind(R.id.shopImage)
    RoundImageView shopImage;
    @Bind(R.id.ShopName)
    TextView ShopName;
    @Bind(R.id.content_text)
    TextView contentText;
    @Bind(R.id.PhonoGrild)
    GridViewForScrollView PhonoGrild;
    @Bind(R.id.shop_reply)
    TextView shopReply;
    @Bind(R.id.TopView)
    View TopView;
    @Bind(R.id.iv_close)
    ImageView ivClose;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private String comment_id;
    private String Type;//订单类型  团购 tuan 买单 maidan
    private String shop_logo;
    private String shop_title;
    private ArrayList<String> mNewPhontoDataList = new ArrayList<>();
    private ArrayList<photoModel> mPhoneDataList = new ArrayList<>();
    private PhontoGrildviewAdapter mPhotoAdapter;
    Response_LookTuanOrderDetailsActivity mDataModel;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_tuan_order_evaluate);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        inintitent();
        inintGallerFinal();
        InintPhotoData();
        RequestData();

    }

    private void InintPhotoData() {
        mPhotoAdapter = new PhontoGrildviewAdapter(mPhoneDataList, LookTuanOrderMerchantEvaluationActivity.this);
        PhonoGrild.setAdapter(mPhotoAdapter);

        mPhotoAdapter.setOnPicItemClickListener(new PhontoGrildviewAdapter.OnPicItemClickListener() {
            @Override
            public void picItemClick(View view, int position) {
                Intent intent = new Intent(LookTuanOrderMerchantEvaluationActivity.this, PicturePreviewActivity.class);
                intent.putExtra(PicturePreviewActivity.POSITION, position);
                intent.putStringArrayListExtra(PicturePreviewActivity.IMAGELIST, mNewPhontoDataList);
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(view,
                        view.getWidth() / 2, view.getHeight() / 2, 0, 0);
                ActivityCompat.startActivity(LookTuanOrderMerchantEvaluationActivity.this, intent,
                        compat.toBundle());
            }
        });
    }

    private void RequestData() {
        try {
            JSONObject js = new JSONObject();
            js.put("comment_id", comment_id);
            HttpUtils.postUrl(LookTuanOrderMerchantEvaluationActivity.this, Api.TUAN_ORDER_COMMENT_DETAIL, js.toString(), false, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void inintGallerFinal() {
        ThemeConfig theme = new ThemeConfig.Builder()
                .build();
        CoreConfig coreConfig = new CoreConfig.Builder(LookTuanOrderMerchantEvaluationActivity.this, new GlideImageLoader(), theme).build();
        GalleryFinal.init(coreConfig);
    }


    private void inintitent() {
        Intent intent = getIntent();
        if (intent != null) {
            comment_id = intent.getStringExtra("comment_id");
            ShopEvaluate.setVisibility(View.GONE);
            ImmediateEvaluationTv.setVisibility(View.GONE);
            TopView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initActionBar() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle.setText("查看评价");
    }

    @Override
    protected void initFindViewById() {

    }

    @Override
    protected void initEvent() {

    }

    private void bindViewData(Response_LookTuanOrderDetailsActivity mDataModel) {
        Utils.LoadUrlImage(LookTuanOrderMerchantEvaluationActivity.this, Api.IMAGE_URL + mDataModel.getData().getDetail().getShop_logo(), shopImage);
        ShopName.setText(mDataModel.getData().getDetail().getShop_title());
        ratingbarId.setIsIndicator(true);
        ratingbarId.setRating(Float.parseFloat(mDataModel.getData().getDetail().getScore()));
        if (mDataModel.getData().getDetail().getPhotos() != null && mDataModel.getData().getDetail().getPhotos().size() > 0) {
            mPhoneDataList.addAll(mDataModel.getData().getDetail().getPhotos());
            mPhotoAdapter.notifyDataSetChanged();
            for (int i = 0; i < mPhoneDataList.size(); i++) {
                mNewPhontoDataList.add(mPhoneDataList.get(i).getPhoto());
            }
        }
        if (mDataModel.getData().getDetail().getContent() != null) {
            contentText.setVisibility(View.VISIBLE);
            contentText.setText(mDataModel.getData().getDetail().getContent());
        }
        if (mDataModel.getData().getDetail().getReply() != null && mDataModel.getData().getDetail().getReply().length() > 0) {
            shopReply.setVisibility(View.VISIBLE);
            shopReply.setText(mDataModel.getData().getDetail().getReply());
        }


    }

    @Override
    public void onSuccess(String url, String Json) {
        dismiss(LookTuanOrderMerchantEvaluationActivity.this);
        Gson gson = new Gson();
        try {
            mDataModel = gson.fromJson(Json, Response_LookTuanOrderDetailsActivity.class);
            if (mDataModel.error.equals("0")) {
                bindViewData(mDataModel);
            } else {
                ToastUtil.show(mDataModel.message);
            }
        } catch (Exception e) {
            ToastUtil.show("网络出现异常！");

        }
    }


    @Override
    public void onBeforeAnimate() {
        showProgressDialog(LookTuanOrderMerchantEvaluationActivity.this);
    }

    @Override
    public void onErrorAnimate() {
        dismiss(LookTuanOrderMerchantEvaluationActivity.this);
    }

}
